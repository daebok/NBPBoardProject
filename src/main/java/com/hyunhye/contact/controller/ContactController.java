package com.hyunhye.contact.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hyunhye.board.model.Criteria;
import com.hyunhye.board.model.PageMaker;
import com.hyunhye.contact.model.Contact;
import com.hyunhye.contact.model.ContactComment;
import com.hyunhye.contact.service.ContactService;
import com.hyunhye.utils.UserSessionUtils;

@RequestMapping("contact")
@Controller
public class ContactController {
	Logger logger = LoggerFactory.getLogger(ContactController.class);

	@Autowired
	private ContactService contactService;


	/**
	 * contactUs 페이지로 이동
	 * 문의사항 리스트 보기
	 * @return
	 */
	@RequestMapping("list")
	public String goContactUsPage(@ModelAttribute("cri") Criteria cri, Model model) {
		model.addAttribute("contact", contactService.contactSelectListAll(cri));

		/* 페이징 계산하기 */
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(contactService.contactSelectListCount(cri));

		/* 페이징 정보 */
		model.addAttribute("pageMaker", pageMaker);

		return "contact/contact-list";
	}

	/**
	 * 문의하기 작성 페이지로 이동
	 */
	@RequestMapping("regist")
	public String goContactUsInsertPage(Model model) {
		return "contact/contact-regist";
	}

	/**
	 * 문의하기 작성하기
	 * @param model
	 */
	@RequestMapping("regist/insert")
	public String contactUsInsert(@ModelAttribute Contact model) {
		contactService.contactUsInsert(model);
		return "redirect:/contact/list";
	}

	/**
	 * 문의하기 상세보기
	 * @param contactMoodel
	 * @param model
	 * @return
	 */
	@RequestMapping("view")
	public String contactUsSelectOne(@RequestParam("contactNo") int contactNo,
		@ModelAttribute("cri") Criteria cri, Model model) {
		model.addAttribute("user", UserSessionUtils.currentUserInfo());
		model.addAttribute("model", contactService.contactUsSelectOne(contactNo));
		model.addAttribute("contactComment", contactService.contactCommentSelectListAll(contactNo));

		return "contact/contact-view";
	}

	/**
	 * 문의하기에 비밀번호가 있나 확인
	 * @param contactMoodel
	 * @param model
	 * @return
	 */
	@RequestMapping("password/is")
	public String contactUsPasswordSelectOne(@ModelAttribute Contact contactMoodel, Model model) {
		model.addAttribute("contact", contactMoodel.getContactNo());
		return "contact/contact-password";
	}

	/**
	 * 문의하기 비밀번호 확인 페이지로 이동
	 * TODO 추후에 다이얼로그
	 * @param contactMoodel
	 * @param model
	 */
	@RequestMapping("password")
	public String goContactUsPasswordCheckPage(@ModelAttribute Contact contactMoodel, Model model) {
		model.addAttribute("contact", contactMoodel);
		return "contact/contact-password";
	}

	/**
	 * 해당 문의하기의 비밀번호 확인
	 * @param contactMoodel
	 * @param model
	 */
	@RequestMapping("password/check")
	public ResponseEntity<Integer> contactUsPasswordCheck(@ModelAttribute Contact contactMoodel, Model model) {
		return new ResponseEntity<Integer>(contactService.contactUsPasswordCheck(contactMoodel), HttpStatus.OK);
	}

	@RequestMapping("delete")
	public String contactUsDelete(@ModelAttribute Contact contactMoodel) {
		contactService.contactUsDelete(contactMoodel);
		return "redirect:/contact/list";
	}

	@RequestMapping("comment/regist")
	public String contactCommentInsert(@ModelAttribute ContactComment contactCommentModel, Model model) {
		contactService.contactCommentInsert(contactCommentModel);

		/* 세션에 저장된 사용자 정보 */
		model.addAttribute("user", UserSessionUtils.currentUserInfo());
		model.addAttribute("comment", contactService.contactCommentLastSelect());

		return "contact/contact-comment-form";
	}

	@RequestMapping(value = "comment/delete", method = RequestMethod.GET)
	public ResponseEntity<String> contactCommentDelete(@RequestParam("contactCommentNo") int contactCommentNo) {
		contactService.contactCommentDelete(contactCommentNo);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
