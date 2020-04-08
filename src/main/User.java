package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import JSONParsing.DataLists;
import JSONParsing.DataWriter;

public class User {
	/**
	 * These are the current logged in users info
	 */
	public static String currUserName;
	public static String currPassword;
	public static String currEmail;
	public static int currPoints;
	public static int currAge;
	public static String workingAt;
	public static int currEmployeeID;
	/**
	 * These are some arrays for printing menus
	 */
	private String[] guestMenuOptions = {"Order Tickets", "Display Movies", "Display Shows" , "Search Shows", "Search Theaters", "View Shopping Cart", "Checkout", "Print Tickets", "Logout"};
	private String[] memberMenuOptions = {"Order Tickets", "Display Movies", "Display Shows" , "Search Shows",
										"Search Theaters", "Checkout", "Refund", "Print Tickets","View Shopping Cart", "View Ticket List", "Change Account Details", "Logout"};
	private String[] employeeMenuOptions = {"Add Show To Theater", "Update Show Times", "Display Shows", "Logout"};
	/**
	 * Variables for our User object. THESE ARE NOT THE CURRENT USER
	 */
	private String username;
	private String password;
	private String email;
	private int age;
	private int points;
	private int employeeID;
	private int discountType;
	private ArrayList<String> shoppingCart;
	private ArrayList<String> ticketCart;
	/**
	 * 
	 * @param username - Users username
	 * @param password - Users password
	 * @param email - Users email
	 * @param age - Users age
	 * @param points - Users points
	 */
	public User(String username, String password, String email, int age, int points, int employeeID, int discountType, ArrayList<String> shoppingCart, ArrayList<String> ticketCart) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.age = age;
		this.points = points;
		this.employeeID = employeeID;
		this.discountType = discountType;
		this.shoppingCart = shoppingCart;
		this.ticketCart = ticketCart;
	}
	
	public void updateCurrentUser(String currUserName,String currPassword, String currEmail, int currPoints, int currAge) {
		User.currUserName = currUserName;
		User.currPassword = currPassword;
		User.currEmail = currEmail;
		User.currPoints = currPoints;
		User.currAge = currAge;
	}
	
	public void userMainMenu() {
		System.out.println("\nMovie Theater Application ");
		int currentUserType = getUserType();
		if (currentUserType == 0) {
			printGuestMenu();
		} else if (currentUserType == 1) {
			printMemberMenu();
		} else if (currentUserType == 2) {
			printEmployeeMenu();
		}
	}
	
	private int getUserType() {
		DataLists dataLists = DataLists.getInstance();
		ArrayList<User> userList = dataLists.getUsers();
		if(currUserName == null) {
			currEmployeeID = 0;
			return 0;
		} 
		for(User users : userList) {
			if ((users.getUsername() == currUserName) && (users.getEmployeeID() == 0)) {
				currEmployeeID = 0;
				return 1;
			} else if ((users.getUsername() == currUserName) && (users.getEmployeeID() != 0)) {
				currEmployeeID = users.getEmployeeID();
				updateEmployeeWorking();
				return 2;
			}
		}
		System.out.println("Error! Unable to get the user type. Proceeding as guest. (How did this happen?)");
		return 0;
	}
	
	private void updateEmployeeWorking() {
		DataLists dataLists = DataLists.getInstance();
		ArrayList<Theaters> theaterList = dataLists.getTheaters();
		for(Theaters theaters : theaterList) {
			if(theaters.getEmployeeID() == currEmployeeID) {
				workingAt = theaters.getTitle();
				return;
			}
		}
	}
	
	private void printGuestMenu() {
		System.out.println("\nMember: Guest");
		System.out.println("=========================");
		for (int i = 0; i < guestMenuOptions.length; i++) {
			System.out.println(guestMenuOptions[i]);
		}
		System.out.println("=========================");
	}
	
	private void printMemberMenu() {
		System.out.println("\nMember: " + currUserName + " Points: " + currPoints);
		System.out.println("=========================");
		for(int i = 0; i < memberMenuOptions.length; i++) {
			System.out.println(memberMenuOptions[i]);
		}
		System.out.println("=========================");
	}
	
	private void printEmployeeMenu() {
		System.out.println("\nMember: " + currUserName + " Theater: " + workingAt);
		System.out.println("=========================");
		for(int i = 0; i < employeeMenuOptions.length; i++) {
			System.out.println(employeeMenuOptions[i]);
		}
		System.out.println("=========================");
	}
	
	public void displayCurrentUserInfo() {
		System.out.println("\n UserName: " +currUserName + "\n Password: " + currPassword + "\n Email: " + currEmail + "\n Point total: " + currPoints + "\n Age: " + currAge);
	}
	/**
	 * Updates the username of the current logged in user and good example of how
	 * to change data in our jsons
	 */
	public void updateUserName() {
		Scanner scanner = new Scanner(System.in);
		DataLists dataLists = DataLists.getInstance(); //Pulls instance of the data list class
		ArrayList<User> userList = dataLists.getUsers(); //Grabs the current list of users/movies/plays
		System.out.println("******** Updating Username ********");
		for(User users : userList) {
			if (users.getUsername() == currUserName) { 
				System.out.println("Enter Username: ");
				users.setUsername(scanner.nextLine());
				DataWriter.saveUsers(); // need to call this to save to our json savePlays() / saveMovies() etc...
			}
		}
		
		System.out.println("You must log back in with your new info to update the changes. \n Type 'logout' to logout");
	}
	/**
	 * Allow the user to pick a movie and a theater playing that movie. As well as it's showtime
	 * Only need the info from Movie/Play ArrayList now. ie.) movies.getTitle() / movies.getShowTimes() / movies.inTheaters()
	 * Append them to variables and you can add it to the users shopping cart users.getShoppingCart() etc....
	 * 
	 */
	public void orderTicket() {
		
	}
	
	/**
	 * Example function for adding a review to a movie
	 */
	public void testFunction() {
		System.out.println("Command Fired"); //just testing to see if they command is executed
		DataLists dataLists = DataLists.getInstance(); //Pulls instance of the data list class. This is important because it gives us access to load commands
		ArrayList<Movie> movieList = dataLists.getMovie(); //grabbing the Movie arraylist from json.
		String searchMovie = "Frozen 2"; //dummy variable for testing
		for(Movie movie : movieList) { //for each loop going through the movie arraylist
			if (movie.getTitle().contentEquals(searchMovie)) { //checking if the movie its searching for exists
				System.out.println("found movie"); //test print out to see if its finding a movie
				ArrayList<String> addReview = movie.getReviews(); //creating an arraylist and setting it equal to the current reviews of the movie ***reviews is an array inside the json so this is why its like that****
				addReview.add("this is testing adding a new review to frozen2"); //adding to the addReview list. Can be any string this is just a test example. Since addReview is a string arraylist it takes in strings
				movie.setReviews(addReview); //calls the mutator for the movie revies and sets it equal to the new arraylist addReview. Remember addReview is equal to the old movie.getReview() so it adds to it this way
				DataWriter.saveMovies(); // Please call this whenever you deal with adding/removing from an ArrayList<Movie/Play/Theater/user> or else it wont save to that current one and the changes wont be reflected in the jsons
			}
		}
	}
	/**
	 * Example function for adding a theater to a shows playing at array = movies/plays.getInTheater()
	 */
	public void testAddTheaterToShowFunction() {
		DataLists dataLists = DataLists.getInstance(); //we know what this does now
		ArrayList<Movie> movieList = dataLists.getMovie(); //grabs movie arraylist from json
		ArrayList<Play> playList = dataLists.getPlays(); //grabs play arraylist from json - dont really use this one in this case but here for example
		ArrayList<Theaters> theaterList = dataLists.getTheaters(); //grabs theater from arraylist
		String theaterToShow = "Spark Theater"; //hard coded variable for testing which theater we want to add to the shows [now playing]
		String showToAddTheater = "The Lion King"; //hard coded variable for testing which movie we want to append the theater to
		boolean valid = false;
		for(Theaters theater : theaterList) {
			if (theater.getTitle().contentEquals(theaterToShow)) { //checking to make sure the theater is a valid theater
				System.out.println("Theater Valid!"); //if theater is valid(in our theater array list) print this
				valid = true; //setting valid to true;
				break; //breaking out of the loop so we dont continue to search for no reason
			} 
		}
		if(!valid) { //if valid is still false (we never found a theater) print out invalid theater and return(exit funciton)
			System.out.println("Invalid Theater");
			return;
		}
		for(Movie movie : movieList) {
			if (movie.getTitle().contentEquals(showToAddTheater)) { //checking if its a valid movie (in our movie arraylist)
				ArrayList<String> addTheater = movie.getInTheaters(); //creating a new array list equal to the current array in the movie json object "inTheaters"
				if(!addTheater.contains(theaterToShow)) { //if the theater is not already in that "inTheaters" array in the json then continue
					System.out.println("Adding " +showToAddTheater+ " to play at " +theaterToShow); //printing out which movie is getting added
					addTheater.add(theaterToShow); //adding the theater to the "inTheaters" copy array list we created
					movie.setInTheaters(addTheater); //setting the newly created array list to the current selected movies "inTheaters" array
					DataWriter.saveMovies();
				} else {
					System.out.println("That theater is already playing that movie"); //if the theater is already playing that movie then print thsi
				}
			}
		}
		
	}
	/**
	 * Example function for adding tickets to the shopping cart and then adding that to the member users owned ticket list/cart
	 */
	public void testArrayListSizeAndRemoving() {
		DataLists dataLists = DataLists.getInstance();
		ArrayList<User> userList = dataLists.getUsers();
		for(User users: userList) {
			if((users.getShoppingCart().contains("Empty Cart")) && (users.getUsername() == currUserName)) { //Default value for users shopping and ticket cart is "Empty Cart". If cart is empty then proceed. currUserName is assigned when you login with an account if guest account then it will be null for now
				System.out.println("Adding tickets to empty cart"); //printing test
				ArrayList<String> addTickets = users.getShoppingCart(); //array list set equal to the current shopping cart
				addTickets.remove(0); //removing the Empty Cart object since in this if statement it should always be at index 0
				addTickets.add("Test Ticket1"); //adding one ticket
				addTickets.add("Test ticket2"); //adding another
				addTickets.add("Test ticket3"); //and another
				users.setShoppingCart(addTickets); //setting the shopping cart equal to addTickets
				DataWriter.saveUsers(); //saving to json
			} else if((!users.getShoppingCart().contains("Empty Cart")) && (users.getUsername().contentEquals(currUserName))) { //if its not "Empty Cart" then it will instead just add the tickets instead of removing index(0)
				System.out.println("Adding tickets");
				ArrayList<String> addTickets = users.getShoppingCart();
				addTickets.add("Test Ticket1");
				addTickets.add("Test ticket2");
				addTickets.add("Test ticket3");
				users.setShoppingCart(addTickets);
				DataWriter.saveUsers();
			}
		}
		//at this point we have a shopping cart
		for(User users : userList) {			
			if(users.getUsername() == currUserName) {
				double ticketCount = users.getShoppingCart().size(); //getting the size of the cart. aka how many tickets are inside
				System.out.println(users.getShoppingCart()); // test print out
				System.out.println("Your total is: $" + ticketCount*7.50); //multiplying by 7.50 to simlute the cost of each ticket
				System.out.println("Your tickets will now be removed from cart and added to your ticket list"); //emptying the shopping cart. to simulate checking out and added tickets to your ticketCart 
				ArrayList<String> removeTickets = users.getShoppingCart(); //removeTickets now equal to shopping cart
				ArrayList<String> addTickets = new ArrayList<String>(); //new blank array list so we can safely copy / empty the removeTickets list without dealing with copy values(if we were to make it ArrayList<String> addTickets = users.getShoppingCart() then it would act as a pointer and thats annoying
				if (users.getTicketCart().contains("Empty_Cart")) { //if your current ticket cart is empty the remove the empty cart index
					users.getTicketCart().remove(0); //0 is the index of empty cart
				}
				for(int i = 0; i < users.getShoppingCart().size(); i++) { //indivdually adding the tickets from removeTickets to addTickets to prevent issues with pointers
					addTickets.add(removeTickets.get(i));
				}
				System.out.println(addTickets); //test print
				users.setTicketCart(addTickets); //finalizing our setTicketCart value in the json
				removeTickets.clear(); //deleting our shopping cart
				removeTickets.add("Empty Cart"); //setting it to empty cart to prevent some null issues in the jsons i dont wanna deal with anymore
				users.setShoppingCart(removeTickets); //finalizing our new empty shopping cart
				DataWriter.saveUsers(); //saving to our json
			}
		}
	}
	/**
	 * Will access the member users ticketCart() and remove the ticket and refund the money to the user [if you want to deal with a users wallet amount you can add that into the jsons/parsing yourself. Im ignoring that]
	 */
	public void requestRefund() {
		DataLists dataLists = DataLists.getInstance();
		ArrayList<User> userList = dataLists.getUsers();
		for(User users : userList) {
			double total = 0.0;
			if(users.getUsername() == currUserName) {
				for(int i = 0; i < users.getTicketCart().size(); i++) {
					users.getTicketCart().remove(i); //could just display message that their card will be refunded instead of dealing with a wallet
					total += 7.50;
				}
			}
			System.out.println("Your card will be refunded $" + total + " for the refunded tickets.");
		}
	}
	/**
	 * Removes the data from the users list.
	 */
	public void deleteAccount() {
		DataLists dataLists = DataLists.getInstance();
		ArrayList<User> userList = dataLists.getUsers();
		for(User users : userList) {
			if(users.getUsername() == currUserName) {
				userList.remove(users);
			}
		}
	}
	/**
	 * User json has a discountType value to it. Allow the user to input an id (discountType maybe based on int length or something) then update
	 * the value in the array list to save to the json. Then later you can compare if they user has a certain discount value.
	 */
	public void useDiscount() {
		DataLists dataLists = DataLists.getInstance();
		ArrayList<User> userList = dataLists.getUsers();
		Scanner scanner = new Scanner(System.in);
		for(User users : userList) {
			if(users.getUsername() == currUserName) {
				System.out.println("Enter your discount id.");
				int discId = scanner.nextInt();
				users.setDiscountType(discId);
				DataWriter.saveUsers();
			}
		}
	}
	/**
	 * Get the users current shopping cart and price the tickets inside. If the user is a guest then they will only be able to have the tickets printed out
	 * instead of adding to their ticket list. If they are a member then add the shopping cart values to the ticket cart for ability to refund/print/view
	 */
	public void checkout() {
		DataLists dataLists = DataLists.getInstance();
		ArrayList<User> userList = dataLists.getUsers();
		Scanner scanner = new Scanner(System.in);
		double price = 0.0;
		for(User users : userList) {
			if(users.getUsername() == currUserName) {
				for(int i = 0; i < users.getShoppingCart().size(); i++) {
					price += 7.50;
				}
				System.out.println("The total price of the tickets in your cart is $" + price);
				if(users.getUserType() == 0) {
					System.out.println("Would you like to have a printable copy of your tickets? (y/n)");
					String response = scanner.nextLine();
					if(response.equalsIgnoreCase("y"))
						users.printTicket();
				}
				else if(users.getUserType() == 1 || users.getUserType() == 2) {
					System.out.println("Options");
					System.out.println("- Press 1 to apply for a refund on your tickets.");
					System.out.println("- Press 2 to have a printable copy of your tickets.");
					System.out.println("- Press 3 to view your tickets.");
					int userResponse = scanner.nextInt();
					
					if(userResponse == 1) {
						users.requestRefund();
					}
					else if(userResponse == 2) {
						users.printTicket();
					}
					else if(userResponse == 3) {
						users.getShoppingCart();
					}
				}
			}
		}
	}
	/**
	 * Print out the ticket info to a fancy txt file. Include Movie Name/Theater Name/Show Times/Seat Location if reservered
	 */
	public void printTicket() {
		DataLists dataLists = DataLists.getInstance();
		ArrayList<Movie> movieList = dataLists.getMovie();
		try {
			FileWriter writer = new FileWriter("Print.txt", true);
			BufferedWriter bWriter = new BufferedWriter(writer);
			
			for(Movie movie : movieList) {
				bWriter.write("Movie: " + movie.getTitle());
				bWriter.newLine();
				bWriter.write("Show time: " + movie.getShowTimes());
				bWriter.newLine();
				bWriter.write("Theater: " + movie.getInTheaters());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	 * Getters and Setters for our User object
	 */
	public String getUsername() {
		return username;
	}

	public String getCurrUserName() {
		return currUserName;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public ArrayList<String> getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ArrayList<String> shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public ArrayList<String> getTicketCart() {
		return ticketCart;
	}

	public void setTicketCart(ArrayList<String> ticketCart) {
		this.ticketCart = ticketCart;
	}

	public int getDiscountType() {
		return discountType;
	}

	public void setDiscountType(int discountType) {
		this.discountType = discountType;
	}
	
}
