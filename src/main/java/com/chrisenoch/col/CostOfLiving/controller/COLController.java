package com.chrisenoch.col.CostOfLiving.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chrisenoch.col.CostOfLiving.entity.COLIndex;
import com.chrisenoch.col.CostOfLiving.entity.COLIndexModel;
import com.chrisenoch.col.CostOfLiving.entity.COLIndexModelAssembler;
import com.chrisenoch.col.CostOfLiving.entity.COLIndexes;
import com.chrisenoch.col.CostOfLiving.entity.COLResults;
import com.chrisenoch.col.CostOfLiving.service.CostOfLivingService;

@RestController
@RequestMapping("/costofliving")
public class COLController extends RepresentationModel<COLController> {

	
	@Autowired
	CostOfLivingService costOfLivingService;
	
	@GetMapping("/gethateoas")
	public EntityModel<COLIndex>getHateoas() throws Exception{
		COLIndex rate = new COLIndex("Birmingmam", "England", 1456F, new Date());
		EntityModel<COLIndex> model = EntityModel.of(rate);
		model.add(Link.of("http://localhost:8080/costofliving/123"));
		//rate.add(Link.of("http://localhost:8080/costofliving/123"));
		
		return model;
	}
	
	@GetMapping
	public ResponseEntity<COLIndexes> getIndexes() throws Exception{
		//CollectionModel<Person> model = CollectionModel.of(people);
		
		return new ResponseEntity<COLIndexes>(new COLIndexes(costOfLivingService.findColIndexes(), new Date()),HttpStatus.OK);
	}
	
	@GetMapping("/country/{country}")
	public ResponseEntity<CollectionModel<COLIndex>> getIndexesByCountry(@PathVariable("country") String country) throws Exception{
		//Need to change this so returns a COLIndexes object
		
		List<COLIndex> test = costOfLivingService.findColIndexesByCountry(country);
		
		Collection<List<COLIndex>> colIndexCollection = Collections.singleton(test);
		CollectionModel<COLIndex> model = CollectionModel.of(test);	
		model.add(linkTo(methodOn(COLController.class).getIndexesByCountry(country)).withSelfRel());
		//greeting.add(linkTo(methodOn(COLController.class).getIndexesByCountry()).withSelfRel());
			
		return new ResponseEntity<CollectionModel<COLIndex>>(model, HttpStatus.OK);

	}
	
	@GetMapping("/countryx/{country}")
	public ResponseEntity<CollectionModel<COLIndexModel>> getIndexesByCountry2(@PathVariable("country") String country) throws Exception{
		//Need to change this so returns a COLIndexes object
		COLIndexModelAssembler colIndexModelAssembler = new COLIndexModelAssembler();
		
		List<COLIndex> test = costOfLivingService.findColIndexesByCountry(country);
		test.forEach(System.out::println);
		List<COLIndexModel> test4= test.stream().map(c-> colIndexModelAssembler
				.toModel(c)).collect(Collectors.toList());
				
		
		//List<COLIndex> test4 = test.stream().map(c-> colIndexModelAssembler
				//.toModel(c)).map(c-> c.add("Http://localhost.com")).collect(Collectors.toList()));
		
		
		//CollectionModel<COLIndexModel> test3 = new COLIndexModelAssembler().toCollectionModel(test4);
		
		//Collection<List<COLIndex>> colIndexCollection = Collections.singleton(test);
		
		//COLIndexModelAssembler assembler = new COLIndexModelAssembler();
	
		//CollectionModel<COLIndexModel> model = assembler.toCollectionModel(test);
		
		//CollectionModel<COLIndex> model = CollectionModel.of(test);	
		test4.add(linkTo(methodOn(COLController.class).getIndexesByCountry2(country)).withSelfRel());
		//greeting.add(linkTo(methodOn(COLController.class).getIndexesByCountry()).withSelfRel());
			
		return new ResponseEntity<CollectionModel<COLIndexModel>>(test3, HttpStatus.OK);

	}
	
	@GetMapping("/{date}")
	public ResponseEntity<COLIndexes> getRatesByDate(@PathVariable("date") @DateTimeFormat(pattern="yyyy-MM-dd")Date date) throws Exception{
		return new ResponseEntity<COLIndexes>(new COLIndexes(costOfLivingService.findColIndexes(date), new Date()),HttpStatus.OK);
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
	
	Link link = linkTo(COLController.class).withRel("people");
	
	
	/*
	 * 	@GetMapping("/{amount}/{base}/to/{code}")
	String calculateCol(@PathVariable ("amount") float amount, @PathVariable("base")String base
			, @PathVariable("code")String code) {
		System.out.println("amount: " + amount + " code:" + code + " base: " + base);
		
		costOfLivingService.calculateEquivalentSalary(amount, base, code);
		
		return "mainget";
	}
	 */
	


	
	// get rate by base code
	//get rate by code
	
	//calculate the ratio and multiply this by the amount
	
	//below are totals, not rates
	//a = 90  and b = 130
	// I need to know how much more or less is a compared to b
	//a/b * amount 
	
//	repository.save(new Rate("EUR",0.88857F,new Date()));
//	repository.save(new Rate("JPY",102.17F,new Date()));
//	repository.save(new Rate("GBP",0.75705F,new Date()));
//	repository.save(new Rate("MXN",19.232F,new Date()));
//	repository.save(new Rate("GBP",0.75705F,new Date()));
	
	

	

	//@RequestMapping(path="/new",method = {RequestMethod.POST})
	

}
