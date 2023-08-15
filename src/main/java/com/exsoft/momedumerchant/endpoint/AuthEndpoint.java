package com.exsoft.momedumerchant.endpoint;

import com.exsoft.momedumerchant.dto.auth.LoginRequest;
import com.exsoft.momedumerchant.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthEndpoint {

    private final AuthService service;


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return service.login(request);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        service.logout(request);
        return ResponseEntity.noContent().build();
    }



}
