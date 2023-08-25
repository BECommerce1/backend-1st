package com.github.backend1st.config;

import com.github.backend1st.repository.member.Role;
import com.github.backend1st.service.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret-key-source}")
    private String secretKeySource;

    private String secretKey;

    @PostConstruct
    public void setUp() {
        secretKey = Base64.getEncoder().encodeToString(secretKeySource.getBytes());
    }

    private long tokenValidMillisecond = 1000L * 60 * 60; // 1시간

    private final CustomUserDetailsService customUserDetailsService;

    // create Access Token
    public String createToken(String email, Role role) {
        // Claim = Jwt Token에 들어갈 정보
        // subject(id) == email, Role 정보 추가
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

    }

    // token 유효한지 아닌지 Boolean
    public boolean validateToken(String jwtToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken)
                    .getBody();

            Date now = new Date();

            return claims.getExpiration().after(now);

        } catch (Exception e) {
            return false;
        }
    }

    // Token 가져오기
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    // Token에서 이메일 가져오기
    public String getUserEmail(String jwtToken) {

        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }

    // 토큰에서 인증 가져오기(UwernamePasswordAuthenticationToken)
    public Authentication getAuthentication(String jwtToken) {
        if (jwtToken == null) return null;
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(getUserEmail(jwtToken));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


} // end class
