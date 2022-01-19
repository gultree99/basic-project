package com.goodplatform.insa.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goodplatform.insa.controller.BaseController;
import com.goodplatform.insa.service.CodeService;
import com.goodplatform.insa.util.CommonUtil;
import com.goodplatform.insa.util.FormatUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class StellarAspect extends BaseAspect{

	@Autowired
	CodeService codeService;
	/*
	@Autowired
	ManageService manageService;
	*/
	
	@Override
	protected boolean beforeAction(ProceedingJoinPoint pjp, Map<String, Object> pjpMap) throws Exception {
		BaseController controller = (BaseController) pjpMap.get("controller");
		HttpServletRequest request = (HttpServletRequest)pjpMap.get("request");
		HttpServletResponse response = (HttpServletResponse)pjpMap.get("response");

		@SuppressWarnings("unchecked")
		HashMap<String, Object> params = (HashMap<String, Object>)pjpMap.get("params");

		@SuppressWarnings("unchecked")
		HashMap<String, Object> user = (HashMap<String, Object>)request.getSession().getAttribute("USER");

		CommonUtil.CODE_LIST = codeService.selectCodeList(request, params);
		//CommonUtil.SETTING_LIST = manageService.selectSettingList(request, params);
		CommonUtil.LOAD_TIME = System.currentTimeMillis();

		//CommonUtil.systemSettingUpdate(params, null);
		
		params.put("auth_no", user == null ? "" : FormatUtil.toString(user.get("USER_NO")));
		params.put("auth_id", user == null ? "" : FormatUtil.toString(user.get("USER_ID")));
		params.put("auth_type", user == null ? "" : FormatUtil.toString(user.get("USER_TYPE")));
		params.put("auth_role", user == null ? "" : FormatUtil.toString(user.get("USER_ROLE")));
		params.put("auth_status", user == null ? "" : FormatUtil.toString(user.get("USER_STATUS")));

		if(request.getAttribute("req_refer") != null){
			params.put("refer", request.getAttribute("req_refer"));
		}
		if(request.getAttribute("req_returnObject") != null){
			if(request.getAttribute("responseBody_auth") != null){
				String rb_auth = FormatUtil.toString(request.getAttribute("responseBody_auth"));
				HashMap<String,Object> r_map = new HashMap<String,Object>();
				r_map.put("responseBody_auth",rb_auth);
				pjpMap.put("returnObject", r_map);
			}else{
				pjpMap.put("returnObject", request.getAttribute("req_returnObject"));
			}
		}
		if(request.getAttribute("req_return") != null){
			return (boolean)request.getAttribute("req_return");
		}
		return true;
	}

	@Override
	protected boolean afterAction(ProceedingJoinPoint pjp, Map<String, Object> pjpMap) throws Exception {
		// HttpServletRequest request = (HttpServletRequest)pjpMap.get("request");
		// HttpServletResponse response = (HttpServletResponse)pjpMap.get("response");
		// Model model = (Model)pjpMap.get("model");
		// Map<String, Object> params = (Map<String, Object>)pjpMap.get("params");
		return true;
	}

}
