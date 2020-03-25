package com.reyes.tutorial.config;

public class StaticBase {
	
	
	/**
	 * web jar與映射資源規則 WebMvcAutoConfiguration，spring webmvc的自動配置 
	 * - addResourceHandlers添加資源映射
	 *  	if (!registry.hasMappingForPattern("/webjars/**")) {
	 * 				customizeResourceHandlerRegistration(registry.addResourceHandler("/webjars/**")
	 * 					.addResourceLocations("classpath:/META-INF/resources/webjars/")
	 * 					.setCachePeriod(getSeconds(cachePeriod)).setCacheControl(cacheControl));
	 * 		}
	 * 		
	 * 		讀取的路徑/META-INF/resources/webjars/，使用jar的方式載入前端資源
	 * 		因此映射路徑/webjars/xxxx會對應/META-INF/resources/webjars/xxx
	 * 		
	 * - 自訂義的引入
	 *   ResourceProperties.class
	 *   ConfigurationProperties(prefix = "spring.resources", ignoreUnknownFields = false)，在properties可以設定與資源相關的參數
	 *   
	 *   ResourceProperties不設定預設為以下路徑
	 *   private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/",
	 *   			"classpath:/resources/", "classpath:/static/", "classpath:/public/" };
	 *   
	 *   訪問時
	 *   String staticPathPattern = this.mvcProperties.getStaticPathPattern(); -> 
	 *   staticPathPattern = "/**";
	 *   因此。當/** 來訪問時，若沒有被處理則會到ResourceProperties設定的路徑尋找
	 *   
	 *   
	 *   
	 * - 歡迎頁
	 *   WebMvcAutoConfiguration.class內
	 *   public WelcomePageHandlerMapping welcomePageHandlerMapping(ApplicationContext applicationContext,
	 *   	FormattingConversionService mvcConversionService, ResourceUrlProvider mvcResourceUrlProvider) {
	 *   }
	 *   
	 *   當/** 來訪問時，會找index.html，放置位置也同static resource
	 *   
	 * - 圖標
	 *   WebMvcAutoConfiguration.class內(讀取規則)
	 *   favicon.ico，放置位置也同static resource
	 *   
	 * - static resource，可以交由spring.resources設定
	 *   spring.resources-static-locations=classpath:/xxx/, classpath:/xxx/, ....
	 */
	
	/**
	 * <!-- (當純href不加/，則會根據上下文；加/，則會變成url從/(不管有無context-path)開始) -->
	 */
	
}
