package web.ThuThapMau.Util;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import web.ThuThapMau.entities.User;
@Slf4j
@Component
public class JwtTokenProvider  {
    private static final String SECRET_KEY = "CT240THssadasdasdsadasdasdasdsadasUTHAPMAU31312312asdasdasdasdasdasdasdasdasdas32131231231212345678910111213141516171812312312312312312312";

    public static String createJwt(User user) {
        // Tạo token
        return Jwts.builder()
                .setSubject(String.valueOf(user.getUser_id())) // Đặt subject trực tiếp trên builder
                .claim("user_name", user.getUser_full_name()) // Thêm claim cho user name
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public Claims getUserFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims;
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }catch (Exception e){
            log.error(String.valueOf(e));
        }
        return false;
    }

}
