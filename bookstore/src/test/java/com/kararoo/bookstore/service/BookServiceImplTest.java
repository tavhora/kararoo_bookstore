package com.kararoo.bookstore.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kararoo.bookstore.dto.Book;
import com.kararoo.bookstore.repository.BookRepository;
import com.kararoo.bookstore.service.impl.BookServiceImpl;

@ExtendWith(SpringExtension.class)
@Service
public class BookServiceImplTest{

	@Mock
	BookRepository bookRepository;
	
	@InjectMocks
	private BookServiceImpl bookService;
	
	@Test
	public void testSaveBook() throws Exception {
		when(bookRepository.save(Mockito.any())).thenReturn(this.getBookEntityForUnitTesting());
		Book response = bookService.saveBook(this.getBookDtoForUnitTesting());
		assertEquals("FICTION", response.getBookType());
	}
	
	@Test
	public void testUpdateBook() throws Exception {
		when(bookRepository.save(Mockito.any())).thenReturn(this.getBookEntityForUnitTesting());
		Book response = bookService.updateBook(1,this.getBookDtoForUnitTesting());
		assertEquals("FICTION", response.getBookType());
	}
	
	@Test
	public void testGetBook() throws Exception {
		Optional<com.kararoo.bookstore.entity.Book> booOptional = Optional.of(this.getBookEntityForUnitTesting());
		when(bookRepository.getById(Mockito.any())).thenReturn(booOptional.get());
		Book response = bookService.getBook(1);
		assertEquals("FICTION", response.getBookType());
	}
	
	@Test
	public void testDeleteBook() throws Exception {
		//when(bookRepository.deleteById(Mockito.any())).thenReturn(true);
		boolean response = bookService.deleteBook(1);
		assertEquals(true, response);
	}
	
	@Test
	public void testGetBooks() throws Exception {
		List<com.kararoo.bookstore.entity.Book> books = new ArrayList<com.kararoo.bookstore.entity.Book>(1);
		books.add(getBookEntityForUnitTesting());
		when(bookRepository.findAll()).thenReturn(books);
		List<Book> response = bookService.getBooks();
		assertEquals(1, response.size());
	}
	
	@Test
	public void testcheckOutBook() throws Exception {
		List<com.kararoo.bookstore.entity.Book> books = new ArrayList<com.kararoo.bookstore.entity.Book>(2);
		books.add(getBookEntityForUnitTesting());
		com.kararoo.bookstore.entity.Book book = getBookEntityForUnitTesting();
		book.setBookType("COMIC");
		book.setBookId(2);
		books.add(book);
		when(bookRepository.findAllById(Mockito.any())).thenReturn(books);
		List<Integer> bookIds = new ArrayList<Integer>(1);
		bookIds.add(1);
		bookIds.add(2);
		Double response = bookService.checkOutBook(bookIds, "SAVE50");
		assertEquals(190D, response);
	}
	
	public com.kararoo.bookstore.entity.Book getBookEntityForUnitTesting() {
		
		com.kararoo.bookstore.entity.Book book = new com.kararoo.bookstore.entity.Book();
		book.setBookAuthor("AUTHOR1");
		book.setBookDescription("DESC1");
		book.setBookId(1);
		book.setBookISBN("ISBN1");
		book.setBookName("NAME1");
		book.setBookPrice(100D);
		book.setBookType("FICTION");
		return book;
	}
	
	public Book getBookDtoForUnitTesting() {
		Book bookRequest = new Book();
		bookRequest.setBookAuthor("AUTHOR1");
		bookRequest.setBookDescription("DESC1");
		bookRequest.setBookId(1);
		bookRequest.setBookISBN("ISBN1");
		bookRequest.setBookName("NAME1");
		bookRequest.setBookPrice(100D);
		bookRequest.setBookType("FICTION");
		return bookRequest;
	}
}