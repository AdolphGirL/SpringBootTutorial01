package com.reyes.tutorial.components;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.util.StringUtils;

public class SelfLocaleResolver implements LocaleResolver {
	
	private final static Logger logger = LoggerFactory.getLogger(SelfLocaleResolver.class);

	/**
	 * 需要實現resolveLocale，即可解析請求的情況
	 * 預設為運行的系統語系設置
	 */
	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		Locale local = Locale.getDefault();
		
		String lang = request.getParameter("l");
		
		logger.info("[SelfLocaleResolver]-[解析語系請求]-[{}]", lang);
		
		if(StringUtils.isEmpty(lang)){
			return local;
		}
		
		String[] arr = lang.split("_");
		if(arr==null || (arr!=null && arr.length!=2)){
			return local;
		}
		
		local = new Locale(arr[0], arr[1]);
		
		return local;
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		// TODO Auto-generated method stub
		
	}

}
