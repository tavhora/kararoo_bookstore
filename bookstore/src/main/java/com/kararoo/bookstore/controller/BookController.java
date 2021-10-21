package com.kararoo.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kararoo.bookstore.dto.Book;
import com.kararoo.bookstore.service.BookService;

@RestController
@Validated
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping("/book")
public class BookController {

	@Autowired
	BookService bookService;

	@PostMapping
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Book saveBook(@RequestBody Book book) throws Exception {
		return bookService.saveBook(book);
	}
	
	@GetMapping("/{bookId}")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Book getBook(@PathVariable Integer bookId) throws Exception {
		return bookService.getBook(bookId);
	}

	@PutMapping("/{bookId}")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Book updateBook(@PathVariable Integer bookId, @RequestBody Book book) throws Exception {
		return bookService.updateBook(bookId, book);
	}

	@DeleteMapping("/{bookId}")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public boolean deleteBook(@PathVariable Integer bookId) throws Exception {
		return bookService.deleteBook(bookId);
	}
	
	@GetMapping("/list")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<Book> getBooks() throws Exception {
		return bookService.getBooks();
	}

	@PostMapping("/checkout")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public double checkOutBook(@RequestBody List<Integer> bookIds, @RequestParam(required = false) String promoCode) throws Exception {
		return bookService.checkOutBook(bookIds, promoCode);
	}
}