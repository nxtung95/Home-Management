package com.tungnx.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/app/client/")
public class ContactController {
	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String contact() {
		return "client/contact";
	}
}
