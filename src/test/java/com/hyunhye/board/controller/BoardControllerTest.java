package com.hyunhye.board.controller;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hyunhye.board.service.BoardService;
import com.hyunhye.test.AbstractTestCaseRunWithSpring;

public class BoardControllerTest extends AbstractTestCaseRunWithSpring {
	Logger log = LoggerFactory.getLogger(BoardControllerTest.class);

	@Autowired
	BoardService service;

	@Override
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
