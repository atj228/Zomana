package store.test;

import static org.junit.Assert.*;

import org.junit.Test;
import store.business.*;
import junit.framework.*;
import java.util.*;
import java.text.*;
import store.business.Transaction;

import java.sql.Timestamp;
import javax.xml.parsers.*;

/**
* @authors Thierry KHAMPHOUSONE & Tata Joseph ASSOUMA
*/ 

public class StoreTest extends TestCase {

	Store s1 = new Store();
	Store s2 = new Store();
	Store s3 = new Store();

	private UUID t1u1 = UUID.randomUUID();
	private UUID t1u2 = UUID.randomUUID();
	private UUID t2u1 = UUID.randomUUID();
	private UUID t2u2 = UUID.randomUUID();

	Date parseDate1 = new Date(System.currentTimeMillis());
	Timestamp time1 = new Timestamp(parseDate1.getTime());
	Date parseDate2 = new Date(System.currentTimeMillis());
	Timestamp time2 = new Timestamp(parseDate2.getTime());

	private UUID identifier1 = UUID.randomUUID();
	Client c1 = new Client("clientName1", "clientLastName1", "clientAddress1", identifier1);
	private UUID identifier2 = UUID.randomUUID();
	Client c2 = new Client("clientName2", "clientLastName2", "clientAddress2", identifier2);
	private UUID identifier3 = UUID.randomUUID();
	Client c3 = new Client("clientName3", "clientLastName3", "clientAddress3", identifier3);


	private UUID identifier4 = UUID.randomUUID();
	DVD dvd1 = new DVD("dvdTest1", 2.0, 1, "imagedvdTest1", identifier4, 10, Genre.ACTION);
	private UUID identifier5 = UUID.randomUUID();
	DVD dvd2 = new DVD("dvdTest2", 4.0, 2, "imagedvdTest2", identifier5, 20, Genre.HORROR);
	
	private UUID identifier6 = UUID.randomUUID();
	Book book1 = new Book("nameTest1", 1.0, 1, "imageTest1", identifier6, "authorTest1", 1, Language.FRANCAIS);
	private UUID identifier7 = UUID.randomUUID();
	Book book2 = new Book("nameTest2", 2.0, 2, "imageTest2", identifier7, "authorTest2", 1, Language.ANGLAIS);

	private UUID identifier8 = UUID.randomUUID();
	Game game1 = new Game("gameTest1", 1.0, 1, "imageGame1", identifier8, GameGenre.ARCADE, Platform.NINTENDO);
	private UUID identifier9 = UUID.randomUUID();
	Game game2 = new Game("gameTest1", 4.0, 4, "imageGame2", identifier9, GameGenre.MJ, Platform.PLAYSTATION);
	
	Transaction t1 = new Transaction(t1u1, t1u2, 1, time1);
	Transaction t2 = new Transaction(t2u1, t2u2, 2, time2);

	public void testStore() {
		assertNotSame(s1, s2);
	}

	public void testAddProduct() {
		s1.addProduct(dvd1);
		assertEquals("Size incorrect", 1, s1.getProductList().size());
		s1.addProduct(dvd2);
		assertEquals("Size incorrect", 2, s1.getProductList().size());
		
	}

	public void testAddClient() {
		s1.addClient(c1);
		assertEquals("Size incorrect", 1, s1.getClientList().size());
		s1.addClient(c3);
		assertEquals("Size incorrect", 2, s1.getClientList().size());
	}

	public void testSearchClientExist() {
		s1.addClient(c3);
		boolean exist = s1.searchClientExist(c3);
		assertEquals("searchClientExist incorrect", true, exist);
	}

	public void testAddTransaction() {
		s1.addTransaction(t1);
		assertEquals("Size incorrect", 1, s1.getTransactionList().size());
		s1.addTransaction(t2);
		assertEquals("Size incorrect", 2, s1.getTransactionList().size());
	}

	public void testAddCategory() {
		s1.addCategory(dvd1, "DVD");
		assertEquals("Size incorrect", 1, s1.getDvdList().size());
		s1.addCategory(dvd2, "DVD");
		assertEquals("Size incorrect", 2, s1.getDvdList().size());
		s1.addCategory(book1, "Book");
		assertEquals("Size incorrect", 1, s1.getBookList().size());
		s1.addCategory(book2, "Book");
		assertEquals("Size incorrect", 2, s1.getBookList().size());
		s1.addCategory(game1, "Game");
		assertEquals("Size incorrect", 1, s1.getGameList().size());
		s1.addCategory(game2, "Game");
		assertEquals("Size incorrect", 2, s1.getGameList().size());
		
	}

	public void testGetProductList() {
		s2.addProduct(book1);
		assertEquals("Size incorrect", 1, s2.getProductList().size());
		s2.addProduct(book2);
		assertEquals("Size incorrect", 2, s2.getProductList().size());
	}

	public void testGetBookList() {
		s2.addProduct(book1);
		assertEquals("Size incorrect", 1, s2.getBookList().size());
		s2.addProduct(book2);
		assertEquals("Size incorrect", 2, s2.getBookList().size());
	}

	public void testGetDvdList() {
		s2.addProduct(dvd1);
		assertEquals("Size incorrect", 1, s2.getDvdList().size());
		s2.addProduct(dvd2);
		assertEquals("Size incorrect", 2, s2.getDvdList().size());
	}

	public void testGetGameList() {
		s2.addProduct(game1);
		assertEquals("Size incorrect", 1, s2.getGameList().size());
		s2.addProduct(game2);
		assertEquals("Size incorrect", 2, s2.getGameList().size());
	}

	public void testGetClientList() {
		s2.addClient(c1);
		assertEquals("Size incorrect", 1, s2.getClientList().size());
		s2.addClient(c2);
		assertEquals("Size incorrect", 2, s2.getClientList().size());
	}

	public void testGetPartialProductList() {
		s3.addProduct(book1);
		assertEquals("Size incorrect", 1, s3.getPartialProductList(1).size());
		s3.addProduct(book2);
		assertEquals("Size incorrect", 3, s3.getPartialProductList(2).size());
	}
}
