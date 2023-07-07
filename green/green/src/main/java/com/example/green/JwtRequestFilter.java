package com.example.green;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

 private final JwtUserDetailsService jwtUserDetailsService;
 private final JwtTokenUtil jwtTokenUtil;

 @Autowired
 public JwtRequestFilter(JwtUserDetailsService jwtUserDetailsService, JwtTokenUtil jwtTokenUtil) {
     this.jwtUserDetailsService = jwtUserDetailsService;
     this.jwtTokenUtil = jwtTokenUtil;
 }

 @Override
 protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                 FilterChain chain) throws ServletException, IOException {
     final String authorizationHeader = request.getHeader("Authorization");

     String username = null;
     String jwtToken = null;

     if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
         jwtToken = authorizationHeader.substring(7);
         try {
             username = jwtTokenUtil.extractUsername(jwtToken);
         } catch (ExpiredJwtException ignored) {
             // Token has expired
         }
     }

     if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
         UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

         if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
             UsernamePasswordAuthenticationToken authenticationToken =
                     new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
             authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

             SecurityContextHolder.getContext().setAuthentication(authenticationToken);
         }
     }

     chain.doFilter(request, response);
 }
}
