package de.hska.eb.ui.main;

import static de.hska.eb.ui.main.Prefs.username;
import static de.hska.eb.ui.main.Prefs.password;
import static de.hska.eb.util.Constants.USER_LOGGED_IN_KEY;
import de.hska.eb.R;
import de.hska.eb.domain.User;
import de.hska.eb.service.EventService;
import de.hska.eb.service.UserService;
import de.hska.eb.service.EventService.EventServiceBinder;
import de.hska.eb.service.UserService.UserServiceBinder;
import de.hska.eb.ui.Login;
import de.hska.eb.ui.MyEvents;
import de.hska.eb.ui.Overview;
import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class Main extends Activity {
	private static final String LOG_TAG = Main.class.getSimpleName();
    
	private UserServiceBinder userServiceBinder;
	private EventServiceBinder eventServiceBinder;
	
	private ServiceConnection userServiceConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			userServiceBinder = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder serviceBinder) {
			Log.v(LOG_TAG, "onServiceConnected() for userServiceBinder");
			userServiceBinder = (UserServiceBinder) serviceBinder;
		}
	};
	
	private ServiceConnection eventServiceConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			eventServiceBinder = null;
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder serviceBinder) {
			Log.v(LOG_TAG, "onServiceConnected() for eventServiceBinder");
			eventServiceBinder = (EventServiceBinder) serviceBinder;
			
		}
	};
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
//    	super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//        
//        Fragment detailsFragment = null;
//        if (username == null || password == null) {
//
//        	detailsFragment = new Login();
//        	
//            Prefs.init(this);
//        }
//        else {
//        	final Context ctx = getCurrentFocus().getContext();
//        	final User user = getUserServiceBinder().getUserByEmail(username, ctx).resultObject;
//        	
//        	final Bundle args = new Bundle(1);
//        	args.putSerializable(USER_LOGGED_IN_KEY, user);
//        	detailsFragment = new MyEvents();
//        	detailsFragment.setArguments(args);
//        }
		
		Fragment detailsFragment = null;

        getFragmentManager().beginTransaction()
        					       .add(R.id.details, detailsFragment)
        					       .commit();
	}
	
	@Override
	public void onStart() {
		super.onStart();
			
		Intent intent = new Intent(this, UserService.class);
		bindService(intent, userServiceConnection, Context.BIND_AUTO_CREATE);
			
		intent = new Intent(this, EventService.class);
		bindService(intent, eventServiceConnection, Context.BIND_AUTO_CREATE);
	}
	    
	@Override
	public void onStop() {
		super.onStop();
			
		unbindService(userServiceConnection);
		unbindService(eventServiceConnection);
	}
	
	public UserServiceBinder getUserServiceBinder() {
		return userServiceBinder;
	}
	
	public EventServiceBinder getEventServiceBinder() {
		return eventServiceBinder;
	}

}
