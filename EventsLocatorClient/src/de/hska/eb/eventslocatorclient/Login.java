package de.hska.eb.eventslocatorclient;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import de.hska.eb.domain.Event;
import de.hska.eb.rest.EventService;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Login extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
	}
	
	public void signIn (View view) throws ClientProtocolException, IllegalStateException, IOException, JSONException {
		//TODO validate login
		EventService es = new EventService();
		Event event = es.getRecentEvents().get(0);
		
		TextView tv = (TextView) findViewById(R.id.email);
		tv.setText(event.getName());
	}
}
