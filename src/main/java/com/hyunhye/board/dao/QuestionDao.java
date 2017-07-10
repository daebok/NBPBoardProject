package com.hyunhye.board.dao;

import java.util.List;

import com.hyunhye.board.dto.QuestionDto;

public interface QuestionDao {
	public List<QuestionDto> listAll();
	public void regist(QuestionDto dto);
	public QuestionDto read(QuestionDto dto);
	List<QuestionDto> listAll(String searchOption, String keyword) throws Exception;
	int countArticle(String searchOption, String keyword);
}
