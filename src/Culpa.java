import java.io.IOException;
import java.util.HashMap;

import com.google.gson.Gson;

/**
 * A tool to utilize Culpa
 * @author Xingzhou He
 *
 */
public class Culpa {

	private static String culpaURL = "https://s3.amazonaws.com/culpa/professors.json";
	
	private HashMap<String, Integer> list;
	
	/**
	 * Default constructor. Construct the list of professors.
	 */
	public Culpa() {
		list = new HashMap<String, Integer>();
		constructList();
	}
	
	/**
	 * Construct the List
	 */
	private void constructList()
	{
		System.out.println("Culpa: Try to construct list of profeesors on Culpa");
		String result = null;
		try {
			result = HttpRequestor.getRequest(culpaURL);
			Gson gson = new Gson(); // Initiate parser
			CulpaItem[] info = gson.fromJson(result, CulpaItem[].class);
			
			for (CulpaItem i : info)
			{
				list.put(i.getConventionalName(), i.getId());
			}
		} catch (Exception e) {
			System.out.println("Request failed");
			e.printStackTrace();
		} finally {
			System.out.println("Culpa: Finished. " + list.size() + " profesors are in.");
		}

	}
	
	/**
	 * Get the id of professor in Culpa
	 * @param professor Name of the professor (Last, first, 
	 * @return id. Null if not found
	 */
	public Integer get(String professor) {
		String[] conventional = professor.trim().split(" ");
		if (conventional.length<2)
			return null;
		return list.get((conventional[0]+" "+conventional[1]).toUpperCase());
	}
	
	/**
	 * A class to be handled by GSON to parse. Fields not following naming
	 * convention to follow the JSON format.
	 */
	private class CulpaItem {
		private int id;
		private String first_name;
		private String last_name;
		private String middle_name;
		
		public int getId() {
			return id;
		}
		
		public String getConventionalName() {
			return last_name.toUpperCase()+", "+first_name.toUpperCase();
		}

	}
	
}
