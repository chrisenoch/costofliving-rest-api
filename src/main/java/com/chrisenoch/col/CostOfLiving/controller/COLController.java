package com.chrisenoch.col.CostOfLiving.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.chrisenoch.col.CostOfLiving.entity.COLIndex;
import com.chrisenoch.col.CostOfLiving.entity.COLIndexModel;
import com.chrisenoch.col.CostOfLiving.entity.COLIndexModelAssembler;
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
		CollectionModel<COLIndex> result = new CollectionModel<>(colIndexes, link);
		return result;
	}
	
	//@GetMapping("/country/{country}")
	public CollectionModel<COLIndexModel> getIndexesByCountryOld(@PathVariable("country") String country) throws Exception{
		System.out.println("Inside getIndexesByCountry");
		//Need to change this so returns a COLIndexes object
		
		COLIndexModelAssembler colIndexModelAssembler = new COLIndexModelAssembler();

		List<COLIndex> test = costOfLivingService.findColIndexesByCountry(country).get();
		//CollectionModel<COLIndexModel> test123 =   colIndexModelAssembler.toCollectionModel(test);
		
//		Collection<List<COLIndex>> colIndexCollection = Collections.singleton(test);
//		CollectionModel<COLIndex> model = CollectionModel.of(test);	
		//test123.add(Link.of("http://localhost:8080/costofliving/123"));
//		System.out.println("List of model");
		//test123.forEach(System.out::println);
		//greeting.add(linkTo(methodOn(COLController.class).getIndexesByCountry()).withSelfRel());
//		CollectionModel<COLIndexModel> test456 = colIndexModelAssembler.toCollectionModel(costOfLivingService.findColIndexesByCountry(country).orElseThrow(()-> new COLIndexNotFoundException(country)));
//		System.out.println("Print list below");
//		test456.forEach(System.out::println);

		//return ResponseEntity.ok(colIndexModelAssembler.toCollectionModel(costOfLivingService.findColIndexesByCountry(country).orElseThrow(()-> new COLIndexNotFoundException(country))));
		return colIndexModelAssembler.toCollectionModel(test);

	}

//	@GetMapping("/country/{country}")
//	public ResponseEntity<COLIndexes> getIndexesByCountryExample(@PathVariable("country") String country) throws Exception{
//		
//		return new ResponseEntity<COLIndexes>(new COLIndexes(costOfLivingService.findColIndexesByCountry(country).get(), new Date()),HttpStatus.OK);
//	}
	
	
//	@GetMapping("/countrya/{country}")
//	public ResponseEntity<CollectionModel<COLIndex>> getIndexesByCountry2(@PathVariable("country") String country) throws Exception{
//		//Need to change this so returns a COLIndexes object
//		
//		List<COLIndex> test = costOfLivingService.findColIndexesByCountry(country);
//		
//		//Need to transform every COLIndex to a COLIndexModel and then make a list out of it.
//		COLIndexModelAssembler colIndexModelAssembler = new COLIndexModelAssembler();
//		
//		//List<COLIndexModel> nexttry = test.stream().map(c-> colIndexModelAssembler.toModel(c))
//				//.collect(Collectors.toList());
//				
//		//Iterable<? extends COLIndex> nexttry2 = 
//		//CollectionModel<COLIndexModel> nextry3 = colIndexModelAssembler.toCollectionModel(nexttry);
//		
//
//		Collection<List<COLIndex>> colIndexCollection = Collections.singleton(test);
//		CollectionModel<COLIndex> model = CollectionModel.of(test);	
//		//model.add(linkTo(methodOn(COLController.class).getIndexesByCountry2(country)).withSelfRel());
//		//greeting.add(linkTo(methodOn(COLController.class).getIndexesByCountry()).withSelfRel());
//			
//		
//		
//		//return new ResponseEntity<CollectionModel<COLIndex>>(model, HttpStatus.OK);
//
//	}
	
//	@GetMapping("/countryb/{country}")
//	public ResponseEntity<CollectionModel<COLIndexModel>> getIndexesByCountry3(@PathVariable("country") String country) throws Exception{
//		//Need to change this so returns a COLIndexes object
//		COLIndexModelAssembler colIndexModelAssembler = new COLIndexModelAssembler();
//		
//		List<COLIndex> test = costOfLivingService.findColIndexesByCountry(country);
//		test.forEach(System.out::println);
//		List<COLIndexModel> test4= test.stream().map(c-> colIndexModelAssembler
//				.toModel(c)).collect(Collectors.toList());
				
		
		//List<COLIndex> test4 = test.stream().map(c-> colIndexModelAssembler
				//.toModel(c)).map(c-> c.add("Http://localhost.com")).collect(Collectors.toList()));
		
		
		//CollectionModel<COLIndexModel> test3 = new COLIndexModelAssembler().toCollectionModel(test4);
		
		//Collection<List<COLIndex>> colIndexCollection = Collections.singleton(test);
		
		//COLIndexModelAssembler assembler = new COLIndexModelAssembler();
	
		//CollectionModel<COLIndexModel> model = assembler.toCollectionModel(test);
		
		//CollectionModel<COLIndex> model = CollectionModel.of(test);	
		//test4.add(linkTo(methodOn(COLController.class).getIndexesByCountry2(country)).withSelfRel());
		//greeting.add(linkTo(methodOn(COLController.class).getIndexesByCountry()).withSelfRel());
			
		//return new ResponseEntity<CollectionModel<COLIndexModel>>(test3, HttpStatus.OK);

	//}
	
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
		String baseErrorMessage = base; //defined because argument in orElseThrow must be final or effectively final
		COLIndex colIndex = costOfLivingService.findByCity(base).orElseThrow(()-> new COLIndexNotFoundException(baseErrorMessage));
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
