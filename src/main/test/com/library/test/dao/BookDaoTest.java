package com.library.test.dao;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.library.config.HibernateUtil;
import com.library.dao.BookDao;
import com.library.model.Role;
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
		fail("Not yet implemented");
	}
	
	@Test
	public void testAddBook(){
		Book book = new Book("MyFirstBook","MyFirstBookisbn",10);
		this.bookDao.saveOrUpdate(book);
		
		Assert.assertSame("Pass", bookDao.getBookByID(book.getBookid()).getBookName(), book.getBookName());
		System.out.println(book.getBookName());
	}
	
	@Test
	public void testDeleteBook(){
		Book book = bookDao.getBookByName("MyFirstBook");
		bookDao.deleteBook(book);
		Assert.assertNull(bookDao.getBookByID(book.getBookid()));
	}

}
