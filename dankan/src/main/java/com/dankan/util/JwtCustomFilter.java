package com.dankan.util;

import com.dankan.domain.User;
import com.dankan.exception.token.TokenNotFoundException;
import com.dankan.repository.TokenRepository;
import com.dankan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtCustomFilter extends OncePerRequestFilter {
    @Value("${jwt.secret}")
    private String secretKey;

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // bearer이 아니면 오류
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("text/plain;charset=UTF-8"); // content-type을 text/plain으로 설정
            response.getWriter().write("JWT Token does not begin with Bearer String");

            return;
        }

        // Token 꺼내기
        String token = authorizationHeader.split(" ")[1];

        tokenRepository.findTokenByAccessToken(token).orElseThrow(
                () -> new TokenNotFoundException(token)
        );

        // Token 검증
        if (!JwtUtil.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("text/plain;charset=UTF-8"); // content-type을 text/plain으로 설정
            response.getWriter().write("JWT Token is not valid");

            return;
        }

        // Token 만료 체크
        if (JwtUtil.isExpired(token)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("text/plain;charset=UTF-8"); // content-type을 text/plain으로 설정
            response.getWriter().write("JWT Token is expired");

            return;
        }

        // 유저 식별
        Long memberId = JwtUtil.getMemberId();

        Optional<User> member = userRepository.findById(memberId);

        if(member.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("text/plain;charset=UTF-8"); // content-type을 text/plain으로 설정
            response.getWriter().write("JWT Token is not valid");

            filterChain.doFilter(request, response);

            return;
        }

        User result = member.get();
        String role;

        if(result.hasRole("ADMIN")) {
            role = "ROLE_ADMIN";
        }
        else if(result.hasRole("USER")) {
            role = "ROLE_USER";
        }
        else {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            response.setContentType("text/plain;charset=UTF-8"); // content-type을 text/plain으로 설정
            response.getWriter().write("User do not have permission");

            return;
        }

        // 권한 부여
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member, null, List.of(new SimpleGrantedAuthority(role)));

        // UserDetail을 통해 인증된 사용자 정보를 SecurityContext에 저장
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}