package com.tungnx.home.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutSuccessAuthenticationHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {
	private static final Logger log = LoggerFactory.getLogger(LogoutSuccessAuthenticationHandler.class);

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		String refererUrl = request.getHeader("Referer");
		log.info("Logout from: " + refererUrl);
		super.setDefaultTargetUrl("/app/login");
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("userId") != null) {
			log.info("Session userId: " + session.getAttribute("userId"));
			session.removeAttribute("userId");
		}
		super.onLogoutSuccess(request, response, authentication);
	}
}
