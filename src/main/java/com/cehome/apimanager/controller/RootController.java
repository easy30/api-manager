package com.cehome.apimanager.controller;

import com.cehome.apimanager.utils.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class RootController {

    @RequestMapping("/")
    public String redirect(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        HttpSession session = request.getSession();
        boolean login = WebUtils.isLogin(session);
        if(login){
            return "index.html";
        } else {
            return "login.html";
        }
    }
}
