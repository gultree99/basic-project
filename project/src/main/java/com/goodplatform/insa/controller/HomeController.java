package com.goodplatform.insa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.goodplatform.insa.service.CodeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Controller
public class HomeController extends BaseController{
	
	@Autowired
	private CodeService codeService;

    @RequestMapping(value={"/index.do","/home.do","","/"})
    public String home(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam HashMap<String,Object> params) throws Exception {
    	//List<HashMap<String, Object>> user = codeService.selectCodeList(params);
    	//model.addAttribute("user",user);
        return "home";
    }
}
