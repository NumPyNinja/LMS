package com.numpyninja.lms.security;

import com.numpyninja.lms.cache.UserDetailsCache;
import com.numpyninja.lms.security.jwt.AuthEntryPointJwt;
import com.numpyninja.lms.security.jwt.AuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *   AuthenticationEntryPoint : a filter which is the first point of entry for Spring Security.
 *   It is the entry point to check if a user is authenticated and logs the person in or throws exception (unauthorized).
 *   Usually the class can be used like that in simple applications but when using Spring security in REST, JWT etc
 *   one will have to extend it to provide better Spring Security filter chain management.
 */
@Configuration
@EnableWebSecurity   // allows Spring to find and automatically apply the class to the global Web Security.
//@EnableMethodSecurity
//@EnableGlobalMethodSecurity(
//        // securedEnabled = true,
//        jsr250Enabled = true,    // enables @RolesAllowed annotation.
//        prePostEnabled = true)  // provides AOP security on methods. It enables @PreAuthorize, @PostAuthorize
@RequiredArgsConstructor
public class WebSecurityConfig {


    private final AuthEntryPointJwt unauthorizedHandler;
    private static final String[] PUBLIC_URLS = {
            "/v2/api-docs",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-ui/index.html",
            "/webjars/**"
    };

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserCache userCache() {
        return new UserDetailsCache();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(unauthorizedHandler)
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .anyRequest().authenticated()
                )

                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}