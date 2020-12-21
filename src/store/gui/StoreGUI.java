package store.gui;

import java.util.List;
import java.util.LinkedList;
import java.util.Collections;
import java.util.logging.*;

import java.text.*;

import java.io.FileNotFoundException;
import java.io.*;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.Container;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Scrollbar;
import java.awt.ScrollPane;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.Color;

import javax.swing.*;
import javax.swing.JComboBox;
import javax.swing.UIManager.*;

import store.business.*;

/**
 * @authors Thierry KHAMPHOUSONE & Tata Joseph ASSOUMA
 */

public class StoreGUI implements ActionListener {

	private Store store = new Store();

	private JButton searchButtonProduct = new JButton("Search");
	private JButton createButton = new JButton("Create Account");
	private JButton searchButtonClient = new JButton("Search");
	private JButton buyButton = new JButton("Buy");
	private JButton selectButton = new JButton("Select");
	private JButton selectButtonThird = new JButton("Select");
	private JTextField clientTextField = new JTextField(15);

	private JTextArea clientInformationArea = new JTextArea(5, 2);

	private DefaultListModel<String> productListArea = new DefaultListModel<String>();
	private DefaultListModel<String> clientListArea = new DefaultListModel<String>();

	private JTextArea currentProductListArea = new JTextArea(5, 2); // product current

	private JTextArea currentClientArea = new JTextArea(2, 2);
	private JTextArea currentProductArea = new JTextArea(2, 2);
	private JComboBox<Integer> numberlist;
	private JComboBox<String> list;

	private LinkedList<Product> partialList = new LinkedList<Product>();

	private List<Product> allList;
	private List<Product> dvdList;
	private List<Product> bookList;
	private List<Product> gameList;
	private List<Client> clientList;

	private JList<String> productListA = new JList<String>(productListArea);
	private JList<String> clientListA = new JList<String>(clientListArea);

	private GridLayout gridLayout = new GridLayout(4, 1);
	private JPanel secondRightPanel = new JPanel(new BorderLayout());
	private JPanel thirdRightPanel = new JPanel(new BorderLayout());
	private JPanel generalPanel = new JPanel(gridLayout);

	private PriceComparator pc = new PriceComparator();

	protected static Logger logger = Logger.getLogger(StoreGUI.class.getName());

	/********** POP UP PANEL TO CREATE AN ACCOUNT ***************/

	private JFrame popUpFrame = new JFrame("Form : Client Account");
	private JTextField firstNameTextField = new JTextField(10);
	private JTextField lastNameTextField = new JTextField(10);
	private JTextField addressTextField = new JTextField(17);

	private Client clientTemp;
	private String productNameToBuy;
	private boolean readyProduct = false;
	private boolean readyClient = false;

	private String firstName = null;
	private String lastName = null;
	private String address = null;

	/********** POP UP PANEL TO CREATE AN ACCOUNT ***************/

	/**
	 * Constructor (GUI)
	 */

	public StoreGUI() throws FileNotFoundException {

		store.readProductXML();
		store.readClientXML();
		store.readTransactionXML();

		// PriceComparator pc = new PriceComparator();

		/**
		 * Products will always be sorted by price (SHOULD)
		 */
		Collections.sort(store.getProductList(), pc);

		/**
		 * Displays the product categories (MUST) The list of categories contains the
		 * item "All" (MUST)
		 */
		// store.getCategoryListToString();

		/**
		 * Displays a list of products (partial) (MUST) User can browse product list
		 * (MUST) with the wheel
		 */
		// store.getPartialProductListToString(8);

		allList = new LinkedList<Product>();
		dvdList = new LinkedList<Product>();
		bookList = new LinkedList<Product>();
		gameList = new LinkedList<Product>();
		clientList = new LinkedList<Client>();

		allList= store.getProductList();
		dvdList = store.getDvdList();
		bookList = store.getBookList();
		gameList = store.getGameList();
		clientList = store.getClientList();

		/********* Initialisation du logger ************/
		logger.setLevel(Level.FINE);
        logger.addHandler(new ConsoleHandler());
        try {
        	Handler fileHandler = new FileHandler("./files/log.xml", 2000, 1);
        	logger.addHandler(fileHandler);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }

		/********** POP UP PANEL TO CREATE AN ACCOUNT ***************/

		//JFrame popUpFrame = new JFrame();
		GridLayout popUpGridLayout = new GridLayout(2,2);
        JLabel firstNameLabel = new JLabel("First Name");
        JLabel lastNameLabel = new JLabel("Last Name");
        JLabel addressLabel = new JLabel("Address");

        JButton validateButton = new JButton("Validate");

        JPanel popUpPanel = new JPanel(popUpGridLayout);
        JPanel popUpTopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel popUpUselessPanel1 = new JPanel();
        JPanel popUpUselessPanel2 = new JPanel();
        JPanel popUpBottomPanel = new JPanel(new BorderLayout());

        popUpFrame.setLayout(new BorderLayout());


        popUpTopPanel.add(firstNameLabel, BorderLayout.NORTH);
        popUpTopPanel.add(firstNameTextField);
        popUpTopPanel.add(lastNameLabel, BorderLayout.CENTER);
        popUpTopPanel.add(lastNameTextField, BorderLayout.CENTER);
        popUpTopPanel.add(addressLabel, BorderLayout.SOUTH);
        popUpTopPanel.add(addressTextField, BorderLayout.SOUTH);



        popUpPanel.add(popUpTopPanel, BorderLayout.NORTH);
        popUpPanel.add(popUpUselessPanel1, BorderLayout.NORTH);
        popUpPanel.add(popUpUselessPanel2, BorderLayout.NORTH);
        popUpPanel.add(popUpBottomPanel);

        validateButton.setActionCommand("key9");
  		validateButton.addActionListener(this);
  		validateButton.setEnabled(true); 
        popUpBottomPanel.add(validateButton, BorderLayout.SOUTH);

        popUpFrame.add(popUpPanel);

        popUpFrame.setSize(300, 400);
        popUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        popUpFrame.setLocationRelativeTo(null);
        popUpFrame.setVisible(false);


		/********** POP UP PANEL TO CREATE AN ACCOUNT ***************/




		JFrame frame = new JFrame("Zomana");
		frame.setMinimumSize(new Dimension(880, 480));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		FlowLayout flowLayoutLeft = new FlowLayout(FlowLayout.LEFT);
		FlowLayout flowLayoutRight = new FlowLayout(FlowLayout.RIGHT);

		frame.setLayout(new BorderLayout());

		//GridLayout gridLayout = new GridLayout(4,1);
		GridLayout gridLayoutFirstPanel = new GridLayout(1,2);
		GridLayout gridLayoutSecondPanel = new GridLayout(1,3);
		GridLayout gridLayoutForthPanel = new GridLayout(2,1);

		//JPanel generalPanel = new JPanel(gridLayout);
		generalPanel.setBorder(BorderFactory.createTitledBorder(""));

		JPanel firstPanel = new JPanel(gridLayoutFirstPanel);
		JPanel firstLeftPanel = new JPanel(new BorderLayout());
		JPanel firstRightPanel = new JPanel(flowLayoutLeft);

		JPanel secondPanel = new JPanel(gridLayoutSecondPanel);
		JPanel secondLeftPanel = new JPanel(new BorderLayout());
		//JPanel secondRightPanel = new JPanel(new BorderLayout());
		JPanel secondMiddlePanel = new JPanel(new BorderLayout());

		JPanel thirdPanel = new JPanel(gridLayoutFirstPanel);
		JPanel thirdLeftPanel = new JPanel(gridLayoutFirstPanel);
		JPanel thirdLeftTopPanel = new JPanel(gridLayoutForthPanel);
		JPanel thirdLeftBottomPanel = new JPanel(new BorderLayout());
		JPanel thirdLeftTopTopPanel = new JPanel(new BorderLayout());
		JPanel thirdLeftTopBottomPanel = new JPanel(new BorderLayout());


		//JPanel thirdRightPanel = new JPanel(new BorderLayout());


		JPanel forthPanel = new JPanel(gridLayoutFirstPanel);
		JPanel forthLeftPanel = new JPanel(gridLayoutForthPanel);
		JPanel forthLeftTopPanel = new JPanel(new BorderLayout());
		JPanel forthLeftBottomPanel = new JPanel(new BorderLayout());


		JPanel forthRightPanel = new JPanel(gridLayoutForthPanel);
		JPanel forthRightTopPanel = new JPanel(new BorderLayout());
		JPanel forthRightBottomPanel = new JPanel(new BorderLayout());

		firstPanel.setBorder(BorderFactory.createTitledBorder(""));
		secondPanel.setBorder(BorderFactory.createTitledBorder(""));
		thirdPanel.setBorder(BorderFactory.createTitledBorder(""));
		forthPanel.setBorder(BorderFactory.createTitledBorder(""));


/*FIRST PANEL */ 
		firstPanel.setBorder(BorderFactory.createTitledBorder("Zomana Store"));
		
		JLabel label1 = new JLabel("Product Category");
		JLabel label44 = new JLabel("(by Thierry KHAMPHOUSONE & Tata Joseph ASSOUMA)");
		firstLeftPanel.add(label44, BorderLayout.SOUTH);
		firstRightPanel.add(label1, BorderLayout.NORTH);
		addLogoToPanel(firstLeftPanel, "logo.png");

		String[] categories = {"All", "DVDs", "Games", "Books"};
		list = new JComboBox<String>(categories);
		firstRightPanel.add(list);

		searchButtonProduct.setActionCommand("key1");
  		searchButtonProduct.addActionListener(this);
  		searchButtonProduct.setEnabled(true);
  		firstRightPanel.add(searchButtonProduct);



/*SECOND PANEL */
		secondPanel.setBorder(BorderFactory.createTitledBorder("Product"));
		
		JLabel label2 = new JLabel("Product available (order by increasing price)");
		JLabel label3 = new JLabel("Current product");
		secondLeftPanel.add(label2, BorderLayout.NORTH);
		secondLeftPanel.add(productListA);

		JScrollPane scrollProductListA = new JScrollPane(productListA, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		secondLeftPanel.add(scrollProductListA);


		secondMiddlePanel.add(label3, BorderLayout.NORTH);
		secondMiddlePanel.add(currentProductListArea);
		currentProductListArea.setEditable(false);

		selectButton.setActionCommand("key2");
  		selectButton.addActionListener(this);
  		selectButton.setEnabled(true);
  		secondMiddlePanel.add(selectButton, BorderLayout.SOUTH);
		 
		JScrollPane scrollMiddle = new JScrollPane(currentProductListArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		secondMiddlePanel.add(scrollMiddle);
		

/*THIRD PANEL */ 
		thirdPanel.setBorder(BorderFactory.createTitledBorder("Client"));
		
		JLabel label4 = new JLabel("with a SPACE (case sensitive)");
		thirdLeftTopPanel.setBorder(BorderFactory.createTitledBorder("<FirstName> <LastName>"));
		JLabel label5 = new JLabel("Current Client Informations");
		thirdLeftTopTopPanel.add(label4, BorderLayout.NORTH);
		thirdRightPanel.add(label5, BorderLayout.NORTH);

		thirdLeftTopTopPanel.add(clientTextField, BorderLayout.CENTER);

		searchButtonClient.setActionCommand("key3");
  		searchButtonClient.addActionListener(this);
  		searchButtonClient.setEnabled(true);
  		thirdLeftTopBottomPanel.add(searchButtonClient, BorderLayout.NORTH);
  		
  		selectButtonThird.setActionCommand("key4");
  		selectButtonThird.addActionListener(this);
  		selectButtonThird.setEnabled(true);
  		thirdLeftBottomPanel.add(selectButtonThird, BorderLayout.SOUTH);

  		thirdLeftBottomPanel.setBorder(BorderFactory.createTitledBorder("ClientList"));
  		thirdLeftBottomPanel.add(clientListA, BorderLayout.NORTH);

  		JScrollPane scrollClientListA = new JScrollPane(clientListA, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		thirdLeftBottomPanel.add(scrollClientListA);


		clientInformationArea.setText("");
  		thirdRightPanel.add(clientInformationArea);
  		clientInformationArea.setEditable(false);

  		createButton.setActionCommand("key5");
  		createButton.addActionListener(this);
  		createButton.setEnabled(true);
  		thirdRightPanel.add(createButton, BorderLayout.SOUTH);


/* FORTH PANEL */
		forthPanel.setBorder(BorderFactory.createTitledBorder("Transaction"));

		JLabel label6 = new JLabel("Connected Client");
		JLabel label7 = new JLabel("Selected Product");
		JLabel label8 = new JLabel("Number of Product");
		forthLeftTopPanel.add(label6, BorderLayout.NORTH);
		forthRightTopPanel.add(label7, BorderLayout.NORTH);
		forthRightBottomPanel.add(label8, BorderLayout.NORTH);

		forthLeftTopPanel.add(currentClientArea);
		forthRightTopPanel.add(currentProductArea);

		currentClientArea.setEditable(false);
		currentProductArea.setEditable(false);

		Integer[] number = {1,2,3,4,5,6,7,8,9,10};
		numberlist = new JComboBox<Integer>(number);
		forthRightBottomPanel.add(numberlist, BorderLayout.CENTER);

		
		buyButton.setActionCommand("key6");
  		buyButton.addActionListener(this);
  		buyButton.setEnabled(false);
  		forthRightBottomPanel.add(buyButton, BorderLayout.SOUTH);


///////////////
		firstPanel.add(firstLeftPanel, BorderLayout.WEST);
		firstPanel.add(firstRightPanel, BorderLayout.EAST);

		secondPanel.add(secondLeftPanel, BorderLayout.WEST);
		secondPanel.add(secondMiddlePanel, BorderLayout.CENTER);
		secondPanel.add(secondRightPanel, BorderLayout.EAST);

		thirdLeftPanel.add(thirdLeftTopPanel, BorderLayout.NORTH);
		thirdLeftPanel.add(thirdLeftBottomPanel, BorderLayout.NORTH);
		thirdLeftTopPanel.add(thirdLeftTopTopPanel, BorderLayout.NORTH);
		thirdLeftTopPanel.add(thirdLeftTopBottomPanel, BorderLayout.NORTH);
		thirdPanel.add(thirdLeftPanel, BorderLayout.WEST);
		thirdPanel.add(thirdRightPanel, BorderLayout.EAST);


		forthLeftPanel.add(forthLeftTopPanel, BorderLayout.NORTH);
		forthLeftPanel.add(forthLeftBottomPanel, BorderLayout.SOUTH);
		forthRightPanel.add(forthRightTopPanel, BorderLayout.NORTH);
		forthRightPanel.add(forthRightBottomPanel, BorderLayout.SOUTH);
		forthPanel.add(forthLeftPanel, BorderLayout.WEST);
		forthPanel.add(forthRightPanel, BorderLayout.EAST);

		generalPanel.add(firstPanel, BorderLayout.NORTH);
		generalPanel.add(secondPanel, BorderLayout.NORTH);
		generalPanel.add(thirdPanel, BorderLayout.NORTH);
		generalPanel.add(forthPanel, BorderLayout.NORTH);


		frame.add(generalPanel,BorderLayout.CENTER);


		String choix = list.getSelectedItem().toString();
		partialList = store.getPartialProductList(8);
		
		productListArea.clear();
		for(Product p : partialList) {
			productListArea.addElement(p.getProductName()+"\n");
		}

		clientListArea.clear();
		for(Client c : clientList) {
			clientListArea.addElement(c.getFirstLastName()+"\n");
		}

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

		switch (e.getActionCommand()) {
		case "key1":
			String choix = list.getSelectedItem().toString();
			// clientInformationArea.setText(choix);

			productListArea.clear();

			if (choix == "DVDs") {
				Collections.sort(store.getDvdList(), pc);
				for (Product p : dvdList) {
					productListArea.addElement(p.getProductName() + "\n");
				}
			} else if (choix == "Games") {
				Collections.sort(store.getGameList(), pc);
				for (Product p : gameList) {
					productListArea.addElement(p.getProductName() + "\n");
				}
			} else if (choix == "Books") {
				Collections.sort(store.getBookList(), pc);
				for (Product p : bookList) {
					productListArea.addElement(p.getProductName() + "\n");
				}
			} else if (choix == "All") {
				Collections.sort(store.getProductList(), pc);
				for (Product p : allList) {
					productListArea.addElement(p.getProductName() + "\n");
				}
			}
			break;

		case "key2":
			currentProductArea.setText("");
			if (productListA.getSelectedIndex() != -1) {
				String data = (String) productListA.getSelectedValue().replaceAll("\n", "");
				currentProductListArea.setText("");

				for (Product p : allList) {
					if (data.contains(p.getProductName())) {

						if (p.getClass().getName() == "store.business.Book") {
							currentProductListArea.append("Name : " + data + "\nPrice : " + p.getPrice() + "€"
									+ "\nStock : " + p.getProductStock() + "\nAuthor : " + ((Book) p).getBookAuthor()
									+ "\nPages : " + ((Book) p).getBookPages() + "\nLanguage : "
									+ ((Book) p).getBookLanguage());
						} else if (p.getClass().getName() == "store.business.DVD") {
							currentProductListArea.append("Name : " + data + "\nPrice : " + p.getPrice() + "€"
									+ "\nStock : " + p.getProductStock() + "\nGenre : " + ((DVD) p).getGenre()
									+ "\nLength : " + ((DVD) p).getLength() + "min");

							for (int j = 0; j < ((DVD) p).getVectorActors().size(); j++) {
								String actors = ((DVD) p).getActors(j);
								currentProductListArea.append("\nActor : " + actors);
							}
						} else if (p.getClass().getName() == "store.business.Game") {
							currentProductListArea.append("Name : " + data + "\nPrice : " + p.getPrice() + "€"
									+ "\nStock : " + p.getProductStock() + "\nGameGenre : " + ((Game) p).getGameGenre()
									+ "\nPlatform : " + ((Game) p).getPlatform());
						}

						currentProductArea.append(data);
						productNameToBuy = data;
						readyProduct = true;
					}
				}

				secondRightPanel.removeAll();
				try {
					addImageToPanel(secondRightPanel, data);
				} catch (Exception ex) {
					System.out.println("Erreur lors du chargement du fichier");
					logger.log(Level.WARNING, "Cannot load the file : " + data);
				}
				secondRightPanel.updateUI();
			}
			ifReady();
			break;

		case "key3":
			clientInformationArea.setText("");
			currentClientArea.setText("");

			String text = clientTextField.getText().replaceAll("\n", "");

			String dataFirstName1 = "";
			String dataLastName1 = "";
			int cpt1 = 0;

			for (int i = 0; i < text.length(); i++) {
				if (cpt1 == 0) {
					if (text.charAt(i) != ' ') {
						dataFirstName1 = dataFirstName1 + text.charAt(i);
					} else {
						cpt1++;
					}
				} else {
					if (text.charAt(i) != ' ') {
						dataLastName1 = dataLastName1 + text.charAt(i);
					}
				}
			}

			// System.out.println(text);
			// System.out.println(dataFirstName1);
			// System.out.println(dataLastName1);

			for (Client c : clientList) {
				if ((dataFirstName1.equals(c.getFirstName()) && (dataLastName1.equals(c.getLastName())))) {
					clientInformationArea.append("FirstName : " + c.getFirstName() + "\nLastName : " + c.getLastName()
							+ "\nAddress : " + c.getAddress() + "\nUUID : " + c.getUniqueID());
					currentClientArea.append(c.getFirstName() + " " + c.getLastName());
					clientTemp = new Client(c.getFirstName(), c.getLastName(), null, null);
					boolean ex = store.searchClientExist(clientTemp);// on se connecte en cherchant si le client est
																		// bien dans la base
					logger.log(Level.INFO,
							"Client Connexion : " + clientTemp.getFirstName() + " " + clientTemp.getLastName());
					readyClient = true;
				}
			}
			ifReady();
			thirdRightPanel.updateUI();
			clientTextField.setText("");

			break;

		case "key4":
			clientInformationArea.setText("");
			currentClientArea.setText("");
			if (clientListA.getSelectedIndex() != -1) {
				String data = (String) clientListA.getSelectedValue().replaceAll("\n", "");
				clientInformationArea.setText("");

				String dataFirstName = "";
				String dataLastName = "";
				int cpt = 0;

				for (int i = 0; i < data.length(); i++) {
					if (cpt == 0) {
						if (data.charAt(i) != ' ') {
							dataFirstName = dataFirstName + data.charAt(i);
						} else {
							cpt++;
						}
					} else {
						if (data.charAt(i) != ' ') {
							dataLastName = dataLastName + data.charAt(i);
						}
					}
				}

				// System.out.println(dataFirstName);
				// System.out.println(dataLastName);

				for (Client c : clientList) {
					if ((dataFirstName.equals(c.getFirstName()) && (dataLastName.equals(c.getLastName())))) {
						clientInformationArea.append("FirstName : " + c.getFirstName() + "\nLastName : "
								+ c.getLastName() + "\nAddress : " + c.getAddress() + "\nUUID : " + c.getUniqueID());
						currentClientArea.append(c.getFirstName() + " " + c.getLastName());
						clientTemp = new Client(c.getFirstName(), c.getLastName(), null, null);
						boolean ex1 = store.searchClientExist(clientTemp);// on se connecte en cherchant si le client
																			// est bien dans la base
						logger.log(Level.INFO,
								"Client Connexion : " + clientTemp.getFirstName() + " " + clientTemp.getLastName());
						readyClient = true;
					}
				}
				thirdRightPanel.updateUI();
				ifReady();
			}
			break;

		case "key5": // to create your account
			firstNameTextField.setText("");
			lastNameTextField.setText("");
			addressTextField.setText("");

			popUpFrame.setVisible(true);
			break;

		case "key6": // to finalize his purchase
			int quantity = Integer.parseInt(numberlist.getSelectedItem().toString());
			store.buyProductGUI(productNameToBuy, quantity);
			readyProduct = false;
			readyClient = false;
			ifReady();
			currentProductArea.setText("");
			currentClientArea.setText("");
			currentProductListArea.setText("");
			clientInformationArea.setText("");
			secondRightPanel.removeAll();
			logger.log(Level.INFO, "Transaction : " + clientTemp.getFirstName() + " " + clientTemp.getLastName() + " : "
					+ productNameToBuy + " : " + quantity);
			updateStore();

			break;

		case "key9": // validation of account creation
			firstName = firstNameTextField.getText();
			lastName = lastNameTextField.getText();
			address = addressTextField.getText();

			store.writeClientXML(firstName, lastName, address); // allows to add a client in the XML file
			updateStore();

			logger.log(Level.INFO, "Account create : " + firstName + " " + lastName + " " + address);

			popUpFrame.setVisible(false);

			break;
		}
	}

	public void updateStore() {
		try {
			generalPanel.updateUI();
			store.readProductXML();
			store.readClientXML();
			store.readTransactionXML();
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "DATA CORRUPTED ! (not up to date)");
		}

		productListArea.clear();
		for (Product p : partialList) {
			productListArea.addElement(p.getProductName() + "\n");
		}

		clientListArea.clear();
		for (Client c : clientList) {
			clientListArea.addElement(c.getFirstLastName() + "\n");
		}
		System.gc();
	}

	public void ifReady() {
		if (readyProduct == true && readyClient == true)
			buyButton.setEnabled(true);
		else
			buyButton.setEnabled(false);
	}

	public void addLogoToPanel(JPanel p, String fileName) throws FileNotFoundException {

		File file = new File("./files/" + fileName);
		if (!file.exists()) {
			throw new FileNotFoundException("Can't find " + file.getPath());
		}

		ImageIcon icon = new ImageIcon(
				new ImageIcon(file.getPath()).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		JLabel label = new JLabel(icon);
		p.add(label, BorderLayout.WEST);
	}

	public void addImageToPanel(JPanel p, String fileName) throws FileNotFoundException {

		File file = new File("./files/" + fileName);
		// System.out.println(fileName);
		if (!file.exists()) {
			throw new FileNotFoundException("Can't find " + file.getPath());
		}

		ImageIcon icon = new ImageIcon(
				new ImageIcon(file.getPath()).getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT));
		JLabel label = new JLabel(icon);
		p.add(label, BorderLayout.CENTER);
	}

	/**
	 * Main (GUI) Method
	 */
	public static void main(String[] args) throws FileNotFoundException {
		try {
			for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if("Nimbus".equals(info.getName())) {	
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}
		catch(Exception e) {
			//If Nimbus is not available, you can set the GUI to another look
		}
		new StoreGUI();
	}

}
