package com.hyunhye.naver.ouath.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import lombok.Getter;
import lombok.Setter;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Getter
@Setter
@JsonIgnoreProperties
public class NaverUser {

	@JsonProperty("email")
	private String email;

	@JsonProperty("nickname")
	private String nickname;

	@JsonProperty("enc_id")
	private String encId;

	@JsonProperty("age")
	private String age;

	@JsonProperty("gender")
	private String gender;

	@JsonProperty("id")
	private String id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("birthday")
	private String birthday;
}