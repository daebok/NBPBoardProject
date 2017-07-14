package com.hyunhye.board.service;

import lombok.Data;

@Data
public class BoardPager {
	public static final int PAGE_SCALE = 10;
	public static final int BLOCK_SCALE = 10;
	private int curPage; // current page
	private int prevPage; // prev page
	private int nextPage; // next page
	private int totPage; // total page num
	private int totBlock; // total page block num
	private int curBlock; // current page block
	private int prevBlock; // prev page block
	private int nextBlock; // next page block
	private int pageBegin; // #{start}
	private int pageEnd; // #{end}
	private int blockBegin;
	private int blockEnd;

	public BoardPager(int count, int curPage) {
		curBlock = 1; 
		this.curPage = curPage;
		setTotPage(count);
		setPageRange();
		setTotBlock();
		setBlockRange(); 
	}

	public void setBlockRange() {
		curBlock = (int) Math.ceil((curPage - 1) / BLOCK_SCALE) + 1;
		
		blockBegin = (curBlock - 1) * BLOCK_SCALE + 1;
		
		blockEnd = blockBegin + BLOCK_SCALE - 1;
		
		if (blockEnd > totPage) {
			blockEnd = totPage;
		}
		
		prevPage = (curPage == 1) ? 1 : (curBlock - 1) * BLOCK_SCALE;
		
		nextPage = curBlock > totBlock ? (curBlock * BLOCK_SCALE) : (curBlock * BLOCK_SCALE) + 1;
		if (nextPage >= totPage) {
			nextPage = totPage;
		}
	}

	public void setPageRange() {
		pageBegin = (curPage - 1) * PAGE_SCALE + 1;
		pageEnd = pageBegin + PAGE_SCALE - 1;
	}

	public void setTotPage(int count) {
		totPage = (int) Math.ceil(count * 1.0 / PAGE_SCALE);
	}


	public void setTotBlock() {
		totBlock = (int) Math.ceil(totPage / BLOCK_SCALE);
	}


}
