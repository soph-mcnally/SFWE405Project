/*  @Author: Karri Fox
    Security configuration class to set up basic security for the application. This is currently 
    very basic and allows all requests to the /api/auth/** endpoints while requiring authentication 
    for all other endpoints. This is a starting point and can be expanded upon in the future to 
    add more specific security rules and authentication mechanisms.
*/
package SFWE405.Project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> {})
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/people/**").permitAll()
                .requestMatchers("/api/manageUserAccounts/**").permitAll()
                .requestMatchers("/api/academic-record/**").permitAll()
                .requestMatchers("/api/enrollment/**").permitAll()
                .requestMatchers("/api/homework-assignment/**").permitAll()
                .requestMatchers("/api/semester/**").permitAll()
                .requestMatchers("/api/universities/**").permitAll()
                .requestMatchers("/api/university-requirements/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
