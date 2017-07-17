package com.hyunhye.board.model;

public class Criteria {
	private int page;
	private int perPageNum;
	private int startPage;

	public Criteria() {
		this.page = 1;
		this.perPageNum = 10;
		setStartPage();
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
		if (perPageNum <= 0 || perPageNum > 100) {
			this.perPageNum = 19;
			return;
		}

		this.perPageNum = perPageNum;
	}

	public void setStartPage() {
		this.startPage = (page - 1) * perPageNum;
	}

	public int getPage() {
		return page;
	}

	// method for MyBatis SQL Mapper
	public int getStartPage() {
		return this.startPage;
	}

	// method for MyBatis SQL Mapper
	public int getPerPageNum() {
		return this.perPageNum;
	}

	@Override
	public String toString() {
		return "Criteria [page=" + page + ", "
			+ "perPageNum=" + perPageNum + "]";
	}
}