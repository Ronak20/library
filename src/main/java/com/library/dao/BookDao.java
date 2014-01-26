package com.library.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.library.model.Book;
import com.library.model.User;

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

	public List<User> getAll() {
		List<User> userList = session.createCriteria(User.class).list();
		return userList;
	}
}
