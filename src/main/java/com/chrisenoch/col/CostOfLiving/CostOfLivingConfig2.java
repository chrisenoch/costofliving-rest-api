package com.chrisenoch.col.CostOfLiving;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import com.chrisenoch.col.CostOfLiving.controller.COLCalculationsController;

@Component
public class CostOfLivingConfig2 implements RepresentationModelProcessor<EntityModel<LinkController>>{

	@Override
	public EntityModel<LinkController> process(EntityModel<LinkController> entityModel) {
		entityModel.add(Link.of("http://localhost:8080/costoflivingxyz/123"));
		return entityModel;
	}

}
