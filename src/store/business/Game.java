package store.business;

import java.util.*;

/**
* @authors Thierry KHAMPHOUSONE & Tata Joseph ASSOUMA
*/

public class Game extends Product {
	
	private GameGenre gameGenre;
	private Platform platform; 

	public Game(String name, double price, int stock, String image, UUID identifier, GameGenre gameGenre, Platform platform) {
		super(name, price, stock, image, identifier);
		this.gameGenre = gameGenre;
		this.platform = platform;
	}

	public String toString() {
		return super.toString() + " " + gameGenre + " " + platform; 
	}

	public GameGenre getGameGenre() {
		return this.gameGenre;
	}

	public Platform getPlatform() {
		return this.platform;
	}	

}
