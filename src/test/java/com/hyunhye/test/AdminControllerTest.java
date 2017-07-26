package com.hyunhye.test;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hyunhye.admin.service.AdminService;
import com.hyunhye.user.model.UserModel;


public class AdminControllerTest extends AbstractTestCaseRunWithSpring {
	Logger log = LoggerFactory.getLogger(AdminControllerTest.class);

	@Autowired
	AdminService service;

	@Override
	@Test
	public void test() {
		List<UserModel> model = service.userListAll();

		assertNotNull(model);

		Iterator<UserModel> it = model.iterator();

		while (it.hasNext()) {
			log.info(it.next().getUserName());
		}
	}

}
