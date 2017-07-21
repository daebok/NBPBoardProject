package com.hyunhye.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hyunhye.common.MediaUtils;
import com.hyunhye.common.UploadFileUtils;

@Controller
@RequestMapping("/upload")
public class UploadController {

	@Resource(name = "uploadPath")
	private String uploadPath;

	/* 파일 업로드 처리 */
	@ResponseBody
	@RequestMapping(value = "uploadAjax", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception {
		return new ResponseEntity<String>(
			UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes()),
			HttpStatus.CREATED);
	}

	/* 이미지 표시 */
	@ResponseBody
	@RequestMapping("displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception {
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		try {
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1); // 확장자 추출

			MediaType mediaType = MediaUtils.getMediaType(formatName); // MediaUtils에서 이미지 파일 여부 검사

			HttpHeaders headers = new HttpHeaders(); // 헤더 구성 객체

			in = new FileInputStream(uploadPath + fileName);

			if (mediaType != null) { // 이미지 파일이면...
				headers.setContentType(mediaType);
			} else { // 이미지 파일이 아니면...
				fileName = fileName.substring(fileName.indexOf("_") + 1);
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); // 다운로드 용 확장자 구성
				headers.add("Content-Disposition",
					"attachment; filename=\"" + new String(fileName.getBytes("utf-8"), "iso-8859-1") + "\"");
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

	/* 파일 삭제 처리 */
	@ResponseBody
	@RequestMapping(value = "deleteFile", method = RequestMethod.POST)
	public ResponseEntity<String> deleteFile(String fileName) throws Exception {
		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);

		MediaType mediaType = MediaUtils.getMediaType(formatName);

		if (mediaType != null) {
			String front = fileName.substring(0, 12);
			String end = fileName.substring(14);
			new File(uploadPath + (front + end).replace('/', File.separatorChar)).delete(); // 썸네일 이미지 삭제
		}

		new File(uploadPath + fileName.replace('/', File.separatorChar)).delete();

		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
}
