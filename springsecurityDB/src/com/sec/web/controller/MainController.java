package com.sec.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sec.service.UserDetailsServiceImpl;

@Controller
public class MainController {

	@Autowired
	private UserDetailsServiceImpl auth;

	@RequestMapping(value = { "/", "/welcome**" }, method = RequestMethod.GET)
	public ModelAndView defaultPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title",
				"Spring Security Login Form - Database Authentication");
		model.addObject("message", "This is default page!");
		model.setViewName("hello");
		return model;

	}

	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title",
				"Spring Security Login Form - Database Authentication");
		model.addObject("message", "This page is for ROLE_ADMIN only!");
		model.setViewName("admin");
		return model;

	}

	@RequestMapping(value = "/login.htm", method = RequestMethod.GET)
	public ModelAndView login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
			model.setViewName("login");

		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
			model.setViewName("login");

		}

		model.setViewName("login");

		return model;

	}

	@RequestMapping(value = "/security_check", method = RequestMethod.POST)
	public ModelAndView wel(
			@RequestParam(value = "username") String user,
			@RequestParam(value = "password") String pass)

	{
		
		UserDetails userDetails=auth.loadUserByUsername(user);

		System.out.println(userDetails.getUsername()+" "+userDetails.getPassword());
		
		if (user.equals(userDetails.getUsername()) && pass.equals(userDetails.getPassword())) {
			System.out.println("welcome");
			return new ModelAndView("welcome");
		} else {

			System.out.println("login");
			return new ModelAndView("login");
		}

	}

	// for 404 access denied page
	@RequestMapping(value = "/404", method = RequestMethod.GET)
	public ModelAndView pagenotfound() {
		return new ModelAndView("login");
	}

	// for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		ModelAndView model = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			model.addObject("username", userDetail.getUsername());
		}

		model.setViewName("403");
		return model;

	}

}