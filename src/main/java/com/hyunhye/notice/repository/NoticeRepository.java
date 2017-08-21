package com.hyunhye.notice.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyunhye.notice.model.Notice;

@Repository
public interface NoticeRepository {
	/**
	 * {@link Notice} 추가하기
	 * @param notice
	 */
	public void insertNotice(Notice notice);

	/**
	 * @return {@link Notice} 전체 리스트
	 */
	public List<Notice> selectAllNoticeList();

	/**
	 * @param notice 공지사항 번호
	 * @return {@link Notice} 상세보기
	 */
	public Notice selectNoticeDetail(Notice notice);

	/**
	 * {@link Notice} 삭제하기
	 * @param notice 공지사항 번호
	 */
	public void deleteNotice(Notice notice);

	/**
	 * {@link Notice} 수정하기
	 * @param notice 공지사항 번호
	 */
	public void updateNotice(Notice notice);

	/**
	 * {@link Notice} 존재 여부 판단
	 * @param noticeNo 공지사항 번호
	 * @return 공지사항이 존재하면 1, 없으면 0 (int)
	 */
	public int isExistedNotice(int noticeNo);

}
