package services;

public class main {

	public static final String METHOD = "POST";
	public static final String URL = "http://localhost:8080/api/v1/endpoint"
	
	public static void main(String [] args) {
		TestServer t = new TestServer();
		String response = t.test(URL, METHOD);
	}
}
