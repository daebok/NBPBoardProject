package com.hyunhye.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyunhye.admin.model.NoticeModel;
import com.hyunhye.admin.repository.AdminRepository;
import com.hyunhye.board.model.CategoryModel;
import com.hyunhye.security.UserSession;
import com.hyunhye.user.model.UserModel;

@Service
public class AdminService {

	@Autowired
	AdminRepository repository;

	/** 카테고리 관리 **/
	/* 카테고리 추가 */
	public void categoryAdd(CategoryModel categoryModel) {
		repository.categoryAdd(categoryModel);
	}

	/* 카테고리 목록 가져오기 */
	public List<CategoryModel> categoryListAll() {
		return repository.categoryListAll();
	}

	/* 카테고리 개수 구하기 */
	public int categoryCount(CategoryModel categoryModel) {
		return repository.categoryCount(categoryModel);
	}

	/* 카테고리 삭제 */
	public void categoryDelete(CategoryModel categoryModel) {
		repository.categoryDelete(categoryModel);
	}

	/* 카테고리 수정 */
	public void categoryModify(CategoryModel categoryModel) {
		repository.categoryModify(categoryModel);
	}

	/* 카테고리 중복 체크 */
	public Integer categoryCheck(CategoryModel categoryModel) {
		// TODO Auto-generated method stub
		return repository.categoryCheck(categoryModel);
	}

	/** 회원 관리 **/
	/* 회원 정보 리스트 */
	public List<UserModel> userListAll() {
		return repository.userListAll();
	}

	/* 회원 정보 변경 */
	public void userModify(UserModel userModel) {
		repository.userModify(userModel);
	}

	/* 회원 삭제 */
	public void userDelete(UserModel userModel) {
		repository.userDelete(userModel);
	}

	public void onlyUserDelete(UserModel userModel) {
		repository.onlyUserDelete(userModel);
	}

	/** 공지사항 **/
	/* 공지사항 리스트 */
	public List<NoticeModel> noticeListAll() {
		return repository.noticeListAll();
	}

	/* 공지사항 등록 */
	public void noticeRegist(NoticeModel noticeModel) {
		noticeModel.setUserNo(UserSession.currentUserNo());

		repository.noticeRegist(noticeModel);
	}

	/* 공지사항 한개 가져오기 */
	public NoticeModel noticeSelect(NoticeModel noticeModel) {
		return repository.noticeSelect(noticeModel);
	}

	/* 공지사항 삭제 */
	public void deleteNotice(NoticeModel noticeModel) {
		repository.deleteNotice(noticeModel);
	}

	/* 공지사항 수정 */
	public void noticeModify(NoticeModel noticeModel) {
		repository.noticeModify(noticeModel);
	}


}
