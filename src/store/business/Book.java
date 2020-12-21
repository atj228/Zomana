package store.business;

/**
* @authors Thierry KHAMPHOUSONE & Tata Joseph ASSOUMA
*/

import java.util.*;

public class Book extends Product {
	
	private String author;
	private int pages;
	private Language language; 

	public Book(String name, double price, int stock, String image, UUID identifier, String author, int pages, Language language) {
		super(name, price, stock, image, identifier);
		this.author = author;
		this.pages = pages;
		this.language = language;
	}

	public String toString() {
		return super.toString() + " " + author + " " + pages + " " + language.getLanguage(); 
	}

	public String getBookAuthor() {
		return this.author;
	}

	public int getBookPages() {
		return this.pages;
	}

	public Language getBookLanguage() {
		return this.language;
	}

}
