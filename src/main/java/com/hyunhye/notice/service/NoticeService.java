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

	/** 공지사항 **/
	/* 공지사항  리스트 가져오기 */
	@Cacheable("notice")
	public List<Notice> noticeListAll() {
		return noticeRepository.noticeListAll();
	}

	/* 공지사항  추가 */
	@CacheEvict(value = "notice", allEntries = true)
	public void noticeInsert(Notice noticeModel) {
		noticeModel.setUserNo(UserSessionUtils.currentUserNo());

		noticeRepository.noticeInsert(noticeModel);
	}

	/* 공지사항  상세보기 */
	public Notice noticeSelectOne(Notice noticeModel) {
		return noticeRepository.noticeSelectOne(noticeModel);
	}

	/* 공지사항  삭제하기 */
	public void noticeDelete(Notice noticeModel) {
		noticeRepository.noticeDelete(noticeModel);
	}

	/* 공지사항  수정하기  */
	public void noticeUpdate(Notice noticeModel) {
		noticeRepository.noticeUpdate(noticeModel);
	}

	public int isExistedNotice(int noticeNo) {
		return noticeRepository.isExistedNotice(noticeNo);
	}

}
