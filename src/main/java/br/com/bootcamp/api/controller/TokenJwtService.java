package br.com.bootcamp.api.controller;

import br.com.bootcamp.api.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenJwtService {

    private final String expiration = "86400000";
    private final String secret = "minhasecret";

    public String gerarToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date dataExpiracao = new Date(new Date().getTime() + Long.parseLong(expiration));
        return Jwts.builder()
                .setIssuer("API")
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isTokenValido(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public Long getIdUsuario(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }
}
