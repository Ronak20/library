package com.library.service;

import com.library.dao.BookDao;
import com.library.dao.UserDao;
import com.library.model.Book;
import com.library.model.User;

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
