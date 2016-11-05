package com.muhe.myshop.admin.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;


@Component
public class WebUtility {
	
private WebUtility(){
	
}
	
	public static final String IMAGE_PREFIX="/ptoduct/immage";
	public static final String IMAGE_DIR="C:/spring/myshop/products";
	
	public static String  getURLWithContextPath(HttpServletRequest request){
		
		return request.getScheme()+"://"+request.getServerName()+request.getServerPort()+request.getContextPath();
	}
	
}
