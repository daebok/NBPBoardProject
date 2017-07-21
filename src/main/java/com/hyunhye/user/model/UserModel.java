package com.hyunhye.user.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("user")
public class UserModel {
	/* 사용자 번호 */
	private int userNo;
	/* 사용자 아이디 */
	private String userId;
	/* 사용자 패스워드 */
	private String userPassword;
	/* 사용자 이름 */
	private String userName;
}
