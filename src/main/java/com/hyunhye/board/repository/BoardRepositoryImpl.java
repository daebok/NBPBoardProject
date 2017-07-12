package com.hyunhye.board.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.hyunhye.board.model.CategoryModel;
import com.hyunhye.board.model.BoardModel;

@Repository(value = "BoardRepository")
public class BoardRepositoryImpl implements BoardRepository {

	@Inject
	SqlSession sqlSession;

	private static final String namespace = "com.hyunhye.board.boardMapper";
	private static final String namespace2 = "com.hyunhye.board.fileMapper";
	private static final String namespace3 = "com.hyunhye.board.categoryMapper";

	@Override
	public List<BoardModel> listAll() {
		return sqlSession.selectList(namespace + ".list");
	}

	@Override
	public List<CategoryModel> categoryListAll() {
		return sqlSession.selectList(namespace3 + ".categoryList");
	}
	
	@Override
	public void regist(BoardModel model) {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace + ".regist", model);
	}

	@Override
	public BoardModel read(int id) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + ".read", id);
	}

	// 05. Question All List
	@Override
	public List<BoardModel> listAll(String searchOption, String keyword) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		return sqlSession.selectList(namespace + ".listAll", map);
	}
	
	// 07. Questions Count
	@Override
	public int countArticle(String searchOption, String keyword) {
		// TODO Auto-generated method stub

	    Map<String, String> map = new HashMap<String, String>();
	    map.put("searchOption", searchOption);
	    map.put("keyword", keyword);
	    return sqlSession.selectOne(namespace + ".count", map);
	}

	public BoardModel modify(BoardModel model) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + ".modify", model);
	}

	@Override
	public void delete(BoardModel model) {
		// TODO Auto-generated method stub
		sqlSession.delete(namespace + ".delete", model);
	}

	@Override
	public List<BoardModel> listAll(int start, int end, String searchOption, String keyword) {
		// TODO Auto-generated method stub
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("searchOption", searchOption);
	    map.put("keyword", keyword);
	    map.put("start", start);
	    map.put("end", end);
	    return sqlSession.selectList(namespace + ".listAllPaging", map);	}

	@Override
	public void insertFile(Map<String, Object> map) {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace2 + ".insertFile", map);
	}
}
