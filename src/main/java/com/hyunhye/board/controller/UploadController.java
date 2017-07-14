package com.hyunhye.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hyunhye.board.service.FileServiceImpl;
import com.hyunhye.common.MediaUtils;
import com.hyunhye.common.UploadFileUtils;

@Controller
public class UploadController {
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	@Autowired
	public FileServiceImpl service;

	@Resource(name = "uploadPath")
	private String uploadPath;

	@RequestMapping(value = "/uploadForm", method = RequestMethod.POST)
	public void uploadForm(MultipartFile file, Model model) throws Exception {
		String savedName = uploadfile(file.getOriginalFilename(), file.getBytes());
		model.addAttribute("savedName", savedName);
	}

	@ResponseBody
	@RequestMapping(value = "/uploadAjax", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception {
		return new ResponseEntity<>(
			UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes()),
			HttpStatus.CREATED);
	}

	/*
	 * send file to browser
	 * image mapping
	 */
	@ResponseBody // send byte[]
	@RequestMapping("/upload/displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception { // return file data
		InputStream in = null; // download server file
		ResponseEntity<byte[]> entity = null;
		try {
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1); // extract contentType

			MediaType mediaType = MediaUtils.getMediaType(formatName); // image or not check

			HttpHeaders headers = new HttpHeaders(); // header

			logger.info("uploadPath: " + uploadPath);
			in = new FileInputStream(uploadPath + fileName);
			if (mediaType != null) { // if image
				headers.setContentType(mediaType);
			} else { // if not
				fileName = fileName.substring(fileName.indexOf("_") + 1);
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); // contentType for downloading
				headers.add("Content-Disposition",
					"attachment; filename=\"" + new String(fileName.getBytes("utf-8"), "iso-8859-1") + "\""); // korean
			}
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		return entity;
	}

	@ResponseBody
	@RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
	public ResponseEntity<String> deleteFile(String fileName) {
		logger.info("delete file: " + fileName);

		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);

		MediaType mediaType = MediaUtils.getMediaType(formatName);

		if (mediaType != null) {
			String front = fileName.substring(0, 12);
			String end = fileName.substring(14);
			new File(uploadPath + (front + end).replace('/', File.separatorChar)).delete();
		}

		new File(uploadPath + fileName.replace('/', File.separatorChar)).delete();

		return new ResponseEntity<String>("deleted", HttpStatus.OK);

	}

	private String uploadfile(String originalName, byte[] fileData) throws IOException {
		// TODO Auto-generated method stub
		UUID uid = UUID.randomUUID();

		String savedName = uid.toString() + "_" + originalName;

		File target = new File(uploadPath, savedName);

		FileCopyUtils.copy(fileData, target);

		return savedName;
	}

}
