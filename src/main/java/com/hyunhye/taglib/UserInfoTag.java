package com.hyunhye.taglib;

import java.util.Objects;

import javax.servlet.jsp.JspWriter;

import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.hyunhye.common.SpringBeanFactory;
import com.hyunhye.user.service.UserService;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
public class UserInfoTag extends RequestContextAwareTag {
	@Getter
	@Setter
	int no;

	@Override
	protected int doStartTagInternal() throws Exception {

		if (Objects.isNull(no)) {
			return SKIP_BODY;
		}

		UserService userService = SpringBeanFactory.getBean(UserService.class);
		String userId = userService.selectUserId(no);

		if (Objects.isNull(userId)) {
			return SKIP_BODY;
		}

		JspWriter writer = this.pageContext.getOut();
		writer.write(userId);

		return SKIP_BODY;
	}
}
