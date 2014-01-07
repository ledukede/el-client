package de.hska.eb.service;

import static android.app.ProgressDialog.STYLE_SPINNER;
import static de.hska.eb.ui.main.Prefs.mock;
import static de.hska.eb.ui.main.Prefs.timeout;
import static de.hska.eb.util.Constants.USER_PATH;
import static de.hska.eb.util.Constants.EMAIL_PATH;
import static de.hska.eb.util.Constants.USER_UPCOMING_PATH;
import static de.hska.eb.util.Constants.USER_PASSED_PATH;
import static de.hska.eb.util.Constants.HOST_EMULATOR;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.util.concurrent.TimeUnit.SECONDS;

import org.apache.http.util.TextUtils;

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
import de.hska.eb.util.InternalEventslocatorError;

public class UserService extends Service{
	private static final String LOG_TAG = UserService.class.getSimpleName();
	
	private UserServiceBinder binder = new UserServiceBinder();
	
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}
	
	public class UserServiceBinder extends Binder {
		public UserService getService() {
			return UserService.this;
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
		
		private void setCommentsUri(User user) {
			final String commentsUri = user.commentsUri;
			if (!TextUtils.isEmpty(commentsUri)) {
				user.commentsUri = commentsUri.replace("localhost", HOST_EMULATOR);
			}
		}
		
		private void setPicUri(User user) {
			final String picUri = user.picUri;
			if (!TextUtils.isEmpty(picUri)) {
				user.picUri = picUri.replace("localhost", HOST_EMULATOR);
			}
		}
		
		private void setEventsUri(User user) {
			final String eventsUri = user.eventsUri;
			if (!TextUtils.isEmpty(eventsUri)) {
				user.eventsUri = eventsUri.replace("localhost", HOST_EMULATOR);
			}
		}
		
		public HttpResponse<User> getUserById(Long id, final Context ctx) {
					
					final AsyncTask<Long, Void, HttpResponse<User>> getUserByIdTask = new AsyncTask<Long, Void, HttpResponse<User>>() {
						@Override
						protected void onPreExecute() {
							progressDialog = showProgressDialog(ctx);
						}
						
						@Override
						protected HttpResponse<User> doInBackground(Long ... ids) {
							final Long id = ids[0];
							final String path = USER_PATH + "/" + id;
							Log.v(LOG_TAG, "path = " + path);
							final HttpResponse<User> result = mock 
															? Mock.getUserById(id)
															: WebServiceClient.getJsonSingle(path, User.class);
							Log.d(LOG_TAG + ".AsyncTask", "doInBackground: " + result);
							return result;
						}
						
						@Override
			    		protected void onPostExecute(HttpResponse<User> unused) {
							progressDialog.dismiss();
			    		}
					};
					
					getUserByIdTask.execute(id);
					HttpResponse<User> result = null;
					try {
						result = getUserByIdTask.get(timeout, SECONDS);
					}
					catch (Exception e) {
						throw new InternalEventslocatorError(e.getMessage(), e);
					}
					
					if (result.responseCode != HTTP_OK) {
						return result;
					}
					
					// für Emulator
		//			setCommentsUri(result.resultObject);
		//			setPicUri(result.resultObject);
		//			setEventsUri(result.resultObject);
					return result;
				}

		public HttpResponse<User> getUserByEmail(String email, final Context ctx) {
			final AsyncTask<String, Void, HttpResponse<User>> getUserByEmailTask = new AsyncTask<String, Void, HttpResponse<User>>() {
				@Override
				protected void onPreExecute() {
					progressDialog = showProgressDialog(ctx);
				}
				
				@Override
				protected HttpResponse<User> doInBackground(String... emails) {
					final String email = emails[0];
					final String path = EMAIL_PATH + email;
					Log.v(LOG_TAG, "path = " + path);
					final HttpResponse<User> result = mock
													? Mock.getUserByEmail(email)
													: WebServiceClient.getJsonSingle(path, User.class);
					Log.d(LOG_TAG + ".AsyncTask", "doInBackground: " + result);
					return result;
				}
				
				@Override
				protected void onPostExecute(HttpResponse<User> unused) {
					progressDialog.dismiss();
				}
			};
			
			getUserByEmailTask.execute(email);
			HttpResponse<User> result = null;
			try {
				result = getUserByEmailTask.get(timeout, SECONDS);
			}
			catch ( Exception e) {
				throw new InternalEventslocatorError(e.getMessage(), e);
			}
			
			if (result.responseCode != HTTP_OK) {
				return result;
			}
			
			final User user = result.resultObject;
			
//			setCommentsUri(user);
//			setEventsUri(user);
//			setPicUri(user);
			
			return result;
		}
		
		public HttpResponse<Event> getEventsByUserId(User user, final Context ctx) {
			final AsyncTask<User, Void, HttpResponse<Event>> getEventsByUserIdTask = new AsyncTask<User, Void, HttpResponse<Event>>() {
				@Override
				protected void onPreExecute() {
					progressDialog = showProgressDialog(ctx);
				}
				
				@Override
				protected HttpResponse<Event> doInBackground(User... users) {
					final User user = users[0];
					final String path = user.eventsUri;
					Log.v(LOG_TAG, "path = " + path);
					
					final HttpResponse<Event> result = mock
													 ? Mock.getEventsByUserId(path, user.id)
													 : WebServiceClient.getJsonList(path, Event.class);
					Log.d(LOG_TAG + ".AsyncTask", "doInBackground: " + result);
					return result;
				}
				
				@Override
				protected void onPostExecute(HttpResponse<Event> unused) {
					progressDialog.dismiss();
				}
					
			};
			
			getEventsByUserIdTask.execute(user);
			HttpResponse<Event> result = null;
			try {
				result = getEventsByUserIdTask.get(timeout, SECONDS);
			}
			catch (Exception e) {
				throw new InternalEventslocatorError(e.getMessage(), e);
			}
			
			if (result.responseCode != HTTP_OK) {
				return result;
			}
			
			return result;
		}
		
		public HttpResponse<Comment> getCommentsByUserId(User user, final Context ctx) {
			final AsyncTask<User, Void, HttpResponse<Comment>> getCommentsByUserIdTask = new AsyncTask<User, Void, HttpResponse<Comment>>() {
				@Override
				protected void onPreExecute() {
					progressDialog = showProgressDialog(ctx);
				}
				
				@Override
				protected HttpResponse<Comment> doInBackground(User... users) {
					final User user = users[0];
					final String path = user.commentsUri;
					Log.v(LOG_TAG, "path = " + path);
					final HttpResponse<Comment> result = mock
													   ? Mock.getCommentsByUserId(path, user.id)
													   : WebServiceClient.getJsonList(path, Comment.class);
					Log.d(LOG_TAG + ".AsyncTask", "doInBackground: " + result);
					return result;
				}
				
				@Override
				protected void onPostExecute(HttpResponse<Comment> unused) {
					progressDialog.dismiss();
				}
			};
			
			getCommentsByUserIdTask.execute(user);
			HttpResponse<Comment> result = null;
			try {
				result = getCommentsByUserIdTask.get();
			}
			catch (Exception e) {
				throw new InternalEventslocatorError(e.getMessage(), e);
			}
			
			return result;
		}
		
		public HttpResponse<Event> getUpcomingEventsByUserId(User user, final Context ctx) {
			final AsyncTask<User, Void, HttpResponse<Event>> getUpcomingEventsByUserIdTask = new AsyncTask<User, Void, HttpResponse<Event>>() {
				@Override
				protected void onPreExecute() {
					progressDialog = showProgressDialog(ctx);
				}
				
				@Override
				protected HttpResponse<Event> doInBackground(User... users) {
					final User user = users[0];
					final String path = USER_PATH + "/" + user.id + USER_UPCOMING_PATH;
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
			getUpcomingEventsByUserIdTask.execute(user);
			HttpResponse<Event> result = null;
			try {
				result = getUpcomingEventsByUserIdTask.get(timeout, SECONDS);
			}
			catch (Exception e) {
				throw new InternalEventslocatorError(e.getMessage(), e);
			}
			
			return result;
		}
		
		public HttpResponse<Event> getPassedEventsByUserId(User user, final Context ctx) {
			final AsyncTask<User, Void, HttpResponse<Event>> getPassedEventsByUserIdTask = new AsyncTask<User, Void, HttpResponse<Event>>() {
				@Override
				protected void onPreExecute() {
					progressDialog = showProgressDialog(ctx);
				}
				
				@Override
				protected HttpResponse<Event> doInBackground(User... users) {
					final User user = users[0];
					final String path = USER_PATH + "/" + user.id + USER_PASSED_PATH;
					Log.v(LOG_TAG, "path = " + path);
					final HttpResponse<Event> result = mock
													 ? Mock.getRecentEvents()
													 : WebServiceClient.getJsonList(path, Event.class);
					Log.d(LOG_TAG + ".AsyncTask", "doInBackground" + result);
					return result;
				}
				
				@Override
				protected void onPostExecute(HttpResponse<Event> unused) {
					progressDialog.dismiss();
				}
			};
			getPassedEventsByUserIdTask.execute(user);
			HttpResponse<Event> result = null;
			try {
				result = getPassedEventsByUserIdTask.get(timeout, SECONDS);
			}
			catch (Exception e) {
				throw new InternalEventslocatorError(e.getMessage(), e);
			}
			
			return result;
		}
		
		public HttpResponse<File> getUserPic(User user, final Context ctx) {
			final AsyncTask<User, Void, HttpResponse<File>> getUserPicTask = new AsyncTask<User, Void, HttpResponse<File>>() {
				@Override
				protected void onPreExecute() {
					progressDialog = showProgressDialog(ctx);
				}
				
				@Override
				protected HttpResponse<File> doInBackground(User... users) {
					final User user = users[0];
					final String path = user.picUri;
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
			getUserPicTask.execute(user);
			HttpResponse<File> result = null;
			try {
				result = getUserPicTask.get(timeout, SECONDS);
			}
			catch (Exception e) {
				throw new InternalEventslocatorError(e.getMessage(), e);
			}
			
			return result;
		}
		
		public HttpResponse<User> createUser(User user, final Context ctx) {
			
			final AsyncTask<User, Void, HttpResponse<User>> createUserTask = new AsyncTask<User, Void, HttpResponse<User>>() {
				@Override
				protected void onPreExecute() {
					progressDialog = showProgressDialog(ctx);
				}
				
				@Override
				protected HttpResponse<User> doInBackground(User... users) {
					final User user = users[0];
					final String path = USER_PATH;
					Log.v(LOG_TAG, "path = " + path);
					
					final HttpResponse<User> result = mock 
													? Mock.createUser(user)
													: WebServiceClient.postJson(user, path);
					Log.d(LOG_TAG + ".AsyncTask", "doInBackground: " + result);
					return result;
				}
				
				@Override
				protected void onPostExecute(HttpResponse<User> unused) {
					progressDialog.dismiss();
				}
			};
			
			createUserTask.execute(user);
			HttpResponse<User> response = null;
			try {
				response = createUserTask.get(timeout, SECONDS);
			}
			catch (Exception e) {
				throw new InternalEventslocatorError(e.getMessage(), e);
			}
			
			user.id = Long.valueOf(response.content);
			final HttpResponse<User> result = new HttpResponse<User>(response.responseCode, response.content, user);
			return result;
		}
		
		public HttpResponse<Comment> createCommentForUser(Comment comment, User user, final Context ctx) {
			Object[] params = new Object[2];
			params[0] = comment;
			params[1] = user;
			AsyncTask<Object[], Void, HttpResponse<Comment>> createCommentForUserTask = new AsyncTask<Object[], Void, HttpResponse<Comment>>() {
				@Override
				protected void onPreExecute() {
					progressDialog = showProgressDialog(ctx);
				}
				
				@Override
				protected HttpResponse<Comment> doInBackground(Object[]... objects) {
					final Object[] object = objects[0];
					final User user = (User) object[1];
					final Comment comment = (Comment) object[0];
					final String path = user.commentsUri;
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
			createCommentForUserTask.execute(params);
			HttpResponse<Comment> response = null;
			try {
				response = createCommentForUserTask.get(timeout, SECONDS);
			}
			catch (Exception e) {
				throw new InternalEventslocatorError(e.getMessage(), e);
			}
			
			comment.id = Long.valueOf(response.content);
			final HttpResponse<Comment> result = new HttpResponse<Comment>(response.responseCode, response.content, comment);
			return result;
		}
		
		public HttpResponse<File> createUserPic(File file, User user, final Context ctx) {
			Object[] params = new Object[2];
			params[0] = file;
			params[1] = user;
			AsyncTask<Object[], Void, HttpResponse<File>> createUserPicTask = new AsyncTask<Object[], Void, HttpResponse<File>>() {
				@Override
				protected void onPreExecute() {
					progressDialog = showProgressDialog(ctx);
				}
				
				@Override
				protected HttpResponse<File> doInBackground(Object[]... objects) {
					Object[] object = objects[0];
					final File file = (File) object[0];
					final User user = (User) object[1];
					final String path = user.picUri;
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
			createUserPicTask.execute(params);
			HttpResponse<File> response = null;
			try {
				response = createUserPicTask.get(timeout, SECONDS);
			}
			catch (Exception e) {
				throw new InternalEventslocatorError(e.getMessage(), e);
			}
			
			file.id = Long.valueOf(response.content);
			final HttpResponse<File> result = new HttpResponse<File>(response.responseCode, response.content, file);
			return result;
		}
		
		public HttpResponse<User> updateUser(User user, final Context ctx) {
			final AsyncTask<User, Void, HttpResponse<User>> updateUserTask = new AsyncTask<User, Void, HttpResponse<User>>() {
				@Override
				protected void onPreExecute() {
					progressDialog = showProgressDialog(ctx);
				}
				
				@Override
				protected HttpResponse<User> doInBackground(User... users) {
					final User user = users[0];
					final String path = USER_PATH;
					Log.v(LOG_TAG, "path = " + path);
					
					final HttpResponse<User> result = mock
													? Mock.updateUser(user)
													: WebServiceClient.putJson(user, path);
					Log.d(LOG_TAG + ".AsyncTask", "doInBackground: " + result);
					return result;
				}
				
				@Override
				protected void onPostExecute(HttpResponse<User> unused) {
					progressDialog.dismiss();
				}
			};
			
			updateUserTask.execute(user);
			HttpResponse<User> result = null;
			try {
				result = updateUserTask.get(timeout, SECONDS);
			}
			catch (Exception e) {
				throw new InternalEventslocatorError(e.getMessage(), e);
			}
			
			if (result.responseCode == HTTP_NO_CONTENT || result.responseCode == HTTP_OK) {
				result.resultObject = user;
			}
			
			return result;
		}
		
		// TODO addEvent
	}	
}
