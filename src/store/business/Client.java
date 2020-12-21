package store.business;

/**
* @authors Thierry KHAMPHOUSONE & Tata Joseph ASSOUMA
*/

import java.util.*;

public class Client {
	
	private String firstName;
	private String lastName;
	private String address;
	private UUID uniqueID; 

	public Client(String firstName, String lastName, String address, UUID uniqueID) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.uniqueID = uniqueID; 
	}

	public String getFirstName() {
		return this.firstName; 
	}

	public String getFirstLastName() {
		return this.firstName+" "+this.lastName; 
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getAddress() {
		return this.address; 
	}

	public UUID getUniqueID() {
		return this.uniqueID;
	}

	public String toString() {
		return firstName + " " + lastName + " " + address + " " + uniqueID.toString(); 
	}

	public boolean equals (Client c) {
		return (firstName.equals(c.firstName) && lastName.equals(c.lastName));
	}

}
