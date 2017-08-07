package com.hyunhye.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.junit.Test;
import org.springframework.util.ResourceUtils;

public class BadWordFilteringUtilsTest {

	@Test
	public void test() {
		//System.out.println(BadWordFilteringUtils.readProperties().getProperty("badWords"));

		/*	BadWordFilteringUtils.badWordsArray = new ArrayList<>(BadWordFilteringUtils.badWordsArray);

			BadWordFilteringUtils.badWordInsert("fuck");
			BadWordFilteringUtils.badWordsArray.toString();

			System.out.println(BadWordFilteringUtils.badWordsArray);

			System.out.println(BadWordFilteringUtils.readProperties().getProperty("badWords"));*/

		Properties properties = new Properties();
		FileInputStream in = null;
		try {
			in = new FileInputStream(ResourceUtils.getFile("classpath:config/badwords.properties"));
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String badWords = null;
		try {
			badWords = new String(properties.getProperty("badWords").getBytes("ISO-8859-1"), "UTF-8");
			in.close();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(badWords);

		badWords.concat(",1");

		properties.setProperty("badWords", badWords);
		try {
			properties.store(new FileOutputStream(ResourceUtils.getFile("classpath:config/badwords.properties")), "");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			badWords = new String(properties.getProperty("badWords").getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(badWords);
	}

}
