package com.reyes.tutorial.config;

public class EmbeddedServletBase {
	
	/**
	 * 
	 * 嵌入式servlet
	 * 	- 預設引入embedtomcat
	 * 	- 如何修改
	 * 		- 修改properties，server.xxx；ServerProperties.class
	 * 
	 * 
	 * 	- 切換其他的servlet容器
	 * spring boot 2.x使用WebServerFactoryCustomizer接口替代EmbeddedServletContainerCustomizer完成嵌入式servlet容器的配置
	 * 
	 * 
	 * 註冊
	 * 	Servlet
	 * 		- ServletRegistrationBean
	 * 	Filter
	 * 	Listener
	 * 
	 * 
	 * 
	 * DispatcherServletAutoConfiguration.class，自動配置servler相關
	 * 
	 * undertow (非阻塞容器)
	 * jetty
	 * 1. 排除pom文件內的tomcat
	 * 2. 引入其他容器
	 */

}
