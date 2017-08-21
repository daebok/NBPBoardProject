package com.hyunhye.notice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.hyunhye.notice.model.Notice;
import com.hyunhye.notice.repository.NoticeRepository;
import com.hyunhye.utils.UserSessionUtils;

@Service
public class NoticeService {

	@Autowired
	NoticeRepository noticeRepository;

	/**
	 * @return {@link Notice} 전체 리스트
	 */
	@Cacheable("notice")
	public List<Notice> selectAllNoticeList() {
		return noticeRepository.selectAllNoticeList();
	}

	/**
	 * {@link Notice} 추가하기.
	 * 공지사항 추가 할 때마다, 캐시 삭제하고 다시 생성
	 * @param notice
	 */
	@CacheEvict(value = "notice", allEntries = true)
	public void insertNotice(Notice notice) {
		notice.setUserNo(UserSessionUtils.currentUserNo());
		noticeRepository.insertNotice(notice);
	}

	/**
	 * @param notice 문의사항 번호
	 * @return {@link Notice} 상세보기
	 */
	public Notice selectNoticeDetail(Notice notice) {
		return noticeRepository.selectNoticeDetail(notice);
	}

	/**
	 * {@link Notice} 삭제하기
	 * @param notice 공지사항 번호
	 */
	public void deleteNotice(Notice notice) {
		noticeRepository.deleteNotice(notice);
	}

	/**
	 * {@link Notice} 수정하기
	 * @param notice 공지사항 번호
	 */
	public void updateNotice(Notice notice) {
		noticeRepository.updateNotice(notice);
	}

	/**
	 * {@link Notice} 존재 여부 판단
	 * @param noticeNo 공지사항 번호
	 * @return 공지사항이 존재하면 1, 없으면 0 (int)
	 */
	public int isExistedNotice(int noticeNo) {
		return noticeRepository.isExistedNotice(noticeNo);
	}

}
