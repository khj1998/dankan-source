package com.dankan.config;

import com.dankan.repository.UserRepository;
import com.dankan.util.JwtCustomFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//@PreAuthorize 어노테이션을 메소드 단위로 추가하기 위해
public class SecurityConfig {
    private final UserRepository userRepository;

    public SecurityConfig(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return new WebSecurityCustomizer() {
            @Override
            public void customize(WebSecurity web) {
                web.ignoring().antMatchers(
                        "/",
                        "/auth/**",
                        "/user/signup",
                        "/login/**",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/token/**",
                        "/chat/**",
                        "/chatting/**"
                );
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterAfter(new JwtCustomFilter(userRepository), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/post/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .antMatchers("/room/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .antMatchers("/report/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .antMatchers("/user/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/univ/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/review/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .anyRequest().denyAll()
                .and().build();
    }
}