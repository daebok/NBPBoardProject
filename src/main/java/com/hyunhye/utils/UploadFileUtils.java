package com.hyunhye.utils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

public class UploadFileUtils {
	static Logger log = LoggerFactory.getLogger(UploadFileUtils.class);

	/* UUID를 통해 유일한 파일 이름 생성하여 '/년/월/일'폴더를 만들어 그 폴더로 파일을 복사하는 메소드 */
	public static String uploadFile(String uploadPath, String fileName, String fileContentType, byte[] fileData)
		throws Exception {
		UUID uuid = UUID.randomUUID();
		String savedName = uuid.toString() + "." + fileContentType; // 유일한 파일 이름 생성
		String savedPath = calcPath(uploadPath); // 폴더 경로 생성
		File target = new File(uploadPath + savedPath, savedName); // 파일 객체 생성

		/* permission denied 해결 */
		/*Runtime.getRuntime().exec("chmod 777 " + uploadPath + savedPath + savedName);
		target.setExecutable(true, false);
		target.setReadable(true, false);
		target.setWritable(true, false);
		target.createNewFile();*/

		FileCopyUtils.copy(fileData, target); // 폴더에 디렉토리 복사
		String uploadedFileName = null;
		uploadedFileName = makeIcon(uploadPath, savedPath, savedName);

		return uploadedFileName;
	}

	private static String makeIcon(String uploadPath, String path, String fileName) throws Exception {
		String iconName = uploadPath + path + File.separator + fileName;
		return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}

	/* '년/월/일'을 계산하여 경로 지정 */
	private static String calcPath(String uploadPath) {

		Calendar cal = Calendar.getInstance();

		String yearPath = File.separator + cal.get(Calendar.YEAR);
		String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
		String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));

		makeDir(uploadPath, yearPath, monthPath, datePath);

		return datePath;
	}

	/* '/년/월/일' 이름의 폴더 생성 */
	private static void makeDir(String uploadPath, String... paths) {

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
}
