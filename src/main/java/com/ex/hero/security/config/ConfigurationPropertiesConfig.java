package com.ex.hero.security.config;

import com.ex.hero.security.jwt.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({
    JwtProperties.class
})
@Configuration
public class ConfigurationPropertiesConfig {}
