package com.hyunhye.utils;

import javax.servlet.http.HttpServletRequest;

public class UriUtils {
	public static void getUri(HttpServletRequest request) {
		String uri = request.getRequestURI();

		String query = request.getQueryString();

		if (query == null || query.equals("null")) {
			query = "";
		} else {
			query = "?" + query;
		}

		if (request.getMethod().equals("GET")) {
			request.getSession().setAttribute("uri", uri + query);
		}
	}
}
