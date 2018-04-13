package com.cehome.apimanager.filter;

import com.alibaba.fastjson.JSONObject;
import com.cehome.apimanager.common.CommonMeta;
import com.cehome.apimanager.model.dto.AmActionResDto;
import com.cehome.apimanager.service.IAmActionService;
import com.cehome.apimanager.utils.ApplicationContextUtils;
import com.cehome.apimanager.utils.HttpUtils;
import com.cehome.apimanager.utils.MockUtils;
import com.cehome.apimanager.utils.WebUtils;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;

public class RedirectFilter implements Filter {
	private static final String CONTENT_TYPE = "application/json";
	private static final String ENCODING = "GB2312";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		StringBuffer requestURL = httpRequest.getRequestURL();
		//对本系统请求进行登录验证
		if(requestURL.indexOf("localhost") > 0
				|| requestURL.indexOf("192.168.0.21") > 0
				|| requestURL.indexOf("apimanager.tiebaobei.com") > 0){
			String requestURI = httpRequest.getRequestURI();
			HttpSession session = httpRequest.getSession();
			if (whileList(requestURI) || WebUtils.isLogin(session)) {
				chain.doFilter(request, response);
			} else {
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.html");
			}
		} else {
			IAmActionService actionService = ApplicationContextUtils.getBean(IAmActionService.class);
			Integer actionId = actionService.findIdByRequestUrl(requestURL.toString());
			AmActionResDto actionResDto = actionService.findById(actionId);
			String responseText = "";
			if(actionResDto != null && actionResDto.getStatus() == CommonMeta.Status.DOING.getCode()){
				String responseMock = actionResDto.getResponseMock();
				responseText = MockUtils.buildMockData(responseMock);
			} else {
				JSONObject headers = new JSONObject();
				Enumeration<String> headerNames = httpRequest.getHeaderNames();
				while(headerNames.hasMoreElements()){
					String headerName = headerNames.nextElement();
					if(isValidHeader(headerName)){
						headers.put(headerName, httpRequest.getHeader(headerName));
					}
				}
				HttpEntity responseEntity = null;
				String method = httpRequest.getMethod();
				if("GET".equals(method)){
					Enumeration<String> parameterNames = httpRequest.getParameterNames();
					JSONObject nameValuePair = new JSONObject();
					while(parameterNames.hasMoreElements()){
						String parameterName = parameterNames.nextElement();
						nameValuePair.put(parameterName, httpRequest.getParameter(parameterName));
					}
					HttpUtils httpUtils = HttpUtils.getInstance();
					responseEntity = httpUtils.execute(requestURL.toString(), null, nameValuePair);
				} else if("POST".equals(method)){
					BufferedReader reader = new BufferedReader(new InputStreamReader(httpRequest.getInputStream()));
					StringBuffer buffer = new StringBuffer();
					String line = "";
					while ((line = reader.readLine()) != null){
						buffer.append(line);
					}
					HttpUtils httpUtils = HttpUtils.getInstance();
					responseEntity = httpUtils.execute(requestURL.toString(), headers, buffer.toString());
				}
				responseText = EntityUtils.toString(responseEntity, ENCODING);
			}

			response.setContentType(CONTENT_TYPE);
			response.setCharacterEncoding(ENCODING);
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(responseText.getBytes());
			outputStream.flush();
			outputStream.close();
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	@Override
	public void destroy() {
		
	}
	
	public boolean isValidHeader(String headerName){
		return false;
	}


	/**
	 * 配置白名单
	 *
	 * @param requestURI
	 * @return
	 */
	private boolean whileList(String requestURI) {
		if(requestURI.equals("/")){
			return true;
		}
        if (requestURI.contains("/reload.html")) {
            return true;
        }
		if (requestURI.contains("/login.html")) {
			return true;
		}
		if (requestURI.contains("/user/login")) {
			return true;
		}
		if (requestURI.contains("/common/") || requestURI.contains("/plugins/")) {
			return true;
		}
		return false;
	}
}
