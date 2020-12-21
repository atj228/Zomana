package store.business;

import java.util.*;
import java.text.*;

/**
* @authors Thierry KHAMPHOUSONE & Tata Joseph ASSOUMA
*/

public class StoreConsole {
	
	Client login;

/**
* Constructor (Console)
*/	

	public StoreConsole() {

		System.out.println("Welcome into the store !");

		Store store = new Store(); //Creation of the store

		store.readProductXML(); //Allows you to load all products directly from an XML file
		store.readClientXML(); //Allows you to load all customers directly from an XML file
		store.readTransactionXML(); //Allows to load transactions already completed
		//store.writeTransactionXML(); //Adds a transaction (overwrites the old file and rewrites everything)

		PriceComparator pc = new PriceComparator();

		/**
		*Products will always be sorted by price (SHOULD)
		*/
		Collections.sort(store.getProductList(), pc);
		
		/**
		* Displays the product categories (MUST)
		* The list of categories contains the item "All" (MUST)
		*/
		store.getCategoryListToString();

		/**
		* Displays a list of products (partial) (MUST)
		* User can browse product list (MUST) with the wheel
		*/ 
		store.getPartialProductListToString(8); 
		
		boolean p = true;
		boolean q = true;
		boolean e = true;
		boolean connected = false;
		Scanner scan = new Scanner(System.in);
		Scanner productScan = new Scanner(System.in);
		Scanner identityScan = new Scanner(System.in);
		String firstName = null;
		String lastName = null;
		String address, choice;
		String buyChoiceName;

		while(q){
			
			System.out.println("Please select a category :");
			System.out.println("a : All ");
			System.out.println("b : Books ");
			System.out.println("d : DVDs ");
			System.out.println("g : Games ");
			System.out.println("z : Log in ");
			System.out.println("q : Leave the shop ");

			choice = scan.next();
			switch(choice) 
			{
				default:
				System.out.print("\033[H\033[2J");  
				System.out.flush();  
				System.out.println("Unknown command. Type h for help");
				break;

				case "q" :  
				System.out.print("\033[H\033[2J");  
				System.out.flush();  
				System.out.println("Bye bye!");
				q = false;
				break;

				case "b" : 
				System.out.print("\033[H\033[2J");  
				System.out.flush();   
				System.out.println("Here's our Books!" + store.getBookListToString());
				System.out.println("to buy a book, please enter the title of the book");
				System.out.println("to leave, enter a random key");
					buyChoiceName = productScan.nextLine(); //We get the name of the product

					switch(buyChoiceName) {
						default :	
							//method to check availability and purchase (transaction record)
						if(connected == true){
							store.buyProduct(buyChoiceName);
						}
						else {
							System.out.print("\033[H\033[2J");  
							System.out.flush();  
							System.out.println("Please connect on the store first.");
						}
						break;

						case "q" :  
						System.out.print("\033[H\033[2J");  
						System.out.flush();  
						break;
					}
					
					break;

					case "d" : 
					System.out.print("\033[H\033[2J");  
					System.out.flush();   
					System.out.println("Here's our DVDs !" + store.getDvdListToString());
					System.out.println("to buy a dvd, please enter the title of the dvd");
					System.out.println("to leave, enter a random key");
					buyChoiceName = productScan.nextLine(); //We get the name of the product

					switch(buyChoiceName) {
						default :	
							//method to check availability and purchase (transaction record)
						if(connected == true){
							store.buyProduct(buyChoiceName);
						}
						else {
							System.out.print("\033[H\033[2J");  
							System.out.flush();  
							System.out.println("Please connect on the store first.");
						}
						break;

						case "q" :  
						System.out.print("\033[H\033[2J");  
						System.out.flush();  
						break;
					}

					break;

					case "g" : 
					System.out.print("\033[H\033[2J");  
					System.out.flush();  
					System.out.println("Here's our Games !" + store.getGameListToString());
					
					System.out.println("to buy a game, please enter the title of the game");
					System.out.println("to leave, enter a random key");
					buyChoiceName = productScan.nextLine(); //We get the name of the product

					switch(buyChoiceName) {
						default :	
							//method to check availability and purchase (transaction record)
						if(connected == true){
							store.buyProduct(buyChoiceName);
						}
						else {
							System.out.print("\033[H\033[2J");  
							System.out.flush();  
							System.out.println("Please connect on the store first.");
						}
						break;

						case "q" :  
						System.out.print("\033[H\033[2J");  
						System.out.flush();  
						break;
					}

					break;

					case "a" :  
					System.out.print("\033[H\033[2J");  
					System.out.flush();  
					System.out.println("All " + store.getProductListToString());
					
					System.out.println("to buy something, please enter the title of the product");
					System.out.println("to leave, enter a random key");
					buyChoiceName = productScan.nextLine(); //We get the name of the product

					switch(buyChoiceName) {
						default :	
							//method to check availability and purchase (transaction record)
						if(connected == true){
							store.buyProduct(buyChoiceName);
						}
						else {
							System.out.print("\033[H\033[2J");  
							System.out.flush();  
							System.out.println("Please connect on the store first.");
						}
						break;

						case "q" :  
						System.out.print("\033[H\033[2J");  
						System.out.flush();  
						break;
					}

					break;

					case "z" :
					p = true;
					e = true;
					if(this.login == null) {
						while(p){
							System.out.print("\033[H\033[2J");  
							System.out.flush();
							System.out.println("Client List : ");
							System.out.println(store.getClientListToString());

							/**
							*The user can search for the client in the Client List (MUST)
							*/
							System.out.println("is your client Name in the list ? (y/n)");
							choice = scan.next();
							switch(choice){

								default : 
								break;

								case "y" :
								while(e) {

									System.out.println("Please select your client firstName :");
									firstName = scan.next();

									if(firstName.equals("n")) {
										e = false;
										p = false; 
										break;
									}

									System.out.println("Please select your client LastName :");
									lastName = scan.next();

									if(lastName.equals("n")) {
										e = false;
										p = false; 
										break;
									}
									Client temp = new Client(firstName, lastName, null, null);
										boolean ex = store.searchClientExist(temp); //we log in by searching if the client is in the XML file

										if(ex == false && (!firstName.equals("n") || !lastName.equals("n"))){
											System.out.println("Error : write correctly");
										}
										else if(ex == true) {
											connected = true;
											this.login = temp;
											e = false;
											p = false;
											break;
										}
									}
									break;

									case "n" :	
									p = false;
									break;

								}
							}
						}
						else {
							System.out.print("\033[H\033[2J");  
							System.out.flush();  
							System.out.println("You're already logged in !");
							System.out.println("Login : " + firstName +" " + lastName);
						}

						if(this.login == null) {
							System.out.println("Please create your account : ");
							System.out.println("firstName : ");
							firstName = identityScan.next();

							System.out.println("lastName : ");
							lastName = identityScan.next();

							System.out.println("address : ");
							identityScan.nextLine();
							address = identityScan.nextLine();

						//Client client2 = new Client(firstName, lastName, address);
						//store.addClient(client2);
						store.writeClientXML(firstName, lastName, address); //allows you to add a client to the customer list
						scan.nextLine(); //to empty the buffer

						System.out.print("\033[H\033[2J");  
						System.out.flush();  
						System.out.println("You have been added in the client List ! ");
						break;
					}
					else
					{
						break;
					}
				}
			}
	}

/**
* Main (Console) Method
*/
	public static void main(String[] args) {
		System.out.print("\033[H\033[2J");  
		System.out.flush();
		new StoreConsole();
	}

}
