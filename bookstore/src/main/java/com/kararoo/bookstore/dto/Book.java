package com.kararoo.bookstore.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class Book {
	private Integer bookId;
	private String bookName;
	private String bookDescription;
	private String bookAuthor;
	@NonNull
	private String bookType;
	private String bookISBN;
	private Double bookPrice;
}
