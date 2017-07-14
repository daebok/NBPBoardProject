package com.hyunhye.board.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyunhye.board.repository.FileRepositoryImpl;

@Service
public class FileServiceImpl implements FileService {
	private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

	@Autowired
	FileRepositoryImpl repository;


}
