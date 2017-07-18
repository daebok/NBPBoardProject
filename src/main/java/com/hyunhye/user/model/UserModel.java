package com.hyunhye.user.model;

import lombok.Data;

@Data
public class UserModel {

	private int userId;
	private String id;
	private String password;
	private String name;
	private String sessionKey;
	private String sessionLimit;
	private boolean useCookie;
}
