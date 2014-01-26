package com.library.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "user", catalog = "library")
public class User
{
	private String userId;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private Role role;
	
	public User(String userId) {
		super();
		this.userId = userId;
	}
	
	public User(String firstName, String lastName,
			String username, String password, Role role) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	
	public User(){}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "userid", unique = true)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name = "firstname")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name = "lastname")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Column(name = "username")
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name = "password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "role")
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (role != other.role)
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", username=" + username
				+ ", password=" + password + ", role=" + role + "]";
	}
	
	
}

/*PLEASE DO NOT EDIT THIS CODE
This code was generated using the UMPLE 1.19.0.3385 modeling language!

package com.library.model;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


// line 26 "../../../../../../ump/tmp369439/model.ump"
@Entity
@Table(name = "user", catalog = "library")
public class User
{
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
    public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}
    
    //------------------------
    // MEMBER VARIABLES
    //------------------------
    
    //User Associations
    @OneToMany(targetEntity=Book.class, mappedBy="user", fetch=FetchType.EAGER)
    private List<Book> rent;
    @ManyToMany(targetEntity=Loan.class, mappedBy="users", fetch=FetchType.EAGER)
    private List<Loan> loans;
    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", unique = true)
    private String uid;
    private String firstName;
    private String lastName;
    private String role;
    
    
    //------------------------
    // CONSTRUCTOR
    //------------------------
    
    public User(String aFirstName, String aLastName, String aRole)
    {
        setFirstName(aFirstName);
       setLastName(aLastName);
       role = aRole;
        
        rent = new ArrayList<Book>();
        loans = new ArrayList<Loan>();
    }
    
    
    //------------------------
    // INTERFACE
    //------------------------
    
    public Book getRent(int index)
    {
        Book aRent = rent.get(index);
        return aRent;
    }
    
    public List<Book> getRent()
    {
        List<Book> newRent = Collections.unmodifiableList(rent);
        return newRent;
    }
    
    public int numberOfRent()
    {
        int number = rent.size();
        return number;
    }
    
    public boolean hasRent()
    {
        boolean has = rent.size() > 0;
        return has;
    }
    
    public int indexOfRent(Book aRent)
    {
        int index = rent.indexOf(aRent);
        return index;
    }
    
    public Loan getLoan(int index)
    {
        Loan aLoan = loans.get(index);
        return aLoan;
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
    
    public static int minimumNumberOfRent()
    {
        return 0;
    }
    
    public Book addRent(int aCopies, int aIsbn, String aName)
    {
        return new Book(aCopies, aIsbn, aName, this);
    }
    
    public boolean addRent(Book aRent)
    {
        boolean wasAdded = false;
        if (rent.contains(aRent)) { return false; }
        User existingUser = aRent.getUser();
        boolean isNewUser = existingUser != null && !this.equals(existingUser);
        if (isNewUser)
        {
            aRent.setUser(this);
        }
        else
        {
            rent.add(aRent);
        }
        wasAdded = true;
        return wasAdded;
    }
    
    public boolean removeRent(Book aRent)
    {
        boolean wasRemoved = false;
        //Unable to remove aRent, as it must always have a user
        if (!this.equals(aRent.getUser()))
        {
            rent.remove(aRent);
            wasRemoved = true;
        }
        return wasRemoved;
    }
    
    public boolean addRentAt(Book aRent, int index)
    {
        boolean wasAdded = false;
        if(addRent(aRent))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfRent()) { index = numberOfRent() - 1; }
            rent.remove(aRent);
            rent.add(index, aRent);
            wasAdded = true;
        }
        return wasAdded;
    }
    
    public boolean addOrMoveRentAt(Book aRent, int index)
    {
        boolean wasAdded = false;
        if(rent.contains(aRent))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfRent()) { index = numberOfRent() - 1; }
            rent.remove(aRent);
            rent.add(index, aRent);
            wasAdded = true;
        }
        else
        {
            wasAdded = addRentAt(aRent, index);
        }
        return wasAdded;
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
        if (aLoan.indexOfUser(this) != -1)
        {
            wasAdded = true;
        }
        else
        {
            wasAdded = aLoan.addUser(this);
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
        if (aLoan.indexOfUser(this) == -1)
        {
            wasRemoved = true;
        }
        else
        {
            wasRemoved = aLoan.removeUser(this);
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
        for(int i=rent.size(); i > 0; i--)
        {
            Book aRent = rent.get(i - 1);
            aRent.delete();
        }
        ArrayList<Loan> copyOfLoans = new ArrayList<Loan>(loans);
        loans.clear();
        for(Loan aLoan : copyOfLoans)
        {
            aLoan.removeUser(this);
        }
        delete();
    }


	public String getUid() {
		return uid;
	}


	public void setUid(String uid) {
		this.uid = uid;
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


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


    
}*/
