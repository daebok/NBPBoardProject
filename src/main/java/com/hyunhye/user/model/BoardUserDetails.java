package com.hyunhye.user.model;

import java.util.Collection;

import org.apache.ibatis.type.Alias;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("serial")
@Alias("userDetails")
public class BoardUserDetails extends User {

	/* 사용자 번호 */
	private int userNo;
	/* 사용자 이름 */
	private String userName;

	public BoardUserDetails(
		String username,
		String password,
		boolean enabled,
		boolean accountNonExpired,
		boolean credentialsNonExpired,
		boolean accountNonLocked,
		Collection<? extends GrantedAuthority> authorities,
		int userNo,
		String userName) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userNo = userNo;
		this.userName = userName;
	}

}
