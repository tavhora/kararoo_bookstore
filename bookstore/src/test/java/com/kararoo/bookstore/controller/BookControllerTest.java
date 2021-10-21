package com.kararoo.bookstore.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kararoo.bookstore.dto.Book;
import com.kararoo.bookstore.service.BookService;
import com.kararoo.bookstore.service.BookServiceImplTest;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@EnableWebMvc
public class BookControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookService bookService;

	@InjectMocks
	private BookServiceImplTest bookServiceImplTest;

	private ObjectMapper objectMapper = null;

	private String url = "/book", bookResponse = "{\"bookId\":1,\"bookName\":\"NAME1\",\"bookDescription\":\"DESC1\",\"bookAuthor\":\"AUTHOR1\",\"bookType\":\"FICTION\",\"bookISBN\":\"ISBN1\",\"bookPrice\":100.0}";

	@BeforeEach
	void setup () {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
		this.mockMvc = builder.build();
		objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
		objectMapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS , false);
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS"));
	}

	@Test
	public void testPostBook() throws Exception {
		when(bookService.saveBook(Mockito.any())).thenReturn(bookServiceImplTest.getBookDtoForUnitTesting());
		MvcResult mvcResult = mockMvc
				.perform(post(url)
						.contentType(MediaType.APPLICATION_JSON)
						.content((objectMapper.writeValueAsString(bookServiceImplTest.getBookDtoForUnitTesting()))) 
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		/*.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.bookType").value("FICTION"));*/

		String result = mvcResult.getResponse().getContentAsString();
		assertThat(result).isEqualTo(bookResponse);
	}

	@Test
	public void testGetBook() throws Exception {
		when(bookService.getBook(Mockito.any())).thenReturn(bookServiceImplTest.getBookDtoForUnitTesting());
		MvcResult mvcResult = mockMvc
				.perform(get(url+"/1"))
				.andExpect(status().isOk())
				.andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		assertThat(result).isEqualTo(bookResponse);
	}

	@Test
	public void testUpdateBook() throws Exception {
		when(bookService.updateBook(Mockito.any(), Mockito.any())).thenReturn(bookServiceImplTest.getBookDtoForUnitTesting());
		MvcResult mvcResult = mockMvc
				.perform(put(url+"/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content((objectMapper.writeValueAsString(bookServiceImplTest.getBookDtoForUnitTesting()))) 
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		assertThat(result).isEqualTo(bookResponse);
	}

	@Test
	public void testDeleteBook() throws Exception {
		when(bookService.deleteBook(Mockito.any())).thenReturn(true);
		MvcResult mvcResult = mockMvc
				.perform(delete(url+"/1"))
				.andExpect(status().isOk())
				.andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		assertThat(result).isEqualTo("true");
	}

	@Test
	public void testGetBooks() throws Exception {
		List<Book> books = new ArrayList<Book>(1);
		books.add(bookServiceImplTest.getBookDtoForUnitTesting());
		when(bookService.getBooks()).thenReturn(books);
		MvcResult mvcResult = mockMvc
				.perform(get(url+"/list"))
				.andExpect(status().isOk())
				.andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		assertThat(result).isEqualTo("["+bookResponse+"]");
	}

	@Test
	public void testCheckOutBook() throws Exception {
		when(bookService.checkOutBook(Mockito.any(), Mockito.any())).thenReturn(200D);
		MvcResult mvcResult = mockMvc
				.perform(post(url+"/checkout")
						.contentType(MediaType.APPLICATION_JSON)
						.content("[1,2]")
						.queryParam("promoCode", "promoCode"))
				.andExpect(status().isOk())
				.andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		assertThat(result).isEqualTo("200.0");
	}
}