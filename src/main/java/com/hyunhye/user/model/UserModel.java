package com.hyunhye.user.model;

public class UserModel {

	int UID;
	String ID;
	String PASSWORD;
	String NAME;

	public UserModel() {
		// TODO Auto-generated constructor stub
	}

	public int getUID() {
		return UID;
	}

	public void setUID(int uID) {
		UID = uID;
	}

	public UserModel(String iD, String pASSWORD, String nAME) {
		super();
		ID = iD;
		PASSWORD = pASSWORD;
		NAME = nAME;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

}
