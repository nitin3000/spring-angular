package com.springular.springangular;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;

import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;



@SpringBootApplication
@RestController
public class SpringAngularApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAngularApplication.class, args);
	}

	@GetMapping("/resource")
	@ResponseBody
	public Map<String, Object> home() {
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", UUID.randomUUID().toString());
		model.put("content", "Hello World!");
		
		return model;
		
	}
	
	@RequestMapping("/user")
	public Principal user( Principal user) {
		return user;		
	}
	
	@Configuration
	@EnableWebSecurity
	@EnableMethodSecurity
	protected static class SecurityConfiguration {
		
		@Bean
		protected SecurityFilterChain configure (HttpSecurity http) throws Exception {
			CookieCsrfTokenRepository tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
			XorCsrfTokenRequestAttributeHandler delegate = new XorCsrfTokenRequestAttributeHandler();
			// set the name of the attribute the CsrfToken will be populated on
			delegate.setCsrfRequestAttributeName("_csrf");
			// Use only the handle() method of XorCsrfTokenRequestAttributeHandler and the
			// default implementation of resolveCsrfTokenValue() from CsrfTokenRequestHandler
			CsrfTokenRequestHandler requestHandler = delegate::handle;			
	        http.httpBasic(Customizer.withDefaults())
	        .csrf((csrf) -> csrf
	    			.csrfTokenRepository(tokenRepository)
	    			.csrfTokenRequestHandler(requestHandler))
			//.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
	        .authorizeHttpRequests((authz)->authz.requestMatchers("/index.html","/","/home","/login","/*.js","/*.ico","/*.css").permitAll()
	        		.anyRequest().authenticated()
	        		);
	        return http.build();
		}
	}	
	
	private static final class CsrfCookieFilter extends OncePerRequestFilter {

		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
				throws ServletException, IOException {
			CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
			// Render the token value to a cookie by causing the deferred token to be loaded
			csrfToken.getToken();

			filterChain.doFilter(request, response);
		}

	}	
	
}
