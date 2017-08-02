package com.hyunhye.admin.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyunhye.admin.model.NoticeModel;
import com.hyunhye.board.model.CategoryModel;
import com.hyunhye.user.model.UserModel;

@Repository
public interface AdminRepository {

	public void categoryAdd(CategoryModel categoryModel);

	public List<CategoryModel> categoryListAll();

	public void categoryDelete(CategoryModel categoryModel);

	public void categoryModify(CategoryModel categoryModel);

	public List<UserModel> userListAll();

	public void userModify(UserModel userModel);

	public void userDelete(UserModel userModel);

	public int categoryCount(CategoryModel categoryModel);

	public List<NoticeModel> noticeListAll();

	public void noticeRegist(NoticeModel noticeModel);

	public NoticeModel noticeSelect(NoticeModel noticeModel);

	public void noticeDelete(NoticeModel noticeModel);

	public void noticeModify(NoticeModel noticeModel);

	public Integer categoryCheck(CategoryModel categoryModel);

	public void onlyUserDelete(UserModel userModel);

}
