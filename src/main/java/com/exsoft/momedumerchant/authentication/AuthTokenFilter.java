package com.exsoft.momedumerchant.authentication;

import com.exsoft.momedumerchant.domain.Staff;
import com.exsoft.momedumerchant.util.ApplicationConstant;
import com.exsoft.momedumerchant.util.ApplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

    public static final String TOKEN_SCHEME_NAME = "Bearer ";

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Value("${spring.profiles.active}")
    private String activeProfile;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        if (true) {
            // If you want to skip this filter, just proceed to the next filter in the chain
            filterChain.doFilter(request, response);

            return;
        }


        String jwt = parseJwt(request);
        // Check if not have jwt
        boolean isDisableAuthorize = false;
        for (String check : ApplicationUtils.SECURITY_CONFIG.DISABLE_AUTHORIZE) {
            isDisableAuthorize = new AntPathMatcher().match(check, request.getRequestURI().substring(request.getContextPath().length()));
            if (isDisableAuthorize) {
                break;
            }
        }
        try {
            if (jwt != null && jwtProvider.validateJwtToken(jwt)) {
                // Parse jwt
                JwtParseFormat jwtParseFormat = jwtProvider.getJwtInfo(jwt);
                // get customer
                Staff userDetails = userDetailsService.loadUserByUsername(jwtParseFormat.getUsername());

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Create a new Security context instance instead of using SecurityContextHolder.getContext().setAuthentication(authentication) to avoid race conditions across multiple threads.
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
            } else if (!isDisableAuthorize) {
                handleUnauthorized(response);
                return;
            }
        } catch (Exception e) {
            handleUnauthorized(response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private HttpServletResponse handleUnauthorized(HttpServletResponse response) throws IOException {
        SecurityContextHolder.clearContext();
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write("Full authentication is required to access this resource unauthorized");
        return response;
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(TOKEN_SCHEME_NAME)) {
            return headerAuth.substring(TOKEN_SCHEME_NAME.length());
        }

        return null;
    }
}
