package com.hyunhye.board.daoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.hyunhye.board.dto.QuestionDto;

@Repository(value = "QuestionDao")
public class QuestionDaoImpl implements com.hyunhye.board.dao.QuestionDao {

	@Inject
	SqlSession sqlSession;

	private static final String namespace = "com.hyunhye.board.questionMapper";

	@Override
	public List<QuestionDto> listAll() {
		return sqlSession.selectList(namespace + ".listDao");
	}

	@Override
	public void regist(QuestionDto dto) {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace + ".insertDao", dto);
	}

	@Override
	public QuestionDto read(QuestionDto dto) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + ".selectDao", dto);
	}

	// 05. �Խñ� ��ü ���
	@Override
	public List<QuestionDto> listAll(String searchOption, String keyword) throws Exception {
		// �˻��ɼ�, Ű���� �ʿ� ����
		Map<String, String> map = new HashMap<String, String>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		return sqlSession.selectList(namespace + ".listAllDao", map);
	}
	
	// 07. �Խñ� ���ڵ� ����
	@Override
	public int countArticle(String searchOption, String keyword) {
		// TODO Auto-generated method stub
		// �˻��ɼ�, Ű���� �ʿ� ����
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("searchOption", searchOption);
	    map.put("keyword", keyword);
	    return sqlSession.selectOne(namespace + ".countArticleDao", map);
	}
}
