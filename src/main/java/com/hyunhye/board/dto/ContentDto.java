package com.hyunhye.board.dto;

public class ContentDto {

	String ID;
	String PASSWORD;
	String NAME;

	public ContentDto() {
		// TODO Auto-generated constructor stub
	}

	public ContentDto(String iD, String pASSWORD, String nAME) {
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
