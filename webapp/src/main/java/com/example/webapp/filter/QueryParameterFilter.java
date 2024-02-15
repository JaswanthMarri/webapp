package com.example.webapp.filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class QueryParameterFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    if (httpRequest.getQueryString() != null) {
      // Reject the request with a custom error response
      ResponseEntity<String> errorResponse =
          ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      httpResponse.setStatus(HttpStatus.BAD_REQUEST.value());
      // httpResponse.getWriter().write(errorResponse));
      return;
    }

    // If no query parameters are present, proceed with the request
    chain.doFilter(request, response);
  }
}
