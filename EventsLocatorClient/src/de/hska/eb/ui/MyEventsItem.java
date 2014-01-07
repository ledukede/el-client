package de.hska.eb.ui;

import static de.hska.eb.util.Constants.EVENTS_KEY;

import java.util.ArrayList;

import de.hska.eb.R;
import de.hska.eb.domain.Event;
import de.hska.eb.ui.main.Main;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MyEventsItem extends Fragment implements OnClickListener {
	private static final String LOG_TAG = MyEventsItem.class.getSimpleName();
	
	private ArrayList<Event> events;
	private TableLayout eventTable;
	private TableRow originalRow;
	private TextView textView;
	private RatingBar ratingBar;
	private Main mainActivity;
	
	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		mainActivity = (Main) getActivity();
		eventTable = (TableLayout) mainActivity.findViewById(R.id.my_event_table);
		originalRow = (TableRow) mainActivity.findViewById(R.id.my_event_row);
		textView = (TextView) mainActivity.findViewById(R.id.my_event_data);
		ratingBar = (RatingBar) mainActivity.findViewById(R.id.my_event_rating);
		events = (ArrayList<Event>) getArguments().get(EVENTS_KEY);
		return inflater.inflate(R.layout.my_events_item, container, false);	
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		eventTable = (TableLayout) mainActivity.findViewById(R.id.my_event_table);

		eventTable.removeAllViews();
		originalRow.removeAllViews();
		for (Event event : events) {
			addRow(event);
		}
	}
	
	private void addRow(Event event) {
		Log.v(LOG_TAG, event.toString());
		textView.setText(event.name + "\n" + event.date + ", " + event.place);
		ratingBar.setRating(event.voting);
		originalRow.addView(textView);
		originalRow.addView(ratingBar);
		eventTable.addView(originalRow, originalRow.getLayoutParams());
	}

	@Override
	public void onClick(View view) {
		
	}


}
