package com.dunji.dankan.config;

import com.danram.server.jwt.*;
import com.danram.server.service.member.MemberService;
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
    private final TokenProvider tokenProvider;
    private final MemberService memberService;

    public SecurityConfig(final TokenProvider tokenProvider, final MemberService memberService) {
        this.tokenProvider = tokenProvider;
        this.memberService = memberService;
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
                        "/health/**");
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
                .addFilterAfter(new JwtCustomFilter(memberService), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/member/info").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/member/info/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/member/change/{id}").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/member/name/{name}").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/member/profile/img").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/member/delete").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/party/create").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/party/create/without").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/party/info").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/party/info/{id}").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/party/myInfo").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/party/info/{partyId}").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/party/remove/member").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/party/remove/{partyId}").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/party/modify/alarm").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/fcm/api/fcm").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/feed/create/post").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/feed/find/{feedId}", "/feed/delete/{feedId}").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/comment/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("feed/find/post/{postId}").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .and().build();
    }
}