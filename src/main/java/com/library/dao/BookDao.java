package com.library.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.library.model.Book;

public class BookDao {

	Session session;

	public BookDao(Session session) {
		this.session = session;
	}

	public void saveOrUpdate(Book book) {
		System.out.println("saveOrUpdate : " + book.toString());
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(book);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
	}

	public List<Book> getAll() {
		List<Book> bookList = session.createCriteria(Book.class).list();
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
