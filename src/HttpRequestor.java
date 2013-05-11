import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Handles html requests
 * @author Xingzhou He (xh2187)
 *
 */
public final class HttpRequestor {

	private HttpRequestor() {
	}
	
	/**
	 * Simple program to make GET request
	 * @param urlString URL of target
	 * @return Result
	 */
	public static String getRequest(String urlString) throws IOException
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
	
}
