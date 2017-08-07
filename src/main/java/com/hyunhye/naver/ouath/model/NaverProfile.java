package com.hyunhye.naver.ouath.model;

import lombok.Data;

@Data
public class NaverProfile {

	private String resultcode;
	private String message;
	private NaverUser response;

}
