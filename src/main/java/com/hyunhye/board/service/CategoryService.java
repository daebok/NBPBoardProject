package com.hyunhye.board.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import com.hyunhye.board.model.Category;
import com.hyunhye.board.repository.CategoryRepository;

@Service
public class CategoryService {
	Logger logger = LoggerFactory.getLogger(CategoryService.class);

	@Autowired
	public CategoryRepository categoryRepository;

	@Resource(name = "cacheManager")
	EhCacheCacheManager cacheManager;

	/* 카테고리 추가 */
	public void categoryInsert(Category categoryModel) {
		// URL url = getClass().getResource("classpath:ehcache.xml");
		// CacheManager cacheManager = new CacheManager(url);

		Ehcache cache = cacheManager.getCacheManager().getCache("category");

		Element newElement = new Element(categoryModel.getCategoryNo(), categoryModel);
		logger.info("newElement:{}", newElement);
		cache.put(newElement);

		categoryRepository.categoryInsert(categoryModel);
	}

	/* 카테고리 목록 가져오기 */
	@Cacheable("category")
	public List<Category> categorySelectList() {
		List<Category> category = categoryRepository.categorySelectList();

		System.out.println("categorySelectList");

		List<Category> list = category.stream()
			.filter(s -> s.getCategoryEnabled() != 0)
			.collect(Collectors.toList());

		return list;
	}

	/* 카테고리를 가진 게시물 개수 구하기 */
	public int boardSelectCountOfCategory(Category categoryModel) {
		return categoryRepository.boardSelectCountOfCategory(categoryModel);
	}

	/* 카테고리 삭제 */
	public void categoryDelete(Category categoryModel) {
		categoryRepository.categoryDelete(categoryModel);
	}

	/* 카테고리 중복 체크 */
	public Integer categoryItemNameCheck(Category categoryModel) {
		return categoryRepository.categoryItemNameCheck(categoryModel);
	}

}
