package com.library.service;

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

}
