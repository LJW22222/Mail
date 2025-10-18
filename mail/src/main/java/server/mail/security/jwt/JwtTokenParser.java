package server.mail.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import server.mail.security.init.JwtParserUtils;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenParser {

    private final JwtParserUtils jwtParserUtils;
    private static final String TYPE_CLAIM = "typ";

    /* ===================== 파싱/검증 ===================== */

    public boolean validateToken(String token) {
        try {
            jwtParserUtils.getParser().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUserId(String token) {
        return getAllClaims(token).getSubject();
    }

    public Date getExpiration(String token) {
        return getAllClaims(token).getExpiration();
    }

    public boolean isAccessToken(String token) {
        return "access".equals(getAllClaims(token).get(TYPE_CLAIM, String.class));
    }

    public boolean isRefreshToken(String token) {
        return "refresh".equals(getAllClaims(token).get(TYPE_CLAIM, String.class));
    }

    public long getRemainingSeconds(String token) {
        Date exp = getExpiration(token);
        long diffMs = exp.getTime() - System.currentTimeMillis();
        return Math.max(0, diffMs / 1000L);
    }


    /* ===================== 요청에서 토큰 추출 ===================== */
    //요청에서 Jwt Token 추출
    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    /* ===================== 내부 유틸 ===================== */

    private Claims getAllClaims(String token) {
        return jwtParserUtils.getParser().parseClaimsJws(token).getBody();
    }


}
