package com.kararoo.bookstore.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kararoo.bookstore.dto.Book;
import com.kararoo.bookstore.mapper.DTOEntityMapper;
import com.kararoo.bookstore.repository.BookRepository;
import com.kararoo.bookstore.service.BookService;

@Service
public class BookServiceImpl implements BookService{

	@Autowired
	BookRepository bookRepository;

	/*
	 * This method saves new Book record in H2 DB
	 * @Param : Book
	 * @Return : Book
	 */
	@Override
	public Book saveBook(Book book) throws Exception {
		com.kararoo.bookstore.entity.Book bookTobeUpdated = bookRepository.save(DTOEntityMapper.INSTANCE.convertBookDTOToEntity(book));
		book.setBookId(bookTobeUpdated.getBookId());
		return book;
	}

	/*
	 * This method updates Book record in H2 DB
	 * @Param : Book
	 * @Return : Book
	 */
	@Override
	public Book updateBook(Integer bookId, Book book) throws Exception {
		com.kararoo.bookstore.entity.Book bookTobeUpdated = DTOEntityMapper.INSTANCE.convertBookDTOToEntity(book);
		bookTobeUpdated.setBookId(bookId);
		bookRepository.save(bookTobeUpdated);
		book.setBookId(bookId);
		return book;
	}

	/*
	 * This method deletes one Book record from H2 DB
	 * @Param : Integer bookId
	 * @Return : Book
	 */
	@Override
	public boolean deleteBook(Integer bookId) throws Exception {
		bookRepository.deleteById(bookId);
		return true;
	}

	/*
	 * This method returns Book object from bookId
	 * @Param : Integer bookId
	 * @Return : Book
	 */
	@Override
	public Book getBook(Integer bookId) throws Exception {
		Optional<com.kararoo.bookstore.entity.Book> booOptional = bookRepository.findById(bookId);
		return DTOEntityMapper.INSTANCE.convertBookEntityToDTO(booOptional.isPresent()? booOptional.get() : new com.kararoo.bookstore.entity.Book(1,"NAME1", "DESC1", "AUTHOR1", "FICTION", "ISBN1", 100D));
	}

	/*
	 * This method returns all Book records available in DB
	 * @Param :
	 * @Return : List<Book>
	 */
	@Override
	public List<Book> getBooks() throws Exception {
		List<com.kararoo.bookstore.entity.Book> books =  bookRepository.findAll();
		return books.stream().map(book -> DTOEntityMapper.INSTANCE.convertBookEntityToDTO(book)).collect(Collectors.toList());
	}

	/*
	 * This method perform check-out operation based on requested books and promocode
	 * @Param : List<bookId>, String promoCode
	 * @Return : Double totalAmount
	 */
	@Override
	public double checkOutBook(List<Integer> bookIds, String promoCode) throws Exception {

		if(promoCode != null && !"".equalsIgnoreCase(promoCode) && promoCode.length()>5) {
			return bookRepository.findAllById(bookIds)
					.stream()
					.mapToDouble(prizeCalculator)
					.sum();
		} else {
			return bookRepository.findAllById(bookIds)
					.stream()
					.mapToDouble((book) -> book.getBookPrice())
					.sum();
		}

	}

	/*
	 * This is supporting functional interface implementation for checkOut method
	 * @Param : Book
	 * @Return : Double amount
	 */
	ToDoubleFunction<com.kararoo.bookstore.entity.Book> prizeCalculator = (book) -> {
		if("FICTION".equalsIgnoreCase( book.getBookType())) {
			return book.getBookPrice()*0.90;
		}  else {
			return book.getBookPrice();
		}
	};
}
