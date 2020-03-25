package com.reyes.tutorial.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 透過@Value注入
 *  - 支持#{演算}
 *  - 配置文件命名需要與變數一致，不支持鬆散語法
 *  - 引用的地方，需要啟動後須在容器內，以此範例為例，需要加個@Component
 *  - 不支持JSR303數據校驗
 *  - 不支持複雜類型
 */ 
@Component
public class ValueBean {
	
	@Value(value = "${vb.name}")
	private String name;
	
	@Value(value = "#{123 * 1}")
	private int age;
	
	public ValueBean(){
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "ValueBean [name=" + name + ", age=" + age + "]";
	}
	
	
	
}
