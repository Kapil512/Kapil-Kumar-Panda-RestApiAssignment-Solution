package com.greatlearning.employee.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	DataSource dataSource;

	private static final String[] AUTH_WHITELIST = {
	        "/swagger-resources/**",
	        "/swagger-ui.html",
	        "/v2/api-docs",
	        "/webjars/**",
	        "/h2-console/**",
	        "/configuration/security",
	        "/swagger/**",
	        "/configuration/ui"
	};
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).withDefaultSchema()
				.withUser(User.withUsername("ADMIN").password(getPasswordEncoder().encode("ADMIN")).roles("ADMIN"))
				.withUser(User.withUsername("USER").password(getPasswordEncoder().encode("USER")).roles("USER"));
	}

	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(AUTH_WHITELIST);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/employee/addEmployee", "/employee/updateEmployee", "/employee/deleteEmployeeByid")
				.hasRole("ADMIN")
				.antMatchers("/employee/list", "/employee/sortedList", "/employee/findById", "/employee/findByName")
				.hasAnyRole("USER", "ADMIN").antMatchers("/").permitAll()
				.antMatchers("/swagger-resources/**").permitAll()
				.anyRequest().authenticated()
				.and().formLogin();
	}
}
