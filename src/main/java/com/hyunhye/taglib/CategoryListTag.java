package com.hyunhye.taglib;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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

		if (Objects.isNull(category)) {
			return SKIP_BODY;
		}

		Stream<Category> stream = category.stream().filter(s -> s.getCategoryEnabled() != 0);

		JspWriter writer = this.pageContext.getOut();
		writer.write("<select name='categoryNo' id='category'>");
		stream.forEach(c -> {
			try {
				writer.write("<option value='" + c.getCategoryNo() + "'>");
				writer.write(c.getCategoryItem());
				writer.write("</option>");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		writer.write("</select>");


		return SKIP_BODY;
	}
}
