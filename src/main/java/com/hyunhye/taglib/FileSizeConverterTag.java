package com.hyunhye.taglib;

import java.util.Objects;

import javax.servlet.jsp.JspWriter;

import org.springframework.web.servlet.tags.RequestContextAwareTag;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
public class FileSizeConverterTag extends RequestContextAwareTag {
	@Getter
	@Setter
	long value;
	@Override
	protected int doStartTagInternal() throws Exception {

		if (Objects.isNull(value)) {
			return SKIP_BODY;
		}


		JspWriter writer = this.pageContext.getOut();
		writer.write(Math.ceil(value / Math.pow(1024, 1)) + " KB");

		return SKIP_BODY;
	}
}
