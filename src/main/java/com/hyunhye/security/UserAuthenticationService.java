package com.hyunhye.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hyunhye.user.model.UserModelDetails;
import com.hyunhye.user.repository.UserRepository;

@Service
public class UserAuthenticationService implements UserDetailsService {

	@Autowired
	public UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Map<String, Object> user = repository.selectUser(userName);
		if (user == null) {
			throw new UsernameNotFoundException(userName);
		}
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority(user.get("authority").toString()));
		return new UserModelDetails(
			user.get("username").toString(),
			user.get("password").toString(),
			(Integer)user.get("enabled") == 1,
			true,
			true,
			true,
			grantedAuthorities,
			(Integer)user.get("user_no"),
			user.get("user_name").toString());

	}
}
