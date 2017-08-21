package com.hyunhye.naver.ouath.model;

import lombok.Data;

@Data
public class NaverLoginCode {

	/** 요청 코드 **/
	private String resultcode;
	/** 메시지  **/
	private String message;
	/** 응답  **/
	private NaverUser response;

}
