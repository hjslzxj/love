/**
 * 
 */
package com.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author dell
 *
 */
@Controller
public class UserController {

	@RequestMapping("/hello")
	@ResponseBody
	public String hello(){
		return "hello";
		
	}
	
	@RequestMapping("/index")
	public String index(Model model){
		model.addAttribute("name", "zxj");
		model.addAttribute("age", 24);
		model.addAttribute("info", "111");
		
		return "index";
	}
}
