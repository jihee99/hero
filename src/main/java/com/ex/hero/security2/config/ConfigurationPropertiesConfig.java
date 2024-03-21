package com.ex.hero.security2.config;

import com.ex.hero.security2.jwt.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({
    JwtProperties.class
})
@Configuration
public class ConfigurationPropertiesConfig {}