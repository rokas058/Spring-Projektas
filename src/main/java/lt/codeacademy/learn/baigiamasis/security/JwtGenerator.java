package lt.codeacademy.learn.baigiamasis.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtGenerator {

    private final Date currentDate = new Date();
    //milisekundes
    private final int expDate = 1000*60*60;
    private final Date exiredDate = new Date(currentDate.getTime() + expDate);

    private final String JWT_SECRET = "655368566D597133743677397A24432646294A404E635166546A576E5A723475";

    public String generateToken(Authentication authentication){
        String email = authentication.getName();

        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(exiredDate)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
        return token;

    }
    public  String getUsernameFromJwt(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(JWT_SECRET).build().parseClaimsJws(token);
            return true;
        }catch (Exception ex){
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }
}
