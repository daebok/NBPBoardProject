package com.hyunhye.user.repository;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hyunhye.board.model.Board;
import com.hyunhye.board.model.PageCriteria;
import com.hyunhye.user.model.BoardUser;

@Repository
public interface UserRepository {

	/**
	 * {@link BoardUser} 추가하기
	 * @param user 사용자 정보
	 */
	public void insertUser(BoardUser user);

	/**
	 * {@link BoardUser} 아이디 중복 확인
	 * @param id 사용자 아이디
	 * @return 중복된 아이디 있으면 1, 없으면 0
	 */
	public int checkUserIdDuplication(String id);

	/**
	 * {@link BoardUser} 아이디를 통해, 사용자 정보 가져오기
	 * @param userId
	 * @return
	 */
	public Map<String, Object> selectUser(String userId);

	/**
	 * @param criteria 페이징 정보
	 * @return {@link BoardUser} 전체 리스트
	 */
	public List<BoardUser> selectAllUserList(PageCriteria criteria);

	/**
	 * {@link BoardUser} 권한 변경
	 * @param user 사용자 번호, 변경된 권한
	 */
	public void updateUserAuthority(BoardUser user);

	/**
	 * {@link BoardUser}가 작성한 {@link Board}와 함께 사용자 삭제하기
	 * @param user 사용자 번호
	 */
	public void deleteUserWithBoard(BoardUser user);

	/**
	 * {@link BoardUser}가 작성한 {@link Board}는 남겨두고 사용자만 삭제하기
	 * @param user 사용자 번호
	 */
	public void deleteOnlyUser(BoardUser user);

	/**
	 * 네이버 아이디로 회원가입 한 적이 있는 지 확인
	 * @param email 네이버 이메일
	 * @return 회원가입 한 적이 있으면 1, 없으면 0
	 */
	public int selectNaverUser(String email);

	/**
	 * {@link BoardUser} 비밀번호 업데이트
	 * @param user 변경하려는 비밀번호
	 */
	public void updatePassword(BoardUser user);

	/**
	 * @param criteria 페이징 정보
	 * @return {@link BoardUser} 전체 리스트 개수
	 */
	public int selectAllUserCount(PageCriteria criteria);

	/**
	 * {@link BoardUser} 정보 검색
	 * @param user 검색한 사용자 정보
	 * @return 해당 사용자 정보
	 */
	public BoardUser searchUserInfo(BoardUser user);

	/**
	 * {@link BoardUser} 번호를 통해, 사용자 아이디 가져오기
	 * @param userNo 사용자 번호
	 * @return 사용자 아이디
	 */
	public String selectUserId(int userNo);

}
