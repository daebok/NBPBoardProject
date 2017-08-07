package com.hyunhye.naver.captcha.service;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;

import com.hyunhye.naver.captcha.repository.NaverCaptchaRepository;

public class NaverCaptchaService {

	@Autowired
	private NaverCaptchaRepository naverCaptchaRepository;

	public String getCaptchaKey() {
		try {
			String captchaKey = naverCaptchaRepository.getCaptchaKey();
			return captchaKey;
		} catch (IOException e) {

		}
		return null;
	}

	public String getCaptchaImageUrl(String captchaKey) {
		String captchaUrl = naverCaptchaRepository.getCaptchaImageUrl(captchaKey);
		return captchaUrl;
	}

	public boolean isValidCaptcha(String captchaKey, String captchaInput) {

		try {
			boolean result = naverCaptchaRepository.isValidCaptcha(captchaKey, captchaInput);
			return result;
		} catch (URISyntaxException | IOException e) {
			return false;
		}
	}

}
