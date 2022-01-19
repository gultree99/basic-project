package com.goodplatform.insa.aop;

import com.goodplatform.insa.controller.BaseController;
import com.goodplatform.insa.util.FormatUtil;
import com.goodplatform.insa.util.PageUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Aspect
public abstract class BaseAspect {

	@Resource(name = "config")
	private Properties config;
	
	@Value("#{config['SERVICE.ID']}")
	private String serviceId;
	
	@Value("#{config['FRAMEWORK.MSG.INFO']}")
	private String isInfoMessage;
	
	protected abstract boolean beforeAction(ProceedingJoinPoint pjp, Map<String,Object> pjpMap) throws Exception;
	
	protected abstract boolean afterAction(ProceedingJoinPoint pjp, Map<String,Object> pjpMap) throws Exception;
	
	// * - 紐⑤뱺 �젒洹쇱젣�뼱�옄 諛섑솚�삎�쓣 媛�吏��뒗
	// *.. - 紐⑤뱺 �뙣�궎吏��쓽 �븯�쐞 寃쎈줈(�엫�쓽�쓽 源딆씠)�뿉 �엳�뒗
	// *. - 紐⑤뱺 �꽌釉뚰뙣�궎吏� �븞(�븯�굹�쓽 源딆씠)�뿉 �엳�뒗
	// controller.* - controller �뙣�궎吏� �븞�쓽 紐⑤뱺 �겢�옒�뒪
	// ..* - 洹� �겢�옒�뒪 �븯�쐞�뿉 �엳�뒗 紐⑤뱺 硫붿꽌�뱶
	// (..) 洹� 硫붿꽌�뱶�뒗 �엫�쓽�쓽 �뙆�씪誘명꽣瑜� 媛�吏� �닔 �엳�쓬
	@Pointcut("execution(* *..*.controller.*..*(..))&& !@annotation(com.goodplatform.insa.annotation.NoLogging)")
	protected void pointCutForController() {
		
	}
	
	@SuppressWarnings("unchecked")
	@Around("pointCutForController()")
	private final Object action(ProceedingJoinPoint pjp) throws Throwable {
		BaseController controller = null;
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		Model model = null;
		Map<String, Object> params = null;

		Object returnObject = null;
		
		if(pjp.getTarget() instanceof BaseController) {
			controller = (BaseController)pjp.getTarget();
		}
		
		if(controller == null) {
			return pjp.proceed();
		}
		
		Object[] args = pjp.getArgs();
		
		for(Object arg : args) {
			if(arg instanceof HttpServletRequest) {
				request = (HttpServletRequest)arg;
			} else if(arg instanceof HttpServletResponse) {
				response = (HttpServletResponse)arg;
			} else if(arg instanceof Model) {
				model = (Model)arg;
			} else if(arg instanceof Map) {
				params = (Map<String, Object>)arg;
			}
		}
		
		if (request != null && params == null) {
			params = PageUtil.getParameterMap(request);
		}
		
		if(request != null && model != null) {
			model.addAttribute("paramsSearch", new HashMap<String, Object>(params));
			model.addAttribute("params", params);
			
			boolean havePageNum = false, haveRowCount = false;
			
			for(String key : params.keySet()) {
				if (key.toLowerCase().matches("(?i)(page|no|num)")) {
					if (FormatUtil.toInt(params.get(key)) < 1) {
						params.put(key, 1);
					}

					havePageNum = true;
				}

				if (key.toLowerCase().matches("(?i)(sub_page|sub_no|sub_num)")) {
					if (FormatUtil.toInt(params.get(key)) < 1) {
						params.put(key, 1);
					}

					havePageNum = true;
				}

				if (key.toLowerCase().matches("(?i)(row|list)(count|size)")) {
					if (FormatUtil.toInt(params.get(key)) < 1) {
						params.put(key, 10);
					}

					haveRowCount = true;
				}

				if (key.toLowerCase().matches("(?i)(sub_row|sub_list)(sub_count|sub_size)")) {
					if (FormatUtil.toInt(params.get(key)) < 1) {
						params.put(key, 10);
					}

					haveRowCount = true;
				}
			}
			
			if(!havePageNum) {
				params.put("page", 1);
				params.put("sub_page",1);
			}
			
			if(!haveRowCount) {
				params.put("rowcount", 10);
				params.put("sub_rowcount", 10);
			}
			
			params.put("pageIndex", (FormatUtil.toInt(params.get("page"))-1 < 0 ? 0 : FormatUtil.toInt(params.get("page"))-1 ));
			params.put("sub_pageIndex", (FormatUtil.toInt(params.get("sub_page"))-1 < 0 ? 0 : FormatUtil.toInt(params.get("sub_page"))-1 ));
			
		}
		
		HashMap<String,Object> pjpMap = new HashMap<String, Object>();
		pjpMap.put("controller", controller);
		pjpMap.put("request", request);
		pjpMap.put("response", response);
		pjpMap.put("model", model);
		pjpMap.put("params", params);
		pjpMap.put("returnObject", returnObject);
		pjpMap.put("HTMLMessage", null);

		if (!beforeAction(pjp, pjpMap)) {
			if(request.getAttribute("responseBody_auth") != null){
				returnObject = getDefaultReturnObject(pjp, (pjpMap.get("returnObject") == null ? "message/before" : (HashMap<String,Object>)pjpMap.get("returnObject")));
			}else{
				returnObject = getDefaultReturnObject(pjp, (pjpMap.get("returnObject") == null ? "message/before" : pjpMap.get("returnObject").toString()));
			}
			return returnObject;
		}

		params.put("httpRequest", request);
		Object result = pjp.proceed();
		params.remove("httpRequest");
		
		if (!afterAction(pjp, pjpMap)) {
			returnObject = getDefaultReturnObject(pjp, (pjpMap.get("returnObject") == null ? "message/after" : pjpMap.get("returnObject").toString()));
			return returnObject;
		}

		if (model != null) {
			model.addAttribute("paging", PageUtil.getPaging(params));
			model.addAttribute("sub_paging", PageUtil.getSubPaging(params));

			if (params.get("paging") != null) {
				String[] paging = params.get("paging").toString().split(",");

				for (int i = 0; i < paging.length; ++i) {
					String[] pagingFix = paging[i].split("[*]");

					String pagingPrefix = pagingFix[0];
					String pagingSuffix = "";

					if (pagingFix.length > 1) {
						pagingSuffix = pagingFix[1];
					}

					model.addAttribute(pagingPrefix + "paging" + pagingSuffix, PageUtil.getPaging(params, pagingPrefix, pagingSuffix));
				}
			}

			if (params.get("sub_paging") != null) {
				String[] paging = params.get("sub_paging").toString().split(",");

				for (int i = 0; i < paging.length; ++i) {
					String[] pagingFix = paging[i].split("[*]");

					String pagingPrefix = pagingFix[0];
					String pagingSuffix = "";

					if (pagingFix.length > 1) {
						pagingSuffix = pagingFix[1];
					}

					model.addAttribute(pagingPrefix + "sub_paging" + pagingSuffix, PageUtil.getSubPaging(params, pagingPrefix, pagingSuffix));
				}
			}
		}
		
		returnObject = (result == null ? getDefaultReturnObject(pjp, "message/nopage") : result);		
		
		if (model != null) {
			Map<String, Object> modelMap = model.asMap();
			modelMap.remove("report");
			
			for (String key : modelMap.keySet()) {
				if (key.equals("paramsSearch")) {
					for (String paramsKey : params.keySet()) {
						if (modelMap.get(paramsKey) instanceof String) {
							modelMap.put(paramsKey, FormatUtil.encodeXSS(modelMap.get(paramsKey).toString()));
						}
					}
				} else {
					modelMap.put(key, FormatUtil.encodeObjectForXSS(modelMap.get(key), key));
				}
			}
		}

		return result == null ? returnObject : result;
	}

	protected final Object getDefaultReturnObject(ProceedingJoinPoint pjp, Object returnObject) {
		if (pjp.getSignature().toString().startsWith("String ")) {
			return returnObject.toString();
		} else {
			return returnObject;
		}
	}
}
