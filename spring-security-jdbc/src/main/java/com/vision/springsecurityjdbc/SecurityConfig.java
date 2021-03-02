package com.vision.springsecurityjdbc;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//		.withUser("blah").password("{noop}blah").roles("USER").and()
//		.withUser("foo").password("{noop}foo").roles("USER").and()
//		.withUser("bar").password("{noop}bar").roles("ADMIN");
//	}

	@Autowired
	DataSource ds;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(ds).withDefaultSchema().withUser("blah").password("blah")
				.roles("USER").and().withUser("foo").password("foo").roles("USER").and().withUser("bar")
				.password("bar").roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/admin").hasRole("ADMIN").antMatchers("/user").hasAnyRole("USER", "ADMIN")
				.antMatchers("/").permitAll().and().formLogin();
	}

	@SuppressWarnings("deprecation")
	@Bean
	public PasswordEncoder getPasspordEncoder() {

		return NoOpPasswordEncoder.getInstance();

	}
}
