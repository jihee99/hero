package com.ex.hero.security.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@Getter
@PropertySource("classpath:auth-jwt.yml")
public class JwtProperties {

    private final String secretKey;

    public JwtProperties(
            @Value("${secret-key}") String secretKey
    ) {
        this.secretKey = secretKey;
    }
}
