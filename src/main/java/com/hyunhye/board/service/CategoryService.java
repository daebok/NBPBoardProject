package com.hyunhye.board.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

import com.hyunhye.board.model.Board;
import com.hyunhye.board.model.Category;
import com.hyunhye.board.repository.CategoryRepository;

@Service
public class CategoryService {
	Logger logger = LoggerFactory.getLogger(CategoryService.class);

	@Autowired
	public CategoryRepository categoryRepository;

	@Resource(name = "cacheManager")
	EhCacheCacheManager cacheManager;

	/**
	 * {@link Category} 추가
	 * @param category 추가하는 category 항목
	 */
	@CacheEvict(value = "category", allEntries = true)
	public void insertCategoryItem(Category category) {
		categoryRepository.insertCategoryItem(category);
	}

	/**
	 * @return {@link Category} 리스트 (List<Category>)
	 */
	@Cacheable("category")
	public List<Category> selectAllCategoryList() {
		logger.info("select Category List");
		List<Category> category = categoryRepository.selectAllCategoryList();

		List<Category> list = category.stream()
			.filter(s -> s.getCategoryEnabled() != 0)
			.collect(Collectors.toList());

		return list;
	}

	/**
	 * @param category 카테고리 번호
	 * @return {@link Category}에 해당하는 {@link Board} 개수
	 */
	public int selectBoardCountOfCategory(Category category) {
		return categoryRepository.selectBoardCountOfCategory(category);
	}

	/**
	 * {@link Category} 항목 삭제
	 * @param category 번호
	 */
	@CacheEvict(value = "category", allEntries = true)
	public void deleteCategoryItem(Category category) {
		categoryRepository.deleteCategoryItem(category);
	}

	/**
	 * {@link Category} 항목 이름 중복 체크
	 * @param category 항목 이름
	 * @return {@link Category} 항목 이름에 해당하는 이름이 있으면 1, 없으면 0
	 */
	public int checkCategoryItemNameDuplication(Category category) {
		return categoryRepository.checkCategoryItemNameDuplication(category);
	}

}
