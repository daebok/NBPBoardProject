package com.hyunhye.user.repository;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.hyunhye.user.model.UserModel;

@Repository(value = "UserRepository")
public class UserRepositoryImpl implements UserRepository {

	@Inject
	SqlSession sqlSession;

	private static final String namespace = "com.hyunhye.board.userMapper";

	@Override
	public List<UserModel> listAll() {

		return sqlSession.selectList(namespace + ".list");

	}

	@Override
	public void regist(UserModel model) {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace + ".regist", model);
	}

	@Override
	public void delete(UserModel model) {
		// TODO Auto-generated method stub
		sqlSession.delete(namespace + ".delete", model);
	}

	// Login Check
	@Override
	public boolean loginCheck(UserModel model) {
		// TODO Auto-generated method stub
		String name = sqlSession.selectOne(namespace + ".loginCheck", model);
		return (name == null) ? false : true;
	}

	// Login Info
	@Override
	public UserModel viewUser(UserModel model) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + ".viewUser", model);
	}

	// Login logout
	@Override
	public void logout(HttpSession session) {}

	public int select(String id) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + ".select", id);
	}

}
