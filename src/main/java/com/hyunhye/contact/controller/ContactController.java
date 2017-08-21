package com.hyunhye.contact.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hyunhye.board.model.PageCriteria;
import com.hyunhye.board.model.PageMaker;
import com.hyunhye.contact.model.Contact;
import com.hyunhye.contact.service.ContactService;
import com.hyunhye.utils.UriUtils;
import com.hyunhye.utils.UserSessionUtils;

@RequestMapping("contact")
@Controller
public class ContactController {

	@Autowired
	private ContactService contactService;


	/**
	 * {@link Contact} 전체 리스트 가져오기
	 * @param criteria 페이징 정보
	 * @return 문의사항 페이지로 이동
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String goContactUsPage(@ModelAttribute("criteria") PageCriteria criteria, Model model,
		HttpServletRequest request) {

		model.addAttribute("contact", contactService.selectAllContactList(criteria));

		UriUtils.getUri(request);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(criteria);
		pageMaker.setTotalCount(contactService.selectAllContactCount(criteria));

		model.addAttribute("pageMaker", pageMaker);

		return "contact/contact-list";
	}

	/**
	 * {@link Contact} 작성 페이지로 이동
	 * @param model
	 * @return 문의사항 작성 페이지로 이동
	 */
	@RequestMapping("regist")
	public String goContactUsRegistPage(Model model) {
		return "contact/contact-regist";
	}

	/**
	 * {@link Contact} 작성하기
	 * @param contact
	 */
	@RequestMapping(value = "regist/insert", method = RequestMethod.POST)
	public String insertContact(@ModelAttribute Contact contact) {
		contactService.insertContact(contact);
		return "redirect:/contact/list";
	}

	/**
	 * {@link Contact} 상세보기
	 * @param contact 문의사항 번호
	 * @return 문의사항 상세보기 페이지
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public String selectContactDetail(@ModelAttribute("criteria") PageCriteria criteria,
		@ModelAttribute Contact contact, Model model, HttpServletRequest request) {

		model.addAttribute("user", UserSessionUtils.currentUserInfo());
		model.addAttribute("model", contactService.selectContactDetail(contact));
		model.addAttribute("contactComment", contactService.selectAllContactCommentList(contact.getContactNo()));
		model.addAttribute("uri", request.getSession().getAttribute("uri"));

		return "contact/contact-view";
	}

	/**
	 * {@link Contact} 상세보기 시, 비밀번호가 있나 없나 확인
	 * @param contact 문의사항 번호
	 * @return 비밀번호 확인 페이지
	 */
	@RequestMapping(value = "password/is", method = RequestMethod.GET)
	public String checkContactHasPassword(@ModelAttribute Contact contact, Model model) {
		model.addAttribute("contact", contact.getContactNo());
		return "contact/contact-password";
	}

	/**
	 * {@link Contact} 비밀번호 확인 페이지로 이동
	 * @param contact 문의사항 번호
	 * @return 비밀번호 확인 페이지
	 */
	@RequestMapping(value = "password", method = RequestMethod.GET)
	public String goContactPasswordCheckPage(@ModelAttribute Contact contact, Model model) {
		model.addAttribute("contact", contact);
		return "contact/contact-password";
	}

	/**
	 * {@link Contact} 비밀번호 확인
	 * @param contact 문의사항 번호, 해당 비밀번호
	 */
	@RequestMapping(value = "password/check", method = RequestMethod.POST)
	public ResponseEntity<Integer> checkContactPassword(@ModelAttribute Contact contact, Model model) {
		return new ResponseEntity<Integer>(contactService.checkContactPassword(contact), HttpStatus.OK);
	}

	/**
	 * {@link Contact} 삭제하기
	 * @param contact
	 */
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String deleteContact(@ModelAttribute Contact contact) {
		contactService.deleteContact(contact);
		return "redirect:/contact/list";
	}

}
