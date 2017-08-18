package com.hyunhye.board.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import com.hyunhye.common.SpringBeanFactory;
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

		Element newElement = new Element(50, "cat");
		logger.info("newElement:{}", newElement);
		cache.put(newElement);

		Element element = cache.get("SimpleKey []");
		logger.info("cacheManager: {}", cacheManager.toString());
		logger.info("element: {}", element);
		logger.info("getKey Size: {}", cache.getKeys().size());
		logger.info("getKey0: {}", cache.getKeys().get(0).toString());
	}

}
