package com.reyes.tutorial.config;

public class LogBase {
	
	/**
	 * 
	 * spring boot log 處理
	 *  - 不管底層框架用甚麼。最後都會被抽象層的slf4j-api所套用；因此，造就spring boot可以自動配合底層框架
	 *  - 預設使用抽象層slf4j，底層logack
	 *  - 特殊情況，當使用logback.xml(會被log底層框架先讀取到)當設定檔時，無法使用<springProfile name="xxx"></springProfile>標籤
	 *    若名稱設為logback-spring.xml，則會先交由spring boot讀取，就可以使用spring的功能
	 *    (範例看logback-spring.xml)
	 *  - 客製化設定檔的讀取會以 -spring.xml為最高優先
	 *  
	 *  
	 *  tag語法  
	 *  <springProfile name="staging">
		    <!-- configuration to be enabled when the "staging" profile is active -->
		</springProfile>
		
		<springProfile name="dev | staging">
		    <!-- configuration to be enabled when the "dev" or "staging" profiles are active -->
		</springProfile>
		
		<springProfile name="!production">
		    <!-- configuration to be enabled when the "production" profile is not active -->
		</springProfile>
	 *  
	 */

}
