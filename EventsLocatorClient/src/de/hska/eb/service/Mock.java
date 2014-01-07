package de.hska.eb.service;

import static de.hska.eb.EventsApp.jsonReaderFactory;
import static de.hska.eb.util.Constants.USER_PATH;
import static de.hska.eb.util.Constants.EVENT_PATH;
import static de.hska.eb.ui.main.Prefs.username;
import static java.net.HttpURLConnection.HTTP_CONFLICT;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_FORBIDDEN;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import android.util.Log;
import de.hska.eb.domain.Comment;
import de.hska.eb.domain.Event;
import de.hska.eb.domain.User;
import de.hska.eb.R;
import de.hska.eb.EventsApp;
import de.hska.eb.util.InternalEventslocatorError;

final class Mock {
	private static final String LOG_TAG = Mock.class.getSimpleName();
	
	private static String read(int dateinameId) {
		final BufferedReader reader = new BufferedReader(
						new InputStreamReader(EventsApp.open(dateinameId)));
		final StringBuilder sb = new StringBuilder();
		try {
			for(;;) {
				final String line = reader.readLine();
				if (line == null) {
					break;
				}
				sb.append(line);
			}
		}
		catch (IOException e) {
			throw new InternalEventslocatorError(e.getMessage(), e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
				}
				catch (IOException e) {}
			}
				
		}
		
		final String jsonStr = sb.toString();
		Log.v(LOG_TAG, "jsonStr = " + jsonStr);
		return jsonStr;
	}
	
	private Mock() {}
	
	static HttpResponse<User> getUserById(Long id) {
		
		if (id <= 0 || id >= 10000) {
			return new HttpResponse<User>(HTTP_NOT_FOUND, "No User found with id: " + id);
		}
		
		int dateinameId = R.raw.mock_users;
		final String jsonStr = read(dateinameId);
		JsonReader jsonReader = null;
		JsonObject jsonObject;
		try {
			jsonReader = jsonReaderFactory.createReader(new StringReader(jsonStr));
			jsonObject = jsonReader.readObject();
		}
		finally {
			if (jsonReader != null) {
				jsonReader.close();
			}
		}
		
		final User user = new User();
		
		user.fromJsonObject(jsonObject);
		user.id = id;
		
		final HttpResponse<User> result = new HttpResponse<User>(HTTP_OK, jsonObject.toString(), user);
		return result;
	}
	
	static HttpResponse<User> getUserByEmail(String email) {
		if (email.startsWith("X")) {
			return new HttpResponse<User>(HTTP_NOT_FOUND, "No User found with email: " + email);
		}
		
		final String jsonStr = read(R.raw.mock_users);
		JsonReader jsonReader = null;
		JsonObject jsonObject;
		try {
			jsonReader = jsonReaderFactory.createReader(new StringReader(jsonStr));
			jsonObject = jsonReader.readObject();
			Log.v(LOG_TAG, "TEST");
		}
		catch (Exception e) {
			throw new InternalEventslocatorError(e.getMessage(), e);
		}
		finally {
			if (jsonReader != null) {
				jsonReader.close();
			}
		}
		
		final User user = new User();
		user.fromJsonObject(jsonObject);
		user.email = email;
		
		final HttpResponse<User> result = new HttpResponse<User>(HTTP_OK, jsonObject.toString(), user);
		return result;
	}
	
	static HttpResponse<Event> getEventsByUserId(String path, Long id) {
		final Event event = new Event();
		event.voting = 3;
		event.version = 0;
		event.place = "Pforzheim";
		event.picUri = "http://iwi-w-eb06.hs-karlsruhe.de:8080/friendslocator/rest/events/6000/pic";
		event.name = "Mega Party";
		event.id = Long.valueOf(6000);
		event.guestsUri = "http://iwi-w-eb06.hs-karlsruhe.de:8080/friendslocator/rest/events/6000/guests";
		event.description = "YOLO";
		event.date = new Date();
		event.createrUri = "http://iwi-w-eb06.hs-karlsruhe.de:8080/friendslocator/rest/users/" + id;
		event.created = new Date();
		event.commentsUri = "http://iwi-w-eb06.hs-karlsruhe.de:8080/friendslocator/rest/events/6000/comments";
		final JsonObject jsonObject = event.toJsonObject();
		final HttpResponse<Event> result = new HttpResponse<Event>(HTTP_OK, jsonObject.toString(), event);
		Log.d(LOG_TAG, result.resultObject.toString());
		return result;
	}

	public static HttpResponse<Comment> getCommentsByUserId(String path, Long id) {
		final Comment comment = new Comment();
		comment.userUri = "http://iwi-w-eb06.hs-karlsruhe.de:8080/friendslocator/rest/user/" + id;
		comment.message = "YIHA";
		comment.id = Long.valueOf(6001);
		comment.date = new Date();
		final JsonObject jsonObject = comment.toJsonObject();
		final HttpResponse<Comment> result = new HttpResponse<Comment>(HTTP_OK, jsonObject.toString(), comment);
		Log.d(LOG_TAG, result.resultObject.toString());
		return result;
	}

	public static HttpResponse<User> createUser(User user) {
		user.id = Long.valueOf(user.email.length());
		Log.d(LOG_TAG, "createUser: " + user);
//		Log.d(LOG_TAG, "createUser: " + user.toJsonObject().toString());
		final HttpResponse<User> result = new HttpResponse<User>(HTTP_CREATED, user.id.toString(), user);
		return result;
	}

	public static HttpResponse<Comment> createCommentForUser(String path, Comment comment) {
		comment.id = Long.valueOf(comment.message.length());
		Log.d(LOG_TAG, "createComment: " + comment);
		Log.d(LOG_TAG, "createComment: " + comment.toJsonObject().toString());
		final HttpResponse<Comment> result = new HttpResponse<Comment>(HTTP_CREATED, 
				"http://iwi-w-eb06.hs-karlsruhe.de:8080/friendslocator/rest/comments/" + comment.id, comment);
		return result;
	}

	public static HttpResponse<User> updateUser(User user) {
		return new HttpResponse<User>(HTTP_NO_CONTENT, null, user);
	}

	public static HttpResponse<Event> getEventById(Long id) {

		if (id <= 0 || id >= 10000) {
			return new HttpResponse<Event>(HTTP_NOT_FOUND, "No User found with id: " + id);
		}
		
		int dateinameId = R.raw.mock_events;
		final String jsonStr = read(dateinameId);
		JsonReader jsonReader = null;
		JsonObject jsonObject;
		try {
			jsonReader = jsonReaderFactory.createReader(new StringReader(jsonStr));
			jsonObject = jsonReader.readObject();
		}
		finally {
			if (jsonReader != null) {
				jsonReader.close();
			}
		}
		
		final Event event = new Event();
		
		event.fromJsonObject(jsonObject);
		event.id = id;
		
		final HttpResponse<Event> result = new HttpResponse<Event>(HTTP_OK, jsonObject.toString(), event);
		return result;
	}

	public static HttpResponse<Event> getEventsByName(String name) {
		if (name.startsWith("X")) {
			return new HttpResponse<Event>(HTTP_NOT_FOUND, "No Event found with name: " + name);
		}
		
		final ArrayList<Event> events = new ArrayList<Event>();
		final String jsonStr = read(R.raw.mock_events);
		JsonReader jsonReader = null;
    	JsonArray jsonArray;
    	try {
    		jsonReader = jsonReaderFactory.createReader(new StringReader(jsonStr));
    		jsonArray = jsonReader.readArray();
    	}
    	finally {
    		if (jsonReader != null) {
    			jsonReader.close();
    		}
    	}
		
    	final List<JsonObject> jsonObjectList = jsonArray.getValuesAs(JsonObject.class);
   		for (JsonObject jsonObject : jsonObjectList) {
           	final Event event = new Event();
			event.fromJsonObject(jsonObject);
			event.name = name;
   			events.add(event);
   		}
    	
    	final HttpResponse<Event> result = new HttpResponse<Event>(HTTP_OK, jsonArray.toString(), events);
		return result;
	}

	public static HttpResponse<Event> getEventsByPlace(String place) {
		if (place.startsWith("X")) {
			return new HttpResponse<Event>(HTTP_NOT_FOUND, "No Event found with place: " + place);
		}
		
		final ArrayList<Event> events = new ArrayList<Event>();
		final String jsonStr = read(R.raw.mock_events);
		JsonReader jsonReader = null;
    	JsonArray jsonArray;
    	try {
    		jsonReader = jsonReaderFactory.createReader(new StringReader(jsonStr));
    		jsonArray = jsonReader.readArray();
    	}
    	finally {
    		if (jsonReader != null) {
    			jsonReader.close();
    		}
    	}
		
    	final List<JsonObject> jsonObjectList = jsonArray.getValuesAs(JsonObject.class);
   		for (JsonObject jsonObject : jsonObjectList) {
           	final Event event = new Event();
			event.fromJsonObject(jsonObject);
			event.place = place;
   			events.add(event);
   		}
    	
    	final HttpResponse<Event> result = new HttpResponse<Event>(HTTP_OK, jsonArray.toString(), events);
		return result;
	}

	public static HttpResponse<Event> getRecentEvents() {
		final ArrayList<Event> events = new ArrayList<Event>();
		final String jsonStr = read(R.raw.mock_events);
		JsonReader jsonReader = null;
    	JsonArray jsonArray;
    	try {
    		jsonReader = jsonReaderFactory.createReader(new StringReader(jsonStr));
    		jsonArray = jsonReader.readArray();
    	}
    	finally {
    		if (jsonReader != null) {
    			jsonReader.close();
    		}
    	}
		
    	final List<JsonObject> jsonObjectList = jsonArray.getValuesAs(JsonObject.class);
   		for (JsonObject jsonObject : jsonObjectList) {
           	final Event event = new Event();
			event.fromJsonObject(jsonObject);
   			events.add(event);
   		}
    	
    	final HttpResponse<Event> result = new HttpResponse<Event>(HTTP_OK, jsonArray.toString(), events);
		return result;
	}

	public static HttpResponse<User> getGuestsByEventId(String path, Long id) {
		final String jsonStr = read(R.raw.mock_users);
		JsonReader jsonReader = null;
		JsonObject jsonObject;
		try {
			jsonReader = jsonReaderFactory.createReader(new StringReader(jsonStr));
			jsonObject = jsonReader.readObject();
		}
		finally {
			if (jsonReader != null) {
				jsonReader.close();
			}
		}
		
		final User user = new User();
		
		user.fromJsonObject(jsonObject);
		
		final HttpResponse<User> result = new HttpResponse<User>(HTTP_OK, jsonObject.toString(), user);
		return result;
	}

	public static HttpResponse<Event> createEvent(Event event) {
		return new HttpResponse<Event>(HTTP_CREATED, EVENT_PATH + "/" + event.id, event);
	}

	public static HttpResponse<Event> updateEvent(Event event) {
		return new HttpResponse<Event>(HTTP_NO_CONTENT, null, event);
	}
	
}
