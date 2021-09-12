package com.tungnx.home.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.context.request.RequestContextListener;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("userDetailServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/app/client/*").hasRole("MEMBER")
                    .antMatchers("/app/admin/*").hasRole("ADMIN")
                    .and()
                .formLogin()
                    .loginPage("/app/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .loginProcessingUrl("/app/login")
                    .successHandler(myAuthenticationSuccessHandler())
                    .failureUrl("/app/login?error=true")
//                    .failureHandler(authenticationFailureHandler("/app/login?error=true"))
                    .and()
                .exceptionHandling()
                .accessDeniedPage("/app/login403");
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new UrlAuthenticationSuccessHandler();
    }

//    public AuthenticationFailureHandler authenticationFailureHandler(String failureUrl) {
//        return new AuthenticationFailHandler(failureUrl);
//    }

    public static void main(String[] args) {
        BCryptPasswordEncoder b = new BCryptPasswordEncoder();
        System.out.println(b.encode("test1234"));
    }
}
