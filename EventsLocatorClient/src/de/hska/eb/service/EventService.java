package de.hska.eb.service;

import static android.app.ProgressDialog.STYLE_SPINNER;
import static de.hska.eb.ui.main.Prefs.mock;
import static de.hska.eb.ui.main.Prefs.timeout;
import static de.hska.eb.util.Constants.EVENT_PATH;
import static de.hska.eb.util.Constants.NAME_PATH;
import static de.hska.eb.util.Constants.PLACE_PATH;
import static de.hska.eb.util.Constants.RECENT_EVENTS_PATH;
import static de.hska.eb.util.Constants.USER_PATH;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import de.hska.eb.domain.Comment;
import de.hska.eb.domain.Event;
import de.hska.eb.domain.File;
import de.hska.eb.domain.User;
import de.hska.eb.R;
import de.hska.eb.service.UserService.UserServiceBinder;
import de.hska.eb.util.InternalEventslocatorError;

public class EventService extends Service {
private static final String LOG_TAG = UserService.class.getSimpleName();
	
	private EventServiceBinder binder = new EventServiceBinder();
	
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}
	
	public class EventServiceBinder extends Binder {
		public EventService getService() {
			return EventService.this;
		}
		
		private ProgressDialog progressDialog;
		private ProgressDialog showProgressDialog(Context ctx) {
			progressDialog = new ProgressDialog(ctx);
			progressDialog.setProgressStyle(STYLE_SPINNER);
			progressDialog.setMessage(getString(R.string.please_wait));
			progressDialog.setCancelable(true);
			progressDialog.setIndeterminate(true);
			progressDialog.show();
			return progressDialog;
		}
		
		public HttpResponse<Event> getEventById(Long id, final Context ctx) {
			final AsyncTask<Long, Void, HttpResponse<Event>> getEventByIdTask = new AsyncTask<Long, Void, HttpResponse<Event>>() {
				@Override
				protected void onPreExecute() {
					progressDialog = showProgressDialog(ctx);
				}
				
				@Override
				protected HttpResponse<Event> doInBackground(Long ... ids) {
					final Long id = ids[0];
					final String path = EVENT_PATH + "/" + id;
					Log.v(LOG_TAG, "path = " + path);
					final HttpResponse<Event> result = mock 
													? Mock.getEventById(id)
													: WebServiceClient.getJsonSingle(path, Event.class);
					Log.d(LOG_TAG + ".AsyncTask", "doInBackground: " + result);
					return result;
				}
				
				@Override
	    		protected void onPostExecute(HttpResponse<Event> unused) {
					progressDialog.dismiss();
	    		}
			};
			
			getEventByIdTask.execute(id);
			HttpResponse<Event> result = null;
			try {
				result = getEventByIdTask.get(timeout, SECONDS);
			}
			catch (Exception e) {
				throw new InternalEventslocatorError(e.getMessage(), e);
			}
			
			if (result.responseCode != HTTP_OK) {
				return result;
			}
			
			return result;
		}
		
		public HttpResponse<Event> getEventsByName(String name, final Context ctx) {
			final AsyncTask<String, Void, HttpResponse<Event>> getEventsByNameTask = new AsyncTask<String, Void, HttpResponse<Event>>() {
				@Override
				protected void onPreExecute() {
					progressDialog = showProgressDialog(ctx);
				}
				
				@Override
				protected HttpResponse<Event> doInBackground(String... names) {
					final String name = names[0];
					final String path = NAME_PATH + name;
					Log.v(LOG_TAG, "path = " + path);
					final HttpResponse<Event> result = mock
													 ? Mock.getEventsByName(name)
													 : WebServiceClient.getJsonList(path, Event.class);
					Log.d(LOG_TAG + ".AsyncTask", "doInBackground: " + result);
					return result;
				}
				
				@Override
	    		protected void onPostExecute(HttpResponse<Event> unused) {
					progressDialog.dismiss();
	    		}
			};
			getEventsByNameTask.execute(name);
			HttpResponse<Event> result = null;
			try {
				result = getEventsByNameTask.get(timeout, SECONDS);
			}
			catch (Exception e) {
				throw new InternalEventslocatorError(e.getMessage(), e);
			}
			
			return result;
		}
		
		public HttpResponse<Event> getEventsByPlace(String place, final Context ctx) {
			final AsyncTask<String, Void, HttpResponse<Event>> getEventsByPlaceTask = new AsyncTask<String, Void, HttpResponse<Event>>() {
				@Override
				protected void onPreExecute() {
					progressDialog = showProgressDialog(ctx);
				}
				
				@Override
				protected HttpResponse<Event> doInBackground(String... places) {
					final String place = places[0];
					final String path = PLACE_PATH + place;
					Log.v(LOG_TAG, "path = " + path);
					final HttpResponse<Event> result = mock
													 ? Mock.getEventsByPlace(place)
													 : WebServiceClient.getJsonList(path, Event.class);
					Log.d(LOG_TAG + ".AsyncTask", "doInBackground: " + result);
					return result;
				}
				
				@Override
	    		protected void onPostExecute(HttpResponse<Event> unused) {
					progressDialog.dismiss();
	    		}
			};
			getEventsByPlaceTask.execute(place);
			HttpResponse<Event> result = null;
			try {
				result = getEventsByPlaceTask.get(timeout, SECONDS);
			}
			catch (Exception e) {
				throw new InternalEventslocatorError(e.getMessage(), e);
			}
			
			return result;		
		}
		
		public HttpResponse<Event> getRecentEvents(final Context ctx) {
			final AsyncTask<Void, Void, HttpResponse<Event>> getRecentEventsTask = new AsyncTask<Void, Void, HttpResponse<Event>>() {
				@Override
				protected void onPreExecute() {
					progressDialog = showProgressDialog(ctx);
				}
				
				@Override
				protected HttpResponse<Event> doInBackground(Void... voids) {
					final String path = RECENT_EVENTS_PATH;
					Log.v(LOG_TAG, "path = " + path);
					final HttpResponse<Event> result = mock
													 ? Mock.getRecentEvents()
													 : WebServiceClient.getJsonList(path, Event.class);
					Log.d(LOG_TAG + ".AsyncTask", "doInBackground: " + result);
					return result;
				}

				@Override
	    		protected void onPostExecute(HttpResponse<Event> unused) {
					progressDialog.dismiss();
	    		}
			};
			getRecentEventsTask.execute();
			HttpResponse<Event> result = null;
			try {
				result = getRecentEventsTask.get(timeout, SECONDS);
			}
			catch (Exception e) {
				throw new InternalEventslocatorError(e.getMessage(), e);
			}
			
			return result;		
		}
		
		public HttpResponse<Comment> getCommentsByEventId(Event event, final Context ctx) {
			final AsyncTask<Event, Void, HttpResponse<Comment>> getCommentsByEventIdTask = new AsyncTask<Event, Void, HttpResponse<Comment>>() {
				@Override
				protected void onPreExecute() {
					progressDialog = showProgressDialog(ctx);
				}
				
				@Override
				protected HttpResponse<Comment> doInBackground(Event... events) {
					final Event event = events[0];
					final String path = event.commentsUri;
					Log.v(LOG_TAG, "path = " + path);
					final HttpResponse<Comment> result = mock
													   ? Mock.getCommentsByUserId(path, event.id)
													   : WebServiceClient.getJsonList(path, Comment.class);
					Log.d(LOG_TAG + ".AsyncTask", "doInBackground: " + result);
					return result;
				}
				
				@Override
				protected void onPostExecute(HttpResponse<Comment> unused) {
					progressDialog.dismiss();
				}
			};

			getCommentsByEventIdTask.execute(event);
			HttpResponse<Comment> result = null;
			try {
				result = getCommentsByEventIdTask.get();
			}
			catch (Exception e) {
				throw new InternalEventslocatorError(e.getMessage(), e);
			}
			
			return result;
		}
		
		public HttpResponse<User> getGuestsByEventId(Event event, final Context ctx) {
			final AsyncTask<Event, Void, HttpResponse<User>> getGuestsByEventIdTask = new AsyncTask<Event, Void, HttpResponse<User>>() {
				@Override
				protected void onPreExecute() {
					progressDialog = showProgressDialog(ctx);
				}
				
				@Override
				protected HttpResponse<User> doInBackground(Event... events) {
					final Event event = events[0];
					final String path = event.guestsUri;
					Log.v(LOG_TAG, "path = " + path);
					final HttpResponse<User> result = mock
													   ? Mock.getGuestsByEventId(path, event.id)
													   : WebServiceClient.getJsonList(path, User.class);
					Log.d(LOG_TAG + ".AsyncTask", "doInBackground: " + result);
					return result;
				}
				
				@Override
				protected void onPostExecute(HttpResponse<User> unused) {
					progressDialog.dismiss();
				}
			};
			getGuestsByEventIdTask.execute(event);
			HttpResponse<User> result = null;
			try {
				result = getGuestsByEventIdTask.get(timeout, SECONDS);
			}
			catch (Exception e) {
				throw new InternalEventslocatorError(e.getMessage(), e);
			}
			
			return result;	
		}
		
		public HttpResponse<File> getEventPic(Event event, final Context ctx) {
			final AsyncTask<Event, Void, HttpResponse<File>> getEventPicTask = new AsyncTask<Event, Void, HttpResponse<File>>() {
				@Override
				protected void onPreExecute() {
					progressDialog = showProgressDialog(ctx);
				}
				
				@Override
				protected HttpResponse<File> doInBackground(Event... events) {
					final Event event = events[0];
					final String path = event.picUri;
					Log.v(LOG_TAG, "path = " + path);
					final HttpResponse<File> result = WebServiceClient.getJsonList(path, File.class);
					Log.d(LOG_TAG + ".AsyncTask", "doInBackground: " + result);
					return result;
				}
				
				@Override
				protected void onPostExecute(HttpResponse<File> unused) {
					progressDialog.dismiss();
				}
			};
			getEventPicTask.execute(event);
			HttpResponse<File> result = null;
			try {
				result = getEventPicTask.get(timeout, SECONDS);
			}
			catch (Exception e) {
				throw new InternalEventslocatorError(e.getMessage(), e);
			}
			
			return result;
		}
		
		public HttpResponse<Event> createEvent(Event event, final Context ctx) {
			final AsyncTask<Event, Void, HttpResponse<Event>> createEventTask = new AsyncTask<Event, Void, HttpResponse<Event>>() {
				@Override
				protected void onPreExecute() {
					progressDialog = showProgressDialog(ctx);
				}
				
				@Override
				protected HttpResponse<Event> doInBackground(Event... events) {
					final Event event = events[0];
					final String path = EVENT_PATH;
					Log.v(LOG_TAG, "path = " + path);
					
					final HttpResponse<Event> result = mock 
													? Mock.createEvent(event)
													: WebServiceClient.postJson(event, path);
					Log.d(LOG_TAG + ".AsyncTask", "doInBackground: " + result);
					return result;
				}
				
				@Override
				protected void onPostExecute(HttpResponse<Event> unused) {
					progressDialog.dismiss();
				}
			};
			
			createEventTask.execute(event);
			HttpResponse<Event> response = null;
			try {
				response = createEventTask.get(timeout, SECONDS);
			}
			catch (Exception e) {
				throw new InternalEventslocatorError(e.getMessage(), e);
			}
			
			event.id = Long.valueOf(response.content);
			final HttpResponse<Event> result = new HttpResponse<Event>(response.responseCode, response.content, event);
			return result;
		}
		
		public HttpResponse<Comment> createCommentForEvent(Comment comment, Event event, final Context ctx) {
			Object[] params = new Object[2];
			params[0] = comment;
			params[1] = event;
			AsyncTask<Object[], Void, HttpResponse<Comment>> createCommentForEventTask = new AsyncTask<Object[], Void, HttpResponse<Comment>>() {
				@Override
				protected void onPreExecute() {
					progressDialog = showProgressDialog(ctx);
				}
				
				@Override
				protected HttpResponse<Comment> doInBackground(Object[]... objects) {
					final Object[] object = objects[0];
					final Event event = (Event) object[1];
					final Comment comment = (Comment) object[0];
					final String path = event.commentsUri;
					Log.v(LOG_TAG, "path = " + path);
					
					HttpResponse<Comment> result = mock
												 ? Mock.createCommentForUser(path, comment)
												 : WebServiceClient.postJson(comment, path);
					Log.d(LOG_TAG + ".AsyncTask", "doInBackground: " + result);
					return result;
				}
				
				@Override
				protected void onPostExecute(HttpResponse<Comment> unused) {
					progressDialog.dismiss();
				}
			};
			createCommentForEventTask.execute(params);
			HttpResponse<Comment> response = null;
			try {
				response = createCommentForEventTask.get(timeout, SECONDS);
			}
			catch (Exception e) {
				throw new InternalEventslocatorError(e.getMessage(), e);
			}
			
			comment.id = Long.valueOf(response.content);
			final HttpResponse<Comment> result = new HttpResponse<Comment>(response.responseCode, response.content, comment);
			return result;
		}
		
		//TODO createVote ...
		
		public HttpResponse<File> createEventPic(File file, Event event, final Context ctx) {
			Object[] params = new Object[2];
			params[0] = file;
			params[1] = event;
			AsyncTask<Object[], Void, HttpResponse<File>> createEventPicTask = new AsyncTask<Object[], Void, HttpResponse<File>>() {
				@Override
				protected void onPreExecute() {
					progressDialog = showProgressDialog(ctx);
				}
				
				@Override
				protected HttpResponse<File> doInBackground(Object[]... objects) {
					Object[] object = objects[0];
					final File file = (File) object[0];
					final Event event = (Event) object[1];
					final String path = event.picUri;
					Log.v(LOG_TAG, "path = " + path);
					
					HttpResponse<File> result = WebServiceClient.postJson(file, path);
					Log.d(LOG_TAG + ".AsyncTask", "doInBackground: " + result);
					return result;
				}
				
				@Override
				protected void onPostExecute(HttpResponse<File> unused) {
					progressDialog.dismiss();
				}
			};
			createEventPicTask.execute(params);
			HttpResponse<File> response = null;
			try {
				response = createEventPicTask.get(timeout, SECONDS);
			}
			catch (Exception e) {
				throw new InternalEventslocatorError(e.getMessage(), e);
			}
			
			file.id = Long.valueOf(response.content);
			final HttpResponse<File> result = new HttpResponse<File>(response.responseCode, response.content, file);
			return result;
		}
		
		public HttpResponse<Event> updateEvent(Event event, final Context ctx) {
			final AsyncTask<Event, Void, HttpResponse<Event>> updateEventTask = new AsyncTask<Event, Void, HttpResponse<Event>>() {
				@Override
				protected void onPreExecute() {
					progressDialog = showProgressDialog(ctx);
				}
				
				@Override
				protected HttpResponse<Event> doInBackground(Event... events) {
					final Event event = events[0];
					final String path = USER_PATH;
					Log.v(LOG_TAG, "path = " + path);
					
					final HttpResponse<Event> result = mock
													? Mock.updateEvent(event)
													: WebServiceClient.putJson(event, path);
					Log.d(LOG_TAG + ".AsyncTask", "doInBackground: " + result);
					return result;
				}
				
				@Override
				protected void onPostExecute(HttpResponse<Event> unused) {
					progressDialog.dismiss();
				}
			};
			
			updateEventTask.execute(event);
			HttpResponse<Event> result = null;
			try {
				result = updateEventTask.get(timeout, SECONDS);
			}
			catch (Exception e) {
				throw new InternalEventslocatorError(e.getMessage(), e);
			}
			
			if (result.responseCode == HTTP_NO_CONTENT || result.responseCode == HTTP_OK) {
				result.resultObject = event;
			}
			
			return result;
		}
	}
}
