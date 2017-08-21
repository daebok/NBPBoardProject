package com.hyunhye.board.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import com.hyunhye.board.model.BoardFile;
import com.hyunhye.board.repository.BoardRepository;
import com.hyunhye.utils.MediaUtils;
import com.hyunhye.utils.UploadFileUtils;

@Service
public class FileService {
	Logger logger = LoggerFactory.getLogger(FileService.class);

	@Autowired
	private BoardRepository boardRepository;

	@Resource(name = "uploadPath")
	private String uploadPath;

	/**
	 * {@link BoardFile} 추가
	 * @param board 번호
	 * @param files 정보
	 * @throws Exception
	 */
	public void insertFile(Board board, MultipartFile[] files) throws Exception {

		int[] filesNo = board.getBoardFilesNo();
		int index2 = 0;

		String homePath = System.getProperty("user.home").replaceAll("\\\\", "/");
		for (int index = 0; index < files.length; index++) {
			if (filesNo != null && index <= filesNo.length && (index - 1) == filesNo[index2]) {
				index2++;
				continue;
			}
			MultipartFile file = files[index];

			String fileOriginalName = file.getOriginalFilename();
			long fileSize = file.getSize();
			String fileContentType = fileOriginalName.substring(fileOriginalName.lastIndexOf(".") + 1);

			if (fileContentType.equals("")) {
				continue;
			}

			String fileName = UploadFileUtils.uploadFile(homePath + uploadPath, fileOriginalName, fileContentType,
				file.getBytes());
			BoardFile fileModel = new BoardFile();
			fileModel.setFileName(fileName);
			fileModel.setFileOriginName(fileOriginalName);
			fileModel.setFileExtension(fileContentType);
			fileModel.setFileSize(fileSize);

			boardRepository.insertFile(fileModel);
		}
	}

	/**
	 * {@link BoardFile} 삭제
	 * @param board 번호
	 */
	public void deleteFile(Board board) throws IOException, FileNotFoundException {
		String[] filesDelete = board.getBoardFilesDelete();

		if (Objects.isNull(filesDelete)) {
			return;
		}

		List<String> filesDeletList = Arrays.asList(filesDelete);
		filesDeletList.stream().forEach(f -> {
			String homePath = System.getProperty("user.home").replaceAll("\\\\", "/");
			new File(homePath + uploadPath + f.replace('/', File.separatorChar)).delete();

			boardRepository.deleteFile(f);
		});

	}

	/**
	 * 삭제된 {@link Board}의 {@link BoardFile} 모두 삭제
	 * @param board
	 */
	public void deleteFileFromDatabase(Board board) throws IOException, FileNotFoundException {
		List<BoardFile> filesDelete = boardRepository.selectFileListByBoardId(board);

		if (Objects.isNull(filesDelete)) {
			return;
		}

		filesDelete.stream().forEach(f -> {
			String homePath = System.getProperty("user.home").replaceAll("\\\\", "/");
			new File(homePath + uploadPath + f.getFileName().replace('/', File.separatorChar)).delete();
		});

	}

	/**
	 * @param fileName 다운로드 하려는 파일 이름
	 * @return {@link BoardFile} 다운로드용 파일
	 * @throws IOException
	 */
	public ResponseEntity<byte[]> downloadFile(String fileName) throws IOException {
		ResponseEntity<byte[]> entity = null;

		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
		MediaType mediaType = MediaUtils.getMediaType(formatName);
		HttpHeaders headers = new HttpHeaders();
		String homePath = System.getProperty("user.home").replaceAll("\\\\", "/");

		try (InputStream in = new FileInputStream(homePath + uploadPath + fileName);) {

			if (mediaType != null) {
				headers.setContentType(mediaType);
			} else {
				fileName = fileName.substring(fileName.indexOf("_") + 1);
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition",
					"attachment; filename=\"" + new String(fileName.getBytes("utf-8"), "iso-8859-1") + "\"");
			}
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);

		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);

		}

		return entity;
	}
}