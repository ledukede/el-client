package de.hska.eb.ui;

import static de.hska.eb.util.Constants.EVENTS_KEY;
import static de.hska.eb.util.Constants.EVENT_KEY;
import static de.hska.eb.util.Constants.USER_LOGGED_IN_KEY;

import java.util.ArrayList;

import de.hska.eb.R;
import de.hska.eb.domain.Event;
import de.hska.eb.domain.User;
import de.hska.eb.ui.main.Main;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class MyEvents extends Fragment {
	private static final String LOG_TAG = MyEvents.class.getSimpleName();
	
	private User loggedIn;
	private Main mainActivity;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		return inflater.inflate(R.layout.my_events, container, false);
		
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		loggedIn = (User) getArguments().get(USER_LOGGED_IN_KEY);
		mainActivity = (Main) getActivity();
		getUpcomingEvents(view);
		getPassedEvents(view);
		Log.v(LOG_TAG, "TEST");

	}

	private void getPassedEvents(View view) {
		final Context ctx = view.getContext();
		
		ArrayList<Event> events = mainActivity.getUserServiceBinder().getPassedEventsByUserId(loggedIn, ctx).resultList;
		
		final Bundle args = new Bundle(1);
		args.putSerializable(EVENTS_KEY, events);
		Log.v(LOG_TAG, events.toString());
		
		final Fragment passedEvents = new MyEventsItem();
		passedEvents.setArguments(args);
		
//		getFragmentManager().beginTransaction()
//							.add(R.id.my_passed_evens, passedEvents)
//							.commit();
	}

	private void getUpcomingEvents(View view) {
		final Context ctx = view.getContext();
		
		ArrayList<Event> events = mainActivity.getUserServiceBinder().getUpcomingEventsByUserId(loggedIn, ctx).resultList;
		
		final Bundle args = new Bundle(1);
		args.putSerializable(EVENTS_KEY, events);
		Log.v(LOG_TAG, events.toString());
		
		final Fragment upcomingEvents = new MyEventsItem();
		upcomingEvents.setArguments(args);
		
//		getFragmentManager().beginTransaction()
//							.add(R.id.my_upcoming_events, upcomingEvents)
//							.commit();
		
	}
}
