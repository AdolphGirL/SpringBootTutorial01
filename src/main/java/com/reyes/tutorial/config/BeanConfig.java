package com.reyes.tutorial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Configuration，表示為設定文件
 * @Bean -> 取代以前的xml，方法的返回值會添加到容器中，且id為方法的名稱
 */

@Configuration
public class BeanConfig {
	
	
	@Bean
	public ConfigCreateBean helloConfigCreateBean() {
		return new ConfigCreateBean();
	}
	
}
