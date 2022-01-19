package com.goodplatform.insa.controller;


import com.goodplatform.insa.model.HTMLMessage;
import com.goodplatform.insa.model.PageAuth;
import com.goodplatform.insa.model.PageProtocol;
import com.goodplatform.insa.model.SiteType;
import com.goodplatform.insa.util.FormatUtil;
import com.goodplatform.insa.util.RequestUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    @Autowired
    public HttpServletRequest request;

    protected SiteType siteType = SiteType.NORMAL;

    protected final Logger log = LogManager.getLogger(getClass());

    protected PageProtocol pageProtocol = PageProtocol.HTTP;

    public SiteType getSiteType() {
        return siteType;
    }

    public PageProtocol getPageProtocol() {
        return pageProtocol;
    }

    public String getUserIp(HttpServletRequest request) {
        return RequestUtil.getUserIp(request);
    }

    @SuppressWarnings("unchecked")
    protected Map<String, Object> getUserSession(HttpServletRequest request) {
        return (HashMap<String, Object>)request.getSession().getAttribute("USER");
    }

    protected Object getUserSessionValue(HttpServletRequest request, String attibuteName) {
        HashMap<String, Object> user = (HashMap<String, Object>)getUserSession(request);
        return user == null ? null : user.get(attibuteName);
    }

    protected String getUserSessionStringValue(HttpServletRequest request, String attibuteName) {
        try{
            return FormatUtil.toString(getUserSession(request).get(attibuteName));
        }catch(Exception e){
            return "";
        }
    }

    protected int getUserSessionIntValue(HttpServletRequest request, String attibuteName) {
        return FormatUtil.toInt(getUserSession(request).get(attibuteName));
    }

    protected boolean securityBlock(Model model, HashMap<String, Object> params, String security_str) {

        if("".equals(FormatUtil.toString(params.get("auth_id")))){
            model.addAttribute("HTMLMessage", new HTMLMessage("로그인 후 이용해주세요.", "/member/login.do"));
            return true;
        }

        if("".equals(FormatUtil.toString(params.get("auth_type")))){
            model.addAttribute("HTMLMessage", new HTMLMessage("잘못된 접근입니다.", "", HTMLMessage.TARGET.BACK));
            return true;
        }

        if(security_str.contains(FormatUtil.toString(params.get("auth_type")))){
            return false;
        }

        model.addAttribute("HTMLMessage", new HTMLMessage("잘못된 접근입니다.", "", HTMLMessage.TARGET.BACK));
        return true;
    }

    protected boolean forbiddenBlock(Model model, HashMap<String, Object> params,String role) {


        if("M".equals(FormatUtil.toString(params.get("auth_type")))||"A".equals(FormatUtil.toString(params.get("auth_type")))){
            return false;
        }

        if("".equals(FormatUtil.toString(params.get("auth_role")))){
            model.addAttribute("HTMLMessage", new HTMLMessage("권한이 없습니다.", "", HTMLMessage.TARGET.BACK));
            return true;
        }

        if(FormatUtil.toString(params.get("auth_role")).contains(role)){
            return false;
        }

        model.addAttribute("HTMLMessage", new HTMLMessage("권한이 없습니다.", "", HTMLMessage.TARGET.BACK));
        return true;
    }

    public void setBaseTerm(HashMap<String, Object> params) {
        @SuppressWarnings("unchecked")
        HashMap<String, Object> settingMap = (HashMap<String, Object>)params.get("SETTING");
        Calendar calendar = Calendar.getInstance();
        int baseYear = FormatUtil.toInt(settingMap.get("TIMESET_YEAR"), calendar.get(Calendar.YEAR));
        int baseMonth = calendar.get(Calendar.MONTH) + 1;
        String baseTerm = FormatUtil.toString(settingMap.get("TIMESET_TERM"), baseMonth < 1 || baseMonth > 7 ? "20" : "10");
        String year = params.get("year") == null ? Integer.toString(baseYear) : FormatUtil.toString(params.get("year"));
        String term = params.get("term") == null ? baseTerm : FormatUtil.toString(params.get("term"));

        params.put("year", year);
        params.put("term", term);
    }
}
