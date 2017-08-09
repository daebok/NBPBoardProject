package com.hyunhye.contact.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyunhye.board.model.Criteria;
import com.hyunhye.common.UserSessionUtils;
import com.hyunhye.contact.model.Contact;
import com.hyunhye.contact.repository.ContactRepository;

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

	public Contact contactUsSelectOne(Contact contactMoodel) {
		return contactRepository.contactUsSelectOne(contactMoodel);
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

}