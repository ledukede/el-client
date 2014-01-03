package de.hska.eb.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hska.eb.domain.Comment;
import de.hska.eb.domain.Event;
import de.hska.eb.domain.File;
import de.hska.eb.domain.User;

public class EventService {
	
	public Event getEventById(int id) throws ClientProtocolException, IOException, IllegalStateException, JSONException {
		HttpClient httpclient = new DefaultHttpClient();
	      HttpGet httpGet = new HttpGet(Config.basePath+"/events/"+id);
	      HttpResponse response = httpclient.execute(new HttpHost(Config.host), httpGet);
	      
	      JSONObject json = new JSONObject(RestClient.responseToString(response));
	      Event event = new Event();
			event.fromJsonObject(json);
			return event;
	}
	
	public List<Event> getEventsByName(String name) {
		return null;
	}
	
	public List<Event> getRecentEvents() throws ClientProtocolException, IOException, IllegalStateException, JSONException {
		HttpClient httpclient = new DefaultHttpClient();
	      HttpGet httpGet = new HttpGet(Config.basePath+"/events/recently");
	      HttpResponse response = httpclient.execute(new HttpHost(Config.host), httpGet);
	      
	      JSONArray json = new JSONArray(RestClient.responseToString(response));
	      
	      List<Event> events = new ArrayList<Event>();
	      for (int i = 0; i < json.length(); i++) {
			JSONObject jo = json.getJSONObject(i);
			Event event = new Event();
			event.fromJsonObject(jo);
			events.add(event);
	      }
	      return events;
	}
	
	public List<Event> getEventsByDate(Date date) {
		return null;
	}
	
	public List<User> getGuestsByEventId(int id) {
		return null;
	}
	
	public List<Comment> getCommentsByEventId(int id) {
		return null;
	}
	
	public void voteForEvent(int eventId, int vote) {
		
	}
	
	public void commentEvent(int eventId, Comment comment) {
		
	}
	
	public void addEvent(Event event) {
		
	}
	
	public void updateEvent(Event event) {
		
	}
	
	public void deleteEvent(Event event) {
		
	}
	
	public File downloadPicByEventId(int id) {
		return null;
	}
	
	public void uploadPicByEventId(int id, File file) {
		
	}
	
}
