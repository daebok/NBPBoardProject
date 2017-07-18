package com.hyunhye.board.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hyunhye.board.model.BoardModel;
import com.hyunhye.board.model.CategoryModel;
import com.hyunhye.board.model.FileModel;
import com.hyunhye.board.model.SearchCriteria;

@Repository
public class BoardRepositoryImpl implements BoardRepository {

	@Autowired
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

	@Override
	public BoardModel modify(BoardModel model) {
		return sqlSession.selectOne(namespace + ".modify", model);
	}

	@Override
	public void delete(int boardId) {
		sqlSession.delete(namespace + ".delete", boardId);
	}

	@Override
	public void addAttach(FileModel model) {
		sqlSession.insert(namespace2 + ".addAttach", model);
	}

	@Override
	public List<FileModel> getAttach(int fileId) {
		return sqlSession.selectList(namespace2 + ".getAttach", fileId);
	}

	@Override
	public void deleteAttach(int fileId) throws Exception {
		sqlSession.delete(namespace2 + ".deleteAttach", fileId);
	}

	@Override
	public List<BoardModel> listCriteria(SearchCriteria cri) throws Exception {
		return sqlSession.selectList(namespace + ".listCriteria", cri);
	}

	@Override
	public int countPaging(SearchCriteria cri) throws Exception {
		return sqlSession.selectOne(namespace + ".countPaging", cri);
	}

	@Override
	public void increaseViewCount(int boardId) {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace + ".increaseViewcount", boardId);
	}
}