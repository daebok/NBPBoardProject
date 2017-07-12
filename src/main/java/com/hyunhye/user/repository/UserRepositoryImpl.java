package com.hyunhye.user.repository;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.hyunhye.user.model.UserModel;

@Repository(value = "UserRepository")
public class UserRepositoryImpl implements UserRepository{

	@Inject
	SqlSession sqlSession;

	private static final String namespace = "com.hyunhye.board.userMapper";

	@Override
	public List<UserModel> listAll() {

		return sqlSession.selectList(namespace + ".listDao");

	}

	@Override
	public void regist(UserModel dto) {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace + ".insertDao", dto);
	}

	@Override
	public void delete(UserModel dto) {
		// TODO Auto-generated method stub
		sqlSession.delete(namespace + ".deleteDao", dto);
	}

	// Login Check
	@Override
	public boolean loginCheck(UserModel dto) {
		// TODO Auto-generated method stub
		String name = sqlSession.selectOne("loginCheck", dto);
        return (name == null) ? false : true;
	}

	// Login Info
	@Override
	public UserModel viewUser(UserModel dto) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("viewUser", dto);
	}

	// Login logout
    @Override
    public void logout(HttpSession session) {
    }

}
