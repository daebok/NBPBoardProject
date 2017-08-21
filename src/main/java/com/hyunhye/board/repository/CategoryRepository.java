package com.hyunhye.board.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyunhye.board.model.Board;
import com.hyunhye.board.model.Category;

@Repository
public interface CategoryRepository {

	/**
	 * {@link Category} 전체 리스트 가져오기
	 * @return {@link Category} 전체 리스트 (List<Category>)
	 */
	public List<Category> selectAllCategoryList();

	/**
	 * {@link Category} 추가하기
	 * @param category 추가하는 카테고리 항목
	 */
	public void insertCategoryItem(Category category);

	/**
	 * {@link Category}의 해당하는 {@link Board} 개수 가져오기
	 * @param category
	 * @return {@link Board} 개수 (int)
	 */
	public int selectBoardCountOfCategory(Category category);

	/**
	 * {@link Category} 삭제하기
	 * @param category 카테고리 번호
	 */
	public void deleteCategoryItem(Category category);

	/**
	 * 해당하는 {@link Category} 이름이 있는지 확인
	 * @param category 카테고리 항목 이름
	 * @return 있으면 1, 없으면 0 (int)
	 */
	public int checkCategoryItemNameDuplication(Category category);

}