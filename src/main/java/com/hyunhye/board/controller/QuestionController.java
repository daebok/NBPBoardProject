package com.hyunhye.board.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hyunhye.board.dto.QuestionDto;
import com.hyunhye.board.serviceImpl.BoardPager;
import com.hyunhye.board.serviceImpl.QuestionServiceImpl;

@Controller
public class QuestionController {
	private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

	@Autowired
	public QuestionServiceImpl service;

	@Resource(name = "uploadPath")
	String uploadPath;

	@RequestMapping(value = { "/", "/home.do" })
	public String home(Model model) {
		service.listAll(model);

		return "home";
	}

	// Paging
	@RequestMapping("list.do")
	public ModelAndView list(@RequestParam(defaultValue = "TITLE") String searchOption,
			@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int curPage,
			QuestionDto dto) throws Exception {

		// questions count
		int count = service.countArticle(searchOption, keyword);

		BoardPager boardPager = new BoardPager(count, curPage);
		int start = boardPager.getPageBegin();
		int end = boardPager.getPageEnd();

		List<QuestionDto> list = service.listAll(start, end, searchOption, keyword, dto);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("count", count);
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		map.put("boardPager", boardPager);

		ModelAndView mv = new ModelAndView();
		mv.addObject("map", map);
		mv.setViewName("list");

		return mv;
	}

	@RequestMapping(value = "/upload/uploadForm", method = RequestMethod.GET)
	public void uplodaForm() {
	}

	@RequestMapping("/question/ask")
	public ModelAndView write(MultipartFile file, HttpSession session, HttpServletRequest request, Model model,
			QuestionDto dto) throws Exception {
		model.addAttribute("request", request);
		service.regist(session, model, dto);

		logger.info("file name :" + file.getOriginalFilename());
		logger.info("file size : " + file.getSize());
		logger.info("content type : " + file.getContentType());

		String savedName = file.getOriginalFilename();

		File target = new File(uploadPath, savedName);

		FileCopyUtils.copy(file.getBytes(), target);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/home.do");
		mv.addObject("savedName", savedName);

		return mv; 
	}
	
	// file name overlap prevent
   @SuppressWarnings("unused")
	private String uploadFile(String originalName, byte[] fileData) throws Exception{
        UUID uuid = UUID.randomUUID();
        String savedName = uuid.toString()+"_"+originalName;
        File target = new File(uploadPath, savedName);
        FileCopyUtils.copy(fileData, target);
        return savedName;
    }

	@RequestMapping("/answer.do")
	public ModelAndView read(HttpServletRequest request, Model model, QuestionDto dto) {
		model.addAttribute("request", request);
		service.read(model, dto);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("answer");
		mv.addObject("dto", service.read(model, dto));
		return mv;
	}

	@RequestMapping("search.do")
	public ModelAndView list(Model model, @RequestParam(defaultValue = "TITLE") String searchOption,
			@RequestParam(defaultValue = "") String keyword) throws Exception {

		service.listAll(model, searchOption, keyword);
		int count = service.countArticle(searchOption, keyword);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);

		ModelAndView mv = new ModelAndView();
		mv.addObject("map", map);
		mv.setViewName("search");
		return mv;
	}

	@RequestMapping("modify.do")
	public ModelAndView modify(HttpServletRequest request, Model model, QuestionDto dto) {
		model.addAttribute("request", request);
		service.read(model, dto);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("modify");
		mv.addObject("dto", service.read(model, dto));
		return mv;
	}

	@RequestMapping("/question/modify")
	public String modify(HttpSession session, HttpServletRequest request, Model model, QuestionDto dto) {
		model.addAttribute("request", request);
		service.modify(session, model, dto);

		return "forward:/answer.do?id=" + dto.getBID();
	}

	@RequestMapping("delete.do")
	public String delete(@RequestParam int id, QuestionDto dto) throws Exception {
		service.delete(id, dto);
		return "redirect:home.do";
	}
}
