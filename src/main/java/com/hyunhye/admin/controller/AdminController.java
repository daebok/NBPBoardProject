package com.hyunhye.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hyunhye.board.model.Category;
import com.hyunhye.board.model.Criteria;
import com.hyunhye.board.model.PageMaker;
import com.hyunhye.board.service.CategoryService;
import com.hyunhye.contact.model.ContactComment;
import com.hyunhye.contact.service.ContactService;
import com.hyunhye.notice.model.Notice;
import com.hyunhye.notice.service.NoticeService;
import com.hyunhye.user.model.UserModel;
import com.hyunhye.user.service.UserService;
import com.hyunhye.utils.UriUtils;
import com.hyunhye.utils.UserSessionUtils;

@Controller
@RequestMapping("admin")
public class AdminController {

	@Autowired
	public NoticeService adminService;

	@Autowired
	private ContactService contactService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UserService userService;

	/**
	 * 관리자 페이지로 이동
	 * @param model
	 * @return
	 */
	@RequestMapping("admin")
	public String goAdminPage(@ModelAttribute Criteria cri, Model model, HttpServletRequest request) {

		/* 이전 uri에 대한 정보 저장 */
		UriUtils.getUri(request);
		System.out.println("test" + request.getSession().getAttribute("uri"));

		/** 문의사항 리스트 보기 **/
		model.addAttribute("contact", contactService.contactSelectListAll(cri));

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(contactService.contactSelectListCount(cri));

		model.addAttribute("cri", cri);
		model.addAttribute("pageMaker", pageMaker);

		return "admin/admin";
	}

	/**
	 * 카테고리 목록 가져오기
	 * @param model
	 * @return
	 */
	@RequestMapping("category")
	public String categorySelectList(Model model) {
		model.addAttribute("categoryList", categoryService.categorySelectList());
		return "admin/category/category-manage";
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
			categoryService.boardSelectCountOfCategory(categoryModel),
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
		categoryService.categoryInsert(categoryModel);
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
		categoryService.categoryDelete(categoryModel);
		return "redirect:/admin/category";
	}

	/**
	 * 현재 같은 카테고리의 이름이 있는지 체크
	 * @param categoryModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "categoryCheck", method = RequestMethod.POST)
	public ResponseEntity<Integer> categoryItemNameCheck(@ModelAttribute Category categoryModel) {
		ResponseEntity<Integer> entity = new ResponseEntity<Integer>(
			categoryService.categoryItemNameCheck(categoryModel),
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
		model.addAttribute("userList", userService.userSelectList());
		return "admin/user/user-manage";
	}

	/**
	 * 회원 권한 수정
	 * @param userModel
	 * @return
	 */
	@RequestMapping("userModify")
	public ResponseEntity<String> userAuthorityUpdate(@ModelAttribute UserModel userModel) {
		userService.userAuthorityUpdate(userModel);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 회원과 게시물 함께 삭제
	 * @param userModel
	 * @return
	 */
	@RequestMapping("userDelete")
	public ResponseEntity<String> userWithBoardDelete(@ModelAttribute UserModel userModel) {
		userService.userWithBoardDelete(userModel);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * 게시물은 남기고 회원만 삭제
	 * @param userModel
	 * @return
	 */
	@RequestMapping("onlyUserDelete")
	public ResponseEntity<String> onlyUserDelete(@ModelAttribute UserModel userModel) {
		userService.onlyUserDelete(userModel);
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
		return "admin/notice/notice";
	}

	/**
	 * 공지사항 등록하기 페이지 이동
	 * @return
	 */
	@RequestMapping("noticeRegist")
	public String goNoticeInsertPage() {
		return "admin/notice/notice-regist";
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
		return "admin/notice/notice-modify";
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

	/**
	 * 문의사항에 댓글 달기
	 * @param contactCommentModel
	 * @param model
	 */
	@RequestMapping("comment/regist")
	public String contactCommentInsert(@ModelAttribute ContactComment contactCommentModel, Model model) {
		contactService.contactCommentInsert(contactCommentModel);

		/* 세션에 저장된 사용자 정보 */
		model.addAttribute("user", UserSessionUtils.currentUserInfo());
		model.addAttribute("comment", contactService.contactCommentLastSelect());

		return "contact/contact-comment-form";
	}

	/**
	 * 문의사항 삭제하기
	 * @param contactCommentNo
	 */
	@RequestMapping(value = "comment/delete", method = RequestMethod.GET)
	public ResponseEntity<String> contactCommentDelete(@RequestParam("contactCommentNo") int contactCommentNo) {
		contactService.contactCommentDelete(contactCommentNo);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/*	@RequestMapping(value = "add/badword", method = RequestMethod.POST)
		public String addBadWord(@RequestParam String badWord) {
			BadWordFilteringUtils.badWordInsert(badWord);
			return "redirect:/admin/admin";
		}*/

}
