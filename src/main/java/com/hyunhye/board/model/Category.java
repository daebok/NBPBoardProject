package com.hyunhye.board.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("category")
public class Category {
	/* 카테고리 번호 */
	private int categoryNo;
	/* 카테고리 항목이름 */
	private String categoryItem;
	/* 카테고리 사용가능 유무 */
	private String categoryEnabled;
}
