package com.ex.hero.security.jwt;

import lombok.Getter;

@Getter
public class Token {

    private final String accessToken;
//    private final String refreshToken;


    public Token(String accessToken) {
        this.accessToken = accessToken;
    }
}
