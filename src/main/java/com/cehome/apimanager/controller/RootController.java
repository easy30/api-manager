package com.cehome.apimanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class RootController {

    @RequestMapping("/")
    public String save(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        return "login.html";
    }
}
