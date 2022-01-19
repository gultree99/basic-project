package com.goodplatform.insa.interceptor;


import com.goodplatform.insa.annotation.Auth;
import com.goodplatform.insa.model.Role;
import com.goodplatform.insa.util.FormatUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Properties;

@Component
public class WebInterceptor implements HandlerInterceptor {

	@Resource(name = "config")
	protected Properties config;

	// Logger
	protected final Logger log = LogManager.getLogger(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(!(handler instanceof HandlerMethod)){
			return true;
		}

		HandlerMethod handlerMethod = (HandlerMethod) handler;

		ResponseBody rb = handlerMethod.getMethodAnnotation(ResponseBody.class);

		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		if(auth == null){
			return true;
		}
		HashMap<String, Object> user = (HashMap<String, Object>)request.getSession().getAttribute("USER");

		boolean block = true;

		Role[] role_array =  auth.auth();
		String user_type = (user == null ? "G":FormatUtil.toString(user.get("USER_TYPE")));
		int user_type_value = 0;
		switch(user_type){
			case "G": user_type_value = 0;
				break;
			case "M": user_type_value = 1;
				break;
			case "A": user_type_value = 2;
				break;
			case "B": user_type_value = 3;
				break;
			case "P": user_type_value = 4;
				break;
			case "S": user_type_value = 5;
				break;
			case "O": user_type_value = 6;
				break;
		}


		for(Role role : role_array){
			if(user_type_value == role.getValue()){
				block = false;
			}
		}

		if(block){
			if(user == null){
				request.setAttribute("req_refer",request.getRequestURI()+(request.getQueryString() != null ? ("?"+ request.getQueryString()):""));
				request.setAttribute("req_returnObject","member/login");
				request.setAttribute("req_return",false);

				if(rb != null){
					request.setAttribute("responseBody_auth","login");
				}

			}else{
				request.setAttribute("req_returnObject","message/auth");
				request.setAttribute("req_return",false);

				if(rb != null){
					request.setAttribute("responseBody_auth","auth");
				}
			}
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}
