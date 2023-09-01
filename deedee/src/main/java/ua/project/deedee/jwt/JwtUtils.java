package ua.project.deedee.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecretKey;

    public final Algorithm getJwtMainAlgorithm() {
        return Algorithm.HMAC256(jwtSecretKey.getBytes());
    }

    public final JWTVerifier getJwtVerifier(Algorithm algorithm) {
        return JWT.require(algorithm).build();
    }

    public final String generateUserAccessToken(String tokenSubject, String issuerUrl) {
        return JWT.create()
                .withSubject(tokenSubject)
                .withExpiresAt(new Date(System.currentTimeMillis() + 12 * 60 * 60 * 1000))
                .withIssuer(issuerUrl)
                .sign(getJwtMainAlgorithm());
    }

    public final String getTokenSubject(String token) {
        try {
            DecodedJWT decodedJWT = getJwtVerifier(getJwtMainAlgorithm())
                    .verify(token);
            return decodedJWT.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

}
