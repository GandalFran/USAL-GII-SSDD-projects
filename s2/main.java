package services;

public class main {

	public static final String METHOD = "POST";
	public static final String URL = "http://localhost:8080/api/v1/endpoint";
	
	public static void main(String [] args) {
		try {
			String response = new TestServer().test(URL, METHOD);
		} catch (Exception e) {
			System.err.println("Exception occured: " + e.toString());
			e.printStackTrace();
		}
	}
}
