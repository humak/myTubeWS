package myRESTwsClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.ws.rs.core.MediaType;
import java.io.OutputStream;
import sun.net.www.protocol.http.HttpURLConnection;

public class MyRESTwsMain {
	public static void main(String[] args) {
		
		//GET!
		try {
			URL url = new URL ("http://localhost:8080/myRESTwsWeb/rest/user/userName/passWord");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("PUT");
			conn.setRequestProperty("Accept", MediaType.TEXT_PLAIN);
			
			if(conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed: HTTP error code: " + conn.getResponseCode()); }
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String output;
			while((output = br.readLine()) != null)
			{
			System.out.println("\nClient text_plain: " + output );
			}
			conn.disconnect();
		} 
		catch (MalformedURLException e) { e.printStackTrace(); } 
		catch (IOException e) { e.printStackTrace();}
		
		
		try {
			URL url = new URL ("http://localhost:8080/myRESTwsWeb/rest/json/UsuariClient");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", MediaType.APPLICATION_JSON);
			
			if(conn.getResponseCode() != 200){
			throw new RuntimeException("Failed: HTTP error code: " + conn.getResponseCode()); }
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String output;
			while((output = br.readLine()) != null)
			{
			System.out.println("\nClient json: " + output ); }
			conn.disconnect();
			
			} 
		catch (MalformedURLException e) { e.printStackTrace(); } 
		catch (IOException e) { e.printStackTrace(); }
		
		
		try {
			URL url = new URL ("http://localhost:8080/myRESTwsWeb/rest/post");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			
			String input = "{\"codi\":3,\"nom\":\"pepito\"}";
			
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			
			if(conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) 
			{
			throw new RuntimeException("Failed: HTTP error code: " + conn.getResponseCode()); 
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String output;
			while((output = br.readLine()) != null)
			{
			System.out.println("\nClient POST. Resposta: " + output ); }
			conn.disconnect();
			
			} 
		catch (MalformedURLException e) { e.printStackTrace();} 
		catch (IOException e) { e.printStackTrace(); }
		
		
	}
}
