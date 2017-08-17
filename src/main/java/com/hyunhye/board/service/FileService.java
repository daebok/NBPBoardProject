package com.hyunhye.board.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hyunhye.board.model.Board;
import com.hyunhye.board.model.FileModel;
import com.hyunhye.board.repository.FileRepository;
import com.hyunhye.utils.MediaUtils;
import com.hyunhye.utils.UploadFileUtils;

@Service
public class FileService {
	Logger logger = LoggerFactory.getLogger(FileService.class);

	@Autowired
	private FileRepository fileRepository;

	@Resource(name = "uploadPath")
	private String uploadPath;

	/* 파일 등록 */
	public void fileRegist(Board boardModel, MultipartFile[] files) throws Exception {

		/* 삭제된 첨부파일 번호 가져오기 */
		int[] filesNo = boardModel.getBoardFilesNo();
		int index2 = 0;

		/* 파일 업로드 */
		String homePath = System.getProperty("user.home").replaceAll("\\\\", "/");
		for (int index = 0; index < files.length; index++) {
			/* 삭제 버튼으로 삭제된 파일은 업로드 하지 않는다. */
			if (filesNo != null && index <= filesNo.length && (index - 1) == filesNo[index2]) {
				index2++;
				continue;
			}
			MultipartFile file = files[index];

			String fileOriginalName = file.getOriginalFilename();
			long fileSize = file.getSize();
			String fileContentType = fileOriginalName.substring(fileOriginalName.lastIndexOf(".") + 1);

			/* 파일을 첨부하지 않았을 때 */
			if (fileContentType.equals("")) {
				continue;
			}

			/* 1. 서버에 업로드 */
			String fileName = UploadFileUtils.uploadFile(homePath + uploadPath, fileOriginalName, fileContentType,
				file.getBytes());
			FileModel fileModel = new FileModel();
			fileModel.setFileName(fileName);
			fileModel.setFileOriginName(fileOriginalName);
			fileModel.setFileExtension(fileContentType);
			fileModel.setFileSize(fileSize);

			/* 2. 데이터베이스에 저장 */
			fileRepository.fileInsert(fileModel);
		}
	}

	/* 파일 삭제 (삭제 버튼으로 눌린 파일 삭제) */
	public void fileDelete(Board boardModel) {
		/* 삭제된 첨부파일  가져오기 */
		String[] filesDelete = boardModel.getBoardFilesDelete();

		if (Objects.isNull(filesDelete)) {
			return;
		}

		List<String> filesDeletList = Arrays.asList(filesDelete);
		filesDeletList.stream().forEach(f -> {
			/* 1. 서버에서 삭제 */
			String homePath = System.getProperty("user.home").replaceAll("\\\\", "/");
			new File(homePath + uploadPath + f.replace('/', File.separatorChar)).delete();

			/* 2. 데이버베이스에서 삭제*/
			fileRepository.fileDelete(f);
		});

	}

	/* 파일 삭제 (게시물에 첨부된 file 모두 삭제) - 게시글 삭제 시 */
	public void fileDeletFromDatabase(int boardNo) {
		/* 삭제된 첨부파일  가져오기 */
		List<FileModel> filesDelete = fileRepository.fileSelect(boardNo);

		if (Objects.isNull(filesDelete)) {
			return;
		}

		filesDelete.stream().forEach(f -> {
			/* 서버에서 삭제 */
			String homePath = System.getProperty("user.home").replaceAll("\\\\", "/");
			new File(homePath + uploadPath + f.getFileName().replace('/', File.separatorChar)).delete();
		});

	}

	/* 파일 다운로드 */
	public ResponseEntity<byte[]> fileDownload(String fileName) throws IOException {
		ResponseEntity<byte[]> entity = null;

		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1); // 확장자 추출
		MediaType mediaType = MediaUtils.getMediaType(formatName); // MediaUtils에서 이미지 파일 여부 검사
		HttpHeaders headers = new HttpHeaders(); // 헤더 구성 객체
		String homePath = System.getProperty("user.home").replaceAll("\\\\", "/");

		try (InputStream in = new FileInputStream(homePath + uploadPath + fileName);) {

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

		}

		return entity;
	}
}