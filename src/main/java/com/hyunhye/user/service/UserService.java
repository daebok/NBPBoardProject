package com.hyunhye.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hyunhye.board.model.Board;
import com.hyunhye.board.model.PageCriteria;
import com.hyunhye.naver.ouath.model.NaverUser;
import com.hyunhye.security.UserAuthenticationService;
import com.hyunhye.user.model.BoardUser;
import com.hyunhye.user.repository.UserRepository;
import com.hyunhye.utils.UserSessionUtils;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserAuthenticationService userAuthenticationService;

	@Autowired
	private BCryptPasswordEncoder encoder;

	/**
	 * {@link BoardUser} 추가하기 (회원가입)
	 * @param user 사용자 정보
	 */
	public void insertUser(BoardUser user) {
		user.setUserPassword(encoder.encode(user.getUserPassword()));
		userRepository.insertUser(user);
	}

	/**
	 * {@link BoardUser} 아이디 중복 확인
	 * @param userId 사용자 아이디
	 * @return 중복된 아이디가 있으면 1, 없으면 0
	 */
	public int checkUserIdDuplication(String userId) {
		return userRepository.checkUserIdDuplication(userId);
	}

	/**
	 * 네이버 아이디로 로그인 시, 회원가입 별도 처리
	 * @param naverUser
	 */
	public void insertNaverUser(NaverUser naverUser) {

		int check = userRepository.selectNaverUser(naverUser.getEmail());

		if (check >= 1) {
			setAuthentication(naverUser);

			return;
		}

		BoardUser user = new BoardUser();
		user.setUserId(naverUser.getEmail());
		user.setUserName(naverUser.getName());

		UUID uuid = UUID.randomUUID();
		user.setUserPassword(encoder.encode(uuid.toString()));

		userRepository.insertUser(user);
		setAuthentication(naverUser);
	}

	/**
	 * UserDetailsService를 이용해서 시큐리티 로그인
	 * @param naverUser
	 */
	public void setAuthentication(NaverUser naverUser) {
		UserDetails userDetails = userAuthenticationService.loadUserByUsername(naverUser.getEmail());

		SecurityContextHolder.getContext().setAuthentication(
			new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
	}

	/**
	 * 현재 비밀번호 체크
	 * @param user 현재 비밀번호
	 * @return 일치하면 true, 그렇지 않으면 false
	 */
	public boolean checkUserPassword(BoardUser user) {
		return encoder.matches(user.getUserPassword(), UserSessionUtils.currentUserPassword());
	}

	/**
	 * {@link BoardUser} 비밀번호 변경
	 * @param user 변경된 비밀번호
	 */
	public void updatePassword(BoardUser user) {
		user.setUserPassword(encoder.encode(user.getUserPassword()));
		user.setUserNo(UserSessionUtils.currentUserNo());
		userRepository.updatePassword(user);
	}

	/**
	 * @param criteria 페이징 정보
	 * @return {@link BoardUser} 전체 리스트
	 */
	public List<BoardUser> selectAllUserList(PageCriteria criteria) {
		return userRepository.selectAllUserList(criteria);
	}

	/**
	 * {@link BoardUser} 권한 변경
	 * @param user 사용자 정보, 변경된 권한
	 */
	public void updateUserAuthority(BoardUser user) {
		userRepository.updateUserAuthority(user);
	}

	/**
	 * {@link BoardUser}가 작성한 {@link Board}와 함께 사용자 삭제
	 * @param user 사용자 번호
	 */
	public void deleteUserWithBoard(BoardUser user) {
		userRepository.deleteUserWithBoard(user);
	}

	/**
	 * {@link BoardUser}가 작성한 {@link Board}는 남겨두고 사용자만 삭제
	 * @param user 사용자 번호
	 */
	public void deleteOnlyUser(BoardUser user) {
		userRepository.deleteOnlyUser(user);
	}

	/**
	 * @param criteria 페이징 정보
	 * @return {@link BoardUser} 전체 리스트 개수
	 */
	public int selectAllUserCount(PageCriteria criteria) {
		return userRepository.selectAllUserCount(criteria);
	}

	/**
	 * @param user 사용자 아이디
	 * @return 검색한 {@link BoardUser} 정보
	 */
	public BoardUser searchUserInfo(BoardUser user) {
		return userRepository.searchUserInfo(user);
	}

	/**
	 * {@link BoardUser} 번호를 통해, 사용자 아이디 가져오기
	 * @param userNo 사용자 번호
	 * @return 사용자 아이디
	 */
	@Cacheable("user")
	public String selectUserId(int userNo) {
		return userRepository.selectUserId(userNo);
	}
}
