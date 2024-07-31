package com.example.library_management_system.helpers;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration 
@EnableWebMvc
public class Swagger {                                    
	  @Bean
	    public OpenAPI apiInfo() {
	        return new OpenAPI().info(new Info().title("Library Management System").version("1.0.0"));
	    }

	    @Bean
	    public GroupedOpenApi httpApi() {
	        return GroupedOpenApi.builder()
	                .group("api")
	                .pathsToMatch("/api/**")
	                .build();
	    }

}