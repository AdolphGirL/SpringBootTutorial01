package com.reyes.tutorial.config;

import org.springframework.context.annotation.Bean;

public class NationalBase {
	
	
	/**
	 * 
	 * 國際化配置
	 * - 編寫國際化配置文件，放在resources下
	 * - 配置文件.properties -> 中國(簡體中文)與台灣(正體中文)的是zh-CN與zh-TW
	 *   en_us；語系+國別的命名，若無法取得語系，則會使用預設名稱空項目的
	 * - org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration，
	 *   自動配置類別，spring boot以配置；
	 *   String basename = context.getEnvironment().getProperty("spring.messages.basename", "messages");
	 *   預設可以再classpath，放置messages.propperties，即不用在做任何配置。也可以經由spring.messages.basename指定
	 *   如下範例
	 *   spring.messages.basename=i18n.login (resources下的路徑，和文件前墜名)
	 *   
	 * - 在 WebMvcAutoConfiguration.class內，預設配置的解析locale如下，也就是沒有配置localeResolver的情況下，會使用下方配置
	 *   
	 *  	@Bean
			@ConditionalOnMissingBean
			@ConditionalOnProperty(prefix = "spring.mvc", name = "locale")
			public LocaleResolver localeResolver() {
				if (this.mvcProperties.getLocaleResolver() == WebMvcProperties.LocaleResolver.FIXED) {
					return new FixedLocaleResolver(this.mvcProperties.getLocale());
				}
				AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
				localeResolver.setDefaultLocale(this.mvcProperties.getLocale());
				return localeResolver;
			}
			
			AcceptHeaderLocaleResolver.class
			預設解析是根據
			Locale defaultLocale = getDefaultLocale();
			if (defaultLocale != null && request.getHeader("Accept-Language") == null) {
				return defaultLocale;
			}
			根據請求的Accept-Language，來便是語系和國別
			
		- 可以自行implements LocaleResolver，產生Bean，來取代原先的配置，如目前專案的範例
		  components/SelfLocaleResolver
		 	如果方法名不為localeResolver
		 	則須設定bean name為@Bean(name = "localeResolver")
	 * 
	 */

}
