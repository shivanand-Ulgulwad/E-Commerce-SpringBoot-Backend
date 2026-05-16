//package com.app.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)  throws Exception {
//        return configuration.getAuthenticationManager();
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http,JwtFilter filter){
//     return   http.csrf(csrf -> csrf.disable())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/auth/**").permitAll()
//                        .requestMatchers(HttpMethod.DELETE,"/users/**").hasAuthority("ROLE_ADMIN")
//                        .requestMatchers(HttpMethod.POST,"/users/**").hasAuthority("ROLE_ADMIN")
//                        .requestMatchers(HttpMethod.PUT,"/users/**").hasAuthority("ROLE_ADMIN")
//                        .requestMatchers(HttpMethod.DELETE,"/products/**").hasAuthority("ROLE_ADMIN")
//                        .requestMatchers(HttpMethod.POST,"/products/**").hasAuthority("ROLE_ADMIN")
//                        .requestMatchers(HttpMethod.PUT,"/products/**").hasAuthority("ROLE_ADMIN")
//                        .requestMatchers(HttpMethod.DELETE,"/categories/**").hasAuthority("ROLE_ADMIN")
//                        .requestMatchers(HttpMethod.POST,"/categories/**").hasAuthority("ROLE_ADMIN")
//                        .requestMatchers(HttpMethod.GET,"/categories/**").permitAll()
//                        .requestMatchers(HttpMethod.GET,"/products/**").permitAll()
//                        .requestMatchers("/cart/**").hasAuthority("ROLE_USER")
//                        .requestMatchers("/orders/**").hasAuthority("ROLE_USER")
//                        .requestMatchers("/users/**").authenticated()
//                        .anyRequest().authenticated()).addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class).build();
//    }


//}

package com.app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        return http

                .csrf(csrf -> csrf.disable())

                .formLogin(form -> form.disable())

                .httpBasic(form -> form.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // Public endpoints
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/products/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categories/**").permitAll()

                        // User-only features
                        .requestMatchers("/cart/**").hasRole("USER")
                        .requestMatchers("/orders/**").hasRole("USER")
                        .requestMatchers("/users/**").authenticated()

                        // Admin-only features
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/products/**").hasRole("ADMIN")
                        .requestMatchers("/categories/**").hasRole("ADMIN")

                        // fallback safety
                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .build();
    }
}
