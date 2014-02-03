package com.library.test.dao;

import java.util.UUID;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.library.config.HibernateUtil;
import com.library.dao.BookDao;
import com.library.model.Book;

public class BookDaoTest {
	
	BookDao bookDao;
	Session session; 

	@Before
	public void setUp() throws Exception {
		this.session = HibernateUtil.getSessionFactory().openSession();
		this.bookDao = new BookDao(this.session);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Book book = null;
		Assert.assertNull(book);
	}
	
	@Test
	public void testAddBook(){
		Book book = new Book("MyFirstBook3","MyFirstBookisbn3",0);
		this.bookDao.saveOrUpdate(book);
		
		Assert.assertSame(bookDao.getBookByName(book.getBookName()).getBookName(), book.getBookName());
		System.out.println(book.getBookName());
		//bookDao.deleteBook(book);
	}
	
	@Test
	public void testDeleteBook(){
		Book book = new Book("MyFirstBook10","MyFirstBookisbn10",10);
		bookDao.saveOrUpdate(book);
		book = bookDao.getBookByName("MyFirstBook10");
		bookDao.deleteBook(book);
		Assert.assertNull(bookDao.getBookByName(book.getBookName()));
		
		//Book book1 = new Book("1");
		//bookDao.deleteBook(book1);
		
	}

}
