package it.rad.elearning_platform.service;

import it.rad.elearning_platform.model.Contact;
import it.rad.elearning_platform.model.User;
import it.rad.elearning_platform.req.AuthReq;
import it.rad.elearning_platform.req.RegistrationReq;
import it.rad.elearning_platform.rsp.AuthRsp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthRsp register(RegistrationReq registrationReq){
        String newPassword = passwordEncoder.encode(registrationReq.getPassword());
        Long userId = userService.saveUser(registrationReq.getUsername(), newPassword);
        Contact c = new Contact(registrationReq.getFirstName(),
                registrationReq.getLastName(),
                registrationReq.getEmail());
        userService.addContactUser(c, userId);
        var user = new User(registrationReq.getUsername(), newPassword);
        var token = jwtService.generateToken(user);
        return AuthRsp.builder()
                .token(token)
                .userId(userId)
                .build();
    }

    public AuthRsp authenticate(AuthReq authReq){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authReq.getUsername(),
                        authReq.getPassword()
                )
        );
        var user = userService.findByUsername(authReq.getUsername()).orElseThrow();
        var token = jwtService.generateToken(user);
        return AuthRsp.builder()
                .token(token)
                .build();
    }

}
