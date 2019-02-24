package com.lsl.order.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lsl.commons.pojo.EgoResult;
import com.lsl.commons.util.CookieUtils;
import com.lsl.commons.util.HttpClientUtil;
import com.lsl.commons.util.JsonUtils;


public class OrderIntercept implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String value = CookieUtils.getCookieValue(request, "TT_TOKEN");
		if (value != null && !value.equals("-1")) {
			EgoResult er = JsonUtils.jsonToPojo(HttpClientUtil.doPost("http://localhost:8084/user/token/"+value), EgoResult.class);
			if (er.getStatus() == 200) {
				return true;
			}
		}
		StringBuffer url = request.getRequestURL();
		String[] ids = request.getParameterValues("id");
		StringBuffer idp = new StringBuffer();
		for (int i=0;i<ids.length;i++) {
			if(i == 0) {
				idp.append("id="+ids[i]);
			} else {
				idp.append("%26id="+ids[i]);
			}
		}
//		System.out.println("?url="+url+"?"+idp.toString());
		response.sendRedirect("http://localhost:8084/user/showLogin"+"?url="+url+"%3F"+idp.toString());
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
