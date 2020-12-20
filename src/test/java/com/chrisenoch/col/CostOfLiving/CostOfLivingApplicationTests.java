package com.chrisenoch.col.CostOfLiving;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.chrisenoch.col.CostOfLiving.controller.COLController;
import com.chrisenoch.col.CostOfLiving.entity.COLIndexModelAssemblerNew;
import com.chrisenoch.col.CostOfLiving.service.CostOfLivingService;

@SpringBootTest
//@WebMvcTest
@AutoConfigureMockMvc
class CostOfLivingApplicationTests {
	CostOfLivingService costOfLivingService;
	COLIndexModelAssemblerNew assembler;

	@Autowired
	public CostOfLivingApplicationTests(CostOfLivingService costOfLivingService, COLIndexModelAssemblerNew assembler) {
		this.costOfLivingService = costOfLivingService;
		this.assembler = assembler;
	}

	@Autowired
	private COLController colController;
	
	@Autowired
	private MockMvc mockMvc;

	
	@Test
	public void contextLoads() throws Exception {
		assertThat(colController).isNotNull();
	}
	
	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/costofliving")).andExpect(status().isOk());
				
	}
	
	
	
	

}
