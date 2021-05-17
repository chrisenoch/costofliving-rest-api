package com.chrisenoch.col.CostOfLiving.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.chrisenoch.col.CostOfLiving.CostOfLivingConfig2;
import com.chrisenoch.col.CostOfLiving.entity.COLIndex;
import com.chrisenoch.col.CostOfLiving.entity.COLIndexes;
import com.chrisenoch.col.CostOfLiving.entity.COLResults;
import com.chrisenoch.col.CostOfLiving.service.CostOfLivingService;

//@Controller
//@RequestMapping("/costofliving")
//@RepositoryRestController
@BasePathAwareController 
public class COLCalculationsController {
	
	@Autowired
	CostOfLivingService costOfLivingService;

	@GetMapping("/getIndexes")
	public ResponseEntity<COLIndexes> getIndexes() throws Exception{
		return new ResponseEntity<COLIndexes>(new COLIndexes(costOfLivingService.findColIndexes(), new Date()),HttpStatus.OK);
	}
	
	@GetMapping("/{amount}/{base}/to/{code}")
	public ResponseEntity<COLResults>calculateCostOfLiving(@PathVariable ("amount") float amount
			, @PathVariable("base")String base
			, @PathVariable("code")String code) {
		System.out.println("amount: " + amount + " code:" + code + " base: " + base);

		return new ResponseEntity<COLResults>(costOfLivingService.calculateEquivalentSalary(amount, base, code), HttpStatus.OK);

	}

	@GetMapping("/{amount}/{base}/tocountry/{country}")
	public ResponseEntity<List<COLResults>>calculateCostOfLivingByCountry(@PathVariable ("amount") float amount
			, @PathVariable("base")String base
			, @PathVariable("country")String country) {

		//create instance of COLIndex from city value
		base = base.toUpperCase();
		COLIndex colIndex = costOfLivingService.findByCity(base);
		System.out.println("colIndex value " + colIndex);
		
		return new ResponseEntity<List<COLResults>>(costOfLivingService.calculateEquivalentSalaryByCountry(amount, colIndex, country), HttpStatus.OK);

	} 

}
