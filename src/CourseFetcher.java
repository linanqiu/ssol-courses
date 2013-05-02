import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Fetch course from adicu.com
 * @author Xingzhou He (xh2187)
 *
 */
public class CourseFetcher {

	private static String key = "517c1dd79812ae0002794fe5";
	private static String courseURL = "http://data.adicu.com/courses/v2/courses?api_token=" + key;
	private static String sectionURL = "http://data.adicu.com/courses/v2/sections?api_token=" + key;
	
	/**
	 * Prevent creating instances
	 */
	private CourseFetcher() {
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
	public static Course getCourseByNumber(String dept, int courseNumber, String term) throws IOException {
		
		String urlString = courseURL + "&limit=1" + "&course=" + courseNumber;
		if (dept!=null)
			urlString += "&department=" + dept;
		
		String httpResult = httpGetRequest(urlString);
		
		Gson gson = new Gson();
		JsonObject httpJson = gson.fromJson(httpResult, JsonObject.class);
		if (httpJson.getAsJsonPrimitive("status_code").getAsInt()!=200)
			throw new IOException(); //HTTP Return not 200;
		if (httpJson.getAsJsonArray("data").size() == 0)
			return null;
		Course result = gson.fromJson(httpJson.getAsJsonArray("data").get(0), Course.class); // Automatic parser provided by GSON
		result.limitTerm(term);
		
		return result;
		
	}
	
	public static Section getByCallNumber(int callNumber) throws IOException {
		return null;
	}
	
	public static Course[] getByKeyWord(String dept, String key, String term) throws IOException {
		String urlString = courseURL + "&limit=1" + "&title=" + key;
		if (dept!=null)
			urlString += "&department=" + dept;
		
		String httpResult = httpGetRequest(urlString);
		
		Gson gson = new Gson();
		JsonObject httpJson = gson.fromJson(httpResult, JsonObject.class);
		if (httpJson.getAsJsonPrimitive("status_code").getAsInt()!=200)
			throw new IOException(); //HTTP Return not 200;
		Course[] result = gson.fromJson(httpJson.getAsJsonArray("data"), Course[].class); // Automatic parser provided by GSON
		for (Course i :result)
			i.limitTerm(term);
		return result;
	}
	
}
