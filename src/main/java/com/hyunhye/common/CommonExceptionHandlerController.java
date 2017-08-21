package com.hyunhye.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("com.hyunhye")
public class CommonExceptionHandlerController {
	private static final Logger logger = LoggerFactory.getLogger(CommonExceptionHandlerController.class);

	@ExceptionHandler(RuntimeException.class)
	public String runtimeException(RuntimeException runtimeException, Model model) {
		logger.info(runtimeException.getMessage());

		model.addAttribute("msg", "현재 에러가 발생하였습니다. \n 잠시후 다시 이용해 주세요.");
		return "common/error/error";
	}

	@ExceptionHandler(DataAccessException.class)
	public String dataAccessException(DataAccessException dataAccessException, Model model) {
		logger.error("DataAccessException:{}", dataAccessException.getMessage());

		model.addAttribute("msg", "현재 에러가 발생하였습니다. \n 잠시후 다시 이용해 주세요.");
		return "common/error/error";
	}

	@ExceptionHandler(SQLException.class)
	public String sqlException(SQLException sqlException, Model model) {
		logger.error("SQLException:{}", sqlException.getMessage());

		model.addAttribute("msg", "현재 에러가 발생하였습니다. \n 잠시후 다시 이용해 주세요.");
		return "common/error/error";
	}

	@ExceptionHandler(FileNotFoundException.class)
	public String fileNotFoundException(FileNotFoundException fileNotFoundException, Model model) {
		logger.error("FileNotFoundException:{}", fileNotFoundException.getMessage());

		model.addAttribute("msg", "현재 파일을 찾을 수 없습니다.");
		return "common/error/error";
	}

	@ExceptionHandler(IOException.class)
	public String ioException(IOException ioException, Model model) {
		logger.error("FileNotFoundException:{}", ioException.getMessage());

		model.addAttribute("msg", "현재 파일을 이용할 수 없습니다.");
		return "common/error/error";
	}
}
