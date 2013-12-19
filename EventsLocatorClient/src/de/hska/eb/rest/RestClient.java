package de.hska.eb.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Response;

public class RestClient {
	
	public static String responseToString(HttpResponse response) throws IllegalStateException, IOException {
		InputStream content = null;
		content = response.getEntity().getContent();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
	    StringBuilder out = new StringBuilder();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        out.append(line);
	    }
	    return out.toString();
	}
	
}
