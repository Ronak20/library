package com.library.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.library.dao.BookDao;
import com.library.dao.LoanDao;
import com.library.model.Book;

public class BookService {
	private static Logger logger = Logger.getLogger(BookService.class);

	BookDao bookDao;
	LoanDao loanDao;

	public BookService(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	public BookService(BookDao bookDao, LoanDao loanDao) {
		this.bookDao = bookDao;
		this.loanDao = loanDao;
	}

	public String saveOrUpdate(Book book) {
		return bookDao.saveOrUpdate(book);
	}

	public void increaseCopies(String bookid) {
		bookDao.increaseCopies(bookid);
	}

	public void decreaseCopies(String bookid) {
		bookDao.decreaseCopies(bookid);
	}

	public List<Book> getAll() {
		return bookDao.getAll();
	}

	public void deleteBook(Book book) {
		bookDao.deleteBook(book);
	}

	public boolean deleteBook(String bookId) {
		LoanService loanService = new LoanService(this.loanDao);
		if (loanService.OkayToDeleteBook(bookId)) {
			Book book = bookDao.getBookByID(bookId);
			bookDao.deleteBook(book);
			return true;
		} else
			return false;
	}

	public Book getBookByID(String bookid) {
		return bookDao.getBookByID(bookid);
	}

	public List<Book> getAllBookWithCopies() {
		return bookDao.getAllBookWithCopies();
	}

}
