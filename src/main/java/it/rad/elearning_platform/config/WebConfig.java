package it.rad.elearning_platform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Allow all endpoints
                .allowedOrigins("*")  // Allow all origins (for testing, change to specific origin later)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Allow common methods
                .allowedHeaders("*")  // Allow all headers
                .allowCredentials(false);  // Set to true only if using cookies/auth headers
    }
}
