package com.chrisenoch.col.CostOfLiving.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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
import com.chrisenoch.col.CostOfLiving.entity.COLIndexModelAssemblerNew;
import com.chrisenoch.col.CostOfLiving.entity.COLIndexes;
import com.chrisenoch.col.CostOfLiving.entity.COLResults;
import com.chrisenoch.col.CostOfLiving.error.COLIndexNotFoundException;
import com.chrisenoch.col.CostOfLiving.service.CostOfLivingService;

@RestController
@RequestMapping("/costofliving")
public class COLController extends RepresentationModel<COLController> {


	CostOfLivingService costOfLivingService;
	COLIndexModelAssemblerNew assembler;
	
	@Autowired
	public COLController(CostOfLivingService costOfLivingService, COLIndexModelAssemblerNew assembler) {
		this.costOfLivingService = costOfLivingService;
		this.assembler = assembler;
	}

	@GetMapping
	public ResponseEntity<COLIndexes> getIndexes() throws Exception{
		//CollectionModel<Person> model = CollectionModel.of(people);
		
		return new ResponseEntity<COLIndexes>(new COLIndexes(costOfLivingService.findColIndexes(), OffsetDateTime.now()),HttpStatus.OK);
	}
	
	@GetMapping("/{date}")
	public ResponseEntity<COLIndexes> getRatesByDate(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)OffsetDateTime date) throws Exception{
		return new ResponseEntity<COLIndexes>(new COLIndexes(costOfLivingService.findColIndexes(date), OffsetDateTime.now()),HttpStatus.OK);
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
		String baseErrorMessage = base; //defined because argument in orElseThrow must be final or effectively final
		COLIndex colIndex = costOfLivingService.findByCity(base).orElseThrow(()-> new COLIndexNotFoundException(baseErrorMessage));
		System.out.println("colIndex value " + colIndex);
		
		return new ResponseEntity<List<COLResults>>(costOfLivingService.calculateEquivalentSalaryByCountry(amount, colIndex, country), HttpStatus.OK);
	}
	
	@GetMapping("/colindexes/{city}")
	  public EntityModel<COLIndex> getCity(@PathVariable String city) {

	   COLIndex colIndex =  costOfLivingService.findByCity(city).orElseThrow(()-> new COLIndexNotFoundException(city));
	   
	   return EntityModel.of(colIndex
			   ,  linkTo(methodOn(COLController.class).getCity(city)).withSelfRel(),
			      linkTo(methodOn(COLController.class).getIndexesHATEOAS()).withRel("COLIndexes"));		   
	  }
	
	
	@GetMapping("/colindexesassembler/{city}")
	  public EntityModel<COLIndex> colindexesassembler (@PathVariable String city) {

	   COLIndex colIndex =  costOfLivingService.findByCity(city).orElseThrow(()-> new COLIndexNotFoundException(city));
	   
	   return assembler.toModel(colIndex);	   
	  }
	
	
	@GetMapping("/colindexesassemblerre/{city}")
	  public ResponseEntity<EntityModel<COLIndex>> colindexesassemblerre (@PathVariable String city) {

	   COLIndex colIndex =  costOfLivingService.findByCity(city).orElseThrow(()-> new COLIndexNotFoundException(city));
	   
	   return new ResponseEntity<EntityModel<COLIndex>> (assembler.toModel(colIndex), HttpStatus.OK);	   
	  }
	
	@GetMapping("/colindexesassembler")
	  public CollectionModel<EntityModel<COLIndex>> getIndexesHATEOASAssembler() {

	   List<EntityModel<COLIndex>> colIndexes =  costOfLivingService.findColIndexes()
			   .stream().map(assembler::toModel).collect(Collectors.toList());
	   
	   return CollectionModel.of(colIndexes, 
			   linkTo(methodOn(COLController.class).getIndexesHATEOASAssembler()).withSelfRel());
			   	   
	  }
	
	@GetMapping("/colindexesassemblerre")
	  public ResponseEntity<CollectionModel<EntityModel<COLIndex>>> getIndexesHATEOASAssemblerResponseEntity() {

	   List<EntityModel<COLIndex>> colIndexes =  costOfLivingService.findColIndexes()
			   .stream().map(assembler::toModel).collect(Collectors.toList());
	   
	   CollectionModel<EntityModel<COLIndex>>test = CollectionModel.of(colIndexes, 
			   linkTo(methodOn(COLController.class).getIndexesHATEOASAssembler()).withSelfRel());
	   
	   return new ResponseEntity<CollectionModel<EntityModel<COLIndex>>> (test, HttpStatus.OK);
			   	   
	  }
	
	@GetMapping("/colindexes")
	public CollectionModel<EntityModel<COLIndex>> getIndexesHATEOAS(){
			List<EntityModel<COLIndex>> luckytest= costOfLivingService.findColIndexes()
					.stream().map(COLIndex-> EntityModel.of(COLIndex,
							
							linkTo(methodOn(COLController.class).getCity(COLIndex.getCity())).withSelfRel()
					          ,linkTo(methodOn(COLController.class).getIndexesHATEOAS()).withRel("COLIndexes")
					          ))
				      .collect(Collectors.toList());
							 
			return CollectionModel.of(luckytest, linkTo(methodOn(COLController.class).getIndexesHATEOAS()).withRel("COLIndexes"));
		} 	
	
	@GetMapping("/country/{country}")
	public CollectionModel<COLIndex> getIndexesByCountry(@PathVariable("country") String country) throws Exception{
		System.out.println("Inside getIndexesByCountry");
		//Need to change this so returns a COLIndexes object

		List<COLIndex> colIndexes = costOfLivingService.findColIndexesByCountry(country).get();
		
		for (COLIndex colIndex : colIndexes) {
			//String country = colIndex.getCountry();
			Link selfLink = linkTo(COLController.class).slash("country").slash(country).withSelfRel();
			colIndex.add(selfLink);	
		}
		
		Link link = linkTo(COLController.class).withSelfRel();
		CollectionModel<COLIndex> result = CollectionModel.of(colIndexes, link);
		return result;
	}
	
	//Practice 
		@GetMapping("/gethateoas")
		public EntityModel<COLIndex>getHateoas() throws Exception{
			COLIndex rate = new COLIndex("Birmingmam", "England", 1456F, OffsetDateTime.now());
			EntityModel<COLIndex> model = EntityModel.of(rate);
			model.add(Link.of("http://localhost:8080/costofliving/123"));
			//rate.add(Link.of("http://localhost:8080/costofliving/123"));
			
			return model;
		}
	

}
