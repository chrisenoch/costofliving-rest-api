package com.chrisenoch.col.CostOfLiving;

import java.util.Date;

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
			repository.save(new COLIndex("TOKYO","JAPAN", 70F,new Date()));
			repository.save(new COLIndex("LONDON","ENGLAND", 140F,new Date()));
			repository.save(new COLIndex("SHANGHAI", "CHINA",30F,new Date()));
			repository.save(new COLIndex("MADRID", "SPAIN",230F,new Date()));
			repository.save(new COLIndex("BERLIN","GERMANY", 170F,new Date()));
			repository.save(new COLIndex("BRISTOL","ENGLAND", 110F,new Date()));
		};
	}

}
