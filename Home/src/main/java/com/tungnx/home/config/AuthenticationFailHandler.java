//package com.tungnx.home.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//public class AuthenticationFailHandler implements AuthenticationFailureHandler {
//	private static final Logger log = LoggerFactory.getLogger(AuthenticationFailHandler.class);
//	private String failureUrl;
//
//	public AuthenticationFailHandler(String failureUrl) {
//		this.failureUrl = failureUrl;
//	}
//
//	@Override
//	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
//		HttpSession httpSession = request.getSession(false);
//		if (httpSession != null && httpSession.getAttribute("userId") != null) {
//			log.info("AuthenticationFailHandler, userId removed: " + httpSession.getAttribute("userId"));
//			httpSession.removeAttribute("userId");
//		}
//		response.sendRedirect(this.failureUrl);
//	}
//}
