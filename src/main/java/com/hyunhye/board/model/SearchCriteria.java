package com.hyunhye.board.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SearchCriteria extends Criteria {
	/** 검색 타입 **/
	private String searchType;
	/** 검색 할 키워드 **/
	private String keyword;
	/** 실제 검색 할 키워드 **/
	private String modifiedKeyword;
	/** 카테고리 타입 **/
	private String categoryType;
	/** 날짜 **/
	private String date;
	/** 리스트 번호 (전체리스트인지, 내 질문 리스트인지, 즐겨찾기 리스트인지) **/
	private int option;
	private int tab;
}
