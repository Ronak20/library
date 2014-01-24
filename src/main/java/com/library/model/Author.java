/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.19.0.3385 modeling language!*/

package com.library.model;
import java.util.*;

// line 19 "../../../../../../ump/tmp369439/model.ump"
public class Author
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  private String firstName;
  private String lastName;
  private String aid;
  //Author Associations
  private List<Book> books;
  

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Author(String aFirstName, String aLastName, String aAid)
  {
    firstName = aFirstName;
    lastName = aLastName;
    aid = aAid;
    books = new ArrayList<Book>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Book getBook(int index)
  {
    Book aBook = books.get(index);
    return aBook;
  }

  public List<Book> getBooks()
  {
    List<Book> newBooks = Collections.unmodifiableList(books);
    return newBooks;
  }

  public int numberOfBooks()
  {
    int number = books.size();
    return number;
  }

  public boolean hasBooks()
  {
    boolean has = books.size() > 0;
    return has;
  }

  public int indexOfBook(Book aBook)
  {
    int index = books.indexOf(aBook);
    return index;
  }

  public static int minimumNumberOfBooks()
  {
    return 0;
  }

  public boolean addBook(Book aBook)
  {
    boolean wasAdded = false;
    if (books.contains(aBook)) { return false; }
    books.add(aBook);
    if (aBook.indexOfAuthor(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aBook.addAuthor(this);
      if (!wasAdded)
      {
        books.remove(aBook);
      }
    }
    return wasAdded;
  }

  public boolean removeBook(Book aBook)
  {
    boolean wasRemoved = false;
    if (!books.contains(aBook))
    {
      return wasRemoved;
    }

    int oldIndex = books.indexOf(aBook);
    books.remove(oldIndex);
    if (aBook.indexOfAuthor(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aBook.removeAuthor(this);
      if (!wasRemoved)
      {
        books.add(oldIndex,aBook);
      }
    }
    return wasRemoved;
  }

  public boolean addBookAt(Book aBook, int index)
  {  
    boolean wasAdded = false;
    if(addBook(aBook))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBooks()) { index = numberOfBooks() - 1; }
      books.remove(aBook);
      books.add(index, aBook);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBookAt(Book aBook, int index)
  {
    boolean wasAdded = false;
    if(books.contains(aBook))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBooks()) { index = numberOfBooks() - 1; }
      books.remove(aBook);
      books.add(index, aBook);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBookAt(aBook, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ArrayList<Book> copyOfBooks = new ArrayList<Book>(books);
    books.clear();
    for(Book aBook : copyOfBooks)
    {
      aBook.removeAuthor(this);
    }
    delete();
  }

public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public String getAid() {
	return aid;
}

public void setAid(String aid) {
	this.aid = aid;
}

}