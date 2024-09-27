package com.example.Assignment.Assignment.Configurations;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@SuppressWarnings("ALL")
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1= User.withUsername("om")
                .password(passwordEncoder().encode("pass1"))
                .roles("ADMIN").build();

        UserDetails user2= User.withUsername("veda")
                .password(passwordEncoder().encode("pass2"))
                .roles("USER").build();

        return new InMemoryUserDetailsManager(user1, user2);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeHttpRequests()
//                .requestMatchers("/home/admin/**").hasRole("ADMIN")
//                .requestMatchers("/home/user/**").hasRole("USER")
                .requestMatchers("/home/public")
                .permitAll().anyRequest()
                .authenticated().and()
                .formLogin();
        return httpSecurity.build();



    }
}
