package com.nashtech.rookies.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.nashtech.rookies.jwt.JwtEntryPoint;
import com.nashtech.rookies.jwt.JwtTokenFilter;
import com.nashtech.rookies.security.userprincal.UserDetailService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailService userDetailService;

	@Autowired
	private JwtEntryPoint jwtEntryPoint;

	@Bean
	public JwtTokenFilter jwtTokenFilter() {
		return new JwtTokenFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// CorsConfiguration config = new CorsConfiguration();
		// List<String> newlist = new ArrayList<>();
		// config.setAllowCredentials(true);
		// List<String> newList2 = new ArrayList<>();
		// newList2.add("Authorization");
		// newList2.add("Cache-Control");
		// newList2.add("Content-Type");

		// config.setAllowedHeaders(newList2);
		// config.addAllowedMethod("*");
		// newlist.add("http://localhost:3000");
		// config.setAllowedOrigins(newlist);

		httpSecurity.cors().and().csrf().disable()//.cors().configurationSource(request -> config).and()
				.authorizeRequests()
				.antMatchers("/api/v1/users/login").permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/users/create").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/api/v1/users/").hasAuthority("ADMIN")
				.antMatchers(HttpMethod.GET,"/api/v1/users/id/**").hasAuthority("ADMIN")
				.anyRequest().authenticated()
				.and().exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		httpSecurity.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
