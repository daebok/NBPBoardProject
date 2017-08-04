package com.hyunhye.board.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyunhye.board.model.BoardModel;
import com.hyunhye.board.model.HomeModel;

@Repository
public interface HomeRepository {
	public List<BoardModel> selectBoardList(HomeModel homeModel);
}