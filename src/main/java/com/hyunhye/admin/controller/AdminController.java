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

import com.hyunhye.admin.model.NoticeModel;
import com.hyunhye.admin.service.AdminService;
import com.hyunhye.board.model.CategoryModel;
import com.hyunhye.user.model.UserModel;

@Controller
@RequestMapping("admin")
public class AdminController {

	@Autowired
	public AdminService adminService;

	@RequestMapping("admin")
	public String admin(Model model) {
		return "admin/admin";
	}

	/** 카테고리 관리 **/
	/* 카테고리 목록 가져오기 */
	@RequestMapping("category")
	public String category(Model model) {
		model.addAttribute("categoryList", adminService.categoryListAll());
		return "admin/categoryManage";
	}

	/* 카테고리 개수 구하기 */
	@ResponseBody
	@RequestMapping("categoryCount")
	public ResponseEntity<Integer> categoryCount(@ModelAttribute CategoryModel categoryModel) {
		ResponseEntity<Integer> entity = new ResponseEntity<Integer>(adminService.categoryCount(categoryModel),
			HttpStatus.OK);

		return entity;
	}

	/* 카테고리 추가 */
	@RequestMapping(value = "categoryAdd", method = {RequestMethod.POST, RequestMethod.GET})
	public String categoryAdd(@ModelAttribute CategoryModel categoryModel) {
		adminService.categoryAdd(categoryModel);
		return "redirect:/admin/category";
	}

	/* 카테고리 삭제 */
	@RequestMapping(value = "categoryDelete", method = {RequestMethod.POST, RequestMethod.GET})
	public String categoryDelete(@ModelAttribute CategoryModel categoryModel) {
		adminService.categoryDelete(categoryModel);
		return "redirect:/admin/category";
	}

	/* 카테고리 수정 */
	@RequestMapping(value = "categoryModify", method = {RequestMethod.POST, RequestMethod.GET})
	public String categoryModify(@ModelAttribute CategoryModel categoryModel) {
		adminService.categoryModify(categoryModel);
		return "redirect:/admin/category";
	}

	/* 카테고리 중복 체크 */
	@ResponseBody
	@RequestMapping(value = "categoryCheck", method = RequestMethod.POST)
	public ResponseEntity<Integer> categoryCheck(@ModelAttribute CategoryModel categoryModel) {
		ResponseEntity<Integer> entity = new ResponseEntity<Integer>(adminService.categoryCheck(categoryModel),
			HttpStatus.OK);

		return entity;
	}

	/** 회원 관리 **/
	/* 회원 정보 리스트 */
	@RequestMapping("user")
	public String user(Model model) {
		model.addAttribute("userList", adminService.userListAll());
		return "admin/userManage";
	}

	/* 회원 정보 변경 */
	@RequestMapping("userModify")
	public ResponseEntity<String> userModify(@ModelAttribute UserModel userModel) {
		adminService.userModify(userModel);
		return new ResponseEntity<String>("user modified", HttpStatus.OK);
	}

	/* 회원 삭제 */
	@RequestMapping("userDelete")
	public ResponseEntity<String> userDelete(@ModelAttribute UserModel userModel) {
		adminService.userDelete(userModel);
		return new ResponseEntity<String>("user deleted", HttpStatus.OK);
	}

	/** 공지사항 **/
	/* 공지사항 리스트 */
	@RequestMapping("notice")
	public String notice(Model model) {
		model.addAttribute("noticeList", adminService.noticeListAll());
		return "admin/notice";
	}

	/* 공지사항 등록페이지 이동 */
	@RequestMapping("noticeRegist")
	public String noticeRegistPage() {
		return "admin/noticeManage";
	}

	/* 공지사항 등록 */
	@RequestMapping("notice/regist")
	public String noticeRegist(NoticeModel noticeModel) {
		adminService.noticeRegist(noticeModel);
		return "redirect:/admin/notice";
	}

	/* 공지사항 삭제 */
	@RequestMapping("notice/delete")
	public String noticeDelete(NoticeModel noticeModel) {
		adminService.deleteNotice(noticeModel);
		return "redirect:/admin/notice";
	}

	/* 공지사항 수정 페이지 이동 */
	@RequestMapping("notice/modifyPage")
	public String noticeModifyPage(NoticeModel noticeModel, Model model) {
		model.addAttribute("model", adminService.noticeSelect(noticeModel));
		return "admin/noticeModify";
	}

	/* 공지사항 수정 */
	@RequestMapping("notice/modify")
	public String noticeModify(NoticeModel noticeModel, Model model) {
		adminService.noticeModify(noticeModel);
		return "redirect:/admin/notice";
	}

}
