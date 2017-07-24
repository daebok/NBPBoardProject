package com.hyunhye.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyunhye.admin.repository.AdminRepository;
import com.hyunhye.board.model.CategoryModel;

@Service
public class AdminService {

	@Autowired
	AdminRepository repository;

	public void categoryAdd(CategoryModel categoryModel) {
		repository.categoryAdd(categoryModel);
	}

	public List<CategoryModel> categoryListAll() {
		return repository.categoryListAll();
	}

	public void categoryDelete(CategoryModel categoryModel) {
		repository.categoryDelete(categoryModel);
	}

	public void categoryModify(CategoryModel categoryModel) {
		repository.categoryModify(categoryModel);
	}
}
