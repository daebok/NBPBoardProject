package com.hyunhye.taglib;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.hyunhye.board.repository.CategoryRepository;

@Component
public class SpringBeanFactory implements ApplicationContextAware {
	private static ApplicationContext context = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	public static <Repository> CategoryRepository getBean(Class<CategoryRepository> class1) {
		return context.getBean(class1);
	}
}
