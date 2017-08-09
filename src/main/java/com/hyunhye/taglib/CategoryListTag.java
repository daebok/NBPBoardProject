package com.hyunhye.taglib;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.jsp.JspWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.hyunhye.board.model.Category;
import com.hyunhye.board.repository.CategoryRepository;

@SuppressWarnings("serial")
public class CategoryListTag extends RequestContextAwareTag {
	Logger logger = LoggerFactory.getLogger(CategoryListTag.class);

	@Override
	protected int doStartTagInternal() throws Exception {

		CategoryRepository categoryRepository = SpringBeanFactory.getBean(CategoryRepository.class);
		List<Category> category = categoryRepository.categorySelectList();

		List<Category> list = category.stream()
			.filter(s -> s.getCategoryEnabled() != 0)
			.collect(Collectors.toList());

		if (Objects.isNull(category)) {
			return SKIP_BODY;
		}

		JspWriter writer = this.pageContext.getOut();

		Iterator<Category> it = list.iterator();

		writer.write("<select name='categoryNo' id='category'>");
		while (it.hasNext()) {
			Category cat = it.next();
			writer.write("<option value='" + cat.getCategoryNo() + "'>");
			writer.write(cat.getCategoryItem());
			writer.write("</option>");
		}
		writer.write("</select>");
		return SKIP_BODY;
	}
}
