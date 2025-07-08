package com.example.User.Service.middleware;
import com.example.User.Service.Utils.CookieUtil;
import com.example.User.Service.Utils.JwtUtil;

import com.example.User.Service.Utils.RedisUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CookieUtil cookieUtil;

    @Autowired
    private RedisUtil redisUtil;


    // ========================= WHEN VALIDATING TOKEN THROUGH COOKIES =========================

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String path = request.getRequestURI();
//
//        if (isExcludedPath(path)) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String token = CookieUtil.getCookieValue(request, JwtUtil.TOKEN_NAME);
//
//        if (token != null) {
//            if (jwtUtil.validateToken(token) == null) {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                response.getWriter().write("Invalid or expired token");
//                return;
//            }
//        }
//        else {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write("Token missing");
//            return;
//        }
//
//        filterChain.doFilter(request, response);
//    }
//


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (isExcludedPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        String sessionId = authHeader.substring(7); // removes "Bearer "
        String token = redisUtil.getToken(sessionId);

        if (token == null || jwtUtil.validateToken(token) == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired session");
            return;
        }

        // You can also extract username and set authentication if needed
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(sessionId, null, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }





    private boolean isExcludedPath(String path) {
        return path.startsWith("/login");
    }
}
