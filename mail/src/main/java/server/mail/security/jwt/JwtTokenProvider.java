package server.mail.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import server.mail.security.init.JwtParserUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {


    //Access Token 유효 시간
    @Value("${security.jwt.access-valid-seconds:900}")
    private long accessValidSeconds;


    //Refresh Token 유효 시간
    @Value("${security.jwt.refresh-valid-seconds:604800}")
    private long refreshValidSeconds;

    private static final String AUTH_CLAIM = "auth";     // 권한 클레임
    private static final String TYPE_CLAIM = "typ";      // 토큰 타입: access / refresh

    private final JwtParserUtils jwtParserUtils;
    private final JwtTokenParser jwtTokenParser;


    public String createAccessToken(String userId, Collection<? extends GrantedAuthority> authorities) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + accessValidSeconds * 1000L);

        String authString = (authorities == null ? Collections.<GrantedAuthority>emptyList() : authorities)
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(userId)
                .claim(AUTH_CLAIM, authString)
                .claim(TYPE_CLAIM, "access")
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(jwtParserUtils.getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String userId) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + refreshValidSeconds * 1000L);

        return Jwts.builder()
                .setSubject(userId)
                .claim(TYPE_CLAIM, "refresh")
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(jwtParserUtils.getKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    /* ===================== 인증 객체 생성 ===================== */

    public Authentication getAuthentication(String token, UserDetailsService userDetailsService) {
        String userId = jwtTokenParser.getUserId(token);
        UserDetails user = userDetailsService.loadUserByUsername(userId);

        // 토큰에 담긴 권한이 있으면 사용, 없으면 UserDetails 권한 사용
        Collection<? extends GrantedAuthority> authorities = extractAuthorities(token);
        if (authorities.isEmpty()) authorities = user.getAuthorities();

        return new UsernamePasswordAuthenticationToken(user, "", authorities);
    }


    /* ===================== 내부 유틸 ===================== */

    private Claims getAllClaims(String token) {
        return jwtParserUtils.getParser().parseClaimsJws(token).getBody();
    }

    private Collection<? extends GrantedAuthority> extractAuthorities(String token) {
        Claims claims = getAllClaims(token);
        String auth = claims.get(AUTH_CLAIM, String.class);
        if (auth == null || auth.isBlank()) return Collections.emptyList();
        return Arrays.stream(auth.split(","))
                .filter(s -> !s.isBlank())
                .map(String::trim)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
