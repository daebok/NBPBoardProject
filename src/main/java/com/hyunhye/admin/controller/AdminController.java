package com.hyunhye.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hyunhye.admin.model.Notice;
import com.hyunhye.admin.service.AdminService;
import com.hyunhye.board.model.Category;
import com.hyunhye.user.model.UserModel;

@Controller
@RequestMapping("admin")
public class AdminController {

	@Autowired
	public AdminService adminService;

	/**
	 * 관리자 페이지로 이동
	 * @param model
	 * @return
	 */
	@RequestMapping("admin")
	public String goAdminPage(Model model) {
		return "admin/admin";
	}

	/**
	 * 카테고리 목록 가져오기
	 * @param model
	 * @return
	 */
	@RequestMapping("category")
	public String categorySelectList(Model model) {
		model.addAttribute("categoryList", adminService.categorySelectList());
		return "admin/categoryManage";
	}

	/**
	 * 해당 카테고리를 가진 게시물 개수 가져오기
	 * @param categoryModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("categoryCount")
	public ResponseEntity<Integer> boardSelectCountOfCategory(@ModelAttribute Category categoryModel) {
		ResponseEntity<Integer> entity = new ResponseEntity<Integer>(
			adminService.boardSelectCountOfCategory(categoryModel),
			HttpStatus.OK);

		return entity;
	}

	/**
	 * 카테고리 추가하기
	 * @param categoryModel
	 * @return
	 */
	@RequestMapping(value = "categoryAdd", method = {RequestMethod.POST, RequestMethod.GET})
	public String categoryInsert(@ModelAttribute Category categoryModel) {
		adminService.categoryInsert(categoryModel);
		return "redirect:/admin/category";
	}

	/**
	 * 카테고리 삭제하기
	 * 해당 카테고리에 게시물이 있으면, category_enabled에 삭제 여부 설정
	 * @param categoryModel
	 * @return
	 */
	@RequestMapping(value = "categoryDelete", method = {RequestMethod.POST, RequestMethod.GET})
	public String categoryDelete(@ModelAttribute Category categoryModel) {
		adminService.categoryDelete(categoryModel);
		return "redirect:/admin/category";
	}

	/**
	 * 현재 같은 카테고리의 이름이 있는지 체크
	 * @param categoryModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "categoryCheck", method = RequestMethod.POST)
	public ResponseEntity<Integer> categoryCheck(@ModelAttribute Category categoryModel) {
		ResponseEntity<Integer> entity = new ResponseEntity<Integer>(adminService.categoryCheck(categoryModel),
			HttpStatus.OK);

		return entity;
	}

	/**
	 * 회원 리스트 가져오기
	 * @param model
	 * @return
	 */
	@RequestMapping("user")
	public String userSelectList(Model model) {
		model.addAttribute("userList", adminService.userSelectList());
		return "admin/userManage";
	}

	/**
	 * 회원 권한 수정
	 * @param userModel
	 * @return
	 */
	@RequestMapping("userModify")
	public ResponseEntity<String> userAuthorityUpdate(@ModelAttribute UserModel userModel) {
		adminService.userAuthorityUpdate(userModel);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 회원과 게시물 함께 삭제
	 * @param userModel
	 * @return
	 */
	@RequestMapping("userDelete")
	public ResponseEntity<String> userWithBoardDelete(@ModelAttribute UserModel userModel) {
		adminService.userWithBoardDelete(userModel);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 게시물은 남기고 회원만 삭제
	 * @param userModel
	 * @return
	 */
	@RequestMapping("onlyUserDelete")
	public ResponseEntity<String> onlyUserDelete(@ModelAttribute UserModel userModel) {
		adminService.onlyUserDelete(userModel);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 공지사항 리스트 가져오기
	 * @param model
	 * @return
	 */
	@RequestMapping("notice")
	public String notice(Model model) {
		model.addAttribute("noticeList", adminService.noticeListAll());
		return "admin/notice";
	}

	/**
	 * 공지사항 등록하기 페이지 이동
	 * @return
	 */
	@RequestMapping("noticeRegist")
	public String goNoticeInsertPage() {
		return "admin/noticeManage";
	}

	/**
	 * 공지사항 등록하기
	 * @param noticeModel
	 * @return
	 */
	@RequestMapping("notice/regist")
	public String noticeInsert(Notice noticeModel) {
		adminService.noticeInsert(noticeModel);
		return "redirect:/admin/notice";
	}

	/**
	 * 공지사항 삭제하기
	 * @param noticeModel
	 * @return
	 */
	@RequestMapping("notice/delete")
	public String noticeDelete(Notice noticeModel) {
		adminService.noticeDelete(noticeModel);
		return "redirect:/admin/notice";
	}

	/**
	 * 공지사항 수정페이지 이동
	 * @param noticeModel
	 * @param model
	 * @return
	 */
	@RequestMapping("notice/modifyPage")
	public String goNoticeUpdatePage(Notice noticeModel, Model model) {
		model.addAttribute("model", adminService.noticeSelectOne(noticeModel));
		return "admin/noticeModify";
	}

	/**
	 * 공지사항 수정하기
	 * @param noticeModel
	 * @param model
	 * @return
	 */
	@RequestMapping("notice/modify")
	public String noticeUpdate(Notice noticeModel, Model model) {
		adminService.noticeUpdate(noticeModel);
		return "redirect:/admin/notice";
	}

}
