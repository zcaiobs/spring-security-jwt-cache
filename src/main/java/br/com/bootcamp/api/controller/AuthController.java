package br.com.bootcamp.api.controller;

import br.com.bootcamp.api.domain.FormDto;
import br.com.bootcamp.api.domain.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenJwtService tokenService;

    @PostMapping
    public ResponseEntity<?> auth(@RequestBody @Valid FormDto formDto) {
        UsernamePasswordAuthenticationToken uat = formDto.converter();
        try {
            Authentication authentication = authenticationManager.authenticate(uat);
            String token = tokenService.gerarToken(authentication);
            return ResponseEntity.ok().body(new TokenDto(token, "Bearer"));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }


    }
}
