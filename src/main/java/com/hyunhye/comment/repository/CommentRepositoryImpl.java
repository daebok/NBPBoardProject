package com.hyunhye.comment.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hyunhye.comment.model.CommentModel;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

	@Autowired
	private SqlSession sqlSession;

	private static final String namespace = "com.hyunhye.board.commentMapper";

	@Override
	public List<CommentModel> list(int boardId) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + ".list", boardId);
	}

	@Override
	public void create(CommentModel model) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace + ".create", model);
	}

	@Override
	public void update(CommentModel model) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(namespace + ".update", model);
	}

	@Override
	public void delete(int boardId) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(namespace + ".delete", boardId);
	}

}
