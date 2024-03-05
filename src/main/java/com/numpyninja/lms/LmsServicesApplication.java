package com.numpyninja.lms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
//@ComponentScan(basePackages="com.ninja.lms")
@Configuration
public class LmsServicesApplication {
	
	@Value("${app.frontend.url}")
	private String frontEndURL;

    public static void main(String[] args) {
        SpringApplication.run(LmsServicesApplication.class, args);
    }

    
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				.allowedOrigins(frontEndURL,"http://localhost:4200")
				.allowedMethods("GET","POST","PUT","PATCH","DELETE");
			}
		};
	}
}
