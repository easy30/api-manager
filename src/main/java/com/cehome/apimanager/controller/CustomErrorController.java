package com.cehome.apimanager.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * coolma 2019/7/23
 */

//@Controller
//@RequestMapping({"${server.error.path:${error.path:/error}}"})
public class CustomErrorController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping
    public void error(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (response.getStatus() == 404) {
            String requestURL = request.getRequestURL().toString();
            get(request, response, requestURL.replace(":8099", ":8080"));
        } else {
            response.getWriter().println("hello");
            response.setStatus(200);
        }
        //System.out.println("error");
    }

    public static void get(HttpServletRequest request, HttpServletResponse response, String url) throws Exception {
        int timeout = 30000;
        response.reset();
        response.resetBuffer();

        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            Enumeration<String> values = request.getHeaders(name);
            while (values.hasMoreElements()) {
                conn.setRequestProperty(name, values.nextElement());
            }

        }
        conn.setDoOutput(true);     //需要输出
        conn.setDoInput(true);      //需要输入
        conn.setUseCaches(false);   //不允许缓存
        System.out.println(request.getMethod());
        conn.setRequestMethod(request.getMethod());      //设置POST方式连接
        conn.setConnectTimeout(timeout); //30秒连接超时
        conn.setReadTimeout(timeout);    //30秒读取超时

        //设置请求属性
        //conn.setRequestProperty("Content-Type", request.get "application/x-www-form-urlencoded");
        //conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
        //conn.setRequestProperty("Charset", encoding);
        //conn.connect();
        if (request.getMethod().equalsIgnoreCase("POST")) {
            IOUtils.copy(request.getInputStream(), conn.getOutputStream());
            conn.getOutputStream().close();
        }

        Map<String, List<String>> map = conn.getHeaderFields();

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            for (String value : entry.getValue())
                response.setHeader(entry.getKey(), value);
        }
        //String s=IOUtils.toString(conn.getInputStream(),"UTF-8");
//        System.out.println(s);
        //  response.getWriter().print(s);
        // response.setStatus(200);
        IOUtils.copy(conn.getInputStream(), response.getOutputStream());


        response.setStatus(200);//conn.getResponseCode());
        response.getOutputStream().close();

        // return IOUtils.toString(new URL(url).openConnection().getInputStream(),"UTF-8");


    }
}
