package com.kararoo.bookstore.service;

import java.util.List;

import com.kararoo.bookstore.dto.Book;

public interface BookService {

	Book saveBook(Book book) throws Exception;
	Book updateBook(Integer bookId, Book book) throws Exception;
	boolean deleteBook(Integer bookId) throws Exception;
	Book getBook(Integer bookId) throws Exception;
	List<Book> getBooks() throws Exception;
	double checkOutBook(List<Integer> bookIds, String promoCode) throws Exception;
}
