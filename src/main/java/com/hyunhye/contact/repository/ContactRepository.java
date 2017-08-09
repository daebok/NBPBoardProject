package com.hyunhye.contact.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyunhye.board.model.Criteria;
import com.hyunhye.contact.model.Contact;

@Repository
public interface ContactRepository {

	public void contactUsInsert(Contact model);

	public List<Contact> contactSelectListAll(Criteria cri);

	public Contact contactUsSelectOne(Contact contactMoodel);

	public Integer contactUsPasswordSelectCount(Contact contactMoodel);

	public Integer contactUsPasswordCheck(Contact contactMoodel);

	public void contactUsDelete(Contact contactMoodel);

	public int contactSelectListCount(Criteria cri);

}