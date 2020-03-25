package com.reyes.tutorial.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.reyes.tutorial.components.SelfLocaleResolver;
import com.reyes.tutorial.filter.LoginHandlerInterceptor;

/**
 * 簡單映射務需要再使用到controller
 * 
 * 
 * @EnableWebMvc -> 若開啟，則會全面接管Spring MVC
 * 請使用WebMvcConfigurerAdapter(@Deprecated在2.x之後)拓展Spring MVC
 * 改 implemetns WebMvcConfigurer(WebMvcConfigurerAdapter實作的對象) interface
 * 或者 extends WebMvcConfigurationSupport來實現
 * 但請留意，如果使用WebMvcConfigurationSupport，則其他靜態資源也要映射，有點等同於啟用了EnableWebMvc
 * 
 */
@Configuration
public class WebMvcMappingConfig implements WebMvcConfigurer {
//public class WebMvcMappingConfig extends WebMvcConfigurationSupport {
	
	private final static Logger logger = LoggerFactory.getLogger(WebMvcMappingConfig.class); 
	
	/**
	 * 方法一
	 * implemetns WebMvcConfigurer也可以實現此接口
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("login");
//		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/main").setViewName("dashboard");
	}
	
	/**
	 * 方法二
	 */
//	@Override
//	protected void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/").setViewName("login");
////		registry.addViewController("/main.html").setViewName("dashboard");
////		registry.addViewController("/login.html").setViewName("login");
//	}
	
	/**
	 * 已可以如此方法二
	 */
//	@Bean
//	public WebMvcConfigurationSupport webMvcConfigurationSupport() {
//		WebMvcConfigurationSupport support = new WebMvcConfigurationSupport() {
//			
//			@Override
//			protected void addViewControllers(ViewControllerRegistry registry) {
//				registry.addViewController("/").setViewName("login");
//				registry.addViewController("/main.html").setViewName("dashboard");
//				// registry.addViewController("/login.html").setViewName("login");
//			}
//
//			@Override
//			public void addResourceHandlers(ResourceHandlerRegistry registry) {
//				// registry.addResourceHandler("/resources/static/**").addResourceLocations("classpath:/static/");
//				registry.addResourceHandler("/static/**").addResourceLocations("classpath:/resources/static/");
//				super.addResourceHandlers(registry);
//			}
//		};
//		return support;
//	}
	
	@Bean(name = "localeResolver")
	public LocaleResolver selfLocaleResolver(){
		logger.info("[WebMvcMappingConfig]-[加載]-[{}]", "SelfLocaleResolver解析器");
		return new SelfLocaleResolver();
	}
	
	/**
	 * 增加攔截器，防止未登入者可以查看其餘頁面
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginHandlerInterceptor())
//				所有請求路徑
				.addPathPatterns("/**")
//				spring boot已自動做好靜射資源，所以不需特別排除
//				.excludePathPatterns("/", "/login", "/webjars/**", "/user/auth");
				.excludePathPatterns("/", "/login", "/user/auth");
	}

}
