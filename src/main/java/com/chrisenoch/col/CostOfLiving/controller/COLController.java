package com.chrisenoch.col.CostOfLiving.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	/**
	 * Find all COLIndexes whose {@code String} version of the date contains the {@code date} argument.
	 * @param date
	 * @return
	 */
	@GetMapping("/{date}")
	public ResponseEntity<COLIndexes> getRatesByDateHATEOAS(@PathVariable("date") String date){
		
		List<EntityModel<COLIndex>> colIndexes = costOfLivingService.findColIndexes(date)
				 .stream().map(assembler::toModel).collect(Collectors.toList());
		
		return new ResponseEntity<COLIndexes>(new COLIndexes(colIndexes, OffsetDateTime.now()),HttpStatus.OK);
	}

	/**
	 * Calculates the amount of money needed in {@code city2} to be worth the same as the {@code city1Amount} for {@code city1}.
	 * @param city1Amount
	 * 		  the amount to be converted
	 * @param city1
	 * @param city2
	 * @return  
	 */
	@GetMapping("/{city1Amount}/{city1}/to/{city2}")
	public EntityModel<COLResults>calculateCostOfLiving(@PathVariable ("city1Amount") BigDecimal city1Amount
			, @PathVariable("city1")String city1
			, @PathVariable("city2")String city2) {
		System.out.println("city1Amount: " + city1Amount + " code:" + city2 + " base: " + city1);

		//return new ResponseEntity<COLResults>(costOfLivingService.calculateEquivalentSalary(amount, base, code), HttpStatus.OK);
		COLResults colResults = costOfLivingService.calculateEquivalentSalary(city1Amount, city1, city2);

		String helloString;
		return EntityModel.of(colResults,
			      linkTo(methodOn(COLController.class).calculateCostOfLiving(city1Amount, city1, city2)).withSelfRel() 
				      ,linkTo(methodOn(COLController.class).getIndexesHATEOAS()).withSelfRel()			
				);
		
	}
	
	/**
	 * Calculates the amount of money needed in every city in {@code country} to be worth the same as the {@code city1Amount} for {@code city1}.
	 * @param city1Amount
	 * @param city1
	 * @param country
	 * @return
	 */
	@GetMapping("/{city1Amount}/{city1}/tocountry/{country}")
	public ResponseEntity<CollectionModel<EntityModel<COLResults>>> calculateCostOfLivingByCountry(@PathVariable
			("city1Amount") BigDecimal city1Amount, @PathVariable("city1")String city1
			, @PathVariable("country")String country) {

		city1 = city1.toUpperCase();
		final String city1Final = city1; //defined because argument in orElseThrow must be final or effectively final
		//create instance of COLIndex from city1 value
		COLIndex colIndexCity1 = costOfLivingService.findByCity(city1).orElseThrow(()-> new COLIndexNotFoundException(city1Final ));
		System.out.println("colIndex value " + colIndexCity1);
				
		List<EntityModel<COLResults>> colResultsEntities =  costOfLivingService
				.calculateEquivalentSalaryByCountry(city1Amount, colIndexCity1, country).stream()
				.map(a-> EntityModel.of(a,  linkTo(methodOn(COLController.class)
						.calculateCostOfLivingByCountry( city1Amount
								,  city1Final
								,  country)).withSelfRel())).collect(Collectors.toList());
		
//		return new ResponseEntity<List<COLResults>>(costOfLivingService
//				.calculateEquivalentSalaryByCountry(amount, colIndex, country), HttpStatus.OK);
	
		CollectionModel<EntityModel<COLResults>> colResultsCollection = CollectionModel.of(colResultsEntities, 
				   linkTo(methodOn(COLController.class).getIndexesHATEOAS()).withSelfRel());
		   
	   return new ResponseEntity<CollectionModel<EntityModel<COLResults>>> (colResultsCollection, HttpStatus.OK);
	
	}
	
	/**
	 * Find the {@link com.chrisenoch.col.CostOfLiving.entity.COLIndex} for the {@code city} argument.
	 * @param city
	 * 		  the city to find the {@link com.chrisenoch.col.CostOfLiving.entity.COLIndex} for.
	 * @return
	 */
	@GetMapping("/colindexes/{city}")
	  public EntityModel<COLIndex> getCity(@PathVariable String city) {

	   COLIndex colIndex =  costOfLivingService.findByCity(city).orElseThrow(()-> new COLIndexNotFoundException(city));
	   
	   return EntityModel.of(colIndex
			   ,  linkTo(methodOn(COLController.class).getCity(city)).withSelfRel(),
			      linkTo(methodOn(COLController.class).getIndexesHATEOAS()).withRel("COLIndexes"));		   
	  }
	
	
	/**
	 * Find the {@link com.chrisenoch.col.CostOfLiving.entity.COLIndex} for every city.
	 * @return
	 */
	@GetMapping("/colindexes")
	public CollectionModel<EntityModel<COLIndex>> getIndexesHATEOAS(){
			List<EntityModel<COLIndex>> colIndexEntities= costOfLivingService.findColIndexes()
					.stream().map(COLIndex-> EntityModel.of(COLIndex,
							
							linkTo(methodOn(COLController.class).getCity(COLIndex.getCity())).withSelfRel()
					          ,linkTo(methodOn(COLController.class).getIndexesHATEOAS()).withRel("COLIndexes")
					          ))
				      .collect(Collectors.toList());
							 
			return CollectionModel.of(colIndexEntities, linkTo(methodOn(COLController.class).getIndexesHATEOAS()).withRel("COLIndexes"));
		} 	
	
	
	/**
	 * Find the {@link com.chrisenoch.col.CostOfLiving.entity.COLIndex} for every city in {@code country}
	 * @param country
	 * @return
	 * @throws Exception
	 */
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
	
	//To practise implementing custom methods using SpringData
	/**
	 * Get the {@link com.chrisenoch.col.CostOfLiving.entity.COLIndex} for all countries that match the short country name for the {@code country}
	 * argument. This will yield all countries that begin with the sequence of letters entered as the method argument. E.g. "I" would return the rates for all cities for Italy 
	 * and Iceland, "Ic" would only take into account Iceland, and "e" would return  neither of the two. The method argument is case insensitive. 
	 * @param country
	 * @return
	 */
	@GetMapping("/customcountry/{country}")
	public ResponseEntity<COLIndexes> getRatesByShortCountryName(@PathVariable("country") String country){
		
		List<EntityModel<COLIndex>> colIndexes = costOfLivingService.getRatesByShortCountryName(country)
				 .stream().map(a->  EntityModel.of(a, linkTo(methodOn(COLController.class)
						 .getRatesByShortCountryName(country)).withSelfRel(),
					        linkTo(methodOn(COLController.class).getIndexesHATEOAS()).withRel("colindexes"))						 
						 ).collect(Collectors.toList());
		
		return new ResponseEntity<COLIndexes>(new COLIndexes(colIndexes, OffsetDateTime.now()),HttpStatus.OK);
	}
	
	
	//Just to practise
	@GetMapping("/customcountryspringdata/{country}")
	public ResponseEntity<CollectionModel<EntityModel<COLIndex>>> getRatesByShortCountryNameSpringData
	(@PathVariable("country") String country){
		System.out.println("Debugging Country " + country);
		
		
		List<EntityModel<COLIndex>> colIndexesEntities =  costOfLivingService
				.findByCountryStartingWith(country).get().stream()
				.map(a-> EntityModel.of(a,  linkTo(methodOn(COLController.class)
						.getRatesByShortCountryNameSpringData(country)
						).withSelfRel())).collect(Collectors.toList());
		
	
		CollectionModel<EntityModel<COLIndex>> colIndexesCollection = CollectionModel.of(colIndexesEntities, 
				   linkTo(methodOn(COLController.class).getIndexesHATEOAS()).withSelfRel());
		   
	   return new ResponseEntity<CollectionModel<EntityModel<COLIndex>>> (colIndexesCollection, HttpStatus.OK);
	}
	
	
	//Just to practice
	@GetMapping("/colindexesassembler/{city}")
	  private EntityModel<COLIndex> colindexesassembler (@PathVariable String city) {

	   COLIndex colIndex =  costOfLivingService.findByCity(city).orElseThrow(()-> new COLIndexNotFoundException(city));
	   
	   return assembler.toModel(colIndex);	   
	  }
	
	//Just to practice
	@GetMapping("/colindexesassemblerre/{city}")
	  private ResponseEntity<EntityModel<COLIndex>> colindexesassemblerre (@PathVariable String city) {

	   COLIndex colIndex =  costOfLivingService.findByCity(city).orElseThrow(()-> new COLIndexNotFoundException(city));
	   
	   return new ResponseEntity<EntityModel<COLIndex>> (assembler.toModel(colIndex), HttpStatus.OK);	   
	  }
	
	
	//Just to practice
	@GetMapping("/colindexesassembler")
	  public CollectionModel<EntityModel<COLIndex>> getIndexesHATEOASAssembler() {

	   List<EntityModel<COLIndex>> colIndexes =  costOfLivingService.findColIndexes()
			   .stream().map(assembler::toModel).collect(Collectors.toList());
	   
	   return CollectionModel.of(colIndexes, 
			   linkTo(methodOn(COLController.class).getIndexesHATEOASAssembler()).withSelfRel());
			   	   
	  }
	
	//Just to practise
	@GetMapping("/colindexesassemblerre")
	  public ResponseEntity<CollectionModel<EntityModel<COLIndex>>> getIndexesHATEOASAssemblerResponseEntity() {

	   List<EntityModel<COLIndex>> colIndexEntities =  costOfLivingService.findColIndexes()
			   .stream().map(assembler::toModel).collect(Collectors.toList());
	   
	   CollectionModel<EntityModel<COLIndex>>colIndexCollection= CollectionModel.of(colIndexEntities, 
			   linkTo(methodOn(COLController.class).getIndexesHATEOASAssembler()).withSelfRel());
	   
	   return new ResponseEntity<CollectionModel<EntityModel<COLIndex>>> (colIndexCollection, HttpStatus.OK);
			   	   
	  }
	
	//Practice 
		@GetMapping("/gethateoas")
		public EntityModel<COLIndex>getHateoas() throws Exception{
			COLIndex rate = new COLIndex("Birmingmam", "England", BigDecimal.valueOf(1456), OffsetDateTime.now());
			EntityModel<COLIndex> model = EntityModel.of(rate);
			model.add(Link.of("http://localhost:8080/costofliving/123"));
			
			return model;
		}

}
