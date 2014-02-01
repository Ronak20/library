package com.library.service;

import java.util.List;

import com.library.dao.BookDao;
import com.library.model.Book;

public class BookService {
	
	BookDao bookDao;
	
	public BookService(BookDao bookDao)
	{
		this.bookDao = bookDao;
	}
	
	public void saveOrUpdate(Book book) {
		bookDao.saveOrUpdate(book);
	}
	
	public List<Book> getAll() {
		return bookDao.getAll();
	}
	
	public void deleteBook(Book book){
		bookDao.deleteBook(book);
	}
	
	public Book getBookByID(String bookid){
		return bookDao.getBookByID(bookid);
	}

}
