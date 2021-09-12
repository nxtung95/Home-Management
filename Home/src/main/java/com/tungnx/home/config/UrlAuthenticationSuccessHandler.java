package com.tungnx.home.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private static final Logger log = LoggerFactory.getLogger(UrlAuthenticationSuccessHandler.class);

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		log.info("Authentication sucess to redirect url, role: " + authentication.getAuthorities().toString());
		handle(request, response, authentication);
		setAuthenticationAttributes(request);
		clearAuthenticationAttributes(request);
	}

	private void setAuthenticationAttributes(HttpServletRequest request) {
		HttpSession httpSession = request.getSession(false);
		if (request.getAttribute("userId") != null && httpSession != null) {
			log.info("Authentication sucess, set userId atrribute into session ======= userId =" + request.getAttribute("userId"));
			httpSession.setAttribute("userId", request.getAttribute("userId"));
		}
	}

	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

		String targetUrl = determineTargetUrl(authentication);

		if (response.isCommitted()) {
			log.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(final Authentication authentication) {

		Map<String, String> roleTargetUrlMap = new HashMap<>();
		roleTargetUrlMap.put("ROLE_MEMBER", "/app/client/dashboard");
		roleTargetUrlMap.put("ROLE_ADMIN", "/app/admin/dashboard");

		final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (final GrantedAuthority grantedAuthority : authorities) {
			String authorityName = grantedAuthority.getAuthority();
			if(roleTargetUrlMap.containsKey(authorityName)) {
				return roleTargetUrlMap.get(authorityName);
			}
		}

		throw new IllegalStateException();
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
}
