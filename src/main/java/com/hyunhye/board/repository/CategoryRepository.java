package com.hyunhye.board.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyunhye.board.model.Category;

@Repository
public interface CategoryRepository {

	public List<Category> categorySelectList();

	public void categoryInsert(Category categoryModel);

	public int boardSelectCountOfCategory(Category categoryModel);

	public void categoryDelete(Category categoryModel);

	public Integer categoryCheck(Category categoryModel);

}