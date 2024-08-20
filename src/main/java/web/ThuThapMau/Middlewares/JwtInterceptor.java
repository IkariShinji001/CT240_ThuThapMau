package web.ThuThapMau.Middlewares;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import web.ThuThapMau.Util.JwtTokenProvider;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    JwtTokenProvider tokenProvider;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            // Lấy jwt từ request
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                // Lấy id user từ chuỗi jwt
                Claims claims = tokenProvider.getUserFromJWT(jwt);
                request.setAttribute("claims", claims);
                if (claims != null) {
                    return true;
                }
            }else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }

        if (!request.getMethod().equalsIgnoreCase("OPTIONS")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
        return false;
    }

    public String getJwtFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }




}
