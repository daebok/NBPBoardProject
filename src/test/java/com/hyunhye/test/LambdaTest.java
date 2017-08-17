package com.hyunhye.test;

import java.util.Comparator;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LambdaTest {
	Logger logger = LoggerFactory.getLogger(LambdaTest.class);
	@Test
	public void test() {
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2 - o1;
			}
		};

		Comparator<Integer> lambdaComparator = (o1, o2) -> o2 - o1;

		logger.info("compare: {}", comparator.compare(1, 2));
		logger.info("lambdaComparator: {}", lambdaComparator.compare(3, 2));
	}

}
