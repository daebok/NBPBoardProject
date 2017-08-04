package com.hyunhye.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyunhye.board.model.BoardModel;
import com.hyunhye.board.model.HomeModel;
import com.hyunhye.board.repository.HomeRepository;

@Service
public class HomeService {

	@Autowired
	public HomeRepository homeRepository;

	/** 게시글  Top10 리스트 **/
	public List<BoardModel> selectBoardList(HomeModel homeModel) {
		return homeRepository.selectBoardList(homeModel);
	}
}
