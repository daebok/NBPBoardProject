package com.hyunhye.board.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyunhye.board.model.Category;

@Repository
public interface CategoryRepository {

	/** 카테고리 전체 리스트 **/
	public List<Category> categorySelectList();

	/** 카테고리 추가 **/
	public void categoryInsert(Category categoryModel);

	/** 해당 카테고리의 게시글 개수  **/
	public int boardSelectCountOfCategory(Category categoryModel);

	/** 카테고리 삭제 **/
	public void categoryDelete(Category categoryModel);

	/** 카테고리  같은 이름 있는지 확인 **/
	public Integer categoryItemNameCheck(Category categoryModel);

}