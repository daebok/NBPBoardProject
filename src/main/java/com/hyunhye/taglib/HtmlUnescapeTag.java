package com.hyunhye.taglib;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;

@SuppressWarnings("serial")
public class HtmlUnescapeTag extends BodyTagSupport {
	Logger logger = LoggerFactory.getLogger(HtmlUnescapeTag.class);

	@Override
	public int doAfterBody() throws JspException {

		BodyContent bodycontent = getBodyContent();

		if (Objects.isNull(bodycontent) || StringUtils.isBlank(bodycontent.getString())) {
			return SKIP_BODY;
		}

		try {
			bodycontent.getEnclosingWriter().print(HtmlUtils.htmlUnescape(bodycontent.getString()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return SKIP_BODY;
	}
}
