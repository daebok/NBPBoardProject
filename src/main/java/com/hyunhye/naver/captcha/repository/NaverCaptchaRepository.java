package com.hyunhye.naver.captcha.repository;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class NaverCaptchaRepository {
	private static final String CLIENT_ID = "NNj7dSxNfBX35e9x8k3R";
	private static final String CLIENT_SECRET = "jcMBUQVYT4";
	private static final String CAPTCHA_API_URL = "https://openapi.naver.com/v1/captcha/nkey";
	private static final String CAPTCHA_IMAGE_URL = "https://openapi.naver.com/v1/captcha/ncaptcha.bin";

	public String getCaptchaKey() throws IOException {
		String resultContent = getCaptchaApiResponse(CAPTCHA_API_URL);
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> resultMap;
		resultMap = objectMapper.readValue(resultContent, new TypeReference<Object>() {});
		return (String)resultMap.get("key");
	}

	public String getCaptchaImageUrl(String captchaKey) {
		StringBuilder sb = new StringBuilder(CAPTCHA_IMAGE_URL);
		sb.append("?key=").append(captchaKey);
		return sb.toString();
	}

	public boolean isValidCaptcha(String captchaKey, String captchaInput) throws URISyntaxException, IOException {
		URI uri = new URIBuilder(CAPTCHA_API_URL)
			.addParameter("value", captchaInput)
			.addParameter("key", captchaKey)
			.addParameter("code", "1").build();
		String resultContent = getCaptchaApiResponse(uri.toString());
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> resultMap;
		resultMap = objectMapper.readValue(resultContent, new TypeReference<Object>() {});
		boolean checkResult = (boolean)resultMap.get("result");
		return checkResult;
	}

	private String getCaptchaApiResponse(String url) throws IOException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		request.addHeader("X-Naver-Client-Id", CLIENT_ID);
		request.addHeader("X-Naver-Client-Secret", CLIENT_SECRET);
		HttpResponse response = client.execute(request);
		HttpEntity responseEntity = response.getEntity();
		String resultContent = EntityUtils.toString(responseEntity);
		return resultContent;
	}
}
