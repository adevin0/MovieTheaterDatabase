package JSONParsing;
import java.util.ArrayList;

import main.Movie;
import main.Play;
import main.Show;
import main.Theaters;
import main.User;
/**
 * 
 * @author Devin Adams
 * This class handles the instances of our json parsing. It creates
 * new arraylists of our class objects and loads the data from the
 * their json into them to be used across the app. Also hands adding
 * objects into the arraylist and saving them to the json.
 */
public class DataLists {
	private static DataLists dataLists = null;
	private static ArrayList<Movie> movies = new ArrayList<Movie>();
	private static ArrayList<Play> plays = new ArrayList<Play>();
	private static ArrayList<Theaters> theaters = new ArrayList<Theaters>();
	private static ArrayList<User> users = new ArrayList<User>();
	/**
	 * This constructor loads the arraylists with values from the DataLoader class
	 */
	private DataLists() {
		movies = DataLoader.loadMovies();
		plays = DataLoader.loadPlays();
		theaters = DataLoader.loadTheaters();
		users = DataLoader.loadUsers();
	}
	
	/**
	 * 
	 * @return DataList instance
	 */
	public static DataLists getInstance() {
		if(dataLists == null) {
			dataLists = new DataLists();
		}
		return dataLists;
	}
	public ArrayList<Play> getPlays() {
		return plays;
	}
	
	public ArrayList<Movie> getMovie() {
		return movies;
	}
	
	public ArrayList<Theaters> getTheaters() {
		return theaters;
	}
	
	public ArrayList<User> getUsers() {
		return users;
	}
	
	/**
	 * Adds movie too the datawriter to save to json
	 * @param id
	 * @param title
	 * @param length
	 * @param genre
	 * @param director
	 * @param rating
	 * @param reviews
	 * @param showTimes
	 * @param inTheaters
	 * @param ageRating
	 * @param releaseYear
	 */
	public void addMovie(int id, String title, int length, String genre, String director, int rating, ArrayList<String> reviews,ArrayList<String> showTimes, ArrayList<String> inTheaters, String ageRating, int releaseYear) {
		movies.add(new Movie(id, title, length, genre, director, rating, reviews, showTimes, inTheaters, ageRating, releaseYear));
		DataWriter.saveMovies();
	}
	/**
	 * Adds play to the datawriter to save to json
	 * @param id
	 * @param title
	 * @param length
	 * @param genre
	 * @param director
	 * @param rating
	 * @param reviews
	 * @param showTimes
	 * @param inTheaters
	 * @param amountActors
	 * @param timesPerformed
	 */
	public void addPlay(int id, String title, int length, String genre, String director, int rating, ArrayList<String> reviews, ArrayList<String> showTimes, ArrayList<String> inTheaters, int amountActors, int timesPerformed) {
		plays.add(new Play(id, title, length, genre, director, rating, reviews, showTimes, inTheaters, amountActors, timesPerformed));
		DataWriter.savePlays();
	}
	
	/**
	 * adds theater to the datawriter to save json
	 * @param id
	 * @param name
	 * @param ratings
	 * @param reviews
	 * @param employeeID
	 */
	public void addTheater(int id, String name, int ratings, ArrayList<String >reviews, int employeeID) {
		theaters.add(new Theaters(id, name, ratings, reviews, employeeID));
		DataWriter.saveTheaters();
	}
	/**
	 * adds user to the datawriter to save json
	 * @param username
	 * @param password
	 * @param email
	 * @param age
	 * @param points
	 * @param employeeID
	 * @param discountType
	 * @param shoppingCart
	 * @param ticketCount
	 */
	public void addUser(String username, String password, String email, int age, int points, int employeeID, int discountType, ArrayList<String> shoppingCart, ArrayList<String> ticketCount) {
		users.add(new User(username, password, email, age, points, employeeID, discountType, shoppingCart, ticketCount));
		DataWriter.saveUsers();
	}
}
