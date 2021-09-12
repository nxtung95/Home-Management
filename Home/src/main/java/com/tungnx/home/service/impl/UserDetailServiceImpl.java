package com.tungnx.home.service.impl;

import com.tungnx.home.dto.AuthenticationLoginUserDto;
import com.tungnx.home.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userDetailServiceImpl")
public class UserDetailServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<AuthenticationLoginUserDto> users = userRepository.findByUsername(username);
        log.info("Login with username " + username);
        if (users == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        users.forEach(u -> {
            log.info("Role user: " + u.getRoleName());
            grantedAuthorities.add(new SimpleGrantedAuthority(u.getRoleName()));
        });
        User user = new User(users.get(0).getUsername(), users.get(0).getPassword(), grantedAuthorities);
        log.info("Info user login: " + user);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        request.setAttribute("userId", users.get(0).getId());
        return user;
    }
}
