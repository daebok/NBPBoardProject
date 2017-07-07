package com.hyunhye.board.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hyunhye.board.dao.ContentDao;
import com.hyunhye.board.dao.IDao;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	ContentDao dao;

	@Autowired
	private SqlSession sqlSession;

	@Autowired
	public void setDao(ContentDao dao) {
		this.dao = dao;
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@RequestMapping(value = "/list")
	public String list(Model model) {
		IDao dao = sqlSession.getMapper(IDao.class);
		model.addAttribute("list", dao.listDao());

		return "list";
	}

}
