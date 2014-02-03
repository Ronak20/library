package com.library.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.library.model.Book;

public class BookDao {

	private static Logger logger = Logger.getLogger(BookDao.class);
	Session session;

	public BookDao(Session session) {
		this.session = session;
	}

	public String saveOrUpdate(Book book) {
		logger.info("saveOrUpdate : "+book);
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(book);
			tx.commit();
			session.refresh(book);
			return book.getBookid();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return null;
	}
	
	
	public boolean decreaseCopies(String bookid) {
		
		System.out.println("saveOrUpdate : " + bookid.toString());
		
		
		try{
		String hql = "update Book set copies = copies - 1 where bookId = :book_id"; 
				
		Query query = session.createQuery(hql);
		query.setParameter("book_id", bookid);
		query.executeUpdate();
		System.out.println("Copies -1 , updated !");
		return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Copies were not updated!");
			return false;			
			}
	}
	
	public boolean increaseCopies(String bookid) {
		
		System.out.println("saveOrUpdate : " + bookid);
		
		
		try{
		String hql = "update Book set copies = copies + 1 where bookId = :book_id"; 
				
		Query query = session.createQuery(hql);
		query.setParameter("book_id", bookid);
		query.executeUpdate();
		System.out.println("Copies -1 , updated !");
		return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Copies were not updated!");
			return false;			
			}
	}

	public List<Book> getAll() {
		List<Book> bookList = session.createCriteria(Book.class).list();
		return bookList;
	}
	
	public List<Book> getAllBookWithCopies() {
		List<Book> bookList = session.createCriteria(Book.class).add(
                Restrictions.gt("copies", 0)).list();
		return bookList;
	}
	
	public Book getBookByID(String bookid){
		try {
		String hql = "FROM Book B WHERE B.bookid = :book_id";
		Query query = session.createQuery(hql);
		
		query.setParameter("book_id", bookid);
		List<Book> bookList = query.list();
		Book book = bookList.get(0);
		//Book bookInstance = session
		System.out.println(book.toString());
		return book;
		} catch (Exception e) {
			e.printStackTrace();
			return null;			
			}
	}
	
	public Book getBookByName(String bookName){
		try {
		String hql = "FROM Book B WHERE B.bookName = :book_name";
		Query query = session.createQuery(hql);
		
		query.setParameter("book_name", bookName);
		List<Book> bookList = query.list();
		Book book = bookList.get(0);
		//Book bookInstance = session
		System.out.println(book.toString());
		return book;
		} catch (Exception e) {
			e.printStackTrace();
			return null;			
			}
	}
	
	public void deleteBook(Book book){
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(book);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
	}
	 
}
