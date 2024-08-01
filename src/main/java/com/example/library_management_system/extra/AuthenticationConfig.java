package com.example.library_management_system.extra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthenticationConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authz) -> 
						authz.anyRequest().authenticated())
						.httpBasic();
		http.csrf().disable();

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
		userDetailsService.createUser(User.withUsername("user").password("{noop}password").roles("USER").build());
		return userDetailsService;
	}
}
