package com.library.service;

import java.util.List;

import com.library.dao.BookDao;
import com.library.model.Book;
import com.library.model.User;
import com.library.dao.LoanDao;
import com.library.model.Loan;

public class BookService {
	
	BookDao bookDao;
	
	public BookService(BookDao bookDao)
	{
		this.bookDao = bookDao;
	}
	
	public void saveOrUpdate(Book book) {
		bookDao.saveOrUpdate(book);
	}
	
	public void increaseCopies (String bookid)
	{
		bookDao.increaseCopies(bookid);
	}
	public void decreaseCopies (String bookid)
	{
		bookDao.decreaseCopies(bookid);
	}
	
	
	public List<Book> getAll() {
		return bookDao.getAll();
	}
	
	public void deleteBook(Book book){
		bookDao.deleteBook(book);
	}
	
	public boolean deleteBook(String bookId,LoanDao loanDao){
		LoanService loanService = new LoanService(loanDao);
		if (loanService.OkayToDeleteBook(bookId)) {
			Book book = bookDao.getBookByID(bookId);
			bookDao.deleteBook(book);
			return true;
		} else
			return false;
	}
	
	public Book getBookByID(String bookid){
		return bookDao.getBookByID(bookid);
	}

}
