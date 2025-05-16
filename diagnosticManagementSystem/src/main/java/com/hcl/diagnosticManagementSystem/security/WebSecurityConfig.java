package com.hcl.diagnosticManagementSystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/customers/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/healthcheckup/plans/search").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/healthcheckup/apply").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/healthcheckup/testapply").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/healthcheckup/plans/create").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin((form) -> form.disable())
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}





/*
package com.diagnosticmedicalmanagementsystem.dms.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf((csrf) -> csrf.disable())
	        .authorizeHttpRequests((requests) -> requests
	            .requestMatchers("/api/customers/register").permitAll()
	            .requestMatchers(HttpMethod.POST, "/api/healthcheckup/plans/create").permitAll()
	            .requestMatchers(HttpMethod.GET, "/api/healthcheckup/plans/search").permitAll()
	            .requestMatchers(HttpMethod.POST, "/api/healthcheckup/apply").permitAll()
	            .requestMatchers(HttpMethod.POST, "/api/healthcheckup/testapply").permitAll()
	            .anyRequest().authenticated()
	        )
	        .httpBasic(httpBasic -> httpBasic.disable()) // Explicitly disable basic auth
	        .formLogin((form) -> form.disable())    // Explicitly disable form login
	        .logout((logout) -> logout.permitAll());

	    return http.build();
	}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
    // .requestMatchers("/api/medicare/**").permitAll()
    // .requestMatchers("/api/insurance/**").permitAll()
//    .requestMatchers("/api/report/**").permitAll()
}


*/




/*

package com.diagnosticmedicalmanagementsystem.dms.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable()) // Disable CSRF for simplicity in this example.  **Use with caution in production!**
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/customer/register").permitAll() // Allow anyone to register
                        
                        .requestMatchers("/api/healthcheckup/**").permitAll()
                        
                        .anyRequest().authenticated() // All other requests need authentication
                )
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin((form) -> form
                        .loginPage("/login") //custom login page
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
    // .requestMatchers("/api/medicare/**").permitAll()
    // .requestMatchers("/api/insurance/**").permitAll()
//    .requestMatchers("/api/report/**").permitAll()
}

*/
