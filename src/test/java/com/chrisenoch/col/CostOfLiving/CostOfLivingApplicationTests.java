package com.chrisenoch.col.CostOfLiving;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.chrisenoch.col.CostOfLiving.controller.COLController;
import com.chrisenoch.col.CostOfLiving.entity.COLIndex;
import com.chrisenoch.col.CostOfLiving.entity.COLIndexModelAssemblerNew;
import com.chrisenoch.col.CostOfLiving.entity.COLResults;
import com.chrisenoch.col.CostOfLiving.repository.RateRepository;
import com.chrisenoch.col.CostOfLiving.service.CostOfLivingService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
		
		String todayFormatted = OffsetDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
		
		 
		List<COLIndex> colIndexes = initCOLIndexes();
		System.out.println("Debugging - Object mapper: " + objectMapper.writeValueAsString(colIndexes.get(0)));
		COLResults cR = new COLResults("TK", "LN", BigDecimal.valueOf(6), BigDecimal.valueOf(5));
		System.out.println("Debugging - Object mapper cR: " + objectMapper.writeValueAsString(cR));
		
		
		
		//this.mockMvc.perform(get("/costofliving").content.(objectMapper.writeValueAsString(colIndex)).).andDo(print()).andExpect(status().isOk());
		this.mockMvc.perform(get("/costofliving/colindexes").contentType(MediaType.APPLICATION_JSON)).andExpect(content()
				.string(containsString(serialiseToJSONAndRemoveLinks(colIndexes.get(0)))))
		.andExpect(content()
				.string(containsString(serialiseToJSONAndRemoveLinks(colIndexes.get(1)))))
		
		.andExpect(content()
				.string(containsString(serialiseToJSONAndRemoveLinks(colIndexes.get(2)))))
		.andExpect(content()
				.string(containsString(serialiseToJSONAndRemoveLinks(colIndexes.get(3)))))
		.andExpect(content()
				.string(containsString(serialiseToJSONAndRemoveLinks(colIndexes.get(4)))))
		.andExpect(content()
				.string(containsString(serialiseToJSONAndRemoveLinks(colIndexes.get(5)))))
		//.andExpect(content()
				//.string(containsString(todayFormatted)))
		
		.andDo(print()).andExpect(status().isOk());	

	}
	
	@Test
	public void shouldReturnCOLIndexesByDate() throws Exception {
		OffsetDateTime yesterday = OffsetDateTime.now().minusDays(1L);
		OffsetDateTime tomorrow = OffsetDateTime.now().plusDays(1L);
		
		String todayFormatted = OffsetDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
		String yesterdayFormatted = yesterday.format(DateTimeFormatter.ISO_LOCAL_DATE);
		String tomorrowFormatted = tomorrow.format(DateTimeFormatter.ISO_LOCAL_DATE);
		
//		String todayFormatted = simpleDateFormat.format(new Date());
//		String yesterdayFormatted = simpleDateFormat.format(yesterday());
//		String tomorrowFormatted = simpleDateFormat.format(tomorrow());
		
		COLIndex colIndex7 = new COLIndex("PARIS","FRANCE", BigDecimal.valueOf(70),yesterday);
		COLIndex colIndex8 = new COLIndex("PERU","LIMA", BigDecimal.valueOf(140),tomorrow);
				
		repository.save(colIndex7);
		repository.save(colIndex8);
		 
		List<COLIndex> colIndexes = initCOLIndexes();
		
		
		//this.mockMvc.perform(get("/costofliving").content.(objectMapper.writeValueAsString(colIndex)).).andDo(print()).andExpect(status().isOk());
		this.mockMvc.perform(get("/costofliving/" + todayFormatted).contentType(MediaType.APPLICATION_JSON)).andExpect(content()
				.string(containsString(serialiseToJSONAndRemoveLinks(colIndexes.get(0)))))
		.andExpect(content()
				.string(containsString(serialiseToJSONAndRemoveLinks(colIndexes.get(1)))))
		
		.andExpect(content()
				.string(containsString(serialiseToJSONAndRemoveLinks(colIndexes.get(2)))))
		.andExpect(content()
				.string((containsString(serialiseToJSONAndRemoveLinks(colIndexes.get(3))))
						))
		.andExpect(content()
				.string(containsString(serialiseToJSONAndRemoveLinks(colIndexes.get(4)))))
		.andExpect(content()
				.string(containsString(serialiseToJSONAndRemoveLinks(colIndexes.get(5)))))
		.andExpect(content()
				.string(containsString(todayFormatted)))	
		.andExpect(content()
				.string(not(containsString(yesterdayFormatted))))
		.andExpect(content()
				.string(not(containsString(serialiseToJSONAndRemoveLinks(colIndex7)))))
		.andExpect(content()
				.string(not(containsString(serialiseToJSONAndRemoveLinks(colIndex8)))))
		.andExpect(content()
				.string(not(containsString(tomorrowFormatted))))
		.andDo(print()).andExpect(status().isOk());	
		
	}
	
	@Test
	public void shouldReturnCostOfLivingForRequestedCity() throws Exception {
 	
		COLResults expectedResults = new COLResults("TOKYO", "LONDON", BigDecimal.valueOf(200), BigDecimal.valueOf(228.00));
		
		this.mockMvc.perform(get("/costofliving/200/tokyo/to/london").contentType(MediaType.APPLICATION_JSON))
		.andExpect(content()
				.string((containsString(serialiseToJSONAndRemoveTrailingBrace(expectedResults)))))	
		
		.andDo(print()).andExpect(status().isOk());	

	}
	
	@Test
	public void shouldReturnCostOfLivingForCitiesInRequestedCountry() throws Exception {  
 	
		COLResults expectedResults = new COLResults("TOKYO", "LONDON", BigDecimal.valueOf(200), BigDecimal.valueOf(228.00));
		COLResults expectedResults2 = new COLResults("TOKYO", "BRISTOL", BigDecimal.valueOf(200), BigDecimal.valueOf(258.00));
		
		this.mockMvc.perform(get("/costofliving/200/tokyo/tocountry/england").contentType(MediaType.APPLICATION_JSON))
		.andExpect(content()
				.string((containsString(serialiseToJSONAndRemoveTrailingBrace(expectedResults)))))	
		.andExpect(content()
				.string((containsString(serialiseToJSONAndRemoveTrailingBrace(expectedResults2)))))
		.andDo(print()).andExpect(status().isOk());	

	}
	
	@Test
	public void shouldReturnColIndexesByCity() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/costofliving/colindexes/tokyo").contentType(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(status().isOk())
		.andExpect(content().json("{\"city\":\"TOKYO\","
				+ "\"country\":\"JAPAN\",\"colIndex\":89.69,\"_links\":{\"self\":{\"href\":"
				+ "\"http://localhost/costofliving/colindexes/tokyo\"},\"COLIndexes"
				+ "\":{\"href\":\"http://localhost/costofliving/colindexes\"}}}"))
		.andReturn();

		
		System.out.println("JSON as String: " + result.getResponse().getContentAsString());

	}
	
	
	@Test
	public void shouldReturnColIndexesByCityFromAssembler() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/costofliving/colindexesassembler/tokyo").contentType(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(status().isOk())
		.andExpect(content().json("{\"city\":\"TOKYO\","
				+ "\"country\":\"JAPAN\",\"colIndex\":89.69,\"_links\":{\"self\":{\"href\":"
				+ "\"http://localhost/costofliving/colindexes/TOKYO\"},\"colindexes"
				+ "\":{\"href\":\"http://localhost/costofliving/colindexes\"}}}"))
		.andReturn();

		
		System.out.println("JSON as String: " + result.getResponse().getContentAsString());

	} 
	
	@Test
	public void shouldReturnColIndexesByCityFromAssemblerReturnedAsResponseEntity() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/costofliving/colindexesassemblerre/tokyo").contentType(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(status().isOk())
		.andExpect(content().json("{\"city\":\"TOKYO\","
				+ "\"country\":\"JAPAN\",\"colIndex\":89.69,\"_links\":{\"self\":{\"href\":"
				+ "\"http://localhost/costofliving/colindexes/TOKYO\"},\"colindexes"
				+ "\":{\"href\":\"http://localhost/costofliving/colindexes\"}}}"))
		.andReturn();

		
		System.out.println("JSON as String: " + result.getResponse().getContentAsString());

	}
	
	@Test
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	public void shouldReturnAllCOLIndexes() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/costofliving/colindexes").contentType(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(status().isOk())
		.andExpect(content().json("{\r\n"
				+ "  \"_embedded\": {\r\n"
				+ "    \"cOLIndexList\": [\r\n"
				+ "      {\r\n"
				+ "        \"city\": \"TOKYO\",\r\n"
				+ "        \"country\": \"JAPAN\",\r\n"
				+ "        \"colIndex\": 89.69,\r\n"
				+ "        \"_links\": {\r\n"
				+ "          \"self\": {\r\n"
				+ "            \"href\": \"http://localhost/costofliving/colindexes/TOKYO\"\r\n"
				+ "          },\r\n"
				+ "          \"COLIndexes\": {\r\n"
				+ "            \"href\": \"http://localhost/costofliving/colindexes\"\r\n"
				+ "          }\r\n"
				+ "        }\r\n"
				+ "      },\r\n"
				+ "      {\r\n"
				+ "        \"city\": \"LONDON\",\r\n"
				+ "        \"country\": \"ENGLAND\",\r\n"
				+ "        \"colIndex\": 78.83,\r\n"
				+ "        \"_links\": {\r\n"
				+ "          \"self\": {\r\n"
				+ "            \"href\": \"http://localhost/costofliving/colindexes/LONDON\"\r\n"
				+ "          },\r\n"
				+ "          \"COLIndexes\": {\r\n"
				+ "            \"href\": \"http://localhost/costofliving/colindexes\"\r\n"
				+ "          }\r\n"
				+ "        }\r\n"
				+ "      },\r\n"
				+ "      {\r\n"
				+ "        \"city\": \"SHANGHAI\",\r\n"
				+ "        \"country\": \"CHINA\",\r\n"
				+ "        \"colIndex\": 49.25,\r\n"
				+ "        \"_links\": {\r\n"
				+ "          \"self\": {\r\n"
				+ "            \"href\": \"http://localhost/costofliving/colindexes/SHANGHAI\"\r\n"
				+ "          },\r\n"
				+ "          \"COLIndexes\": {\r\n"
				+ "            \"href\": \"http://localhost/costofliving/colindexes\"\r\n"
				+ "          }\r\n"
				+ "        }\r\n"
				+ "      },\r\n"
				+ "      {\r\n"
				+ "        \"city\": \"MADRID\",\r\n"
				+ "        \"country\": \"SPAIN\",\r\n"
				+ "        \"colIndex\": 61.15,\r\n"
				+ "        \"_links\": {\r\n"
				+ "          \"self\": {\r\n"
				+ "            \"href\": \"http://localhost/costofliving/colindexes/MADRID\"\r\n"
				+ "          },\r\n"
				+ "          \"COLIndexes\": {\r\n"
				+ "            \"href\": \"http://localhost/costofliving/colindexes\"\r\n"
				+ "          }\r\n"
				+ "        }\r\n"
				+ "      },\r\n"
				+ "      {\r\n"
				+ "        \"city\": \"BERLIN\",\r\n"
				+ "        \"country\": \"GERMANY\",\r\n"
				+ "        \"colIndex\": 67.02,\r\n"
				+ "        \"_links\": {\r\n"
				+ "          \"self\": {\r\n"
				+ "            \"href\": \"http://localhost/costofliving/colindexes/BERLIN\"\r\n"
				+ "          },\r\n"
				+ "          \"COLIndexes\": {\r\n"
				+ "            \"href\": \"http://localhost/costofliving/colindexes\"\r\n"
				+ "          }\r\n"
				+ "        }\r\n"
				+ "      },\r\n"
				+ "      {\r\n"
				+ "        \"city\": \"BRISTOL\",\r\n"
				+ "        \"country\": \"ENGLAND\",\r\n"
				+ "        \"colIndex\": 69.37,\r\n"
				+ "        \"_links\": {\r\n"
				+ "          \"self\": {\r\n"
				+ "            \"href\": \"http://localhost/costofliving/colindexes/BRISTOL\"\r\n"
				+ "          },\r\n"
				+ "          \"COLIndexes\": {\r\n"
				+ "            \"href\": \"http://localhost/costofliving/colindexes\"\r\n"
				+ "          }\r\n"
				+ "        }\r\n"
				+ "      }\r\n"
				+ "    ]\r\n"
				+ "  },\r\n"
				+ "  \"_links\": {\r\n"
				+ "    \"COLIndexes\": {\r\n"
				+ "      \"href\": \"http://localhost/costofliving/colindexes\"\r\n"
				+ "    }\r\n"
				+ "  }\r\n"
				+ "}"))
		.andReturn();

		
		System.out.println("JSON as String: " + result.getResponse().getContentAsString());

	}
	
	@Test
	public void shouldReturnCostOfLivingForCitiesInRequestedCountryWithLinks()  throws Exception {
		MvcResult result = this.mockMvc.perform(get("/costofliving/country/England").contentType(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(status().isOk())
		.andExpect(content().json("\n"
				+ "{\"_embedded\":{\"cOLIndexList\":[{\"city\":\"LONDON\",\"country\":\"ENGLAND\""
				+ ",\"colIndex\":78.83,\"_links\":{\"self\":{\"href\":\"http://localhost/costofliving"
				+ "/country/England\"}}},{\"city\":\"BRISTOL\",\"country\":\"ENGLAND\",\"colIndex\":69.37,\""
				+ "_links\":{\"self\":{\"href\":\"http://localhost/costofliving/country/England\"}}}]}"
				+ ",\"_links\":{\"self\":{\"href\":\"http://localhost/costofliving\"}}}"))
		.andReturn();
	}

	
	private List<COLIndex> initCOLIndexes() {	
		COLIndex colIndex = new COLIndex("TOKYO","JAPAN", BigDecimal.valueOf(89.69),OffsetDateTime.now());
		COLIndex colIndex2 = new COLIndex("LONDON","ENGLAND", BigDecimal.valueOf(78.83),OffsetDateTime.now());
		COLIndex colIndex3 = new COLIndex("SHANGHAI", "CHINA",BigDecimal.valueOf(49.25),OffsetDateTime.now());
		COLIndex colIndex4 = new COLIndex("MADRID", "SPAIN",BigDecimal.valueOf(61.15),OffsetDateTime.now());
		COLIndex colIndex5 = new COLIndex("BERLIN","GERMANY",  BigDecimal.valueOf(67.02),OffsetDateTime.now());
		COLIndex colIndex6 = new COLIndex("BRISTOL","ENGLAND", BigDecimal.valueOf(69.37),OffsetDateTime.now());
		
		List<COLIndex> colIndexes = Arrays.asList(colIndex, colIndex2, colIndex3
				, colIndex4, colIndex5, colIndex6);
		
		return colIndexes;
	}
	
	private String serialiseToJSONAndRemoveLinks(COLIndex colIndex) throws JsonProcessingException {
		String colIndexString = objectMapper.writeValueAsString(colIndex);
		System.out.println("debugging: colIndexString b4 cut" + colIndexString);
		colIndexString = colIndexString .replace(",\"links\":[]}", "");
		System.out.println("debugging: colIndexString after cut" + colIndexString);
		return colIndexString;
		
	}
	
	private String serialiseToJSONAndRemoveTrailingBrace(COLIndex colIndex) throws JsonProcessingException {
		String colIndexString = objectMapper.writeValueAsString(colIndex);
		System.out.println("debugging: colIndexString b4 cut" + colIndexString);
		colIndexString = colIndexString.substring(0, colIndexString.length() - 1);
		System.out.println("debugging: colIndexString after cut trailing brace" + colIndexString);
		return colIndexString;
		
	}
	
	private String serialiseToJSONAndRemoveTrailingBrace(COLResults colResults) throws JsonProcessingException {
		String colIndexString = objectMapper.writeValueAsString(colResults);
		System.out.println("debugging: colIndexString b4 cut" + colIndexString);
		colIndexString = colIndexString.substring(0, colIndexString.length() - 1);
		System.out.println("debugging: colIndexString after cut trailing brace" + colIndexString);
		return colIndexString;
		
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
