package com.hyunhye.board.model;

public class FileModel {
	private int FID;
	private int BID;
	private String FILENAME;
	private String ORIGINNAME;
	private String EXTENSION;
	private String FILESIZE;

	public FileModel() {
	}

	public int getFID() {
		return FID;
	}

	public void setFID(int fID) {
		FID = fID;
	}

	public int getBID() {
		return BID;
	}

	public void setBID(int bID) {
		BID = bID;
	}

	public String getFILENAME() {
		return FILENAME;
	}

	public void setFILENAME(String fILENAME) {
		FILENAME = fILENAME;
	}

	public String getORIGINNAME() {
		return ORIGINNAME;
	}

	public void setORIGINNAME(String oRIGINNAME) {
		ORIGINNAME = oRIGINNAME;
	}

	public String getEXTENSION() {
		return EXTENSION;
	}

	public void setEXTENSION(String eXTENSION) {
		EXTENSION = eXTENSION;
	}

	public String getFILESIZE() {
		return FILESIZE;
	}

	public void setFILESIZE(String fILESIZE) {
		FILESIZE = fILESIZE;
	}

}
