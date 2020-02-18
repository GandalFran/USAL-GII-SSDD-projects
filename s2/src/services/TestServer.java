package services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestServer {

	public TestServer() {
		
	}
	
	public String test(String urlStr, String method) throws Exception{
		URL url = new URL(urlStr);
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		conn.setRequestMethod(method);
		
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}else {
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String chunk;
			StringBuilder result = new StringBuilder();
			while ((chunk = br.readLine()) != null) {
				result.append(chunk);
			}
			conn.disconnect();
			return result.toString();
		}
	}
}
