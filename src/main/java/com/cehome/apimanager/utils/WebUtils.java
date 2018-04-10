package com.cehome.apimanager.utils;

import javax.servlet.http.HttpSession;

public class WebUtils {
    public static boolean isLogin(HttpSession session) {
        return session.getAttribute(BaseVar.SESSION_LOGIN_USER) != null;
    }

    public static void setLoginManager(HttpSession session, Object o) {
        session.setAttribute(BaseVar.SESSION_LOGIN_USER, o);
    }

    public static Object getLoginManager(HttpSession session) {
        return session.getAttribute(BaseVar.SESSION_LOGIN_USER);
    }

    interface BaseVar {
        String SESSION_LOGIN_USER = "loginUser";
    }
}
