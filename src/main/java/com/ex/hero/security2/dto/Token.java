package com.ex.hero.security2.dto;

import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "refreshToken")
public class Token {

    private final String accessToken;
    private final String refreshToken;


    public Token(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
