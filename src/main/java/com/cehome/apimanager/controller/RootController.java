package com.cehome.apimanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class RootController {

    @RequestMapping("index.htm")
    public String save(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        return "index";
    }
}
