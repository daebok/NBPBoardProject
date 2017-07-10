package com.hyunhye.board.service;



import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.hyunhye.board.dto.QuestionDto;

public interface QuestionService {
	public void listAll(Model model);

	void regist(HttpSession session, Model model, QuestionDto dto);

	public QuestionDto read(Model model, QuestionDto dto);

	int countArticle(String searchOption, String keyword) throws Exception;

	void listAll(Model model, String searchOption, String keyword) throws Exception;

	
}
