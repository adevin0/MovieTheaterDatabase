import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
public class DataWriter extends DataConstants {
	
	
	public static void saveShows() {
		ShowList showList = ShowList.getInstance();
		ArrayList<Show> titles = showList.getShows();
		JSONArray jsonTitles = new JSONArray();
		
		for (int i = 0; i < titles.size(); i++) {
			jsonTitles.add(getShowsJSON(titles.get(i)));
		}
		
		//write json
		try (FileWriter file = new FileWriter(SHOW_FILE_NAME)){
			file.write(jsonTitles.toJSONString());
			file.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static JSONObject getShowsJSON(Show show) {
		JSONObject showDetails = new JSONObject();
		showDetails.put(SHOW_TITLE, show.getTitle());
		showDetails.put(SHOW_LENGTH, show.getLength());
		showDetails.put(SHOW_RELEASE_YEAR, show.getReleaseYear());
		showDetails.put(SHOW_GENRE, show.getGenre());
		showDetails.put(SHOW_DIRECTOR, show.getDirector());
		showDetails.put(SHOW_RATINGS, show.getRating());
		showDetails.put(SHOW_REVIEWS, show.getReviews());
		
		return showDetails;
		
	}
}
