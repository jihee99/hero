package com.ex.hero.security.dto;

import lombok.Getter;

@Getter
public class Token {

    private final String accessToken;
    private final String refreshToken;


    public Token(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
