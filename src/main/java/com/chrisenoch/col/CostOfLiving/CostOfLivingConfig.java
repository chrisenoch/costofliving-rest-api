package com.chrisenoch.col.CostOfLiving;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;

import com.chrisenoch.col.CostOfLiving.controller.COLCalculationsController;

@Configuration
public class CostOfLivingConfig {

	@Bean
	  CostOfLivingConfig2 colProcessor() {
	    return new CostOfLivingConfig2 ();
	  }

	/*
	 * 	@Bean
	public RepresentationModelProcessor<EntityModel<COLCalculationsController>> colProcessor() {
		return new RepresentationModelProcessor<EntityModel<COLCalculationsController>>() {

			@Override
			public EntityModel<COLCalculationsController> process(EntityModel<COLCalculationsController> entityModel) {
				entityModel.add(Link.of("http://localhost:8080/costoflivingxyz/123"));
				return entityModel;
			}
		};
	}
	 */
	
}
