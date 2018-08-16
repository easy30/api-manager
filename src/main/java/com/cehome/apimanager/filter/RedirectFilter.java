package com.cehome.apimanager.filter;

import com.alibaba.fastjson.JSONObject;
import com.cehome.apimanager.common.CommonMeta;
import com.cehome.apimanager.model.dto.AmActionResDto;
import com.cehome.apimanager.model.po.AmDomain;
import com.cehome.apimanager.service.IAmActionService;
import com.cehome.apimanager.service.IAmDomainService;
import com.cehome.apimanager.utils.AppUtils;
import com.cehome.apimanager.utils.HttpUtils;
import com.cehome.apimanager.utils.MockUtils;
import com.cehome.apimanager.utils.WebUtils;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;

public class RedirectFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(RedirectFilter.class);
	private static final String CONTENT_TYPE = "application/json";
	private static final String ENCODING = "UTF-8";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		StringBuffer requestURL = httpRequest.getRequestURL();

		if(!requestURL.toString().contains("apimanager.tiebaobei.com/mockData/")){
			String requestURI = httpRequest.getRequestURI();
			HttpSession session = httpRequest.getSession();
			if (whileList(requestURI) || WebUtils.isLogin(session)) {
				chain.doFilter(request, response);
			} else {
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.html");
			}
		} else {
			String[] splitUrl = requestURL.toString().split("/mockData");
			String subUrl = splitUrl[1];
			IAmActionService actionService = AppUtils.getBean(IAmActionService.class);
			IAmDomainService domainService = AppUtils.getBean(IAmDomainService.class);
			Integer actionId = actionService.findIdByRequestUrl(subUrl);
			AmActionResDto actionResDto = actionService.findById(actionId);
			String responseText = "";
			if(actionResDto != null){
				Integer domainId = actionResDto.getDomainId();
				AmDomain domain = domainService.findById(domainId);
				String domainName = domain.getDomainName();
				String url = "http://" + domainName + (subUrl.charAt(0) == '/' ? subUrl : "/" + subUrl);
				if(actionResDto.getStatus() == CommonMeta.Status.DOING.getCode()){
					String responseMock = actionResDto.getResponseMock();
					responseText = MockUtils.buildMockData(responseMock);
				} else {
					responseText = sendRequest(httpRequest, domainName, url);
				}
			} else {
				JSONObject responseObject = new JSONObject();
				responseObject.put("ret", -1);
				responseObject.put("msg", "接口没有在平台维护！");
				responseText = responseObject.toJSONString();
			}

//			httpResponse.setHeader("Access-Control-Allow-Origin", "*");
//			httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
//			httpResponse.setHeader("Access-Control-Max-Age", "3600");
//			httpResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with");
			httpResponse.setContentType(CONTENT_TYPE);
			httpResponse.setCharacterEncoding(ENCODING);
			ServletOutputStream outputStream = httpResponse.getOutputStream();
			outputStream.write(responseText.getBytes(ENCODING));
			outputStream.flush();
			outputStream.close();
		}
	}

	private String sendRequest(HttpServletRequest httpRequest, String domainName, String requestURL) throws IOException {
		String responseText;Enumeration<String> headerNames = httpRequest.getHeaderNames();
		JSONObject headers = new JSONObject();
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
            responseEntity = httpUtils.execute(domainName, requestURL, null, nameValuePair);
        } else if("POST".equals(method)){
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpRequest.getInputStream()));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null){
                buffer.append(line);
            }
            HttpUtils httpUtils = HttpUtils.getInstance();
            responseEntity = httpUtils.execute(domainName, requestURL, headers, buffer.toString());
        }
		responseText = EntityUtils.toString(responseEntity, ENCODING);
		return responseText;
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
		if (requestURI.contains("/regist.html")) {
			return true;
		}
		if (requestURI.contains("/user/regist")) {
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
