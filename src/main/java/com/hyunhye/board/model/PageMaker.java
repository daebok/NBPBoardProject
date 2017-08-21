package com.hyunhye.board.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;

/**
 * 페이징에 필요한 계산 정보
 * @author NAVER
 *
 */
@Data
public class PageMaker {
	Logger logger = LoggerFactory.getLogger(PageMaker.class);
	
	/** 게시글 총 개수 **/
	private int totalCount;
	/** 시작 페이지 번호 **/
	private int startPage;
	/** 끝 페이지  번호 **/
	private int endPage;
	/** 이전 페이지 **/
	private boolean prev;
	/** 이후 페이지 **/
	private boolean next;
	/** 화면에 보여지는 페이지 개수 **/
	private int displayPageNum = 5;
	private PageCriteria criteria;

	public void setCriteria(PageCriteria criteria) {
		this.criteria = criteria;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData();
	}

	/**
	 * 페이징 계산
	 */
	private void calcData() {
		endPage = (int)(Math.ceil(criteria.getPage() / (double)displayPageNum) * displayPageNum);
		startPage = (endPage - displayPageNum) + 1;
		int tempEndPage = (int)(Math.ceil(totalCount / (double)criteria.getPerPageNum()));
		if (endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		prev = startPage == 1 ? false : true;
		next = endPage * criteria.getPerPageNum() >= totalCount ? false : true;
	}

	/**
	 * 페이징을 할 때, 쿼리 생성
	 * @param page 페이지 번호
	 * @return 쿼리 스트링
	 */
	public String makeQuery(int page) {
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
			.queryParam("option", criteria.getOption())
			.queryParam("page", page)
			.queryParam("perPageNum", criteria.getPerPageNum())
			.build();

		return uriComponents.toUriString();
	}

	/**
	 * 검색 할 때, 페이징을 위한 쿼리 생성
	 * @param page 페이지 번호
	 * @return 쿼리 스트링
	 */
	public String makeSearch(int page) {
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
			.queryParam("tab", criteria.getTab())
			.queryParam("page", page)
			.queryParam("perPageNum", criteria.getPerPageNum())
			.queryParam("categoryType", ((SearchCriteria)criteria).getCategoryType())
			.queryParam("searchType", ((SearchCriteria)criteria).getSearchType())
			.queryParam("keyword", encoding(((SearchCriteria)criteria).getKeyword()))
			.queryParam("fromDate", ((SearchCriteria)criteria).getFromDate())
			.queryParam("toDate", ((SearchCriteria)criteria).getToDate())
			.build();

		return uriComponents.toUriString();
	}

	private Object encoding(String keyword) {
		if (keyword == null || keyword.trim().length() == 0) {
			return "";
		}
		try {
			return URLEncoder.encode(keyword, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}
