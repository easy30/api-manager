package com.cehome.apimanager.utils;

import com.cehome.apimanager.model.po.AmUser;

import javax.servlet.http.HttpSession;

public class WebUtils {

    public static boolean isLogin(HttpSession session) {
        return true;// session.getAttribute(BaseVar.SESSION_LOGIN_USER) != null;
    }

    public static void setLoginUser(HttpSession session, AmUser o) {
        session.setAttribute(BaseVar.SESSION_LOGIN_USER, o);
    }

    public static AmUser getLoginUser(HttpSession session) {
        AmUser amUser=new AmUser();
        amUser.setId(23);
        amUser.setUserName("马瑞祥");
        amUser.setAccount("13911610242");
        amUser.setPassword("e10adc3949ba59abbe56e057f20f883e");
        return amUser;
        //return (AmUser)session.getAttribute(BaseVar.SESSION_LOGIN_USER);
    }

    public static void removeLoginUser(HttpSession session) {
        session.removeAttribute(BaseVar.SESSION_LOGIN_USER);
    }

    interface BaseVar {
        String SESSION_LOGIN_USER = "loginUser";
    }
}
