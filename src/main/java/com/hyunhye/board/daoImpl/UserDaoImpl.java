package com.hyunhye.board.daoImpl;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.hyunhye.board.dto.UserDto;

@Repository(value = "UserDao")
public class UserDaoImpl implements com.hyunhye.board.dao.UserDao{

	@Inject
	SqlSession sqlSession;

	private static final String namespace = "com.hyunhye.board.userMapper";

	@Override
	public List<UserDto> listAll() {

		return sqlSession.selectList(namespace + ".listDao");

	}

	@Override
	public void regist(UserDto dto) {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace + ".insertDao", dto);
	}

	@Override
	public void delete(UserDto dto) {
		// TODO Auto-generated method stub
		sqlSession.delete(namespace + ".deleteDao", dto);
	}

	// Login Check
	@Override
	public boolean loginCheck(UserDto dto) {
		// TODO Auto-generated method stub
		String name = sqlSession.selectOne("loginCheck", dto);
        return (name == null) ? false : true;
	}

	// Login Info
	@Override
	public UserDto viewUser(UserDto dto) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("viewUser", dto);
	}

	// Login logout
    @Override
    public void logout(HttpSession session) {
    }

}
