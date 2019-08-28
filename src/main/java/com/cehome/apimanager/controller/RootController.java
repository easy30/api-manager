package com.cehome.apimanager.controller;

import com.cehome.apimanager.utils.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;

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

    @RequestMapping("/apimanager/up")
    public void upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        InputStream is=request.getInputStream();
        long t=System.currentTimeMillis();
        System.out.println(t);
        long i=0;
        while (is.read()!=-1){
            i++;
            if(System.currentTimeMillis()-t>100) {
                System.out.println(t+","+is.available());
                t=System.currentTimeMillis();
            }
        }
        System.out.println(i);

    }
}
