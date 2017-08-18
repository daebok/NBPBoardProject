package com.hyunhye.notice.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyunhye.notice.model.Notice;

@Repository
public interface NoticeRepository {
	/** 공지사항  추가 **/
	public void noticeInsert(Notice noticeModel);

	/** 공지사항  리스트 가져오기 **/
	public List<Notice> noticeListAll();

	/** 공지사항  상세보기**/
	public Notice noticeSelectOne(Notice noticeModel);

	/** 공지사항  삭제하기 **/
	public void noticeDelete(Notice noticeModel);

	/** 공지사항  수정하기 **/
	public void noticeUpdate(Notice noticeModel);

	public int isExistedNotice(int noticeNo);

}
