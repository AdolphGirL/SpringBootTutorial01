package com.reyes.tutorial.config;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

public class ErrorBase {
	
	/**
	 * 
	 * - 瀏覽器client: 會返回一個默認的錯誤
	 * - postman: 會返回一個默認的json資料 
	 * 
	 * - 原理: 
	 * 		ErrorMvcAutoConfiguration.class配置
	 * 
	 * 		4xx or 500
	 * 		ErrorPageCustomizer class
	 * 		@Value("${error.path:/error}")
			private String path = "/error";
			
			BasicErrorController接收/error
			@RequestMapping("${server.error.path:${error.path:/error}}")
			public class BasicErrorController extends AbstractErrorController {
			
			BasicErrorController請求的兩種處理
			@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
			public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
				HttpStatus status = getStatus(request);
				Map<String, Object> model = Collections
						.unmodifiableMap(getErrorAttributes(request, isIncludeStackTrace(request, MediaType.TEXT_HTML)));
				response.setStatus(status.value());
				ModelAndView modelAndView = resolveErrorView(request, response, status, model);		resolveErrorView(request, response, status, model)->決定返回頁面
				return (modelAndView != null) ? modelAndView : new ModelAndView("error", model);
			}
			
			若有配置ErrorViewResolver，即會到該頁面
			
			@RequestMapping
			public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
				HttpStatus status = getStatus(request);
				if (status == HttpStatus.NO_CONTENT) {
					return new ResponseEntity<>(status);
				}
				Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
				return new ResponseEntity<>(body, status);
			}
			
	 * - 自行配置
	 * 		如何訂製錯誤頁面
	 * 			(狀態馬，可寫5xx.html 4xx.html)
	 * 			error/狀態馬.html
	 * 			有模板引擎，可讀取資料
				timestamp: 	時間搓
				status: 	狀態馬
				error: 		錯誤提示
				exception:	錯誤對象
				message:	異常訊息
				errors: 	jsr303數據較驗錯誤都在這
	 * 			
	 * 			模板引擎找不到，則到靜態資源文件下
	 * 			static/error/狀態馬.html，但上述的資訊，無法讀取
	 * 
	 * 			以上都沒有，則使用預設
	 * 			
	 * 		如何訂製json
	 * 
	 */

}
