package com.cehome.apimanager.filter;

import com.cehome.apimanager.utils.WebUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserLoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();
        if (whileList(requestURI)) {
            chain.doFilter(request, response);
        } else {
            HttpSession session = httpRequest.getSession();
            if (WebUtils.isLogin(session)) {
                chain.doFilter(request, response);
            } else {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.html");
                return;
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    /**
     * 配置白名单
     *
     * @param requestURI
     * @return
     */
    private boolean whileList(String requestURI) {
        if (requestURI.indexOf("/login.html") > 0) {
            return true;
        }
        if (requestURI.indexOf("/user/login") > 0) {
            return true;
        }
        if (requestURI.indexOf("/common/") > 0 || requestURI.indexOf("/plugins/") > 0) {
            return true;
        }
        return false;
    }
}
