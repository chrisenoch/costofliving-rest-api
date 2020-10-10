package com.chrisenoch.col.CostOfLiving;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.chrisenoch.col.CostOfLiving.entity.Rate;
import com.chrisenoch.col.CostOfLiving.repository.RateRepository;

@SpringBootApplication
public class CostOfLivingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CostOfLivingApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner data(RateRepository repository) {
		return (args) -> {
			repository.save(new Rate("EUR",0.88857F,new Date()));
			repository.save(new Rate("JPY",102.17F,new Date()));
			repository.save(new Rate("GBP",0.75705F,new Date()));
			repository.save(new Rate("MXN",19.232F,new Date()));
			repository.save(new Rate("GBP",0.75705F,new Date()));
		};
	}

}
