package com.hyunhye.board.model;

import lombok.Data;

@Data
public class Criteria {
	/** 페이지 번호 **/
	private int page;
	/** 페이지 당 데이터 개수 **/
	private int perPageNum;
	/** 시작 데이터 번호 **/
	private int startBoardNo;
	/** 사용자 번호 **/
	private int userNo;

	/*
	 * 디폴트: 시작 페이지=1, 페이지 당 데이터 개수=10
	 */
	public Criteria() {
		this.page = 1;
		this.perPageNum = 10;
		setStartBoardNo();
	}

	public void setPage(int page) {
		if (page <= 0) {
			this.page = 1;
			return;
		} else {
			this.page = page;
		}
	}

	public void setPerPageNum(int perPageNum) {
		this.perPageNum = perPageNum;
	}

	public void setStartBoardNo() {
		/* 시작 데이터 번호 = (페이지 번호 - 1) * 페이지 당 데이터 개수 */
		this.startBoardNo = (page - 1) * perPageNum;
	}

	public int getPage() {
		return page;
	}

	public int getStartBoardNo() {
		this.startBoardNo = (page - 1) * perPageNum;
		return this.startBoardNo;
	}

	public int getPerPageNum() {
		return this.perPageNum;
	}

	@Override
	public String toString() {
		return "Criteria [page=" + page + ", "
			+ "perPageNum=" + perPageNum + "]";
	}
}