package com.library.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "book", catalog = "library")
public class Book {

	private String bookid;
	private String bookName;
	private String isbn;
	private int copies;

	public Book() {
		super();
	}

	public Book(String bookid) {
		this.bookid = bookid;
	}

	public Book(String bookid, String bookName, String isbn, int copies) {
		super();
		this.bookid = bookid;
		this.bookName = bookName;
		this.isbn = isbn;
		this.copies = copies;
	}

	public Book(String bookName, String isbn, int copies) {
		super();
		this.bookName = bookName;
		this.isbn = isbn;
		this.copies = copies;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "bookid", unique = true)
	public String getBookid() {
		return bookid;
	}

	public void setBookid(String bookid) {
		this.bookid = bookid;
	}

	@Column(name = "bookname", unique = true)
	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	@Column(name = "isbn", unique = true)
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	@Column(name = "copies")
	public int getCopies() {
		return copies;
	}

	public void setCopies(int copies) {
		this.copies = copies;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bookName == null) ? 0 : bookName.hashCode());
		result = prime * result + ((bookid == null) ? 0 : bookid.hashCode());
		result = prime * result + copies;
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (bookName == null) {
			if (other.bookName != null)
				return false;
		} else if (!bookName.equals(other.bookName))
			return false;
		if (bookid == null) {
			if (other.bookid != null)
				return false;
		} else if (!bookid.equals(other.bookid))
			return false;
		if (copies != other.copies)
			return false;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Book [bookid=" + bookid + ", bookName=" + bookName + ", isbn="
				+ isbn + ", copies=" + copies + "]";
	}
}

/*
 * PLEASE DO NOT EDIT THIS CODE This code was generated using the UMPLE
 * 1.19.0.3385 modeling language!
 * 
 * package com.library.model; import java.util.*;
 * 
 * import javax.persistence.Column; import javax.persistence.Entity; import
 * javax.persistence.FetchType; import javax.persistence.GeneratedValue; import
 * javax.persistence.GenerationType; import javax.persistence.Id; import
 * javax.persistence.ManyToMany; import javax.persistence.ManyToOne; import
 * javax.persistence.Table;
 * 
 * import org.hibernate.metamodel.binding.CascadeType;
 * 
 * // line 8 "../../../../../../ump/tmp369439/model.ump" // line 49
 * "../../../../../../ump/tmp369439/model.ump"
 * 
 * @Entity
 * 
 * @Table(name = "book", catalog = "library") public class Book {
 * 
 * @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
 * public @interface umplesourcefile{int[] line();String[] file();int[]
 * javaline();int[] length();}
 * 
 * //------------------------ // MEMBER VARIABLES //------------------------
 * 
 * //Book Attributes private int copies;
 * 
 * @Id
 * 
 * @GeneratedValue(strategy=GenerationType.AUTO)
 * 
 * @Column(name = "isbn", unique = true) private int isbn; private String name;
 * 
 * //Book Associations
 * 
 * @ManyToMany(targetEntity=Author.class, fetch=FetchType.EAGER) private
 * List<Author> authors;
 * 
 * @ManyToOne (fetch=FetchType.EAGER) private User user;
 * 
 * @ManyToMany(targetEntity=Loan.class, fetch=FetchType.EAGER) private
 * List<Loan> loans;
 * 
 * //------------------------ // CONSTRUCTOR //------------------------
 * 
 * public Book(int aCopies, int aIsbn, String aName, User aUser) { copies =
 * aCopies; isbn = aIsbn; name = aName; authors = new ArrayList<Author>();
 * boolean didAddUser = setUser(aUser); if (!didAddUser) { throw new
 * RuntimeException("Unable to create book due to user"); } loans = new
 * ArrayList<Loan>(); }
 * 
 * public Book(int aCopies, int aIsbn, String aName) { copies = aCopies; isbn =
 * aIsbn; name = aName; }
 * 
 * //------------------------ // INTERFACE //------------------------
 * 
 * public Book(int aCopies, String aBookname) {
 * 
 * copies = aCopies; name = aBookname; // TODO Auto-generated constructor stub }
 * 
 * public boolean setCopies(int aCopies) { boolean wasSet = false; copies =
 * aCopies; wasSet = true; return wasSet; }
 * 
 * public boolean setIsbn(int aIsbn) { boolean wasSet = false; isbn = aIsbn;
 * wasSet = true; return wasSet; }
 * 
 * public boolean setName(String aName) { boolean wasSet = false; name = aName;
 * wasSet = true; return wasSet; }
 * 
 * public int getCopies() { return copies; }
 * 
 * public int getIsbn() { return isbn; }
 * 
 * public String getName() { return name; }
 * 
 * public Author getAuthor(int index) { Author aAuthor = authors.get(index);
 * return aAuthor; }
 * 
 * public List<Author> getAuthors() { List<Author> newAuthors =
 * Collections.unmodifiableList(authors); return newAuthors; }
 * 
 * public int numberOfAuthors() { int number = authors.size(); return number; }
 * 
 * public boolean hasAuthors() { boolean has = authors.size() > 0; return has; }
 * 
 * public int indexOfAuthor(Author aAuthor) { int index =
 * authors.indexOf(aAuthor); return index; }
 * 
 * public User getUser() { return user; }
 * 
 * public Loan getLoan(int index) { Loan aLoan = loans.get(index); return aLoan;
 * }
 * 
 * public List<Loan> getLoans() { List<Loan> newLoans =
 * Collections.unmodifiableList(loans); return newLoans; }
 * 
 * public int numberOfLoans() { int number = loans.size(); return number; }
 * 
 * public boolean hasLoans() { boolean has = loans.size() > 0; return has; }
 * 
 * public int indexOfLoan(Loan aLoan) { int index = loans.indexOf(aLoan); return
 * index; }
 * 
 * public static int minimumNumberOfAuthors() { return 0; }
 * 
 * public boolean addAuthor(Author aAuthor) { boolean wasAdded = false; if
 * (authors.contains(aAuthor)) { return false; } authors.add(aAuthor); if
 * (aAuthor.indexOfBook(this) != -1) { wasAdded = true; } else { wasAdded =
 * aAuthor.addBook(this); if (!wasAdded) { authors.remove(aAuthor); } } return
 * wasAdded; }
 * 
 * public boolean removeAuthor(Author aAuthor) { boolean wasRemoved = false; if
 * (!authors.contains(aAuthor)) { return wasRemoved; }
 * 
 * int oldIndex = authors.indexOf(aAuthor); authors.remove(oldIndex); if
 * (aAuthor.indexOfBook(this) == -1) { wasRemoved = true; } else { wasRemoved =
 * aAuthor.removeBook(this); if (!wasRemoved) { authors.add(oldIndex,aAuthor); }
 * } return wasRemoved; }
 * 
 * public boolean addAuthorAt(Author aAuthor, int index) { boolean wasAdded =
 * false; if(addAuthor(aAuthor)) { if(index < 0 ) { index = 0; } if(index >
 * numberOfAuthors()) { index = numberOfAuthors() - 1; }
 * authors.remove(aAuthor); authors.add(index, aAuthor); wasAdded = true; }
 * return wasAdded; }
 * 
 * public boolean addOrMoveAuthorAt(Author aAuthor, int index) { boolean
 * wasAdded = false; if(authors.contains(aAuthor)) { if(index < 0 ) { index = 0;
 * } if(index > numberOfAuthors()) { index = numberOfAuthors() - 1; }
 * authors.remove(aAuthor); authors.add(index, aAuthor); wasAdded = true; } else
 * { wasAdded = addAuthorAt(aAuthor, index); } return wasAdded; }
 * 
 * public boolean setUser(User aUser) { boolean wasSet = false; if (aUser ==
 * null) { return wasSet; }
 * 
 * User existingUser = user; user = aUser; if (existingUser != null &&
 * !existingUser.equals(aUser)) { existingUser.removeRent(this); }
 * user.addRent(this); wasSet = true; return wasSet; }
 * 
 * public static int minimumNumberOfLoans() { return 0; }
 * 
 * public boolean addLoan(Loan aLoan) { boolean wasAdded = false; if
 * (loans.contains(aLoan)) { return false; } loans.add(aLoan); if
 * (aLoan.indexOfBook(this) != -1) { wasAdded = true; } else { wasAdded =
 * aLoan.addBook(this); if (!wasAdded) { loans.remove(aLoan); } } return
 * wasAdded; }
 * 
 * public boolean removeLoan(Loan aLoan) { boolean wasRemoved = false; if
 * (!loans.contains(aLoan)) { return wasRemoved; }
 * 
 * int oldIndex = loans.indexOf(aLoan); loans.remove(oldIndex); if
 * (aLoan.indexOfBook(this) == -1) { wasRemoved = true; } else { wasRemoved =
 * aLoan.removeBook(this); if (!wasRemoved) { loans.add(oldIndex,aLoan); } }
 * return wasRemoved; }
 * 
 * public boolean addLoanAt(Loan aLoan, int index) { boolean wasAdded = false;
 * if(addLoan(aLoan)) { if(index < 0 ) { index = 0; } if(index >
 * numberOfLoans()) { index = numberOfLoans() - 1; } loans.remove(aLoan);
 * loans.add(index, aLoan); wasAdded = true; } return wasAdded; }
 * 
 * public boolean addOrMoveLoanAt(Loan aLoan, int index) { boolean wasAdded =
 * false; if(loans.contains(aLoan)) { if(index < 0 ) { index = 0; } if(index >
 * numberOfLoans()) { index = numberOfLoans() - 1; } loans.remove(aLoan);
 * loans.add(index, aLoan); wasAdded = true; } else { wasAdded =
 * addLoanAt(aLoan, index); } return wasAdded; }
 * 
 * public void delete() { ArrayList<Author> copyOfAuthors = new
 * ArrayList<Author>(authors); authors.clear(); for(Author aAuthor :
 * copyOfAuthors) { aAuthor.removeBook(this); } User placeholderUser = user;
 * this.user = null; placeholderUser.removeRent(this); ArrayList<Loan>
 * copyOfLoans = new ArrayList<Loan>(loans); loans.clear(); for(Loan aLoan :
 * copyOfLoans) { aLoan.removeBook(this); } }
 * 
 * 
 * public String toString() { String outputString = ""; return super.toString()
 * + "["+ "copies" + ":" + getCopies()+ "," + "isbn" + ":" + getIsbn()+ "," +
 * "name" + ":" + getName()+ "]" +
 * System.getProperties().getProperty("line.separator") + "  " +
 * "user = "+(getUser
 * ()!=null?Integer.toHexString(System.identityHashCode(getUser())):"null") +
 * outputString; } }
 */

