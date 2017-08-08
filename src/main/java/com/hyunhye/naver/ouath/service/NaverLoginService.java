package com.hyunhye.naver.ouath.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.hyunhye.naver.ouath.model.NaverLoginApi;
import com.hyunhye.naver.ouath.model.NaverProfile;
import com.hyunhye.naver.ouath.model.NaverUser;

@Service
public class NaverLoginService {
	Logger logger = LoggerFactory.getLogger(NaverLoginService.class);
	private static final String naverLoginropertiesFile = "classpath:config/naver-login.properties";
	private static String CLIENT_ID;
	private static String CLIENT_SECRET;
	private static String REDIRECT_URI;
	private static String SESSION_STATE;
	private static String PROFILE_API_URL;

	static {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(ResourceUtils.getFile(naverLoginropertiesFile)));
			CLIENT_ID = new String(properties.getProperty("CLIENT_ID").getBytes("ISO-8859-1"), "UTF-8");
			CLIENT_SECRET = new String(properties.getProperty("CLIENT_SECRET").getBytes("ISO-8859-1"), "UTF-8");
			REDIRECT_URI = new String(properties.getProperty("REDIRECT_URI").getBytes("ISO-8859-1"), "UTF-8");
			SESSION_STATE = new String(properties.getProperty("SESSION_STATE").getBytes("ISO-8859-1"), "UTF-8");
			PROFILE_API_URL = new String(properties.getProperty("PROFILE_API_URL").getBytes("ISO-8859-1"), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public String getAuthorizationUrl(HttpSession session) {

		/* 세션 유효성 검증을 위하여 난수를 생성 */
		String state = generateRandomString();

		/* 생성한 난수 값을 session에 저장 */
		setSession(session, state);

		OAuth20Service oauthService = getOauthService(state);

		return oauthService.getAuthorizationUrl();
	}

	/* 네아로 Callback 처리 및  AccessToken 획득 Method */
	public OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) throws IOException {

		/* Callback으로 전달받은 세선검증용 난수값과 세션에 저장되어있는 값이 일치하는지 확인 */
		String sessionState = getSession(session);

		if (StringUtils.pathEquals(sessionState, state)) {
			OAuth20Service oauthService = getOauthService(state);

			/* Scribe에서 제공하는 AccessToken 획득 기능으로 네아로 Access Token을 획득 */
			OAuth2AccessToken accessToken = oauthService.getAccessToken(code);

			return accessToken;
		}
		return null;
	}

	public NaverUser getUserProfile(OAuth2AccessToken oauthToken) throws IOException {
		return getUserProfile(oauthToken, false);
	}

	public NaverUser getUserProfile(OAuth2AccessToken oauthToken, boolean isFinalRequest) throws IOException {

		Response response = sendProfileApiRequest(oauthToken);
		int responseCode = response.getCode();

		if (isSuccessResponse(responseCode)) {
			return getNaverUser(response.getBody());
		}

		if (needRefresh(responseCode, isFinalRequest)) {
			OAuth2AccessToken refreshedToken = refreshAccessToken(oauthToken);
			return getUserProfile(refreshedToken, true);
		}

		return null;
	}

	private Response sendProfileApiRequest(OAuth2AccessToken oauthToken) {
		OAuth20Service oauthService = getOauthService();
		OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_API_URL, oauthService);
		oauthService.signRequest(oauthToken, request);
		Response response = request.send();
		return response;
	}

	private boolean isSuccessResponse(int responseCode) {
		return (responseCode == 200);
	}

	private boolean needRefresh(int responseCode, boolean isFinalRequest) {
		return (responseCode == 401 && !isFinalRequest);
	}

	private String generateRandomString() {
		return UUID.randomUUID().toString();
	}

	private void setSession(HttpSession session, String state) {
		session.setAttribute(SESSION_STATE, state);
	}

	private String getSession(HttpSession session) {
		return (String)session.getAttribute(SESSION_STATE);
	}

	private OAuth20Service getOauthService(String state) {
		ServiceBuilder serviceBuilder = new ServiceBuilder()
			.apiKey(CLIENT_ID)
			.apiSecret(CLIENT_SECRET)
			.callback(REDIRECT_URI);

		if (!StringUtils.isEmpty(state)) {
			serviceBuilder = serviceBuilder.state(state);
		}

		return serviceBuilder.build(NaverLoginApi.instance());
	}

	private OAuth20Service getOauthService() {
		return getOauthService(null);
	}

	private OAuth2AccessToken refreshAccessToken(OAuth2AccessToken oauthToken) throws IOException {
		OAuth20Service oauthService = getOauthService();
		return oauthService.refreshAccessToken(oauthToken.getRefreshToken());
	}

	private NaverUser getNaverUser(String profileResponse)
		throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		NaverProfile naverProfileResponse = null;

		naverProfileResponse = mapper.readValue(profileResponse, NaverProfile.class);

		if (naverProfileResponse != null) {
			return naverProfileResponse.getResponse();
		}
		return null;
	}

}
