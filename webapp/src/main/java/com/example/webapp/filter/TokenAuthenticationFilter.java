package com.example.webapp.filter;

import com.example.webapp.config.CustomAuthenticationProvider;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

  @Autowired private CustomAuthenticationProvider customAuthenticationProvider;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String header = request.getHeader("Authorization");

    if (header == null || !header.startsWith("Basic ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = header.substring(6); // Remove "Bearer " prefix

    try {

      final String authorization = request.getHeader("Authorization");
      if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
        // Authorization: Basic base64credentials
        String base64Credentials = authorization.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String username = values[0];
        // customAuthenticationProvider.authenticate();
        if (username
            != null) { // && SecurityContextHolder.getContext().getAuthentication() == null) {
          UsernamePasswordAuthenticationToken authentication =
              new UsernamePasswordAuthenticationToken(username, values[1]);
          SecurityContextHolder.getContext()
              .setAuthentication(customAuthenticationProvider.authenticate(authentication));
        }
      }
    } catch (Exception e) {
      log.error("Error occurred while processing Basic token", e);
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write("");
      return;
    }

    filterChain.doFilter(request, response);
  }
}
