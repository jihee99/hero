package com.ex.hero.security;

import java.time.Duration;

public class HeroStatic {

    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String WITHDRAW_PREFIX = "DELETED:";
    public static final String TOKEN_ROLE = "role";
    public static final String TOKEN_TYPE = "type";
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String REFRESH_TOKEN = "REFRESH_TOKEN";
    public static final int MILLI_TO_SECOND = 1000;

    public static final Duration ACCESS_TOKEN_EXPIRATION_TIME = Duration.ofMinutes(30);
    public static final Duration REFRESH_TOKEN_EXPIRATION_TIME = Duration.ofMinutes(60);
}
