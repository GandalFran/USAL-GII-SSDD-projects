package client;

import java.util.Arrays;
import java.util.Map;

import services.NTPServer;
import services.NTPServerProxy;;

public class Main {

	public static NTPClient buildClient(boolean runMarzullo) {
		if(runMarzullo)
			return new NTPMarzulloClient();
		else
			return new NTPClient();
	}
	
	public static NTPServer [] buildServers(String [] serviceUris) {
		NTPServer [] servers = new NTPServer [serviceUris.length];
		
		for(int i=0; i<serviceUris.length; i++) {
			servers[i] = NTPServer.buildProxy(serviceUris[i]);
		}
		
		return servers;
	}

	public static String formatResult(Map<NTPServer, Pair>result) {
		StringBuilder sb = new StringBuilder();

		String separator = String.format("+ %50s + %20s + %30s +", " ", " ", " ").replace(' ', '=');
		
		sb.append("\n").append(separator);
		sb.append("\n").append(String.format("| %50s | %20s | %30s |", "uri", "delay", "offset"));
		sb.append("\n").append(separator);
		for(NTPServer server : result.keySet())
			sb.append("\n").append( String.format("| %50s | %20d | %30f |", 
							((NTPServerProxy)server).getServiceUri(), 
							result.get(server).getDelay(), 
							result.get(server).getOffset()
							));
		sb.append("\n").append(separator);
		return sb.toString();
	}
	
	
	public static void main(String [] args) {
		int numIterations = Integer.parseInt(args[0]);
		boolean runMarzullo = Boolean.parseBoolean(args[1]);
		String [] serviceUris = Arrays.copyOfRange(args, 2, args.length);
		
		NTPClient client = Main.buildClient(runMarzullo);
		NTPServer [] servers = Main.buildServers(serviceUris);
		
		Map<NTPServer, Pair>result = client.estimate(servers, numIterations);
		String resultStr = Main.formatResult(result);
		System.out.println(resultStr);		
		
	}
}

