package com.jwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jwt.security.jwt.AuthEntryPointJwt;
import com.jwt.security.jwt.AuthTokenFilter;
import com.jwt.security.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    // securedEnabled = true,
    // jsr250Enabled = true,
    prePostEnabled = true)
public class WebSecurityConfig  { // extends WebSecurityConfigurerAdapter {
 
  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder());
   
      return authProvider;
  }
  
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        //apiGetway
        .authorizeRequests().antMatchers("/api/auth/**").permitAll()
        .antMatchers("/api/test/**").permitAll()
        .antMatchers("/restApi/service/**").permitAll()
        //product service
        .and()
        .authorizeRequests()
        .antMatchers("/product/**").permitAll()
      //RestApi service
 //       .and()
//        .authorizeRequests()
//        .antMatchers("/restApi/service/**").permitAll()
    //Customer service
        .and()
       .authorizeRequests()
       .antMatchers("/customer/services/**").permitAll()
       .anyRequest().authenticated();
    
    http.authenticationProvider(authenticationProvider());

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    
    return http.build();
  }

private HttpSecurity and() {
	// TODO Auto-generated method stub
	return null;
}
  
//  //MISC
//  .authorizeRequests()
//  .antMatchers("/employee/services/**",
//          "/emailer/**",
//          "/test",
//          "/document/reference/fetch/**",
//          "/customer/dashboard/get/finacle/cities",
//          "/gdpr/submit/data",
//          "/campaign/form/**")
//  .permitAll()
//  .and()
//
//  // Resolution Framework
//  .authorizeRequests()
//  .antMatchers(
//          "/resolution/branch/**",
//          "/resolution/application/download/resolution/web/application/excel",
//          "/resolution/application/download/resolution/application/excel",
//          "/resolution/download/resolution/application/pdf",
//          "/resolution/application/download/resolution/web/application/pdf",
//          "/resolution/application/save/data",
//          "/resolution/application/generate/otp",
//          "/resolution/application/verify/otp",
//          "/resolution/application/web/send/internal/mails",
//          "/resolution/send/bulk/emailer",
//          RESOLUTION_DASHBOARD_PREFIX + "/upload/classification/matrix/excel" ).permitAll()
//  .and() 
}
