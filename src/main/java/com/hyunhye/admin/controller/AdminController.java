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
import org.springframework.web.bind.annotation.ResponseBody;

import com.hyunhye.board.model.Board;
import com.hyunhye.board.model.Category;
import com.hyunhye.board.model.PageCriteria;
import com.hyunhye.board.model.PageMaker;
import com.hyunhye.board.service.CategoryService;
import com.hyunhye.contact.model.Contact;
import com.hyunhye.contact.model.ContactComment;
import com.hyunhye.contact.service.ContactService;
import com.hyunhye.notice.model.Notice;
import com.hyunhye.notice.service.NoticeService;
import com.hyunhye.user.model.BoardUser;
import com.hyunhye.user.service.UserService;
import com.hyunhye.utils.UriUtils;
import com.hyunhye.utils.UserSessionUtils;

/**
 * 관리자 페이지를 위한 Controller
 * @author NAVER
 *
 */
@Controller
@RequestMapping("admin")
public class AdminController {

	@Autowired
	public NoticeService noticeService;

	@Autowired
	private ContactService contactService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UserService userService;

	/**
	 * 관리자 페이지로 이동
	 * 1.문의사항 리스트 불러옴
	 * 2.현재 페이지에 대한 저장
	 * @param criteria 문의사항 리스트 페이징
	 * @param model
	 * @param request
	 * @return 관리자 페이지
	 */
	@RequestMapping(value = "admin", method = RequestMethod.GET)
	public String goAdminPage(@ModelAttribute PageCriteria criteria, Model model, HttpServletRequest request) {

		UriUtils.getUri(request);

		model.addAttribute("contact", contactService.selectAllContactList(criteria));

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(criteria);
		pageMaker.setTotalCount(contactService.selectAllContactCount(criteria));

		model.addAttribute("criteria", criteria);
		model.addAttribute("pageMaker", pageMaker);

		return "admin/admin";
	}

	/**
	 * 관리자 페이지에서 카테고리 전체 목록 가져오기
	 * @param model
	 * @return 카테고리 관리 페이지
	 */
	@RequestMapping(value = "category", method = RequestMethod.GET)
	public String selectAllCategoryList(Model model) {
		model.addAttribute("categoryList", categoryService.selectAllCategoryList());
		return "admin/category/category-manage";
	}

	/**
	 * 해당 {@link Category}를 가진 게시물 개수 가져오기
	 * @param category
	 */
	@ResponseBody
	@RequestMapping(value = "categoryCount", method = RequestMethod.GET)
	public ResponseEntity<Integer> selectBoardCountOfCategory(@ModelAttribute Category category) {
		return new ResponseEntity<Integer>(categoryService.selectBoardCountOfCategory(category), HttpStatus.OK);
	}

	/**
	 * {@link Category} 추가하기
	 * @param category
	 * @return 카테고리 리스트 페이지
	 */
	@RequestMapping(value = "categoryAdd", method = RequestMethod.POST)
	public String insertCategoryItem(@ModelAttribute Category category) {
		categoryService.insertCategoryItem(category);
		return "redirect:/admin/category";
	}

	/**
	 * {@link Category} 삭제하기.
	 * 해당 카테고리에 게시물이 있으면, category_enabled에 삭제 여부 설정
	 * @param category
	 * @return 카테고리 관리 페이지 Redirect
	 */
	@RequestMapping(value = "categoryDelete", method = RequestMethod.GET)
	public String deleteCategoryItem(@ModelAttribute Category category) {
		categoryService.deleteCategoryItem(category);
		return "redirect:/admin/category";
	}

	/**
	 * 현재 같은 카테고리의 이름이 있는지 체크
	 * @param category
	 * @return 현재 해당 카테고리가 있으면 1, 그렇지 않으면 0
	 */
	@ResponseBody
	@RequestMapping(value = "categoryCheck", method = RequestMethod.POST)
	public ResponseEntity<Integer> checkCategoryItemNameDuplication(@ModelAttribute Category category) {
		return new ResponseEntity<Integer>(categoryService.checkCategoryItemNameDuplication(category),
			HttpStatus.OK);
	}

	/**
	 * {@link BoardUser} 리스트 가져오기
	 * @param criteria 회원 리스트 페이징
	 * @param model
	 * @return 회원 관리 페이지
	 */
	@RequestMapping(value = "user", method = RequestMethod.GET)
	public String selectAllUserList(@ModelAttribute PageCriteria criteria, Model model) {
		model.addAttribute("userList", userService.selectAllUserList(criteria));

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(criteria);
		pageMaker.setTotalCount(userService.selectAllUserCount(criteria));

		model.addAttribute("pageMaker", pageMaker);

		return "admin/user/user-manage";
	}

	/**
	 * {@link BoardUser} 권한 수정
	 * @param user 사용자 번호
	 */
	@RequestMapping(value = "userModify", method = RequestMethod.GET)
	public ResponseEntity<String> updateUserAuthority(@ModelAttribute BoardUser user) {
		userService.updateUserAuthority(user);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * {@link BoardUser}삭제 시, 해당 사용자가 작성한  {@link Board}와 함께 삭제
	 * @param user 사용자 번호
	 */
	@RequestMapping(value = "userDelete", method = RequestMethod.GET)
	public ResponseEntity<String> deleteUserWithBoard(@ModelAttribute BoardUser user) {
		userService.deleteUserWithBoard(user);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "userSearch", method = RequestMethod.GET)
	public String searchUserInfo(@ModelAttribute BoardUser userModel, Model model) {
		model.addAttribute("user", userService.searchUserInfo(userModel));
		return "admin/user/user-info-search";
	}

	/**
	 * 헤당 {@link BoardUser}가 작성한 게시물은 남기고 사용자만 삭제
	 * @param user 사용자 번호
	 */
	@RequestMapping(value = "onlyUserDelete", method = RequestMethod.GET)
	public ResponseEntity<String> deleteOnlyUser(@ModelAttribute BoardUser user) {
		userService.deleteOnlyUser(user);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * {@link Notice} 리스트 가져오기
	 * @param model
	 * @return 공지사항 관리 페이지 (공지사항 리스트)
	 */
	@RequestMapping("notice/list")
	public String selectAllNoticeList(Model model) {
		model.addAttribute("noticeList", noticeService.selectAllNoticeList());
		return "admin/notice/notice-list";
	}

	/**
	 * {@link Notice} 상세보기
	 * @param notice 공지사항 번호
	 * @param model
	 * @return 공지사항 상세보기 페이지
	 */
	@RequestMapping(value = "notice", method = RequestMethod.GET)
	public String selectNoticeDetail(Notice notice, Model model) {
		model.addAttribute("model", noticeService.selectNoticeDetail(notice));
		return "admin/notice/notice-view";
	}

	/**
	 * {@link Notice} 등록하기 페이지 이동
	 * @return 공지사항 등록하기 페이지
	 */
	@RequestMapping("noticeRegist")
	public String goNoticeRegistPage() {
		return "admin/notice/notice-regist";
	}

	/**
	 * {@link Notice} 등록하기
	 * @param notice 공지사항 번호
	 * @return 공지사항 관리 페이지로 Redirect
	 */
	@RequestMapping(value = "notice/regist", method = RequestMethod.POST)
	public String insertNotice(Notice notice) {
		noticeService.insertNotice(notice);
		return "redirect:/admin/notice/list";
	}

	/**
	 * {@link Notice} 삭제하기
	 * @param notice 공지사항 번호
	 */
	@RequestMapping(value = "notice/delete", method = RequestMethod.GET)
	public String deleteNotice(Notice notice) {
		noticeService.deleteNotice(notice);
		return "redirect:/admin/notice/list";
	}

	/**
	 * {@link Notice} 수정페이지 이동
	 * @param notice 공지사항 번호
	 * @param model
	 * @return 공지사항 수정페이지
	 */
	@RequestMapping("notice/modifyPage")
	public String goNoticeUpdatePage(Notice notice, Model model) {
		model.addAttribute("model", noticeService.selectNoticeDetail(notice));
		return "admin/notice/notice-modify";
	}

	/**
	 * {@link Notice} 수정하기
	 * @param notice 공지사항 수정 사항
	 * @param model
	 * @return 공지사항 관리 페이지로 Redirect
	 */
	@RequestMapping(value = "notice/modify", method = RequestMethod.POST)
	public String updateNotice(Notice notice, Model model) {
		noticeService.updateNotice(notice);
		return "redirect:/admin/notice/list";
	}

	/**
	 * {@link Contact}에 {@link ContactComment} 달기.
	 * 마지막에 달린 {@link ContactComment} 가져옴
	 * @param contactComment 공지 사항 댓글
	 * @param model
	 * @return 공지항 댓글 폼
	 */
	@RequestMapping(value = "comment/regist", method = RequestMethod.POST)
	public String insertContactComment(@ModelAttribute ContactComment contactComment, Model model) {
		contactService.insertContactComment(contactComment);

		model.addAttribute("user", UserSessionUtils.currentUserInfo());
		model.addAttribute("contactComment", contactService.selectAllContactCommentList(contactComment.getContactNo()));

		return "contact/contact-comment-form";
	}

	/**
	 * {@link Contact} 삭제하기
	 * @param contactCommentNo 공지사항 번호
	 */
	@RequestMapping(value = "comment/delete", method = RequestMethod.GET)
	public ResponseEntity<String> deleteContactComment(@ModelAttribute ContactComment contactComment) {
		contactService.deleteContactComment(contactComment);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
