package edu.columbia.coursepost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		while (true) {

			String hash = getHash("https://ssol.columbia.edu");
			System.out.println(hash);

		}
	}

	private static String getHash(String url) throws IOException {
		String source = getUrlSource(url);

		Pattern pattern = Pattern
				.compile(".*\\/cgi-bin\\/ssol\\/([A-Za-z0-9]*)\\/");
		Matcher matcher = pattern.matcher(source);

		if (matcher.find()) {
			return matcher.group(1);
		}

		return "";
	}

	private static String getUrlSource(String url) throws IOException {
		URL ssol = new URL(url);
		URLConnection yc = ssol.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				yc.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuilder a = new StringBuilder();
		while ((inputLine = in.readLine()) != null)
			a.append(inputLine);
		in.close();

		return a.toString();
	}

}
