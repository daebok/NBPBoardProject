package com.hyunhye.board.dto;

public class QuestionDto {
	private int BID;
	private int UID;
	private String TITLE;
	private String CATEGORY;
	private String CONTENT;
	private String DATE;
	
	public QuestionDto() {
	}

	public int getBID() {
		return BID;
	}

	public void setBID(int bID) {
		BID = bID;
	}

	public int getUID() {
		return UID;
	}

	public void setUID(int uID) {
		UID = uID;
	}

	public int getCID() {
		return CID;
	}

	public void setCID(int cID) {
		CID = cID;
	}

	private int CID;

	public String getTITLE() {
		return TITLE;
	}

	public void setTITLE(String tITLE) {
		TITLE = tITLE;
	}

	public String getCATEGORY() {
		return CATEGORY;
	}

	public void setCATEGORY(String cATEGORY) {
		CATEGORY = cATEGORY;
	}

	public String getCONTENT() {
		return CONTENT;
	}

	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}

	public String getDATE() {
		return DATE;
	}

	public void setDATE(String dATE) {
		DATE = dATE;
	}
	
}
