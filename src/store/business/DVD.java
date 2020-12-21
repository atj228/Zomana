package store.business;

/**
* @authors Thierry KHAMPHOUSONE & Tata Joseph ASSOUMA
*/

import java.util.*; 

public class DVD extends Product {
	
	private Vector <String> actors; 
	private int length;
	private Genre genre; 

	public DVD(String name, double price, int stock, String image, UUID identifier, int length, Genre genre) {
		super(name, price, stock, image, identifier);
		this.actors = new Vector <String>();
		this.length = length;
		this.genre = genre; 
	}

	public int getLength() {
		return this.length;
	}

	//the actors are then added in the actor vector by this method
	public Vector <String> addActors(String actorsName) {
		this.actors.add(actorsName);
		return this.actors;
	}

	public Vector<String> getVectorActors() {
		return this.actors;
	}

	public String getActors(int index) {
		return this.actors.get(index);
	}

	public Genre getGenre() {
		return this.genre;
	}

	public String toString() {
		return super.toString() + " " + actors + " " + length + " " + genre; 
	}
}