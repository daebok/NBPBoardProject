package com.hyunhye.user.service;

import javax.annotation.Resource;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("shaEncoder")
public class ShaEncoder {

	@Resource(name = "passwordEncoder")
	private ShaPasswordEncoder encoder;

	public String encoding(String password) {
		return encoder.encodePassword(password, null);
	}

	public String saltEncoding(String password, String salt) {
		return encoder.encodePassword(password, salt);
	}
}