package com.hyunhye.board.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("category")
public class CategoryModel {
	/* 카테고리 번호 */
	private int categoryId;
	/* 카테고리 항목이름 */
	private String item;
}
