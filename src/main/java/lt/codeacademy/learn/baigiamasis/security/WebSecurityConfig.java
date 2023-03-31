package lt.codeacademy.learn.baigiamasis.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.AllArgsConstructor;
import lt.codeacademy.learn.baigiamasis.user.UserService;
import static lt.codeacademy.learn.baigiamasis.user.Roles.*;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig  {
	
	private UserService userService;
	
	@Bean
	public SecurityFilterChain filtherChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeHttpRequests()
			.requestMatchers("/admin/**").permitAll()
//			.requestMatchers("/admin/**").hasAuthority(ADMIN)
			.requestMatchers("/**").permitAll()
			.and()
			.formLogin();
		
		return http.build();	
	}
	
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userService);
		return provider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
