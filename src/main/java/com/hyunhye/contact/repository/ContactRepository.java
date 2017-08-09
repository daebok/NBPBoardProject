package com.hyunhye.contact.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyunhye.board.model.Criteria;
import com.hyunhye.contact.model.Contact;
import com.hyunhye.contact.model.ContactComment;

@Repository
public interface ContactRepository {

	public void contactUsInsert(Contact model);

	public List<Contact> contactSelectListAll(Criteria cri);

	public Contact contactUsSelectOne(int contactNo);

	public Integer contactUsPasswordSelectCount(Contact contactMoodel);

	public Integer contactUsPasswordCheck(Contact contactMoodel);

	public void contactUsDelete(Contact contactMoodel);

	public int contactSelectListCount(Criteria cri);

	public void contactCommentInsert(ContactComment contactCommentModel);

	public ContactComment contactCommentLastSelect();

	public List<ContactComment> contactCommentSelectListAll(int contactNo);

	public void contactCommentDelete(int contactCommentNo);

}