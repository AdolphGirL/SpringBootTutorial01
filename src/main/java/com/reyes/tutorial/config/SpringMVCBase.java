package com.reyes.tutorial.config;

public class SpringMVCBase {
	
	/**
	 * spring boot 自動配置 spring mvc (org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.class)
	 * - 自動配置ViewResolve，可以自訂視圖解析器，寫到容器中，spring boot啟動時，會載入
	 * - 自動註冊 Converter、GenericConverter、Formatter beans
	 *   處理前端請求的轉換
	 * - Support HttpMessageConverters，spring mvc用來轉換請求和回應
	 *   this.messageConvertersProvider.ifAvailable((customConverters) -> converters.addAll(customConverters.getConverters()));
	 * 
	 * - 拓展spring mvc，可以繼承WebMvcConfigurerAdapter.class
	 * - 若配置檔設定，@EnableWebMvc。。則原先spring boot web mvc 自動配置則會取消，包含css等都會取消
	 *   盡量不要使用，使用表示，要全面接管
	 * 
	 */

}
