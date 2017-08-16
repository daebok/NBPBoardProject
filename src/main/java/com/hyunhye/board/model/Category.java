package com.hyunhye.board.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode
@Alias("category")
public class Category  implements Serializable {
	/** 카테고리 번호 **/
	private int categoryNo;
	/** 카테고리 항목이름 **/
	private String categoryItem;
	/** 카테고리 사용가능 유무 **/
	private int categoryEnabled;
}
