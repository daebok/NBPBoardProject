package com.hyunhye.naver.ouath.model;

import java.util.Date;

public class SnsUser {
	private String snsId;
	private String snsType;
	private String snsName;
	private String snsProfile;
	private Date snsConnectDate;


	public String getSnsId() {
		return snsId;
	}

	public String getSnsType() {
		return snsType;
	}

	public String getSnsName() {
		return snsName;
	}

	public String getSnsProfile() {
		return snsProfile;
	}

	public Date getSnsConnectDate() {
		return snsConnectDate;
	}

	public void setSnsId(String snsId) {
		this.snsId = snsId;
	}

	public void setSnsType(String snsType) {
		this.snsType = snsType;
	}

	public void setSnsName(String snsName) {
		this.snsName = snsName;
	}

	public void setSnsProfile(String snsProfile) {
		this.snsProfile = snsProfile;
	}

	public void setSnsConnectDate(Date snsConnectDate) {
		this.snsConnectDate = snsConnectDate;
	}

}
