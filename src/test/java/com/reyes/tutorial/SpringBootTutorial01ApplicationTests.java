package com.reyes.tutorial;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.reyes.tutorial.bean.PropertiesBean;
import com.reyes.tutorial.bean.PropertySourceBean;
import com.reyes.tutorial.bean.ValueBean;
import com.reyes.tutorial.config.ConfigCreateBean;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootTutorial01ApplicationTests {
	
	@Autowired
	private PropertiesBean propertiesBean;
	
	@Autowired
	private ValueBean valueBean;
	
	@Autowired
	private PropertySourceBean propertySourceBean;
	
	@Autowired
	private ApplicationContext ioc;
	
	@Test
	public void jpaOneToOneTest(){
		System.out.println(propertiesBean);
		System.out.println(valueBean);
		System.out.println(propertySourceBean);
		System.out.println(ioc.containsBean("helloConfigCreateBean"));;
	}

}
