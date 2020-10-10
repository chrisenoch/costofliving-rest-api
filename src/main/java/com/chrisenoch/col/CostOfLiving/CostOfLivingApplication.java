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
			repository.save(new COLIndex("EUR",70F,new Date()));
			repository.save(new COLIndex("JPY",140F,new Date()));
			repository.save(new COLIndex("GBP",30F,new Date()));
			repository.save(new COLIndex("MXN",230F,new Date()));
			repository.save(new COLIndex("GBP",80F,new Date()));
		};
	}

}
