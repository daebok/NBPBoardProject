package com.hyunhye.board.dao;

import java.util.List;
import java.util.Map;

import com.hyunhye.board.dto.QuestionDto;

public interface QuestionDao {
	public List<QuestionDto> listAll();
	public void regist(QuestionDto dto);
	public List<QuestionDto> listAll(String searchOption, String keyword) throws Exception;
	public int countArticle(String searchOption, String keyword);
	public void delete(QuestionDto dto);
	public void insertFile(Map<String, Object> map);
	public QuestionDto read(int id);
	public List<QuestionDto> listAll(int start, int end, String searchOption, String keyword);
}
