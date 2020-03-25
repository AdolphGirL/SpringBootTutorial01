package com.reyes.tutorial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	
//	@ResponseBody
//	@RequestMapping("/")
//	public String index(){
//		return "hello";
//	}
	
	@GetMapping("/hello")
	public String index(Model model){
//		DEFAULT_PREFIX = "classpath:/templates/index.html";
		model.addAttribute("hello", "<h5>你好</h5>");
		return "index";
	}

}
