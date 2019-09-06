package com.cehome.apimanager.filter;

import com.alibaba.fastjson.JSONObject;
import com.cehome.apimanager.GlobalConfig;
import com.cehome.apimanager.common.CommonMeta;
import com.cehome.apimanager.model.dto.AmActionResDto;
import com.cehome.apimanager.model.po.AmDomain;
import com.cehome.apimanager.service.IAmActionService;
import com.cehome.apimanager.service.IAmDomainService;
import com.cehome.apimanager.utils.*;
import org.apache.commons.io.IOUtils;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public class RedirectFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(RedirectFilter.class);
	private static final String CONTENT_TYPE = "application/json";
	private static final String ENCODING = "UTF-8";

    public static void get(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
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
        //System.out.println(request.getMethod());
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


        response.setStatus(conn.getResponseCode());
        response.getOutputStream().close();

        // return IOUtils.toString(new URL(url).openConnection().getInputStream(),"UTF-8");


    }
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String requestURL = httpRequest.getRequestURL().toString();
        String path=httpRequest.getServletPath();


        if(GlobalConfig.isDev() && ( path.startsWith(Const.BASE_URL+"/app") || path.startsWith("/sockjs-node/"))){
            get(httpRequest,httpResponse,requestURL.replace(":8099",":8080"));
        }
		else if(!requestURL.toString().contains("apimanager.tiebaobei.com/mockData/")){
			String requestURI = httpRequest.getRequestURI();
			HttpSession session = httpRequest.getSession();
			if (whileList(requestURI) || WebUtils.isLogin(session)) {
				chain.doFilter(request, response);
			} else {
				httpResponse.setStatus(320);
				//httpResponse.sendRedirect(httpRequest.getContextPath() + "/apimanager/app/#/");
			}
		} else {
			String[] splitUrl = requestURL.split("/mockData");
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
