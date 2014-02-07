package com.library.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "loan", catalog = "library")
public class Loan {
	private String loanId;
	private String userId;
	private String bookId;
	private Date expiryDate;
	private int renewalCount;
	private int maxRenewalCount;
	private int loanTime;
	private int lateFee;
	private boolean isLateFeePaid;

	public Loan(String loanId) {
		super();
		this.loanId = loanId;
	}
	public Loan(){}

	public Loan(String userId, String bookId) {
		super();
		this.userId = userId;
		this.bookId = bookId;
	}

	public Loan(String userId, String bookId, Date expiryDate,
			int renewalCount, int lateFee, boolean isLateFeePaid) {
		super();
		this.userId = userId;
		this.bookId = bookId;
		this.expiryDate = expiryDate;
		this.renewalCount = renewalCount;
		this.lateFee = lateFee;
		this.isLateFeePaid = isLateFeePaid;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "loanid", unique = true)
	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	@Column(name = "userid")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "bookid")
	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	@Column(name = "expirydate")
	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Column(name = "renewalcount")
	public int getRenewalCount() {
		return renewalCount;
	}

	public void setRenewalCount(int renewalCount) {
		this.renewalCount = renewalCount;
	}

	@Column(name = "latefee")
	public int getLateFee() {
		return lateFee;
	}

	public void setLateFee(int lateFee) {
		this.lateFee = lateFee;
	}

	@Column(name = "islatefeepaid")
	public boolean getIsLateFeePaid() {
		return isLateFeePaid;
	}

	public void setIsLateFeePaid(boolean isLateFeePaid) {
		this.isLateFeePaid = isLateFeePaid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookId == null) ? 0 : bookId.hashCode());
		result = prime * result
				+ ((expiryDate == null) ? 0 : expiryDate.hashCode());
		result = prime * result + (isLateFeePaid ? 1231 : 1237);
		result = prime * result + lateFee;
		result = prime * result + ((loanId == null) ? 0 : loanId.hashCode());
		result = prime * result + renewalCount;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		Loan other = (Loan) obj;
		if (bookId == null) {
			if (other.bookId != null)
				return false;
		} else if (!bookId.equals(other.bookId))
			return false;
		if (expiryDate == null) {
			if (other.expiryDate != null)
				return false;
		} else if (!expiryDate.equals(other.expiryDate))
			return false;
		if (isLateFeePaid != other.isLateFeePaid)
			return false;
		if (lateFee != other.lateFee)
			return false;
		if (loanId == null) {
			if (other.loanId != null)
				return false;
		} else if (!loanId.equals(other.loanId))
			return false;
		if (renewalCount != other.renewalCount)
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Loan [loanId=" + loanId + ", userId=" + userId + ", bookId="
				+ bookId + ", expiryDate=" + expiryDate + ", renewalCount="
				+ renewalCount + ", lateFee=" + lateFee + ", isLateFeePaid="
				+ isLateFeePaid + "]";
	}
	@Column(name = "maxrenewalcount")
	public int getMaxRenewalCount() {
		return maxRenewalCount;
	}
	public void setMaxRenewalCount(int maxRenewalCount) {
		this.maxRenewalCount = maxRenewalCount;
	}
	@Column(name = "loantime")
	public int getLoanTime() {
		return loanTime;
	}
	public void setLoanTime(int loanTime) {
		this.loanTime = loanTime;
	}

}

/*
 * PLEASE DO NOT EDIT THIS CODE This code was generated using the UMPLE
 * 1.19.0.3385 modeling language!
 * 
 * package com.library.model; import java.sql.Time; import java.util.*;
 * 
 * import javax.persistence.Column; import javax.persistence.Entity; import
 * javax.persistence.FetchType; import javax.persistence.GeneratedValue; import
 * javax.persistence.GenerationType; import javax.persistence.Id; import
 * javax.persistence.ManyToMany; import javax.persistence.OneToMany; import
 * javax.persistence.Table;
 * 
 * // line 31 "../../../../../../ump/tmp369439/model.ump"
 * 
 * @Entity
 * 
 * @Table(name = "loan", catalog = "library") public class Loan {
 * 
 * @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
 * public @interface umplesourcefile{int[] line();String[] file();int[]
 * javaline();int[] length();}
 * 
 * //------------------------ // MEMBER VARIABLES //------------------------
 * 
 * //Loan Attributes
 * 
 * @Id
 * 
 * @GeneratedValue(strategy=GenerationType.AUTO)
 * 
 * @Column(name = "idloan", unique = true) private String id; private Time due;
 * 
 * //Loan Associations
 * 
 * @ManyToMany(targetEntity=User.class, fetch=FetchType.EAGER) private
 * List<User> users;
 * 
 * @ManyToMany(targetEntity=Book.class, mappedBy="loans", fetch=FetchType.EAGER)
 * private List<Book> books;
 * 
 * //------------------------ // CONSTRUCTOR //------------------------
 * 
 * public Loan(String aId, Time aDue) { id = aId; due = aDue; users = new
 * ArrayList<User>(); books = new ArrayList<Book>(); }
 * 
 * //------------------------ // INTERFACE //------------------------
 * 
 * public boolean setId(String aId) { boolean wasSet = false; id = aId; wasSet =
 * true; return wasSet; }
 * 
 * public boolean setDue(Time aDue) { boolean wasSet = false; due = aDue; wasSet
 * = true; return wasSet; }
 * 
 * public String getId() { return id; }
 * 
 * public Time getDue() { return due; }
 * 
 * public User getUser(int index) { User aUser = users.get(index); return aUser;
 * }
 * 
 * public List<User> getUsers() { List<User> newUsers =
 * Collections.unmodifiableList(users); return newUsers; }
 * 
 * public int numberOfUsers() { int number = users.size(); return number; }
 * 
 * public boolean hasUsers() { boolean has = users.size() > 0; return has; }
 * 
 * public int indexOfUser(User aUser) { int index = users.indexOf(aUser); return
 * index; }
 * 
 * public Book getBook(int index) { Book aBook = books.get(index); return aBook;
 * }
 * 
 * public List<Book> getBooks() { List<Book> newBooks =
 * Collections.unmodifiableList(books); return newBooks; }
 * 
 * public int numberOfBooks() { int number = books.size(); return number; }
 * 
 * public boolean hasBooks() { boolean has = books.size() > 0; return has; }
 * 
 * public int indexOfBook(Book aBook) { int index = books.indexOf(aBook); return
 * index; }
 * 
 * public static int minimumNumberOfUsers() { return 0; }
 * 
 * public boolean addUser(User aUser) { boolean wasAdded = false; if
 * (users.contains(aUser)) { return false; } users.add(aUser); if
 * (aUser.indexOfLoan(this) != -1) { wasAdded = true; } else { wasAdded =
 * aUser.addLoan(this); if (!wasAdded) { users.remove(aUser); } } return
 * wasAdded; }
 * 
 * public boolean removeUser(User aUser) { boolean wasRemoved = false; if
 * (!users.contains(aUser)) { return wasRemoved; }
 * 
 * int oldIndex = users.indexOf(aUser); users.remove(oldIndex); if
 * (aUser.indexOfLoan(this) == -1) { wasRemoved = true; } else { wasRemoved =
 * aUser.removeLoan(this); if (!wasRemoved) { users.add(oldIndex,aUser); } }
 * return wasRemoved; }
 * 
 * public boolean addUserAt(User aUser, int index) { boolean wasAdded = false;
 * if(addUser(aUser)) { if(index < 0 ) { index = 0; } if(index >
 * numberOfUsers()) { index = numberOfUsers() - 1; } users.remove(aUser);
 * users.add(index, aUser); wasAdded = true; } return wasAdded; }
 * 
 * public boolean addOrMoveUserAt(User aUser, int index) { boolean wasAdded =
 * false; if(users.contains(aUser)) { if(index < 0 ) { index = 0; } if(index >
 * numberOfUsers()) { index = numberOfUsers() - 1; } users.remove(aUser);
 * users.add(index, aUser); wasAdded = true; } else { wasAdded =
 * addUserAt(aUser, index); } return wasAdded; }
 * 
 * public static int minimumNumberOfBooks() { return 0; }
 * 
 * public boolean addBook(Book aBook) { boolean wasAdded = false; if
 * (books.contains(aBook)) { return false; } books.add(aBook); if
 * (aBook.indexOfLoan(this) != -1) { wasAdded = true; } else { wasAdded =
 * aBook.addLoan(this); if (!wasAdded) { books.remove(aBook); } } return
 * wasAdded; }
 * 
 * public boolean removeBook(Book aBook) { boolean wasRemoved = false; if
 * (!books.contains(aBook)) { return wasRemoved; }
 * 
 * int oldIndex = books.indexOf(aBook); books.remove(oldIndex); if
 * (aBook.indexOfLoan(this) == -1) { wasRemoved = true; } else { wasRemoved =
 * aBook.removeLoan(this); if (!wasRemoved) { books.add(oldIndex,aBook); } }
 * return wasRemoved; }
 * 
 * public boolean addBookAt(Book aBook, int index) { boolean wasAdded = false;
 * if(addBook(aBook)) { if(index < 0 ) { index = 0; } if(index >
 * numberOfBooks()) { index = numberOfBooks() - 1; } books.remove(aBook);
 * books.add(index, aBook); wasAdded = true; } return wasAdded; }
 * 
 * public boolean addOrMoveBookAt(Book aBook, int index) { boolean wasAdded =
 * false; if(books.contains(aBook)) { if(index < 0 ) { index = 0; } if(index >
 * numberOfBooks()) { index = numberOfBooks() - 1; } books.remove(aBook);
 * books.add(index, aBook); wasAdded = true; } else { wasAdded =
 * addBookAt(aBook, index); } return wasAdded; }
 * 
 * public void delete() { ArrayList<User> copyOfUsers = new
 * ArrayList<User>(users); users.clear(); for(User aUser : copyOfUsers) {
 * aUser.removeLoan(this); } ArrayList<Book> copyOfBooks = new
 * ArrayList<Book>(books); books.clear(); for(Book aBook : copyOfBooks) {
 * aBook.removeLoan(this); } }
 * 
 * 
 * public String toString() { String outputString = ""; return super.toString()
 * + "["+ "id" + ":" + getId()+ "]" +
 * System.getProperties().getProperty("line.separator") + "  " + "due" + "=" +
 * (getDue() != null ? !getDue().equals(this) ?
 * getDue().toString().replaceAll("  ","    ") : "this" : "null") +
 * outputString; } }
 */

