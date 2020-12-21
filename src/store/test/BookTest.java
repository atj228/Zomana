package store.test;

import store.business.*;
import junit.framework.*;
import java.util.*;

/**
* @authors Thierry KHAMPHOUSONE & Tata Joseph ASSOUMA
*/ 

public class BookTest extends TestCase {
	
	private UUID identifier1 = UUID.randomUUID();
	private UUID identifier2 = UUID.randomUUID();
	Book book1 = new Book("nameTest1", 1.0, 1, "imageTest1", identifier1, "authorTest1", 1, Language.FRANCAIS);
	Book book2 = new Book("nameTest2", 2.0, 2, "imageTest2", identifier2, "authorTest2", 1, Language.ANGLAIS);


	public void testBook() {
		
		assertNotSame(book1, book2);
	}


	public void testGetBookAuthor() {
		assertEquals("The name author is incorrect", "authorTest1", book1.getBookAuthor());
	}

	
	public void testGetBookPages() {
		assertEquals("The page is incorrect", 1, book1.getBookPages());
	}

	
	public void testGetBookLanguage() {
		assertEquals("The language is incorrect", Language.FRANCAIS, book1.getBookLanguage());
	}

}
