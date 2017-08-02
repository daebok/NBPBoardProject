package com.hyunhye.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hyunhye.admin.model.NoticeModel;
import com.hyunhye.admin.repository.AdminRepository;
import com.hyunhye.board.model.CategoryModel;
import com.hyunhye.user.model.UserModel;
import com.hyunhye.user.model.UserModelDetails;

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

	public List<UserModel> userListAll() {
		return repository.userListAll();
	}

	public void userModify(UserModel userModel) {
		repository.userModify(userModel);
	}

	public void userDelete(UserModel userModel) {
		repository.userDelete(userModel);
	}

	public int categoryCount(CategoryModel categoryModel) {
		return repository.categoryCount(categoryModel);
	}

	public List<NoticeModel> noticeListAll() {
		return repository.noticeListAll();
	}

	public void noticeRegist(NoticeModel noticeModel) {
		UserModelDetails user = (UserModelDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		noticeModel.setUserNo(user.getUserNo());

		repository.noticeRegist(noticeModel);
	}

	public NoticeModel noticeSelect(NoticeModel noticeModel) {
		return repository.noticeSelect(noticeModel);
	}

	public void deleteNotice(NoticeModel noticeModel) {
		repository.deleteNotice(noticeModel);
	}

	public void noticeModify(NoticeModel noticeModel) {
		repository.noticeModify(noticeModel);
	}

	public Integer categoryCheck(CategoryModel categoryModel) {
		// TODO Auto-generated method stub
		return repository.categoryCheck(categoryModel);
	}
}
