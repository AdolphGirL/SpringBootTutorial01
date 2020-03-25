package com.reyes.tutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootTutorial01Application {
	
	/**
	 * 
	 * 自動配置
	 * EnableAutoConfiguration
	 * AutoConfigurationPackage AutoConfigurationPackages.Registrar.class -> 自動註冊package
	 * AutoConfigurationPackage，所在位置的package與其下面的package的所有符合的組件，掃描至springboot中
	 * 
	 */
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootTutorial01Application.class, args);
	}

}
