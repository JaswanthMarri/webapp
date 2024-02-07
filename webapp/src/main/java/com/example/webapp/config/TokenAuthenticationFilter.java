//package com.example.webapp.config;
//
//import com.example.webapp.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class TokenAuthenticationFilter extends OncePerRequestFilter {
//
//    private final UserService userService;
//
//    @Autowired
//    public TokenAuthenticationFilter(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        // Extract token from the request header
//        String token = extractTokenFromHeader(request);
//
//        if (token != null) {
//            // Validate the token and retrieve user details
//            String username = userService.getUsernameFromToken(token);
//
//            if (username != null) {
//                // Create authentication token
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, userService.getAuthorities(username));
//                // Set authentication in the security context
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        }
//
//        // Proceed with the filter chain
//        filterChain.doFilter(request, response);
//    }
//
//    private String extractTokenFromHeader(HttpServletRequest request) {
//        // Extract token from the Authorization header
//        String basicToken = request.getHeader("Authorization");
//        if (basicToken != null && basicToken.startsWith("Basic ")) {
//            return basicToken.substring(6); // Remove "Bearer " prefix
//        }
//        return null;
//    }
//}
