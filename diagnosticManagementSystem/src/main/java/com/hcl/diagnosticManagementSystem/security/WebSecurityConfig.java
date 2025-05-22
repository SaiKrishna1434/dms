package com.hcl.diagnosticManagementSystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.hcl.diagnosticManagementSystem.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	
	private final CustomUserDetailsService userDetailsService;
	
	public WebSecurityConfig(CustomUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                		.requestMatchers("/registration.html").permitAll() // Allow access to the registration HTML
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() // Allow access to static assets
                        .requestMatchers(
								"/api/admin/register",
								"/api/members/register",
								"/api/admin/login",
								"/api/members/login",
								"/v3/api-docs/**",
								"/swagger-ui/**",
								"/swagger-ui.html"
						).permitAll()
                        .requestMatchers("/api/customers/register").permitAll() // Allow registration API without authentication
                        
                        // Allow all GET requests to /api/medicare-services (including search and individual retrieval) for customers
//                      .requestMatchers(HttpMethod.GET, "/api/medicare-services/**").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.POST, "/api/medicare-services").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/medicare-services/{id}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/medicare-services/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/medicare-services/**").permitAll()
                        
                        
                        //  HealthCheckup Endpoints Security Configuration
                        .requestMatchers(HttpMethod.GET, "/api/healthcheckup/search").permitAll() // Allow searching for plans
                        .requestMatchers(HttpMethod.GET, "/api/healthcheckup/{id}").permitAll()   // Allow retrieving a single plan by ID
//                        .requestMatchers(HttpMethod.POST, "/api/healthcheckup/apply/{userId}/{healthCheckupId}").authenticated() // Secure apply
                        .requestMatchers(HttpMethod.POST, "/api/healthcheckup/apply/{userId}/{healthCheckupId}").permitAll() 
//                        .requestMatchers(HttpMethod.GET, "/api/healthcheckup/applications/{userId}").authenticated() // Secure get applications
                        .requestMatchers(HttpMethod.GET, "/api/healthcheckup/applications/{userId}").permitAll() 
                        .requestMatchers(HttpMethod.POST, "/api/healthcheckup/create").permitAll() 
                    
                        
                        
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> httpBasic.disable())
 /*
//                .formLogin(Customizer.withDefaults()) 
//                .formLogin((form) -> form.disable())
                
//                .formLogin((form -> form
//                		.loginPage("/login.html") // Use your custom login page
//                        .permitAll()
//                        .loginProcessingUrl("/login") // Spring Security's default login processing URL
//                        .defaultSuccessUrl("/customer/dashboard", true) // Redirect after login
//                        .failureUrl("/login.html?error=true")
//                	))
//                .logout((logout) -> logout.permitAll());
 */
        .httpBasic(basic -> basic.disable())
        .authenticationProvider(authenticationProvider()); // Register the authentication provider


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
