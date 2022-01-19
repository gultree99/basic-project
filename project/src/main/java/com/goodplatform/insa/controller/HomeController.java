package com.goodplatform.insa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
public class HomeController extends BaseController{

    @RequestMapping(value={"/index.do","/home.do","","/"})
    public String home(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam HashMap<String,Object> params) throws Exception {

        return "home";
    }
}
