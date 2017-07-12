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
		return sqlSession.selectList(namespace + ".listDao");
	}

	@Override
	public List<CategoryModel> categoryListAll() {
		return sqlSession.selectList(namespace3 + ".categoryListDao");
	}
	
	@Override
	public void regist(BoardModel dto) {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace + ".insertDao", dto);
	}

	@Override
	public BoardModel read(int id) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + ".selectDao", id);
	}

	// 05. Question All List
	@Override
	public List<BoardModel> listAll(String searchOption, String keyword) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		return sqlSession.selectList(namespace + ".listAllDao", map);
	}
	
	// 07. Questions Count
	@Override
	public int countArticle(String searchOption, String keyword) {
		// TODO Auto-generated method stub

	    Map<String, String> map = new HashMap<String, String>();
	    map.put("searchOption", searchOption);
	    map.put("keyword", keyword);
	    return sqlSession.selectOne(namespace + ".countArticleDao", map);
	}

	public BoardModel modify(BoardModel dto) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + ".updateDao", dto);
	}

	@Override
	public void delete(BoardModel dto) {
		// TODO Auto-generated method stub
		sqlSession.delete(namespace + ".deleteDao", dto);
	}

	@Override
	public List<BoardModel> listAll(int start, int end, String searchOption, String keyword) {
		// TODO Auto-generated method stub
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("searchOption", searchOption);
	    map.put("keyword", keyword);
	    map.put("start", start);
	    map.put("end", end);
	    return sqlSession.selectList(namespace + ".listAllPagingDao", map);	}

	@Override
	public void insertFile(Map<String, Object> map) {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace2 + ".insertFile", map);
	}
}
