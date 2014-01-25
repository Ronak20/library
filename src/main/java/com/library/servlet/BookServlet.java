package com.library.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;




import com.library.config.HibernateUtil;
import com.library.dao.BookDao;
import com.library.dao.UserDao;
import com.library.model.Book;
import com.library.service.BookService;
import com.library.service.UserService;

/**
 * Servlet implementation class BookServlet
 */
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public BookServlet() {
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    System.out.println("Get received");
		//response.sendRedirect("jsp/createUser.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    System.out.println("Post received");
		String copies = request.getParameter("copies");
	    String isbn = request.getParameter("isbn");
	    String bookname = request.getParameter("bookname");
	    
	    
	    Book book = new Book(Integer.parseInt(copies),bookname);
	    //System.out.print(copies); //testing conversion
	    Session session = HibernateUtil.getSessionFactory().openSession();
	    BookDao bookDao = new BookDao(session);
	    BookService bookService = new BookService(bookDao);
	    //userDao.saveOrUpdate(user);
	    bookService.saveOrUpdate(book);
	    session.close();
	    System.out.println("Book added");
	}

}
