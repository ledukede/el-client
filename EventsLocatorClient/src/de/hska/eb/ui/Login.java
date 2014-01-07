package de.hska.eb.ui;

import static java.net.HttpURLConnection.HTTP_OK;
import static de.hska.eb.ui.main.Prefs.username;
import static de.hska.eb.ui.main.Prefs.password;
import static de.hska.eb.util.Constants.USER_LOGGED_IN_KEY;



import org.apache.http.util.TextUtils;

import de.hska.eb.domain.User;
import de.hska.eb.R;
import de.hska.eb.service.HttpResponse;
import de.hska.eb.ui.main.Main;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

public class Login extends Fragment implements OnClickListener {
	private static final String LOG_TAG = Login.class.getSimpleName();
	
	private EditText emailTxt;
	private EditText passwordTxt;
	private CheckBox rememberCheckBox;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		return inflater.inflate(R.layout.login, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		emailTxt = (EditText) view.findViewById(R.id.login_email);		
		passwordTxt = (EditText) view.findViewById(R.id.login_password);
		rememberCheckBox = (CheckBox) view.findViewById(R.id.checkbox_remember);
		view.findViewById(R.id.button_login).setOnClickListener(this);
		view.findViewById(R.id.button_register).setOnClickListener(this);
	};
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.button_login:
				signIn(view);
				Log.v(LOG_TAG, "TEST1");
				break;
			case R.id.button_register:
				signUp(view);
				Log.v(LOG_TAG, "TEST2");
				break;
			default:
				break;
		}
	}
	
	private void signUp(View view) {
		getFragmentManager().beginTransaction()
							.replace(R.id.details, new SignUp())
							.addToBackStack(null)
							.commit();
	}
	
	private void signIn (View view) {
		final Context ctx = view.getContext();
		
		final String emailStr = emailTxt.getText().toString();
		final String passwordStr = passwordTxt.getText().toString();
		if (TextUtils.isEmpty(emailStr)) {
			emailTxt.setError(getString(R.string.error_email_blank));
		}
		if (TextUtils.isEmpty(passwordStr)) {
			passwordTxt.setError(getString(R.string.error_password_blank));
		}
		
		username = emailStr;
		password = passwordStr;
		
		final Main mainActivity = (Main) getActivity();
		final HttpResponse<User> result = mainActivity.getUserServiceBinder().getUserByEmail(emailStr, ctx);
		Log.v(LOG_TAG, result.toString());
		
		if (!rememberCheckBox.isChecked()) {
			username = null;
			password = null;
		}
		
		if (result.responseCode != HTTP_OK) {
			final String msg = getString(R.string.error_email_invalid, emailStr);
			emailTxt.setError(msg);
			return;
		}
		
		final User user = result.resultObject;
		final Bundle args = new Bundle(1);
		args.putSerializable(USER_LOGGED_IN_KEY, user);
		Log.d(LOG_TAG, user.toString());
		
		final Fragment newFragment = new MyEvents();
		newFragment.setArguments(args);
		
		getFragmentManager().beginTransaction()
							.replace(R.id.details, newFragment)
							.addToBackStack(null)
							.commit();
	}
}
