package it.rad.elearning_platform.controllers;

import it.rad.elearning_platform.req.AuthReq;
import it.rad.elearning_platform.req.RegistrationReq;
import it.rad.elearning_platform.rsp.AuthRsp;
import it.rad.elearning_platform.service.AuthService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthRsp> authenticate(@RequestBody AuthReq request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthRsp> register(@RequestBody RegistrationReq request) {
        AuthRsp rsp = authService.register(request);

        return ResponseEntity.ok(rsp);
    }
}

