package com.hyunhye.board.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;

@Data
public class PageMaker {
	/* 게시글 총 개수 */
	private int totalCount;
	/* 시작 페이지 번호 */
	private int startPage;
	/* 끝 페이지  번호 */
	private int endPage;
	/* 이전 페이지 */
	private boolean prev;
	/* 이후 페이지 */
	private boolean next;
	/* 화면에 보여지는 페이지 개수 */
	private int displayPageNum = 10;
	private Criteria cri;

	public void setCri(Criteria cri) {
		this.cri = cri;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData();
	}

	private void calcData() {
		/*
		 * 마지막 페이지 번호 = (현재 페에지/화면에 보여지는 페이지 개수)*화면에 보여지는 페이지 개수
		 * 현재 페이지가 21이면, (21 / 10) * 10
		 */
		endPage = (int)(Math.ceil(cri.getPage() / (double)displayPageNum) * displayPageNum);
		/* 시작페이지 번호 = (끝페이지 - 화면에 보여지는 페이지 개수) +1 */
		startPage = (endPage - displayPageNum) + 1;
		/* 마지막 페이지 번호가 총 페이지 개수보다 클 경우 처리 */
		int tempEndPage = (int)(Math.ceil(totalCount / (double)cri.getPerPageNum()));
		if (endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		/* 시작 페이지 번호가 1인 경우, 이전 페이지 없음 */
		prev = startPage == 1 ? false : true;
		/* 뒤에 남아있는 데이터가 있는지 확인 */
		next = endPage * cri.getPerPageNum() >= totalCount ? false : true;
	}

	/* */
	public String makeQuery(int page) {
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
			.queryParam("page", page)
			.queryParam("perPageNum", cri.getPerPageNum())
			.build();

		return uriComponents.toUriString();
	}

	public String makeSearch(int page) {
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
			.queryParam("page", page)
			.queryParam("perPageNum", cri.getPerPageNum())
			.queryParam("categoryType", ((SearchCriteria)cri).getCategoryType())
			.queryParam("searchType", ((SearchCriteria)cri).getSearchType())
			.queryParam("keyword", encoding(((SearchCriteria)cri).getKeyword()))
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
