package com.hyunhye.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.springframework.util.ResourceUtils;

public class BadWordFilteringUtils {
	/** 비속어 리스트가 담긴 properties 파일 **/
	private static String badwordsPropertiesFile = "classpath:config/properties/badwords.properties";

	/** properties **/
	private static Properties properties = readProperties();

	/** badWords String **/
	private static String badWords;

	/** badWords List **/
	public static List<String> badWordsArray = Arrays.asList(readBadWords());

	/**
	 * properties 가져오기
	 * @return properties
	 */
	public static Properties readProperties() {
		properties = new Properties();
		try {
			FileInputStream in = new FileInputStream(ResourceUtils.getFile(badwordsPropertiesFile));
			properties.load(in);

			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return properties;
	}

	/**
	 * badWords List 불러오기
	 * @return badWordsArray
	 */
	public static String[] readBadWords() {
		badWords = null;
		try {
			badWords = new String(properties.getProperty("badWords").getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String[] array = badWords.split(",");

		return array;
	}

	/**
	 * 비속어 체크
	 * @param content
	 * @return 게시글에 포함된 비속어 리스트
	 */
	public static List<String> badWordFilteringContainsStream(String content) {
		List<String> list = badWordsArray.parallelStream()
			.filter(s -> content.contains(s))
			.collect(Collectors.toList());

		return list;
	}

	/**
	 * 관리자에 의해 비속어 추가
	 * @param badWord
	 */
	public static void badWordInsert(String badWord) {
		badWords.concat("," + badWord);
		properties.setProperty("badWords", badWords);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(ResourceUtils.getFile(badwordsPropertiesFile));
			properties.storeToXML(out, "");

			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
