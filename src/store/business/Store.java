package store.business;

import java.util.*;
import java.util.logging.*;
import java.text.*;
import java.sql.Timestamp;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;

import org.w3c.dom.*;
import java.io.IOException;
import java.io.File;

/**
* @authors Thierry KHAMPHOUSONE & Tata Joseph ASSOUMA
*/

public class Store {
	
	private List<Product> productList; //list of products available in the shop
	private List<Client> clientList; //list of customers in the store database
	private List<Transaction> transactionList; //list of shop transactions
	private HashMap<String, List<Product>> categories;
	private List<Product> bookList;
	private List<Product> dvdList;
	private List<Product> gameList;

	private LinkedList<Product> partialList;

	private TransformerFactory transformerFactory;
	private Transformer transformer;
	private DocumentBuilderFactory documentFactory;
	private DocumentBuilder documentBuilder;

	private Book book;
	private DVD dvd;
	private Game game;
	private GameGenre gameGenre;
	private Platform platform;
	private Genre genre;
	private Language language;

	private Client client;
	private Client client1;

	private Transaction transaction;

	private Client currentLogin;
	private Timestamp timeStamp; 
	private UUID clientID;

/**
* The names of the XML files (Product,Customer,Transactions)
*/

	private static String XML_PRODUCT_FILE = "./files/produit.xml";
	private static String XML_CLIENT_FILE = "./files/clients.xml";
	private static String XML_TRANSACTION_FILE = "./files/transactions.xml";

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	public Store() {

		this.productList = new LinkedList<Product>();
		this.clientList = new LinkedList<Client>();
		this.transactionList = new LinkedList<Transaction>();

		this.categories = new HashMap<String, List<Product>>(); 
		this.bookList = new LinkedList<Product>();
		this.dvdList = new LinkedList<Product>();
		this.gameList = new LinkedList<Product>();

		this.partialList = new LinkedList<Product>();

		this.categories.put("All", productList);
		this.categories.put("Books", bookList);
		this.categories.put("DVDs", dvdList);
		this.categories.put("Games", gameList);

		try {
			transformerFactory = TransformerFactory.newInstance();
			transformer = transformerFactory.newTransformer();
			documentFactory = DocumentBuilderFactory.newInstance();
			documentBuilder = documentFactory.newDocumentBuilder();
		} catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }

		//getKey() : HashMap Key
		//getValue() : HashMap Value

	}

/**
*The method that reads an XML file and turns it into a list of nodes in memory
*@param filePath the path (directory and name) of the XML file to read
* @return the list of nodes read
*/	

	public NodeList parseXMLFile (String filePath) {
		NodeList elementNodes = null;
		try {
			Document document= documentBuilder.parse(new File(filePath));
			Element root = document.getDocumentElement();
			
			elementNodes = root.getChildNodes();	
		}
		catch (SAXException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return elementNodes;
	}

/**
*Methods to demonstrate reading XML files that contain multiple elements
*/	

	//Product XML file
	public void readProductXML() {
		NodeList nodes = this.parseXMLFile(XML_PRODUCT_FILE);
		productList.clear();
		dvdList.clear();
		bookList.clear();
		gameList.clear();

		if (nodes == null) return;
		
		for (int i = 0; i<nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE)   {
				Element currentElement = (Element) nodes.item(i);
				if (currentElement.getNodeName().equals("book")){
				
						String name = currentElement.getElementsByTagName("name").item(0).getTextContent();
						String priceString = currentElement.getElementsByTagName("price").item(0).getTextContent();
						Double price = Double.parseDouble(priceString); //convert into double
						String stockString = currentElement.getElementsByTagName("stock").item(0).getTextContent();
						int stock = Integer.parseInt(stockString); //convert into int
						String image = currentElement.getElementsByTagName("image").item(0).getTextContent();
						String author = currentElement.getElementsByTagName("author").item(0).getTextContent();
						String pagesString = currentElement.getElementsByTagName("pages").item(0).getTextContent();
						int pages = Integer.parseInt(pagesString); //convert into int
						String languageString = currentElement.getElementsByTagName("language").item(0).getTextContent();
						if(languageString.equals("French")) 
							language = Language.FRANCAIS;
						else if(languageString.equals("English")) 
							language = Language.ANGLAIS;
						else if(languageString.equals("Spanish")) 
							language = Language.ESPAGNOL;
						else if(languageString.equals("German")) 
							language = Language.ALLEMAND;
						else if(languageString.equals("Chinese")) 
							language = Language.CHINOIS;
						String identifierString = currentElement.getElementsByTagName("UUID").item(0).getTextContent();
						UUID identifier = UUID.fromString(identifierString);

						book = new Book(name, price, stock, image, identifier, author, pages, language);
						addProduct(book);
				}
				else if (currentElement.getNodeName().equals("DVD")){
				
						String name = currentElement.getElementsByTagName("name").item(0).getTextContent();
						String priceString = currentElement.getElementsByTagName("price").item(0).getTextContent();
						Double price = Double.parseDouble(priceString); //convert into double
						String stockString = currentElement.getElementsByTagName("stock").item(0).getTextContent();
						int stock = Integer.parseInt(stockString); //convert into int
						String image = currentElement.getElementsByTagName("image").item(0).getTextContent();
						String lengthString = currentElement.getElementsByTagName("length").item(0).getTextContent();
						int length = Integer.parseInt(lengthString);
						String genreString = currentElement.getElementsByTagName("genre").item(0).getTextContent();
						if(genreString.equals("Action"))
							genre = Genre.ACTION;
						else if(genreString.equals("Adventure"))
							genre = Genre.ADVENTURE;
						else if(genreString.equals("Animation"))
							genre = Genre.ANIMATION;
						else if(genreString.equals("Comedy"))
							genre = Genre.COMEDY;
						else if(genreString.equals("Drama"))
							genre = Genre.DRAMA;
						else if(genreString.equals("Horror"))
							genre = Genre.HORROR;
						else if(genreString.equals("Scifi"))
							genre = Genre.SCIFI;
						else if(genreString.equals("Thriller"))
							genre = Genre.THRILLER;
						else if(genre.equals("Romance"))
							genre = Genre.ROMANCE;
						String identifierString = currentElement.getElementsByTagName("UUID").item(0).getTextContent();
						UUID identifier = UUID.fromString(identifierString);

						dvd = new DVD(name, price, stock, image, identifier, length, genre);

						for(int j=0; j<currentElement.getElementsByTagName("actors").getLength(); j++){
							if(currentElement.getElementsByTagName("actors").item(j).getTextContent() != null) {
								String actorsName = currentElement.getElementsByTagName("actors").item(j).getTextContent();
								dvd.addActors(actorsName);
							}
						}
						addProduct(dvd);
				}
				else if (currentElement.getNodeName().equals("game")){
				
						String name = currentElement.getElementsByTagName("name").item(0).getTextContent();
						String priceString = currentElement.getElementsByTagName("price").item(0).getTextContent();
						Double price = Double.parseDouble(priceString); //convert into double
						String stockString = currentElement.getElementsByTagName("stock").item(0).getTextContent();
						int stock = Integer.parseInt(stockString); //convert into int
						String image = currentElement.getElementsByTagName("image").item(0).getTextContent();
						String gameGenreString = currentElement.getElementsByTagName("gameGenre").item(0).getTextContent();
						if(gameGenreString.equals("Arcade"))
							gameGenre = GameGenre.ARCADE; 
						else if(gameGenreString.equals("Role"))
							gameGenre = GameGenre.ROLE; 
						else if(gameGenreString.equals("MJ"))
							gameGenre = GameGenre.MJ; 
						else if(gameGenreString.equals("Educational"))
							gameGenre = GameGenre.EDUCATIONAL; 
						else if(gameGenreString.equals("Strategy"))
							gameGenre = GameGenre.STRATEGY; 
						String platformString  = currentElement.getElementsByTagName("platform").item(0).getTextContent();
						if(platformString.equals("Nintendo"))
							platform = Platform.NINTENDO;
						else if(platformString.equals("PC"))
							platform = Platform.PC;
						else if(platformString.equals("Xbox"))
							platform = Platform.XBOX;
						else if(platformString.equals("Playstation"))
							platform = Platform.PLAYSTATION;
						String identifierString = currentElement.getElementsByTagName("UUID").item(0).getTextContent();
						UUID identifier = UUID.fromString(identifierString);

						game = new Game(name, price, stock, image, identifier, gameGenre, platform);
					
						addProduct(game);
				}
			}  
		}
	}

	//Client XML file
	public void readClientXML() {
		NodeList nodes = this.parseXMLFile(XML_CLIENT_FILE);
		clientList.clear();
		if (nodes == null) return;
		
		for (int i = 0; i<nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE)   {
				Element currentElement = (Element) nodes.item(i);
				if (currentElement.getNodeName().equals("client")){
				
						String firstName = currentElement.getElementsByTagName("firstName").item(0).getTextContent();
						String lastName = currentElement.getElementsByTagName("lastName").item(0).getTextContent();
						String address = currentElement.getElementsByTagName("address").item(0).getTextContent();
						String uuidString = currentElement.getElementsByTagName("UUID").item(0).getTextContent();
						UUID uuid = UUID.fromString(uuidString);

						client = new Client(firstName, lastName, address, uuid);
						addClient(client);
				}
			}
		}
	}

	//Transaction XML file
	public void readTransactionXML() {
		NodeList nodes = this.parseXMLFile(XML_TRANSACTION_FILE);
		transactionList.clear();
		if (nodes == null) return;
		
		for (int i = 0; i<nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE)   {
				Element currentElement = (Element) nodes.item(i);
				if (currentElement.getNodeName().equals("transaction")){
				
						String clientIDString = currentElement.getElementsByTagName("clientID").item(0).getTextContent();
						UUID clientID = UUID.fromString(clientIDString);
						String productIDString = currentElement.getElementsByTagName("productID").item(0).getTextContent();
						UUID productID = UUID.fromString(productIDString);
						String numProductsString = currentElement.getElementsByTagName("numProducts").item(0).getTextContent();
						int numProducts = Integer.parseInt(numProductsString);
						String timeString = currentElement.getElementsByTagName("time").item(0).getTextContent();
						
						try {
							Date parseDate = dateFormat.parse(timeString); //convert from String to Date first
							Timestamp time = new Timestamp(parseDate.getTime()); //convert from Date to Timestamp

							transaction = new Transaction(clientID, productID, numProducts, time);
						} 
						catch(ParseException e) {
							e.printStackTrace(); //allows to raise the exception
						}

						addTransaction(transaction);
				}
			}
		}
	}

/**
* The method that turns the document into a memory XML file on the hard drive
* @param document the document to be transformed
* @param filePath the path (directory and name) of the XML file to be created 
*/	

	public void createXMLFile(Document document, String filePath){

		try {
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(new File(filePath));

		// If you use
		// StreamResult result = new StreamResult(System.out);
		// the output will be pushed to the standard output ...
		// You can use that for debugging 

        //transform the DOM Object to an XML File
		transformer.transform(domSource, streamResult);
		
		} catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
		//System.out.println("Done creating XML File");
	}

/**
* The method that creates the document in memory
* @return the document created
*/	

	public Document createXMLDocument()
	{
		return documentBuilder.newDocument();
	}

/**
* Method to demonstrate writing client XML files with multiple elements
*/			

	public void writeClientXML(String fN, String lN, String aD) {
		Document document = this.createXMLDocument();
		if (document == null) return;

 		// create root element
		Element root = document.createElement("clients");
		document.appendChild(root);

		//Overwrite the old file and rewrite the entire client list
		for(Client c : clientList) {
			Element client = document.createElement("client");
			// clientUUID element
			UUID uniqueID = c.getUniqueID();
			Element clientUUID = document.createElement("UUID");
			clientUUID.appendChild(document.createTextNode(uniqueID.toString()));
			client.appendChild(clientUUID);

			// firstName element
			String firstName = c.getFirstName();
			Element firstNameElement = document.createElement("firstName");
			firstNameElement.appendChild(document.createTextNode(firstName));
			client.appendChild(firstNameElement);
					
			//lastName element
			String lastName = c.getLastName();
			Element lastNameElement = document.createElement("lastName");
			lastNameElement.appendChild(document.createTextNode(lastName));
			client.appendChild(lastNameElement);
					
			//address element
			String address = c.getAddress();
			Element addressElement = document.createElement("address");
			addressElement.appendChild(document.createTextNode(address));
			client.appendChild(addressElement);

			root.appendChild(client);
		}

	 	/**
		* The client added in the XML file
		*/ 

			//save one "client" element
			Element client = document.createElement("client");
			// clientUUID element
			UUID uniqueID =UUID.randomUUID();
			Element clientUUID = document.createElement("UUID");
			clientUUID.appendChild(document.createTextNode(uniqueID.toString()));
			client.appendChild(clientUUID);
			
			// firstName element
			String firstName = fN;
			Element firstNameElement = document.createElement("firstName");
			firstNameElement.appendChild(document.createTextNode(firstName));
			client.appendChild(firstNameElement);
					
			//lastName element
			String lastName = lN;
			Element lastNameElement = document.createElement("lastName");
			lastNameElement.appendChild(document.createTextNode(lastName));
			client.appendChild(lastNameElement);
					
			//address element
			String address = aD;
			Element addressElement = document.createElement("address");
			addressElement.appendChild(document.createTextNode(address));
			client.appendChild(addressElement);
			

			client1 = new Client(firstName, lastName, address, uniqueID);

			int cpt = 0;
			//Check if the customer is not already in the XML file
			for(Client cl : clientList) {
				if(client1.equals(cl))
					cpt++;
			}
			if(cpt == 0) {
				root.appendChild(client);
				addClient(client1);
			}
			cpt = 0;

		
		this.createXMLFile(document, XML_CLIENT_FILE);
	}

/**
* Method to add a product to the store’s XML file
*/

	public void addProduct(Product p) {
		int cpt = 0;

		//Check if the product is not already in the XML file
		for(Product pr : productList) {
			if(p.equals(pr))
				cpt++;
		}
		if(cpt == 0) {
			productList.add(p); //Add in the list "All"

			if(p.getClass().getName() == "store.business.Book") {
				addCategory(p, "Book"); //Add in the list "Book"
			}
			else if(p.getClass().getName() == "store.business.DVD") {
				addCategory(p, "DVD"); //Add in the list "DVD"
			}
			else if(p.getClass().getName() == "store.business.Game") {
				addCategory(p, "Game"); //Add in the list "Game"
			}
		}
		cpt = 0;
	}

/**
* Method to add a customer to the store's XML file
*/

	public void addClient(Client c) {
		int cpt = 0;

		//Check if the customer is not already in the XML file
		for(Client cl : clientList) {
			if(c.equals(cl))
				cpt++;
		}
		if(cpt == 0)
			clientList.add(c);
		cpt = 0;
	}

/**
* Method to find if the customer exists in the store XML file
*/

	public boolean searchClientExist(Client temp) {
		boolean flag1 = false;
		boolean flag = false;

		for(Client c : clientList) {
			flag1 = c.getFirstName().equals(temp.getFirstName());
			flag = c.getLastName().equals(temp.getLastName());
			if(flag1 == true && flag == true){
				System.out.print("\033[H\033[2J");  
   				System.out.flush();
				System.out.println("Your now logged in !");
				this.currentLogin = new Client(c.getFirstName(), c.getLastName(), c.getAddress(), c.getUniqueID());
   				System.out.println("Login : " + temp.getFirstName() + " " + temp.getLastName());
				return true;
			}
		}
		System.out.println("Error : Client not found");
		System.out.println("n : if Definitively not in list");
		return false;
	}

/**
* Method to add a transaction to the store’s XML file
*/

	public void addTransaction(Transaction t) {
		int cpt = 0;

		//Check if the customer is not already in the XML file
		for(Transaction tr : transactionList) {
			if(t.equals(tr))
				cpt++;
		}
		if(cpt == 0) {
			transactionList.add(t);
		}
		cpt = 0;
	}

/*
* Method to associate a product to a category:Book, DVD, Game
*/

	public void addCategory(Product p, String cat) {
		
		if(cat == "Book") {
			bookList.add(p); //Add in the list "Book"
		}
		else if(cat == "DVD") {
			dvdList.add(p); //Add in the list "DVD"
		}
		else if(cat == "Game") {
			gameList.add(p); //Add in the list "Game"
		}
	}

/**
* Methods to display lists in the store
*/

	//Product list
	public String getProductListToString() {
		return this.productList.toString();
	}

	//Book list
	public String getBookListToString() {
		return this.bookList.toString();
	}

	//DVD list
	public String getDvdListToString() {
		return this.dvdList.toString();
	}

	//Game list
	public String getGameListToString() {
		return this.gameList.toString();
	}


	public List<Product> getProductList() {
		return this.productList;
	}

	public List<Product> getBookList() {
		return this.bookList;
	}

	public List<Product> getDvdList() {
		return this.dvdList;
	}

	public List<Product> getGameList() {
		return this.gameList;
	}

	public List<Client> getClientList() {
		return this.clientList;
	}

	public void getCategoryListToString() {
		for(HashMap.Entry<String, List<Product>> entry : categories.entrySet())
		{
 			System.out.println(entry.getKey());
   			//System.out.println(entry.getValue());
		}
	}

	public void getPartialProductListToString(int nb) {
		//Allows you to display a partial product list (on Console)
		int cpt = 0;
		for(Product p : productList) {
			if(cpt < nb) { //nb : number of items to display
				System.out.println(p);
				cpt++;
			}
			else {
				cpt = 0;
				break;
			}
		}
	}

	public LinkedList<Product> getPartialProductList(int nb) {
		//Allows you to display a partial product list (on Console)
		
		int cpt = 0;
		for(Product p : productList) {
			if(cpt < nb) { //nb : number of items to display
				//System.out.println(p);
				partialList.add(p);
				cpt++;
			}
			else {
				cpt = 0;
				break;
			}
		}
		return partialList;
	}

	
	public String getClientListToString() {
		return clientList.toString();
	}

/**
* Method to purchase a product (on console)
*/

	public void buyProduct(String buyChoiceName) {

		Scanner scan = new Scanner(System.in);
		for(Product p : productList) {
			if(buyChoiceName.equals(p.getProductName())) {
				System.out.println("Product Found !");

				if(p.getProductStock()>=1) {
					System.out.println("Product Available ! :) " + "("+p.getProductStock()+")");
					
					System.out.println("How many products do you want ?");
					int quantity = scan.nextInt();

					boolean allowed = p.allowedToBuyProductNb(quantity);
					if(allowed == true) {
						System.out.println("Purchasing . . .");
						transaction = new Transaction(this.currentLogin.getUniqueID(), p.getProductUUID(), quantity, new Timestamp(System.currentTimeMillis()));

						addTransaction(transaction); //the transaction is added to the list of transactions
						p.removeToBuyProductNb(quantity); //the quantity purchased is withdrawn from the stock

						writeProductXML(); //we update: XML product from the list of products
						writeTransactionXML(); //we update: XML transaction from the list of transactions
					}
					else
						System.out.println("Sorry : insuffisant quantity");

				}
				else {
					System.out.println("Product Unavailable ! :( ");
				}
			}
		}
	}

/**
* Method to purchase a product
*/

	public void buyProductGUI(String buyChoiceName, int quantity) {

		for(Product p : productList) {
			if(buyChoiceName.equals(p.getProductName())) {
				System.out.println("Product Found !");

				if(p.getProductStock()>=1) {
					System.out.println("Product Available ! :) " + "("+p.getProductStock()+")");

					boolean allowed = p.allowedToBuyProductNb(quantity);
					if(allowed == true) {
						System.out.println("Purchasing . . .");
						transaction = new Transaction(this.currentLogin.getUniqueID(), p.getProductUUID(), quantity, new Timestamp(System.currentTimeMillis()));

						addTransaction(transaction); //the transaction is added to the list of transactions
						p.removeToBuyProductNb(quantity); //the quantity purchased is withdrawn from the stock

						writeProductXML(); //we update: XML product from the list of products
						writeTransactionXML(); //we update: XML transaction from the list of transactions
					}
					else
						System.out.println("Sorry : insuffisant quantity");

				}
				else {
					System.out.println("Product Unavailable ! :( ");
				}
			}
		}
	}

/**
* Method to demonstrate writing Transaction XML files with multiple elements
*/				

	public void writeTransactionXML() {
		Document document = this.createXMLDocument();
		if (document == null) return;

 		// create root element
		Element root = document.createElement("transactions");
		document.appendChild(root);

		//Overwrite the old file and rewrite the entire transaction list
		for(Transaction t : transactionList) {
			Element transaction = document.createElement("transaction");

			// clientID element
			UUID clientID = t.getClientID();
			Element clientIDElement = document.createElement("clientID");
			clientIDElement.appendChild(document.createTextNode(clientID.toString()));
			transaction.appendChild(clientIDElement);

			// productID element
			UUID productID = t.getProductID();
			Element productIDElement = document.createElement("productID");
			productIDElement.appendChild(document.createTextNode(productID.toString()));
			transaction.appendChild(productIDElement);

			// numProducts element
			int numProducts = t.getNumProducts();
			Element numProductsElement = document.createElement("numProducts");
			numProductsElement.appendChild(document.createTextNode(Integer.toString(numProducts)));
			transaction.appendChild(numProductsElement);
					
			//time element
			Timestamp time = t.getTime();
			String timeDate = dateFormat.format(time); //convert from Timestamp to Date then String
			Element timeElement = document.createElement("time");
			timeElement.appendChild(document.createTextNode(timeDate.toString()));
			transaction.appendChild(timeElement);
					
			root.appendChild(transaction);
		}

		this.createXMLFile(document, XML_TRANSACTION_FILE);
	}

/**
* Method to demonstrate writing Product XML files with multiple elements
*/		

	public void writeProductXML() {
		Document document = this.createXMLDocument();
		if (document == null) return;

 		// create root element
		Element root = document.createElement("products");
		document.appendChild(root);

		//Overwrite the old file and rewrite the entire product list
		for(Product p : productList) {

			if(p.getClass().getName() == "store.business.Book") {
				Element book = document.createElement("book");
				
				UUID bookID = ((Book)p).getProductUUID();
				Element bookIDElement = document.createElement("UUID");
				bookIDElement.appendChild(document.createTextNode(bookID.toString()));
				book.appendChild(bookIDElement);

				String name = ((Book)p).getProductName();
				Element nameElement = document.createElement("name");
				nameElement.appendChild(document.createTextNode(name));
				book.appendChild(nameElement);

				double price = ((Book)p).getPrice();
				Element priceElement = document.createElement("price");
				priceElement.appendChild(document.createTextNode(Double.toString(price)));
				book.appendChild(priceElement);

				int stock = ((Book)p).getProductStock();
				Element stockElement = document.createElement("stock");
				stockElement.appendChild(document.createTextNode(Integer.toString(stock)));
				book.appendChild(stockElement);

				String image = ((Book)p).getProductImage();
				Element imageElement = document.createElement("image");
				imageElement.appendChild(document.createTextNode(image));
				book.appendChild(imageElement);

				String author = ((Book)p).getBookAuthor();
				Element authorElement = document.createElement("author");
				authorElement.appendChild(document.createTextNode(author));
				book.appendChild(authorElement);

				int pages = ((Book)p).getBookPages();
				Element pagesElement = document.createElement("pages");
				pagesElement.appendChild(document.createTextNode(Integer.toString(pages)));
				book.appendChild(pagesElement);

				Language language = ((Book)p).getBookLanguage();
				Element languageElement = document.createElement("language");
				languageElement.appendChild(document.createTextNode(language.getLanguage()));
				book.appendChild(languageElement);

				root.appendChild(book);
			}
			else if(p.getClass().getName() == "store.business.DVD") {
				Element dvd = document.createElement("DVD");
				
				UUID dvdID = ((DVD)p).getProductUUID();
				Element dvdIDElement = document.createElement("UUID");
				dvdIDElement.appendChild(document.createTextNode(dvdID.toString()));
				dvd.appendChild(dvdIDElement);

				String name = ((DVD)p).getProductName();
				Element nameElement = document.createElement("name");
				nameElement.appendChild(document.createTextNode(name));
				dvd.appendChild(nameElement);

				double price = ((DVD)p).getPrice();
				Element priceElement = document.createElement("price");
				priceElement.appendChild(document.createTextNode(Double.toString(price)));
				dvd.appendChild(priceElement);

				int stock = ((DVD)p).getProductStock();
				Element stockElement = document.createElement("stock");
				stockElement.appendChild(document.createTextNode(Integer.toString(stock)));
				dvd.appendChild(stockElement);

				String image = ((DVD)p).getProductImage();
				Element imageElement = document.createElement("image");
				imageElement.appendChild(document.createTextNode(image));
				dvd.appendChild(imageElement);

				for(int j=0; j<((DVD)p).getVectorActors().size(); j++){		
					String actors = ((DVD)p).getActors(j);
					Element actorsElement = document.createElement("actors");
					actorsElement.appendChild(document.createTextNode(actors));
					dvd.appendChild(actorsElement);					
				}

				int length = ((DVD)p).getLength();
				Element lengthElement = document.createElement("length");
				lengthElement.appendChild(document.createTextNode(Integer.toString(length)));
				dvd.appendChild(lengthElement);

				Genre genre = ((DVD)p).getGenre();
				Element genreElement = document.createElement("genre");
				genreElement.appendChild(document.createTextNode(genre.getGenre()));
				dvd.appendChild(genreElement);

				root.appendChild(dvd);
			}
			else if(p.getClass().getName() == "store.business.Game") {
				Element game = document.createElement("game");
				
				UUID gameID = ((Game)p).getProductUUID();
				Element gameIDElement = document.createElement("UUID");
				gameIDElement.appendChild(document.createTextNode(gameID.toString()));
				game.appendChild(gameIDElement);

				String name = ((Game)p).getProductName();
				Element nameElement = document.createElement("name");
				nameElement.appendChild(document.createTextNode(name));
				game.appendChild(nameElement);

				double price = ((Game)p).getPrice();
				Element priceElement = document.createElement("price");
				priceElement.appendChild(document.createTextNode(Double.toString(price)));
				game.appendChild(priceElement);

				int stock = ((Game)p).getProductStock();
				Element stockElement = document.createElement("stock");
				stockElement.appendChild(document.createTextNode(Integer.toString(stock)));
				game.appendChild(stockElement);

				String image = ((Game)p).getProductImage();
				Element imageElement = document.createElement("image");
				imageElement.appendChild(document.createTextNode(image));
				game.appendChild(imageElement);

				GameGenre gameGenre = ((Game)p).getGameGenre();
				Element gameGenreElement = document.createElement("gameGenre");
				gameGenreElement.appendChild(document.createTextNode(gameGenre.getGameGenre()));
				game.appendChild(gameGenreElement);

				Platform platform = ((Game)p).getPlatform();
				Element platformElement = document.createElement("platform");
				platformElement.appendChild(document.createTextNode(platform.getPlatform()));
				game.appendChild(platformElement);

				root.appendChild(game);
			}
		}
		this.createXMLFile(document, XML_PRODUCT_FILE);
	}

	public List<Transaction> getTransactionList() {
		return this.transactionList;
	}
}
