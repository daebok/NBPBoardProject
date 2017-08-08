package com.hyunhye.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyunhye.admin.model.Notice;
import com.hyunhye.admin.repository.NoticeRepository;
import com.hyunhye.board.model.Category;
import com.hyunhye.board.repository.CategoryRepository;
import com.hyunhye.common.UserSessionUtils;
import com.hyunhye.user.model.User;
import com.hyunhye.user.repository.UserRepository;

@Service
public class AdminService {

	@Autowired
	NoticeRepository repository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	UserRepository userRepository;

	/** 카테고리 관리 **/
	/* 카테고리 추가 */
	public void categoryInsert(Category categoryModel) {
		categoryRepository.categoryInsert(categoryModel);
	}

	/* 카테고리 목록 가져오기 */
	public List<Category> categorySelectList() {
		return categoryRepository.categorySelectList();
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

	/** 회원 관리 **/
	/* 회원 정보 리스트 */
	public List<User> userSelectList() {
		return userRepository.userSelectList();
	}

	/* 회원 정보 변경 */
	public void userAuthorityUpdate(User userModel) {
		userRepository.userAuthorityUpdate(userModel);
	}

	/* 회원 삭제 */
	public void userWithBoardDelete(User userModel) {
		userRepository.userWithBoardDelete(userModel);
	}

	public void onlyUserDelete(User userModel) {
		userRepository.onlyUserDelete(userModel);
	}

	/** 공지사항 **/
	/* 공지사항  리스트 가져오기 */
	public List<Notice> noticeListAll() {
		return repository.noticeListAll();
	}

	/* 공지사항  추가 */
	public void noticeInsert(Notice noticeModel) {
		noticeModel.setUserNo(UserSessionUtils.currentUserNo());

		repository.noticeInsert(noticeModel);
	}

	/* 공지사항  상세보기 */
	public Notice noticeSelectOne(Notice noticeModel) {
		return repository.noticeSelectOne(noticeModel);
	}

	/* 공지사항  삭제하기 */
	public void noticeDelete(Notice noticeModel) {
		repository.noticeDelete(noticeModel);
	}

	/* 공지사항  수정하기  */
	public void noticeUpdate(Notice noticeModel) {
		repository.noticeUpdate(noticeModel);
	}

}
