package com.kararoo.bookstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.kararoo.bookstore.entity.Book;

@DataJpaTest
public class BookRepositoryTest{
	
	@Autowired
	BookRepository bookRepository;
	
	@Test
	public void testFindAll() {
		List<Book> items = bookRepository.findAll();
		assertEquals(0,items.size());
	}
}
