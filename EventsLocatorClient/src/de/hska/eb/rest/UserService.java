package de.hska.eb.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import de.hska.eb.domain.Comment;
import de.hska.eb.domain.Event;
import de.hska.eb.domain.File;
import de.hska.eb.domain.User;

public class UserService extends Service{
	
	public User getUserById(int id) throws ClientProtocolException, IOException, IllegalStateException, JSONException {
		HttpClient httpclient = new DefaultHttpClient();
	      HttpGet httpGet = new HttpGet(Config.basePath+"/users/"+id);
	      HttpResponse response = httpclient.execute(new HttpHost("10.0.2.2"), httpGet);
	      
	      JSONObject json = new JSONObject(RestClient.responseToString(response));
	      User user = new User();
	      user.fromJsonObject(json);
	      return user;
	}
	
//	public List<Comment> getCommentsByUserId(int id) throws IllegalStateException, JSONException, IOException {
//		HttpClient httpclient = new DefaultHttpClient();
//	      HttpGet httpGet = new HttpGet(Config.basePath+"/users/"+id+"comments");
//	      HttpResponse response = httpclient.execute(new HttpHost("10.0.2.2"), httpGet);
//	      
//	      JSONArray json = new JSONArray(RestClient.responseToString(response));
//	      List<Comment> comments = new ArrayList<Comment>();
//	      for (int i = 0; i < json.length(); i++) {
//	    	  Comment comment = new Comment();
//		      comment.fromJsonObject(json.getJSONObject(i));
//		       users.add(user);
//		}
//	      return users;
//	}
	
	public List<Event> getEventsByUserId(int id) {
		return null;
	}
	
	public void addUser(User user) {
		
	}
	
	public void updateUser(User user) {
		
	}
	
	public void deleteUser(User user) {
		
	}
	
	public File downloadPicByUserId(int id) {
		return null;
	}
	
	public void uploadPicByUserId(int id, File file) {
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
