package com.chrisenoch.col.CostOfLiving;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.chrisenoch.col.CostOfLiving.entity.COLIndex;
import com.chrisenoch.col.CostOfLiving.repository.RateRepository;

@SpringBootApplication
public class CostOfLivingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CostOfLivingApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner data(RateRepository repository) {
		return (args) -> {
			repository.save(new COLIndex("TOKYO","JAPAN", BigDecimal.valueOf(70),OffsetDateTime.now()));
			repository.save(new COLIndex("LONDON","ENGLAND", BigDecimal.valueOf(140),OffsetDateTime.now()));
			repository.save(new COLIndex("SHANGHAI", "CHINA",BigDecimal.valueOf(30),OffsetDateTime.now()));
			repository.save(new COLIndex("MADRID", "SPAIN",BigDecimal.valueOf(230),OffsetDateTime.now()));
			repository.save(new COLIndex("BERLIN","GERMANY", BigDecimal.valueOf(170),OffsetDateTime.now()));
			repository.save(new COLIndex("BRISTOL","ENGLAND", BigDecimal.valueOf(110),OffsetDateTime.now()));
		};
	}

}
