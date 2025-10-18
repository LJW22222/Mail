package server.mail.security.init;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
@Getter
public class JwtParserUtils {


    //JWT 만료시간 오차 범위 설정 -> 60초
    @Value("${security.jwt.clock-skew-seconds:60}")
    private long clockSkewSeconds;


    //서명 Key
    @Value("${security.jwt.secret:change-me-to-a-long-random-secret}")
    private String secret;


    private Key key;
    private JwtParser parser;


    @PostConstruct
    void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .setAllowedClockSkewSeconds(clockSkewSeconds)
                .build();
    }


}
