package com.numpyninja.lms.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "LMS Application",
                        url = "www.lms.org",
                        email = "lms@gmail.com"
                ),
                title = "Learning Management System Phase 4",
                description = "Openapi Specification For LMS Application",
                termsOfService = "Terms of service",
                license = @License(
                        name = "License of API",
                        url = "API license URL"
                ),
                version = "4"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:1234/lms/" )
        },
        security = {
                @SecurityRequirement(
                        name = "apiKey",
                        scopes = {"read","write"}
                )
        }
)
@SecurityScheme(
        name = "apiKey",
        description = "A JWT token is required to access this API. JWT token can be obtained by providing correct username and password in the LMS User API",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        paramName = "Authorization"
)
public class OpenApiConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
