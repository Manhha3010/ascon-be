package com.exsoft.momedumerchant.service;

import com.exsoft.momedumerchant.authentication.JwtProvider;
import com.exsoft.momedumerchant.domain.Staff;
import com.exsoft.momedumerchant.dto.auth.JwtAuthenticationResponse;
import com.exsoft.momedumerchant.dto.auth.LoginRequest;
import com.exsoft.momedumerchant.problem.UnauthorizedProblem;
import com.exsoft.momedumerchant.repository.StaffRepository;
import com.exsoft.momedumerchant.util.ApplicationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final StaffRepository staffRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    public ResponseEntity<?> login(LoginRequest request) {
        String username = request.getUsername().trim().toLowerCase();
        boolean staffExist = staffRepository.existsByUsernameAndStatus(username, 1L);
        if (!staffExist)
            throw new UnauthorizedProblem("bad_credential", "Username or password is invalid");
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, request.getPassword()));

            Staff user = (Staff) authentication.getPrincipal();
            String userAgent = ApplicationUtils.getUserAgent();
            String token = jwtProvider.generateJwtToken(user, userAgent);

            return ResponseEntity.ok(JwtAuthenticationResponse.builder()
                    .accessToken(token)
                    .type("Bearer")
                    .build());

        } catch (BadCredentialsException exception) {
            throw new UnauthorizedProblem("bad_credential", "Username or password is invalid");
        }
    }

    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContext context = SecurityContextHolder.getContext();
        SecurityContextHolder.clearContext();
        context.setAuthentication(null);
    }
}
