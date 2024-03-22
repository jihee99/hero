package com.ex.hero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TicketHeroApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketHeroApplication.class, args);
	}

}

