package services;

public class main {

	public static final String METHOD = "GET";
	public static final String URL = "http://localhost:8081/s2/myAPI/service/test";
	
	public static void main(String [] args) {
		String response = null;
		try {
			response = new TestServer().test(URL, METHOD);
		} catch (Exception e) {
			System.err.println("Exception occured: " + e.toString());
			e.printStackTrace();
		}
		
		System.out.println("response " + response);
	}
}
