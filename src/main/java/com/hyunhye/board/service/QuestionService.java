package com.hyunhye.board.service;



import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.hyunhye.board.dto.QuestionDto;

public interface QuestionService {
	public void listAll(Model model);

	public int countArticle(String searchOption, String keyword) throws Exception;

	public void listAll(Model model, String searchOption, String keyword) throws Exception;

	public void delete(int bid, QuestionDto dto);

	public void regist(HttpSession session, QuestionDto dto);

	public QuestionDto read(int id);

	public QuestionDto modify(HttpSession session, QuestionDto dto);

	public List<QuestionDto> listAll(int start, int end, String searchOption, String keyword);

	
}
