package com.example.taskservice.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "TaskService APP",
                version = "V1",
                description = "API for managing tasks in the TaskService application."
        )
)
public class OpenApiConfig {
}
