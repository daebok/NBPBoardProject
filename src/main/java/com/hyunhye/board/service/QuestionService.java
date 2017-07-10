package com.hyunhye.board.service;



import org.springframework.ui.Model;

import com.hyunhye.board.dto.QuestionDto;

public interface QuestionService {
	public void listAll(Model model);

	public void regist(Model model, QuestionDto dto);

	public void read(Model model, QuestionDto dto);

	int countArticle(String searchOption, String keyword) throws Exception;

	void listAll(Model model, String searchOption, String keyword) throws Exception;
}
