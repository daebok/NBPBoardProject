package com.hyunhye.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Component("fileUtils")
public class UploadFileUtils {

	public static String uploadFile(String uploadPath, String originalName, byte[] fileData) throws Exception {
		UUID uuid = UUID.randomUUID();
		String savedName = uuid.toString() + "_" + originalName;
		String savedPath = calcPath(uploadPath); // calc saved path
		File target = new File(uploadPath + savedPath, savedName);
		FileCopyUtils.copy(fileData, target); // copy file in temp directory (save)
		String formatName = originalName.substring(originalName.lastIndexOf(".") + 1); // contentType check to make thumbnail
		String uploadedFileName = null;
		if (MediaUtils.getMediaType(formatName) != null) { // if image ?
			/* create thumbnail*/
			uploadedFileName = makeThumbnail(uploadPath, savedPath, savedName);
		} else { // image no
			/* create icon*/
			uploadedFileName = makeIcon(uploadPath, savedPath, savedName);
		}
		return uploadedFileName;
	}

	private static String makeIcon(String uploadPath, String path, String fileName) throws Exception {
		String iconName = uploadPath + path + File.separator + fileName; // icon name
		return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}

	private static final Logger logger = LoggerFactory.getLogger(UploadFileUtils.class);

	public static String fileSave(String uploadPath, MultipartFile file) throws IllegalStateException, IOException {

		File uploadPathDir = new File(uploadPath);

		if (!uploadPathDir.exists()) {
			uploadPathDir.mkdirs();
		}

		String genId = UUID.randomUUID().toString();
		genId = genId.replace("-", "");

		String originalfileName = file.getOriginalFilename();
		String fileExtension = getExtension(originalfileName);
		String saveFileName = genId + "." + fileExtension;

		String savePath = calcPath(uploadPath);

		File target = new File(uploadPath + savePath, saveFileName);

		FileCopyUtils.copy(file.getBytes(), target);

		return makeFilePath(uploadPath, savePath, saveFileName);
	}

	public static String getExtension(String fileName) {
		int dotPosition = fileName.lastIndexOf('.');

		if (-1 != dotPosition && fileName.length() - 1 > dotPosition) {
			return fileName.substring(dotPosition + 1);
		} else {
			return "";
		}
	}

	/* folder create */
	private static String calcPath(String uploadPath) { // uploadPath: inner path

		Calendar cal = Calendar.getInstance();

		String yearPath = File.separator + cal.get(Calendar.YEAR);
		String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH));
		String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));

		makeDir(uploadPath, yearPath, monthPath, datePath);

		logger.info(datePath);

		return datePath;
	}

	private static void makeDir(String uploadPath, String... paths) {

		logger.info(paths[paths.length - 1] + " : " + new File(paths[paths.length - 1]).exists());
		if (new File(paths[paths.length - 1]).exists()) {
			return;
		}

		for (String path : paths) {
			File dirPath = new File(uploadPath + path);

			if (!dirPath.exists()) {
				dirPath.mkdir();
			}

		}
	}

	private static String makeFilePath(String uploadPath, String path, String fileName) throws IOException {
		String filePath = uploadPath + path + File.separator + fileName;
		return filePath.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}

	/* create Thumbnail */
	private static String makeThumbnail(String uploadPath, String path, String fileName) throws Exception {

		BufferedImage sourceImg = ImageIO.read(new File(uploadPath + path, fileName)); // image in memory

		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100); // origin image copy

		String thumbnailName = uploadPath + path + File.separator + "s_" + fileName; // thumbnail image name (s_)

		File newFile = new File(thumbnailName);
		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);

		ImageIO.write(destImg, formatName.toUpperCase(), newFile);

		return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}

}
