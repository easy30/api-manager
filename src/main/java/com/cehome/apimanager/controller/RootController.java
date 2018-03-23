package com.cehome.apimanager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RootController {

    @RequestMapping("index.htm")
    public String save(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        return "index";
    }
}
