package com.hyunhye.board.service;



import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.hyunhye.board.dto.QuestionDto;

public interface QuestionService {
	public void listAll(Model model);

	public void regist(HttpSession session, Model model, QuestionDto dto);

	public QuestionDto read(Model model, QuestionDto dto);

	public int countArticle(String searchOption, String keyword) throws Exception;

	public void listAll(Model model, String searchOption, String keyword) throws Exception;

	public QuestionDto modify(HttpSession session, Model model, QuestionDto dto);

	void delete(int bid, QuestionDto dto);

	// List<QuestionDto> listAll(int start, int end, String searchOption, String keyword);

	List<QuestionDto> listAll(int start, int end, String searchOption, String keyword, QuestionDto dto);

	
}
