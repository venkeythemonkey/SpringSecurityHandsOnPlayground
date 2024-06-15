package com.venkatesh.securityplayground.security.onetomanyuni.security;

import com.venkatesh.securityplayground.security.onetomanyuni.jwt.JwtFilter02;
import com.venkatesh.securityplayground.security.onetomanyuni.jwt.JwtUtils02;
import com.venkatesh.securityplayground.security.onetomanyuni.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public JwtFilter02 jwtFilter02(){
        return new JwtFilter02();
    }


    public UserDetailsService userDetailsService(){
        return username -> userRepository.findByUsername(username);
    }

    // Standard code

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/signin/**").permitAll()
                                .requestMatchers("/login/**").permitAll()
                                .requestMatchers("/hello1").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/hello2").hasRole("ADMIN")
                                .requestMatchers("/hello3").hasRole("USER")
                                .anyRequest().authenticated())
                .authenticationProvider(authenticationProvider());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter02(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//
//        http.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(request ->
//                        request.requestMatchers("/signin","/login").permitAll()
//                                .requestMatchers("/hello1").hasAnyRole("USER", "ADMIN")
//                                .requestMatchers("/hello2").hasRole("ADMIN")
//                                .requestMatchers("/hello3").hasRole("USER")
//                                .anyRequest().authenticated();
//                return http.build();
////
//
//    }

    // Standard code
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // Standard code

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
