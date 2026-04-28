/*  @Author: Karri Fox
    CORS configuration class to allow cross-origin requests from the frontend application running on 
    http://localhost:3000. This is necessary for the frontend to be able to make API calls to the 
    backend without running into CORS issues. This is a starting point and can be expanded upon 
    in the future to allow for more specific CORS configurations if needed.
*/
package SFWE405.Project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}