package com.greenart.MyHome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.greenart.MyHome.model.User;
import com.greenart.MyHome.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {

	
	 @Autowired 
	 private UserService userService;
	

	@GetMapping("/login")
	public String login() {
		return "account/login";
	}

	@GetMapping("/register")
	public String register() {
		return "account/register";
	}

	@PostMapping("/register")
	public String register(User user) {
		try {
		userService.save(user);
		}
		catch (DataIntegrityViolationException e) {
			System.out.println("history already exist");
		}
		return "redirect:/";                 // get mapping으로 이동한다.
	}
}