package com.goodplatform.insa.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class HTMLTagFilter implements Filter{
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		chain.doFilter(new HTMLTagFilterRequestWrapper((HttpServletRequest)request), response);
	}
	
	public void init(FilterConfig config) throws ServletException {
	}

	public void destroy() {

	}
}
