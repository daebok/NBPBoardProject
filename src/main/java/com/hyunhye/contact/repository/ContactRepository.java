package com.hyunhye.contact.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyunhye.board.model.PageCriteria;
import com.hyunhye.contact.model.Contact;
import com.hyunhye.contact.model.ContactComment;

@Repository
public interface ContactRepository {

	/**
	 * {@link Contact} 추가하기
	 * @param contact 문의하기 정보
	 */
	public void insertContact(Contact contact);

	/**
	 * @param criteria 페이징 정보
	 * @return {@link Contact} 전체 리스트
	 */
	public List<Contact> selectAllContactList(PageCriteria criteria);

	/**
	 * @param contact 문의사항 번호
	 * @return {@link Contact} 상세보기
	 */
	public Contact selectContactDetail(Contact contact);

	/**
	 * @param contact 문의사항 번호, 해당 비밀번호
	 * @return {@link Contact} 비밀번호 체크
	 */
	public String checkContactPassword(Contact contact);

	/**
	 * {@link Contact} 삭제하기
	 * @param contact 문의사항 번호
	 */
	public void deleteContact(Contact contact);

	/**
	 * @param criteria 페이징 정보
	 * @return {@link Contact} 전체 리스트 개수
	 */
	public int selectAllContactCount(PageCriteria criteria);

	/**
	 * {@link Contact} 댓글 추가하기
	 * @param contactComment {@link Contact} 댓글 정보
	 */
	public void insertContactComment(ContactComment contactComment);

	/**
	 * @param contact 문의사항 번호
	 * @return {@link Contact} 댓글 전체 리스트
	 */
	public List<ContactComment> selectAllContactCommentList(int contactNo);

	/**
	 * {@link Contact} 댓글 삭제
	 * @param contactComment 문의사항 댓글 번호
	 */
	public void deleteContactComment(ContactComment contactComment);

	/**
	 * {@link Contact} 작성자 확인
	 * @param contactNo 문의사항 번호
	 * @return {@link Contact}이 있는 경우, 작성자 번호 / 없으면, 0
	 */
	public int checkUser(int contactNo);
}