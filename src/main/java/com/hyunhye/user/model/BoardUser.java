package com.hyunhye.user.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

/**
 * 사용자 정보
 * @author NAVER
 *
 */
@Data
@Alias("user")
public class BoardUser {
	/* 사용자 번호 */
	private int userNo;
	/* 사용자 아이디 */
	private String userId;
	/* 사용자 패스워드 */
	private String userPassword;
	/* 사용자 이름 */
	private String userName;
	/* 사용자 권한 */
	private String userAuthority;
	/* 사용자 이용 여부 */
	private int userEnabled;
}
