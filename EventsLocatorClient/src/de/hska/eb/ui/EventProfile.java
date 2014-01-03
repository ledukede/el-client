package de.hska.eb.ui;

import java.util.List;

import de.hska.eb.eventslocatorclient.R;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EventProfile extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_profile);
		
	}
	
	public void onClickPlace(View view) {
		TextView tv = (TextView) findViewById(R.id.event_place);
		Uri location = Uri.parse("geo:0,0?q="+tv.getText());
		Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
		
		PackageManager packageManager = getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(mapIntent, 0);
		boolean isIntentSafe = activities.size() > 0;
		
		if (isIntentSafe) {
		    startActivity(mapIntent);
		}
	}
	
}
