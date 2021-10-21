package com.kararoo.bookstore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer bookId;
    @Column
	private String bookName;
    @Column
	private String bookDescription;
    @Column
    private String bookAuthor;
    @Column
    private String bookType;
    @Column
    private String bookISBN;
    @Column
    private Double bookPrice;
}
