import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Fetch course from adicu.com
 * @author Xingzhou He (xh2187)
 *
 */
public class CourseFetcher {

	private static String key = "517c1dd79812ae0002794fe5";
	private static String courseURL = "http://data.adicu.com/courses/v2/courses?api_token=" + key;
	private static String sectionURL = "http://data.adicu.com/courses/v2/sections?api_token=" + key;
	
	private Gson gson;
	
	public CourseFetcher() {
		gson = new GsonBuilder()
			.registerTypeAdapter(Date.class, new DateAdapter())
			.create();
		
	}
	
	/**
	 * Simple program to make GET request
	 * @param urlString URL of target
	 * @return Result
	 */
	private static String httpGetRequest(String urlString) throws IOException
	{
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
		String result = "", line;
		while ((line = reader.readLine()) != null)
			result += line;
		
		reader.close();
		return result;
		
	}
	
	/**
	 * Return a Course object corresponding to the term
	 * @param dept Department code
	 * @param courseNumber The course number
	 * @param term Term code to limit search
	 * @return A course object if found, or null
	 * @throws IOException
	 */
	public Course getCourseByNumber(String dept, String courseNumber, String term) throws IOException {
		
		String urlString = courseURL + "&limit=1" + "&course=" + courseNumber;
		if (dept!=null)
			urlString += "&department=" + dept;
		
		String httpResult = httpGetRequest(urlString);
		
		JsonObject httpJson = gson.fromJson(httpResult, JsonObject.class);
		if (httpJson.getAsJsonPrimitive("status_code").getAsInt()!=200)
			throw new IOException(); //HTTP Return not 200;
		if (httpJson.getAsJsonArray("data").size() == 0)
			return null;
		Course result = gson.fromJson(httpJson.getAsJsonArray("data").get(0), Course.class); // Automatic parser provided by GSON
		result.postProcess(term);
		
		return result;
		
	}
	
	/**
	 * Return a Section object corresponding to the full number
	 * @param dept Department code
	 * @param courseNumber The course number
	 * @param term Term code to limit search
	 * @param secNumber Section to search for
	 * @return A Section object if found, or null
	 * @throws IOException
	 */
	public Section getSectionByCourseNumber(String dept, String courseNumber, String secNumber, String term) throws IOException {
		Course result = getCourseByNumber(dept, courseNumber, term);
		if (result == null) // Not found
			return null;
		Section[] sections = result.getSections();
		for (Section i : sections)
			if (i.getSectionFull().contains(secNumber))
				return i;
		return null; // Not found
	}
	
	/**
	 * Get a section by its call number
	 * @param callNumber callNumber of the section queried.
	 * @return A Section object, or null if the call number does not exist
	 * @throws IOException
	 */
	public Section getByCallNumber(int callNumber) throws IOException {
		String urlString = sectionURL + "&limit=1&call_number=" + callNumber;
		
		String httpResult = httpGetRequest(urlString);
		
		JsonObject httpJson = gson.fromJson(httpResult, JsonObject.class);
		if (httpJson.getAsJsonPrimitive("status_code").getAsInt()!=200)
			throw new IOException(); // Error from remote server
		
		JsonArray jsonSections = httpJson.getAsJsonArray("data");
		if (jsonSections.size() == 0) // Not Found
			return null;
		else
		{
			String courseNumber = jsonSections.get(0).getAsJsonObject().get("Course").getAsString();
			String term = jsonSections.get(0).getAsJsonObject().get("Term").getAsString();
			
			Course result = getCourseByNumber(null, courseNumber, term); // Find the corresponding course
			Section[] sections = result.getSections();
			for (Section i : sections)
				if (i.getCallNumber() == callNumber)
					return i;
			return null; // Not found
		}
	}
	
	/**
	 * Get available Courses by keyword
	 * @param dept Department (null if not limited)
	 * @param key Keyword of the query
	 * @param term Term string
	 * @return An array of courses
	 * @throws IOException
	 */
	public Course[] getCoursesByKeyword(String dept, String key, String term) throws IOException {
		String urlString = courseURL + "&limit=50" + "&title=" + key;
		if (dept!=null)
			urlString += "&department=" + dept;
		
		String httpResult = httpGetRequest(urlString);
		
		JsonObject httpJson = gson.fromJson(httpResult, JsonObject.class);
		if (httpJson.getAsJsonPrimitive("status_code").getAsInt()!=200)
			throw new IOException(); //HTTP Return not 200;
		Course[] result = gson.fromJson(httpJson.getAsJsonArray("data"), Course[].class); // Automatic parser provided by GSON
		for (Course i :result)
			i.postProcess(term);
		return result;
	}
	
	/**
	 * Deserialize Date correctly
	 */
	private static class DateAdapter implements JsonDeserializer<Date> {

		@Override
		public Date deserialize(JsonElement json, Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {
			SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
			try {
				return formatter.parse(json.getAsJsonPrimitive().getAsString());
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
		
	}
}
