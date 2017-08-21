package com.hyunhye.board.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 검색 조건에 대한 정보
 * @author NAVER
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SearchCriteria extends PageCriteria {
	/** 검색 타입 **/
	private String searchType;
	/** 검색 할 키워드 **/
	private String keyword;
	/** 실제 검색 할 키워드 **/
	private String modifiedKeyword;
	/** 카테고리 타입 **/
	private String categoryType;
	/** 시작 날짜 **/
	private String fromDate;
	/** 종료 날짜 **/
	private String toDate;
}