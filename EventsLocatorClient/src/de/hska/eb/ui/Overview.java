package de.hska.eb.ui;

import de.hska.eb.R;
import android.app.Fragment;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class Overview extends TabActivity {
	private static final String LOG_TAG = Overview.class.getSimpleName();
	
	private TabHost tabHost;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.overview);
		
		tabHost = getTabHost();
		
		TabSpec eventsSpec = tabHost.newTabSpec("Events");
		eventsSpec.setIndicator("Events");
		Intent eventsIntent = new Intent(this, EventsActivity.class);
		eventsSpec.setContent(eventsIntent);
		
		TabSpec myEventsSpec = tabHost.newTabSpec("My Events");
		myEventsSpec.setIndicator("My Events");
		Intent myEventsIntent = new Intent(this, MyEventsActivity.class);
		myEventsSpec.setContent(myEventsIntent);
		
		tabHost.addTab(eventsSpec);
		tabHost.addTab(myEventsSpec);
	}
}
