package com.chrisenoch.col.CostOfLiving.entity;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.chrisenoch.col.CostOfLiving.controller.COLController;

@Component
public class COLIndexModelAssemblerNew implements RepresentationModelAssembler<COLIndex, EntityModel<COLIndex>>{

	@Override
	public EntityModel<COLIndex> toModel(COLIndex colIndex) {

		return EntityModel.of(colIndex,
				 linkTo(methodOn(COLController.class).one(colIndex.getCity())).withSelfRel(),
			        linkTo(methodOn(COLController.class).getIndexesHATEOAS()).withRel("colindexes"));				
	}
}
