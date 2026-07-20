package com.labelflow.labelchange.auth.security.jwt;

import com.labelflow.labelchange.auth.security.CustomUserDetailsService;
import com.labelflow.labelchange.auth.security.UserPrincipal;
import com.labelflow.labelchange.common.constants.SecurityConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. Read Authorization header
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 2. Verify Bearer prefix
        if (authorizationHeader == null || !authorizationHeader.startsWith(SecurityConstants.BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extract JWT
        String jwt = authorizationHeader.substring(SecurityConstants.BEARER_PREFIX.length());

        // 4. Extract username from JWT
        String username = jwtService.extractUsername(jwt);

        // 5. Authenticate only if SecurityContext is empty
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 6. Load UserPrincipal
            UserPrincipal userPrincipal = (UserPrincipal) userDetailsService.loadUserByUsername(username);

            // 7. Validate JWT
            if (jwtService.validateToken(jwt, userPrincipal)) {

                // 8. Create Authentication object
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userPrincipal,
                                null,
                                userPrincipal.getAuthorities()
                        );

                // 9. Attach request details
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 10. Store Authentication in SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 11. Continue the filter chain
        filterChain.doFilter(request, response);
    }
}