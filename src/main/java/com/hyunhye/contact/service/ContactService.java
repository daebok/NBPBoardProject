package com.hyunhye.contact.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyunhye.board.model.Criteria;
import com.hyunhye.contact.model.Contact;
import com.hyunhye.contact.model.ContactComment;
import com.hyunhye.contact.repository.ContactRepository;
import com.hyunhye.utils.UserSessionUtils;

@Service
public class ContactService {

	@Autowired
	private ContactRepository contactRepository;

	public void contactUsInsert(Contact model) {
		model.setUserNo(UserSessionUtils.currentUserNo());
		contactRepository.contactUsInsert(model);
	}

	public int contactSelectListCount(Criteria cri) {
		return contactRepository.contactSelectListCount(cri);
	}

	public List<Contact> contactSelectListAll(Criteria cri) {
		return contactRepository.contactSelectListAll(cri);
	}

	public Contact contactUsSelectOne(int contactNo) {
		return contactRepository.contactUsSelectOne(contactNo);
	}

	public Integer contactUsPasswordSelectCount(Contact contactMoodel) {
		return contactRepository.contactUsPasswordSelectCount(contactMoodel);
	}

	public Integer contactUsPasswordCheck(Contact contactMoodel) {
		return contactRepository.contactUsPasswordCheck(contactMoodel);
	}

	public void contactUsDelete(Contact contactMoodel) {
		contactRepository.contactUsDelete(contactMoodel);
	}

	public void contactCommentInsert(ContactComment contactCommentModel) {
		contactCommentModel.setUserNo(UserSessionUtils.currentUserNo());
		contactRepository.contactCommentInsert(contactCommentModel);
	}

	public ContactComment contactCommentLastSelect() {
		return contactRepository.contactCommentLastSelect();
	}

	public List<ContactComment> contactCommentSelectListAll(int contactNo) {
		return contactRepository.contactCommentSelectListAll(contactNo);
	}

	public void contactCommentDelete(int contactCommentNo) {
		contactRepository.contactCommentDelete(contactCommentNo);
	}

	public int checkUser(int contactNo) {
		return contactRepository.checkUser(contactNo);
	}

}
