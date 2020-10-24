package com.chrisenoch.col.CostOfLiving;

import java.util.Date;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelProcessor;

import com.chrisenoch.col.CostOfLiving.entity.COLIndex;
import com.chrisenoch.col.CostOfLiving.repository.RateRepository;
import com.chrisenoch.col.CostOfLiving.controller.COLCalculationsController;

@SpringBootApplication
public class CostOfLivingApplication implements 
RepresentationModelProcessor<EntityModel<COLCalculationsController>> {

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
	
	
	@Bean
	public ResourceProcessor<Resource<Taco>> tacoProcessor(EntityLinks links) {
		return new ResourceProcessor<Resource<Taco>>() {
			@Override
			public PagedResources<Resource<Taco>> process(PagedResources<Resource<Taco>> resource) {
				resource.add(links.linkFor(Taco.class).slash("recent").withRel("recents"));
				return resource;
			}
		};
	}
	
	
	
	@Bean
	public ResourceProcessor<Resource<Taco>> tacoProcessor(EntityLinks links) {
		return new ResourceProcessor<Resource<Taco>>() {
			@Override
			public PagedResources<Resource<Taco>> process(PagedResources<Resource<Taco>> resource) {
				resource.add(links.linkFor(Taco.class).slash("recent").withRel("recents"));
				return resource;
			}
		};
	}

	@Override
	public EntityModel<COLCalculationsController> process(EntityModel<COLCalculationsController> model) {
		// TODO Auto-generated method stub
		return null;
	}

}
