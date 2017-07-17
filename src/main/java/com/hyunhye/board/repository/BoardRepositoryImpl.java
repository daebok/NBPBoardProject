package com.hyunhye.board.repository;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.hyunhye.board.model.BoardModel;
import com.hyunhye.board.model.CategoryModel;
import com.hyunhye.board.model.FileModel;
import com.hyunhye.board.model.SearchCriteria;

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
		sqlSession.insert(namespace + ".regist", model);
	}

	@Override
	public BoardModel read(int boardId) {
		return sqlSession.selectOne(namespace + ".read", boardId);
	}

	public BoardModel modify(BoardModel model) {
		return sqlSession.selectOne(namespace + ".modify", model);
	}

	@Override
	public void delete(BoardModel model) {
		sqlSession.delete(namespace + ".delete", model);
	}

	@Override
	public void addAttach(FileModel model) {
		sqlSession.insert(namespace2 + ".addAttach", model);
	}

	@Override
	public List<FileModel> getAttach(int boardId) {
		return sqlSession.selectList(namespace2 + ".getAttach", boardId);
	}

	@Override
	public List<BoardModel> listCriteria(SearchCriteria cri) throws Exception {
		return sqlSession.selectList(namespace + ".listCriteria", cri);
	}

	@Override
	public int countPaging(SearchCriteria cri) throws Exception {
		return sqlSession.selectOne(namespace + ".countPaging", cri);
	}
}