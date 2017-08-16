package com.hyunhye.board.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import net.sf.ehcache.Ehcache;

import com.hyunhye.taglib.SpringBeanFactory;
import com.hyunhye.test.AbstractTestCaseRunWithSpring;

public class CategoryServiceTest extends AbstractTestCaseRunWithSpring {
	Logger logger = LoggerFactory.getLogger(CategoryServiceTest.class);

	@Resource(name = "cacheManager")
	EhCacheCacheManager cacheManager;

	@Override
	@Test
	public void test() {
		CategoryService categoryService = SpringBeanFactory.getBean(CategoryService.class);
		categoryService.categorySelectList();

		categoryService.categorySelectList();

		categoryService.categorySelectList();

		categoryService.categorySelectList();

		Ehcache cache = cacheManager.getCacheManager().getCache("category");
		logger.info("cacheManager: {}", cacheManager.toString());
		logger.info("getCacheConfiguration: {}", cache.getKeys().size());

	}

}
