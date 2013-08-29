package edu.columbia.coursepost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import edu.columbia.courseselection.Section;

public class PostCourse {

	private String hash;
	private String username = "";
	private String password = "";

	public PostCourse(String username, String password, Section[] sections)
			throws ClientProtocolException, IOException {
		this.username = username;
		this.password = password;

		for (Section section : sections) {

			post(section);
		}
	}

	public void post(Section section) throws ClientProtocolException,
			IOException {

		String fullCallNum = section.getSectionFull();
		fullCallNum = fullCallNum.substring(0, 4) + " "
				+ fullCallNum.substring(4, 8) + " "
				+ fullCallNum.substring(8, 9) + " "
				+ fullCallNum.substring(9, 12);
		fullCallNum = section.getCallNumber() + fullCallNum;

		int callNum = section.getCallNumber();

		// Get initial page
		DefaultHttpClient httpclient = new DefaultHttpClient();

		HttpGet httpget = new HttpGet("https://ssol.columbia.edu");

		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();

		System.out.println("Login form get: " + response.getStatusLine());

		// Print out intial page source
		BufferedReader bf = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));

		StringBuffer sourceBuffer = new StringBuffer();
		String line = "";
		while ((line = bf.readLine()) != null) {
			sourceBuffer.append(line);
		}

		String source = sourceBuffer.toString();
		System.out.println(source);

		EntityUtils.consume(entity);

		// Login
		hash = getLoginHash(source);

		HttpPost httppost = new HttpPost(
				"https://ssol.columbia.edu/cgi-bin/ssol/" + hash + "/");

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("p_r_id", hash));
		nvps.add(new BasicNameValuePair("p_t_id", "1"));
		nvps.add(new BasicNameValuePair("jsen", "Y"));
		nvps.add(new BasicNameValuePair("tran[1]_tran_name", "slin"));
		nvps.add(new BasicNameValuePair("u_id", username));
		nvps.add(new BasicNameValuePair("u_pw", password));
		nvps.add(new BasicNameValuePair("submit", "Continue"));
		nvps.add(new BasicNameValuePair("reset", "Clear"));

		httppost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));

		response = httpclient.execute(httppost);
		entity = response.getEntity();

		System.out.println("Login form get: " + response.getStatusLine());

		bf = new BufferedReader(new InputStreamReader(response.getEntity()
				.getContent()));

		sourceBuffer = new StringBuffer();
		line = "";
		while ((line = bf.readLine()) != null) {
			sourceBuffer.append(line);
		}

		source = sourceBuffer.toString();

		EntityUtils.consume(entity);

		System.out.println(source);

		// Go to registration page
		String hiddenHash = getHiddenHash(source);

		httpget = new HttpGet(
				"https://ssol.columbia.edu/cgi-bin/ssol/"
						+ hash
						+ "/?p_r_id="
						+ hiddenHash
						+ "&p_t_id=1&tran%5B1%5D_entry=student&tran%5B1%5D_tran_name=sregb&tran%5B1%5D_term_id=20133&tran%5B1%5D_act=Continue%20with%20Fall%202013%20Registration");

		response = httpclient.execute(httpget);
		entity = response.getEntity();

		System.out
				.println("Registration page get: " + response.getStatusLine());

		// Print out registration page source
		bf = new BufferedReader(new InputStreamReader(response.getEntity()
				.getContent()));

		sourceBuffer = new StringBuffer();
		line = "";
		while ((line = bf.readLine()) != null) {
			sourceBuffer.append(line);
		}

		source = sourceBuffer.toString();

		System.out.println(source);

		EntityUtils.consume(entity);

		hiddenHash = getHiddenHash(source);

		httpget = new HttpGet(
				"https://ssol.columbia.edu/cgi-bin/ssol/"
						+ hash
						+ "/?p_r_id="
						+ hiddenHash
						+ "&p_t_id=1&tran%5B1%5D_entry=student&tran%5B1%5D_tran_name=sreg&tran%5B1%5D_act=Query%2FAdd+Class&tran%5B1%5D_CALLNUM="
						+ callNum + "&tran%5B1%5D_act=Query%2FAdd+Class");

		response = httpclient.execute(httpget);
		entity = response.getEntity();

		System.out.println("Course page get: " + response.getStatusLine());

		// Print out course page source
		bf = new BufferedReader(new InputStreamReader(response.getEntity()
				.getContent()));

		sourceBuffer = new StringBuffer();
		line = "";
		while ((line = bf.readLine()) != null) {
			sourceBuffer.append(line);
		}

		source = sourceBuffer.toString();

		System.out.println(source);

		EntityUtils.consume(entity);

		hiddenHash = getHiddenHash(source);

		// Post course request
		httppost = new HttpPost("https://ssol.columbia.edu/cgi-bin/ssol/"
				+ hash + "/");

		nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("p_r_id", hiddenHash));
		nvps.add(new BasicNameValuePair("p_t_id", "1"));
		nvps.add(new BasicNameValuePair("tran[1]_entry", "student"));
		nvps.add(new BasicNameValuePair("tran[1]_tran_name", "sreg"));
		nvps.add(new BasicNameValuePair("tran[1]_CALLNUM", fullCallNum));
		nvps.add(new BasicNameValuePair("tran[1]_fixp", "4.00"));
		nvps.add(new BasicNameValuePair("tran[1]_PASSFAIL", "N"));
		nvps.add(new BasicNameValuePair("tran[1]_act", "Add Class"));

		httppost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));

		response = httpclient.execute(httppost);
		entity = response.getEntity();

		System.out.println("Register post: " + response.getStatusLine());

		bf = new BufferedReader(new InputStreamReader(response.getEntity()
				.getContent()));

		sourceBuffer = new StringBuffer();
		line = "";
		while ((line = bf.readLine()) != null) {
			sourceBuffer.append(line);
		}

		source = sourceBuffer.toString();

		EntityUtils.consume(entity);

		System.out.println(source);

	}

	private String getLoginHash(String source) throws IOException {

		Pattern pattern = Pattern
				.compile(".*\\/cgi-bin\\/ssol\\/([A-Za-z0-9]*)\\/");
		Matcher matcher = pattern.matcher(source);

		if (matcher.find()) {
			return matcher.group(1);
		}

		return "";
	}

	private String getHiddenHash(String source) {

		Pattern pattern = Pattern
				.compile("\\/\\?p\\%\\.5Fr\\%\\.5Fid\\=([A-Za-z0-9]*)\\&");

		Matcher matcher = pattern.matcher(source);

		if (matcher.find()) {
			return matcher.group(1);
		}

		return "";
	}
}
