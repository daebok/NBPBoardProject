package com.hyunhye.admin.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyunhye.board.model.CategoryModel;

@Repository
public interface AdminRepository {

	public void categoryAdd(CategoryModel categoryModel);

	public List<CategoryModel> categoryListAll();

	public void categoryDelete(CategoryModel categoryModel);

	public void categoryModify(CategoryModel categoryModel);

}
