package com.chrisenoch.col.CostOfLiving;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.chrisenoch.col.CostOfLiving.controller.COLController;
import com.chrisenoch.col.CostOfLiving.entity.COLIndex;
import com.chrisenoch.col.CostOfLiving.entity.COLIndexModelAssemblerNew;
import com.chrisenoch.col.CostOfLiving.entity.COLResults;
import com.chrisenoch.col.CostOfLiving.repository.RateRepository;
import com.chrisenoch.col.CostOfLiving.service.CostOfLivingService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
//@WebMvcTest
@AutoConfigureMockMvc
class CostOfLivingApplicationTests {
	private CostOfLivingService costOfLivingService;
	private COLIndexModelAssemblerNew assembler;

	@Autowired
	public CostOfLivingApplicationTests(CostOfLivingService costOfLivingService, COLIndexModelAssemblerNew assembler) {
		this.costOfLivingService = costOfLivingService;
		this.assembler = assembler;
	}

	@Autowired
	private COLController colController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private RateRepository repository;

	
	@Test
	public void contextLoads() throws Exception {
		assertThat(colController).isNotNull();
	}
	
	@Test
	public void shouldReturnCOLIndexesAndDate() throws Exception {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = simpleDateFormat.format(new Date());
		 
		List<COLIndex> colIndexes = initCOLIndexes();
		
		
		//this.mockMvc.perform(get("/costofliving").content.(objectMapper.writeValueAsString(colIndex)).).andDo(print()).andExpect(status().isOk());
		this.mockMvc.perform(get("/costofliving")).andExpect(content()
				.string(containsString(objectMapper.writeValueAsString(colIndexes.get(0)))))
		.andExpect(content()
				.string(containsString(objectMapper.writeValueAsString(colIndexes.get(1)))))
		
		.andExpect(content()
				.string(containsString(objectMapper.writeValueAsString(colIndexes.get(2)))))
		.andExpect(content()
				.string(containsString(objectMapper.writeValueAsString(colIndexes.get(3)))))
		.andExpect(content()
				.string(containsString(objectMapper.writeValueAsString(colIndexes.get(4)))))
		.andExpect(content()
				.string(containsString(objectMapper.writeValueAsString(colIndexes.get(5)))))
		.andExpect(content()
				.string(containsString(date)))
		
		.andDo(print()).andExpect(status().isOk());	

	}
	
	@Test
	public void shouldReturnCOLIndexesByDate() throws Exception {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Date yesterday = yesterday();
		Date tomorrow = tomorrow();
		
		String todayFormatted = simpleDateFormat.format(new Date());
		String yesterdayFormatted = simpleDateFormat.format(yesterday());
		String tomorrowFormatted = simpleDateFormat.format(tomorrow());
		
		COLIndex colIndex7 = new COLIndex("PARIS","FRANCE", 70F,yesterday);
		COLIndex colIndex8 = new COLIndex("PERU","LIMA", 140F,tomorrow);
				
		repository.save(colIndex7);
		repository.save(colIndex8);
		 
		List<COLIndex> colIndexes = initCOLIndexes();
		
		
		//this.mockMvc.perform(get("/costofliving").content.(objectMapper.writeValueAsString(colIndex)).).andDo(print()).andExpect(status().isOk());
		this.mockMvc.perform(get("/costofliving/" + todayFormatted)).andExpect(content()
				.string(containsString(objectMapper.writeValueAsString(colIndexes.get(0)))))
		.andExpect(content()
				.string(containsString(objectMapper.writeValueAsString(colIndexes.get(1)))))
		
		.andExpect(content()
				.string(containsString(objectMapper.writeValueAsString(colIndexes.get(2)))))
		.andExpect(content()
				.string((containsString(objectMapper.writeValueAsString(colIndexes.get(3))))
						))
		.andExpect(content()
				.string(containsString(objectMapper.writeValueAsString(colIndexes.get(4)))))
		.andExpect(content()
				.string(containsString(objectMapper.writeValueAsString(colIndexes.get(5)))))
		.andExpect(content()
				.string(containsString(todayFormatted)))	
		.andExpect(content()
				.string(not(containsString(yesterdayFormatted))))
		.andExpect(content()
				.string(not(containsString(objectMapper.writeValueAsString(colIndex7)))))
		.andExpect(content()
				.string(not(containsString(objectMapper.writeValueAsString(colIndex8)))))
		.andExpect(content()
				.string(not(containsString(tomorrowFormatted))))
		.andDo(print()).andExpect(status().isOk());	
		
	}
	
	@Test
	public void shouldReturnCostOfLivingForRequestedCity() throws Exception {
 	
		COLResults expectedResults = new COLResults("TOKYO", "LONDON", 200, 100);
		
		this.mockMvc.perform(get("/costofliving/200/tokyo/to/london"))
		.andExpect(content()
				.string((containsString(objectMapper.writeValueAsString(expectedResults)))))	
		
		.andDo(print()).andExpect(status().isOk());	

	}
	
	@Test
	public void shouldReturnCostOfLivingForCitiesInRequestedCountry() throws Exception {
 	
		COLResults expectedResults = new COLResults("TOKYO", "LONDON", 200, 100);
		COLResults expectedResults2 = new COLResults("TOKYO", "BRISTOL", 200, 127.27273F);
		
		this.mockMvc.perform(get("/costofliving/200/tokyo/tocountry/england"))
		.andExpect(content()
				.string((containsString(objectMapper.writeValueAsString(expectedResults)))))	
		.andExpect(content()
				.string((containsString(objectMapper.writeValueAsString(expectedResults2)))))
		.andDo(print()).andExpect(status().isOk());	

	}
	
	@Test
	public void shouldReturnColIndexesByCity() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/costofliving/colindexes/tokyo"))
		.andDo(print()).andExpect(status().isOk())
		.andExpect(content().json("{\"city\":\"TOKYO\","
				+ "\"country\":\"JAPAN\",\"rate\":70.0,\"_links\":{\"self\":{\"href\":"
				+ "\"http://localhost/costofliving/colindexes/tokyo\"},\"COLIndexes"
				+ "\":{\"href\":\"http://localhost/costofliving/colindexes\"}}}"))
		.andReturn();

		
		System.out.println("JSON as String: " + result.getResponse().getContentAsString());

	}
	
	@Test
	public void shouldReturnColIndexesByCityFromAssembler() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/costofliving/colindexesassembler/tokyo"))
		.andDo(print()).andExpect(status().isOk())
		.andExpect(content().string(containsString("{\"city\":\"TOKYO\","
				+ "\"country\":\"JAPAN\",\"rate\":70.0,\"_links\":{\"self\":{\"href\":"
				+ "\"http://localhost/costofliving/colindexes/TOKYO\"},\"colindexes"
				+ "\":{\"href\":\"http://localhost/costofliving/colindexes\"}}}")))
		.andReturn();

		
		System.out.println("JSON as String: " + result.getResponse().getContentAsString());

	} 
	
	
	
	private List<COLIndex> initCOLIndexes() {	
		COLIndex colIndex = new COLIndex("TOKYO","JAPAN", 70F,new Date());
		COLIndex colIndex2 = new COLIndex("LONDON","ENGLAND", 140F,new Date());
		COLIndex colIndex3 = new COLIndex("SHANGHAI", "CHINA",30F,new Date());
		COLIndex colIndex4 = new COLIndex("MADRID", "SPAIN",230F,new Date());
		COLIndex colIndex5 = new COLIndex("BERLIN","GERMANY", 170F,new Date());
		COLIndex colIndex6 = new COLIndex("BRISTOL","ENGLAND", 110F,new Date());
		
		List<COLIndex> colIndexes = Arrays.asList(colIndex, colIndex2, colIndex3
				, colIndex4, colIndex5, colIndex6);
		
		return colIndexes;
	}
	
	private Date yesterday() {
	    final Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, -1);
	    return cal.getTime();
	}
	
	private Date tomorrow() {
	    final Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, +1);
	    return cal.getTime();
	}
	
}
