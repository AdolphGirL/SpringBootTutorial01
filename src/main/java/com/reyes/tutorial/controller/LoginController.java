package com.reyes.tutorial.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LoginController {
	
	private final static Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	/* redirect第一種方式
	@PostMapping(value = "/user/auth")
	public String login(@RequestParam String username, @RequestParam String password, Model model){
		logger.info("[LoginController]-[請求參數資料]-[username: {}、password: {}]", username, password);
		
		if(!StringUtils.isEmpty(password) && "123456".equals(password)){
//			重新導向，才不會引起直接連結主頁，F5還會再提交一次請求
			return "redirect:/main";			-> keypoint
		}
		
		model.addAttribute("loginMsg", "帳號密碼有誤");
		return "login";
	}
	*/
	
	@PostMapping(value = "/user/auth")
	public RedirectView login(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes, HttpSession session){
		logger.info("[LoginController]-[請求參數資料]-[username: {}、password: {}]", username, password);
		
		if(!StringUtils.isEmpty(password) && "123456".equals(password)){
			RedirectView redirectView = new RedirectView();
			
//			是否使用相對路徑
			redirectView.setContextRelative(true);
			
			redirectView.setUrl("/main");
			
			session.setAttribute("loginUser", username);
			
			return redirectView;
		}else{
			RedirectView redirectView = new RedirectView();
			redirectView.setContextRelative(true);
			redirectAttributes.addFlashAttribute("loginMsg", "帳號密碼有誤，請重新輸入");
			redirectView.setUrl("/login");
			return redirectView;
		}
	}
	
	@GetMapping(value = "/login")
	public String login(){
		return "login";
	}
	
	/**
	 * redirect: 
	 * 			服務器收到請求後會發送一個狀態碼給客戶端，客戶端會再發一次請求給伺服器，並且第一次的請求request的數據會消失，
	 * 			所以redirect相當於客戶端向伺服器發送兩次請求，第一次請求的數據不會給第二次，url會有兩次的變化
	 * 			解此，再登入時form的提交，應使用重定向，才不會客戶端刷新頁面而導致原先form再重新提交一次
	 * 			redirect基本上都會第二次會變為get請求，因為第一次的資料都消失的緣故
	 * 
	 * 			Flash attribute
	 * 			- addFlashAttribute() 加的 attr，不會出現在url上
	 * 			- addFlashAttribute() 加的 attr，一旦取出後就會清空，適合作為form提交後的feedback Message.
	 * 
	 * 			若重新定向的url有對應處理的controller，則
	 * 			redirectAttributes.addAttribute加的attr，使用 @RequestParam()来fetch
	 * 			redirectAttributes.addFlashAttribute()加的attr, 使用 @ModelAttribute()来fetch
	 * 			public String withFlashTarget(Model model, @RequestParam("param") String param, @ModelAttribute("flashParam") String flashParam) 
	 * 
	 * forward:
	 * 			服務器內部的重新定向，相當於客戶端向服務器發送一次請求，而服務器本身處理兩次，請求數據不會消失，url只會變化一次
	 * 			forward會保有原先的請求的緣故，因此原先是post請求，forward還是post請求
	 */
	
}
