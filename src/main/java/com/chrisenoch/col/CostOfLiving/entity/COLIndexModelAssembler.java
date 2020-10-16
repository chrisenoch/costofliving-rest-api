package com.chrisenoch.col.CostOfLiving.entity;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import com.chrisenoch.col.CostOfLiving.controller.COLController;

public class COLIndexModelAssembler extends RepresentationModelAssemblerSupport<COLIndex, COLIndexModel> {

	public COLIndexModelAssembler() {
	    super(COLController.class, COLIndexModel.class);
	  }

	  @Override
	  public COLIndexModel toModel(COLIndex colIndex) {

	    COLIndexModel resource = instantiateModel(colIndex);
	    // … do further mapping
	    return resource;
	  }
	}
