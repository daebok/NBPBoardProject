package com.hyunhye.naver.ouath.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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
import com.hyunhye.naver.ouath.model.NaverLoginCode;
import com.hyunhye.naver.ouath.model.NaverUser;

@Service
public class NaverLoginService {
	private static final String naverLoginropertiesFile = "classpath:config/properties/naver-login.properties";
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


	/**
	 * 인증 URL 생성
	 * @param session
	 * @return client_id와 cliend_secret으로 생성된 url
	 */
	public String getAuthorizationUrl(HttpSession session) {

		/* 세션 유효성 검증을 위하여 난수를 생성 */
		String state = UUID.randomUUID().toString();

		/* 생성한 난수 값을 session에 저장 */
		session.setAttribute(SESSION_STATE, state);

		OAuth20Service oauthService = getOauthService(state);

		return oauthService.getAuthorizationUrl();
	}

	/**
	 * 네아로 Callback 처리 및  AccessToken 획득 Method
	 * @param session
	 * @param code
	 * @param state
	 * @return
	 * @throws IOException
	 */
	public OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) throws IOException {

		/* Callback으로 전달받은 세선검증용 난수값과 세션에 저장되어있는 값이 일치하는지 확인 */
		String sessionState = (String)session.getAttribute(SESSION_STATE);

		if (StringUtils.pathEquals(sessionState, state)) {
			OAuth20Service oauthService = getOauthService(state);

			/* Scribe에서 제공하는 AccessToken 획득 기능으로 네아로 Access Token을 획득 */
			OAuth2AccessToken accessToken = oauthService.getAccessToken(code);

			return accessToken;
		}
		return null;
	}

	/**
	 * 인증 된 사용자의 프로필 가져오기
	 * @param oauthToken
	 * @return
	 * @throws IOException
	 */
	public NaverUser getUserProfile(OAuth2AccessToken oauthToken) throws IOException {
		return getUserProfile(oauthToken, false);
	}

	public NaverUser getUserProfile(OAuth2AccessToken oauthToken, boolean isFinalRequest) throws IOException {

		/* 사용자 프로필 정보 요청하기 */
		Response response = sendProfileApiRequest(oauthToken);
		int responseCode = response.getCode();

		/* 성공적인 응답시 */
		if (isSuccessResponse(responseCode)) {
			return getNaverUser(response.getBody());
		}

		/* refreshToken이 필요할 시 */
		if (needRefresh(responseCode, isFinalRequest)) {
			OAuth2AccessToken refreshedToken = refreshAccessToken(oauthToken);
			return getUserProfile(refreshedToken, true);
		}

		return null;
	}

	private Response sendProfileApiRequest(OAuth2AccessToken oauthToken) {
		OAuth20Service oauthService = getOauthService(null);
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


	private OAuth2AccessToken refreshAccessToken(OAuth2AccessToken oauthToken) throws IOException {
		OAuth20Service oauthService = getOauthService(null);
		return oauthService.refreshAccessToken(oauthToken.getRefreshToken());
	}

	private NaverUser getNaverUser(String profileResponse)
		throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		NaverLoginCode naverProfileResponse = null;

		naverProfileResponse = mapper.readValue(profileResponse, NaverLoginCode.class);

		if (naverProfileResponse != null) {
			return naverProfileResponse.getResponse();
		}
		return null;
	}

}
