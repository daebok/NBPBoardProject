package com.hyunhye.board.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	// xml에 설정된 리소스 참조
	// bean의 id가 uploadPath인 태그를 참조
	@Resource(name = "uploadPath")
	String uploadPath;

	@RequestMapping(value = { "/", "/home.do" })
	public String home(Model model) {
		service.listAll(model);

		return "home";
	}

	// Paging
	@RequestMapping("list.do")
	// @RequestParam(defaultValue="") ==> 기본값 할당 : 현재페이지를 1로 초기화
	public ModelAndView list(@RequestParam(defaultValue = "TITLE") String searchOption,
			@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int curPage,
			QuestionDto dto) throws Exception {

		// 레코드의 갯수 계산
		int count = service.countArticle(searchOption, keyword);

		// 페이지 나누기 관련 처리
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

	// 업로드 흐름 : 업로드 버튼클릭 => 임시디렉토리에 업로드=> 지정된 디렉토리에 저장 => 파일정보가 file에 저장
	@RequestMapping(value = "/upload/uploadForm", method = RequestMethod.GET)
	public void uplodaForm() {
		// upload/uploadForm.jsp(업로드 페이지)로 포워딩
	}

	@RequestMapping("/question/ask")
	public ModelAndView write(MultipartFile file, HttpSession session, HttpServletRequest request, Model model,
			QuestionDto dto) throws Exception {
		model.addAttribute("request", request);
		service.regist(session, model, dto);

		logger.info("파일이름 :" + file.getOriginalFilename());
		logger.info("파일크기 : " + file.getSize());
		logger.info("컨텐트 타입 : " + file.getContentType());

		String savedName = file.getOriginalFilename();

		File target = new File(uploadPath, savedName);

		// 임시디렉토리에 저장된 업로드된 파일을 지정된 디렉토리로 복사
		// FileCopyUtils.copy(바이트배열, 파일객체)
		FileCopyUtils.copy(file.getBytes(), target);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/home.do");
		mv.addObject("savedName", savedName);

		return mv; // uploadResult.jsp(결과화면)로 포워딩
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
