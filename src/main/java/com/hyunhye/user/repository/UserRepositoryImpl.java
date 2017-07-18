package com.hyunhye.user.repository;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hyunhye.user.model.UserModel;

@Repository
public class UserRepositoryImpl implements UserRepository {

	@Autowired
	SqlSession sqlSession;

	private static final String namespace = "com.hyunhye.board.userMapper";


	@Override
	public void regist(UserModel model) {
		sqlSession.insert(namespace + ".regist", model);
	}

	@Override
	public void delete(UserModel model) {
		sqlSession.delete(namespace + ".delete", model);
	}

	@Override
	public boolean loginCheck(UserModel model) {
		String name = sqlSession.selectOne(namespace + ".loginCheck", model);
		return (name == null) ? false : true;
	}

	@Override
	public UserModel viewUser(UserModel model) {
		return sqlSession.selectOne(namespace + ".viewUser", model);
	}

	@Override
	public void logout(HttpSession session) {}

	@Override
	public int select(String id) {
		return sqlSession.selectOne(namespace + ".select", id);
	}

}
