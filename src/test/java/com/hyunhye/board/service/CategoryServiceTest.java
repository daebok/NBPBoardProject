package com.hyunhye.board.service;

import org.junit.Test;

import com.hyunhye.taglib.SpringBeanFactory;
import com.hyunhye.test.AbstractTestCaseRunWithSpring;

public class CategoryServiceTest extends AbstractTestCaseRunWithSpring {

	@Override
	@Test
	public void test() {
		CategoryService categoryService = SpringBeanFactory.getBean(CategoryService.class);
		categoryService.categorySelectList();

		categoryService.categorySelectList();

		categoryService.categorySelectList();

		categoryService.categorySelectList();

	}

}
