package com.hyunhye.user.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("user")
public class UserModel {

	private int userId;
	private String id;
	private String password;
	private String name;
	private String sessionKey;
	private String sessionLimit;
	private boolean useCookie;
}
