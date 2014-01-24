/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.19.0.3385 modeling language!*/

package com.library.model;
import java.util.*;

// line 8 "../../../../../../ump/tmp369439/model.ump"
// line 49 "../../../../../../ump/tmp369439/model.ump"
public class Book
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Book Attributes
  private int copies;
  private String isbn;
  private String name;

  //Book Associations
  private List<Author> authors;
  private User user;
  private List<Loan> loans;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Book(int aCopies, String aIsbn, String aName, User aUser)
  {
    copies = aCopies;
    isbn = aIsbn;
    name = aName;
    authors = new ArrayList<Author>();
    boolean didAddUser = setUser(aUser);
    if (!didAddUser)
    {
      throw new RuntimeException("Unable to create rent due to user");
    }
    loans = new ArrayList<Loan>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCopies(int aCopies)
  {
    boolean wasSet = false;
    copies = aCopies;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsbn(String aIsbn)
  {
    boolean wasSet = false;
    isbn = aIsbn;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public int getCopies()
  {
    return copies;
  }

  public String getIsbn()
  {
    return isbn;
  }

  public String getName()
  {
    return name;
  }

  public Author getAuthor(int index)
  {
    Author aAuthor = authors.get(index);
    return aAuthor;
  }

  public List<Author> getAuthors()
  {
    List<Author> newAuthors = Collections.unmodifiableList(authors);
    return newAuthors;
  }

  public int numberOfAuthors()
  {
    int number = authors.size();
    return number;
  }

  public boolean hasAuthors()
  {
    boolean has = authors.size() > 0;
    return has;
  }

  public int indexOfAuthor(Author aAuthor)
  {
    int index = authors.indexOf(aAuthor);
    return index;
  }

  public User getUser()
  {
    return user;
  }

  public Loan getLoan(int index)
  {
    Loan aLoan = loans.get(index);
    return aLoan;
  }

  public List<Loan> getLoans()
  {
    List<Loan> newLoans = Collections.unmodifiableList(loans);
    return newLoans;
  }

  public int numberOfLoans()
  {
    int number = loans.size();
    return number;
  }

  public boolean hasLoans()
  {
    boolean has = loans.size() > 0;
    return has;
  }

  public int indexOfLoan(Loan aLoan)
  {
    int index = loans.indexOf(aLoan);
    return index;
  }

  public static int minimumNumberOfAuthors()
  {
    return 0;
  }

  public boolean addAuthor(Author aAuthor)
  {
    boolean wasAdded = false;
    if (authors.contains(aAuthor)) { return false; }
    authors.add(aAuthor);
    if (aAuthor.indexOfBook(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aAuthor.addBook(this);
      if (!wasAdded)
      {
        authors.remove(aAuthor);
      }
    }
    return wasAdded;
  }

  public boolean removeAuthor(Author aAuthor)
  {
    boolean wasRemoved = false;
    if (!authors.contains(aAuthor))
    {
      return wasRemoved;
    }

    int oldIndex = authors.indexOf(aAuthor);
    authors.remove(oldIndex);
    if (aAuthor.indexOfBook(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aAuthor.removeBook(this);
      if (!wasRemoved)
      {
        authors.add(oldIndex,aAuthor);
      }
    }
    return wasRemoved;
  }

  public boolean addAuthorAt(Author aAuthor, int index)
  {  
    boolean wasAdded = false;
    if(addAuthor(aAuthor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAuthors()) { index = numberOfAuthors() - 1; }
      authors.remove(aAuthor);
      authors.add(index, aAuthor);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAuthorAt(Author aAuthor, int index)
  {
    boolean wasAdded = false;
    if(authors.contains(aAuthor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAuthors()) { index = numberOfAuthors() - 1; }
      authors.remove(aAuthor);
      authors.add(index, aAuthor);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAuthorAt(aAuthor, index);
    }
    return wasAdded;
  }

  public boolean setUser(User aUser)
  {
    boolean wasSet = false;
    if (aUser == null)
    {
      return wasSet;
    }

    User existingUser = user;
    user = aUser;
    if (existingUser != null && !existingUser.equals(aUser))
    {
      existingUser.removeRent(this);
    }
    user.addRent(this);
    wasSet = true;
    return wasSet;
  }

  public static int minimumNumberOfLoans()
  {
    return 0;
  }

  public boolean addLoan(Loan aLoan)
  {
    boolean wasAdded = false;
    if (loans.contains(aLoan)) { return false; }
    loans.add(aLoan);
    if (aLoan.indexOfBook(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aLoan.addBook(this);
      if (!wasAdded)
      {
        loans.remove(aLoan);
      }
    }
    return wasAdded;
  }

  public boolean removeLoan(Loan aLoan)
  {
    boolean wasRemoved = false;
    if (!loans.contains(aLoan))
    {
      return wasRemoved;
    }

    int oldIndex = loans.indexOf(aLoan);
    loans.remove(oldIndex);
    if (aLoan.indexOfBook(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aLoan.removeBook(this);
      if (!wasRemoved)
      {
        loans.add(oldIndex,aLoan);
      }
    }
    return wasRemoved;
  }

  public boolean addLoanAt(Loan aLoan, int index)
  {  
    boolean wasAdded = false;
    if(addLoan(aLoan))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLoans()) { index = numberOfLoans() - 1; }
      loans.remove(aLoan);
      loans.add(index, aLoan);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLoanAt(Loan aLoan, int index)
  {
    boolean wasAdded = false;
    if(loans.contains(aLoan))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLoans()) { index = numberOfLoans() - 1; }
      loans.remove(aLoan);
      loans.add(index, aLoan);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLoanAt(aLoan, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ArrayList<Author> copyOfAuthors = new ArrayList<Author>(authors);
    authors.clear();
    for(Author aAuthor : copyOfAuthors)
    {
      aAuthor.removeBook(this);
    }
    User placeholderUser = user;
    this.user = null;
    placeholderUser.removeRent(this);
    ArrayList<Loan> copyOfLoans = new ArrayList<Loan>(loans);
    loans.clear();
    for(Loan aLoan : copyOfLoans)
    {
      aLoan.removeBook(this);
    }
  }


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "copies" + ":" + getCopies()+ "," +
            "isbn" + ":" + getIsbn()+ "," +
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "user = "+(getUser()!=null?Integer.toHexString(System.identityHashCode(getUser())):"null")
     + outputString;
  }
}