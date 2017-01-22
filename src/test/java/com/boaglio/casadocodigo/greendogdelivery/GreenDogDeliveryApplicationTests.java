package com.boaglio.casadocodigo.greendogdelivery;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

 
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("scratch")
// Separate profile for web tests to avoid clashing databases
public class GreenDogDeliveryApplicationTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	public void testHome() throws Exception {

		String URL1="/api";
		
		System.out.println(this.mvc.perform(get(URL1)).andDo(print()));
		
		this.mvc.perform(get(URL1)).andExpect(status().isOk())
				.andExpect(content().string(containsString("clientes")));
	}

	@Test
	public void findClientesByNome() throws Exception {

		String URL2="/api/clientes/search/findByNomeAllIgnoringCase?nome=fernando boaglio";
		
		System.out.println(this.mvc.perform(get(URL2)).andDo(print()));
		
		this.mvc.perform(
				get(URL2))
				.andExpect(status().isOk())
				.andExpect(jsonPath("endereco", equalTo("Sampa")));
	}

	@Test
	public void findClientesByNomeContaining() throws Exception {

		String URL3="/api/clientes/search/findByNomeContainingAllIgnoringCase?nome=e";
		
		System.out.println(this.mvc.perform(get(URL3)).andDo(print()));
		
		this.mvc.perform(
				get(URL3))
				.andExpect(status().isOk())
				.andExpect(jsonPath("_embedded.clientes", hasSize(2)));
	}
	
	@Test
	public void findItensByNome() throws Exception {

		String URL4="/api/item/por-nome/findByNome?nome=Green Dog max salada";
		
		System.out.println(this.mvc.perform(get(URL4)).andDo(print()));
		
		this.mvc.perform(
				get(URL4))
				.andExpect(status().isOk())
				.andExpect(jsonPath("preco", equalTo(30)));
	}
	
	
}