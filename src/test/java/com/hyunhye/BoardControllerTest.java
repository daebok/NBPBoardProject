package com.hyunhye;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hyunhye.board.model.SearchCriteria;
import com.hyunhye.board.service.BoardService;
import com.hyunhye.test.AbstractTestCaseRunWithSpring;

public class BoardControllerTest extends AbstractTestCaseRunWithSpring {

	@Autowired
	public BoardService boardService;

	@Override
	@Test
	public void test() {

		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.setSearchType("title");
		boardService.selectMyQuestions(searchCriteria, 23);

	}

}
