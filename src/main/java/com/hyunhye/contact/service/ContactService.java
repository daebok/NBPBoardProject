package com.hyunhye.contact.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hyunhye.board.model.PageCriteria;
import com.hyunhye.contact.model.Contact;
import com.hyunhye.contact.model.ContactComment;
import com.hyunhye.contact.repository.ContactRepository;
import com.hyunhye.utils.UserSessionUtils;

@Service
public class ContactService {

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	/**
	 * {@link Contact} 추가하기
	 * @param contact 믄의사항 정보
	 */
	public void insertContact(Contact contact) {
		contact.setUserNo(UserSessionUtils.currentUserNo());

		String contactPassword = contact.getContactPassword();
		if (!Objects.isNull(contactPassword)) {
			contact.setContactPassword(encoder.encode(contactPassword));
		}
		contactRepository.insertContact(contact);
	}

	/**
	 *
	 * @param criteria 페이징 정보
	 * @return {@link Contact} 전체 리스트 개수 (int)
	 */
	public int selectAllContactCount(PageCriteria criteria) {
		return contactRepository.selectAllContactCount(criteria);
	}

	/**
	 * @param criteria 페이징 정보
	 * @return {@link Contact} 전체 리스트 (List<Contact>)
	 */
	public List<Contact> selectAllContactList(PageCriteria criteria) {
		return contactRepository.selectAllContactList(criteria);
	}

	/**
	 * @param contact 문의사항 번호
	 * @return {@link Contact} 상세 보기
	 */
	public Contact selectContactDetail(Contact contact) {
		return contactRepository.selectContactDetail(contact);
	}

	/**
	 * @param contact 문의사항 번호, 비밀번호
	 * @return {@link Contact} 비밀번호 체크
	 */
	public int checkContactPassword(Contact contact) {
		String contactPassword = contactRepository.checkContactPassword(contact);
		if (encoder.matches(contact.getContactPassword(), contactPassword)) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * {@link Contact} 삭제하기
	 * @param contact 문의사항 번호
	 */
	public void deleteContact(Contact contact) {
		contactRepository.deleteContact(contact);
	}

	/**
	 * {@link Contact} 댓글 추가하기
	 * @param contactComment 댓글 정보
	 */
	public void insertContactComment(ContactComment contactComment) {
		contactComment.setUserNo(UserSessionUtils.currentUserNo());
		contactRepository.insertContactComment(contactComment);
	}

	/**
	 * @param contact 문의사항 번호
	 * @return {@link Contact} 댓글 전체 리스트 (List<ContactComment>)
	 */
	public List<ContactComment> selectAllContactCommentList(int contactNo) {
		return contactRepository.selectAllContactCommentList(contactNo);
	}

	/**
	 * {@link Contact} 댓글 삭제하기
	 * @param contactComment 문의사항 댓글 번호
	 */
	public void deleteContactComment(ContactComment contactComment) {
		contactRepository.deleteContactComment(contactComment);
	}

	/**
	 * {@link Contact} 작성자 확인
	 * @param contactNo 문의사항 번호
	 * @return {@link Contact}이 있는 경우, 작성자 번호 / 없으면, 0
	 */
	public int checkUser(int contactNo) {
		return contactRepository.checkUser(contactNo);
	}

}
