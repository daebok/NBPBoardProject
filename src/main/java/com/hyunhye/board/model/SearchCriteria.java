package com.hyunhye.board.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SearchCriteria extends Criteria {
	private String searchType;
	private String keyword;
}
