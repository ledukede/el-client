package de.hska.eb.ui;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static de.hska.eb.util.Constants.USER_LOGGED_IN_KEY;
import static de.hska.eb.ui.main.Prefs.password;
import static de.hska.eb.ui.main.Prefs.username;

import org.apache.http.util.TextUtils;

import de.hska.eb.R;
import de.hska.eb.domain.User;
import de.hska.eb.service.HttpResponse;
import de.hska.eb.ui.main.Main;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;

public class SignUp extends Fragment implements OnClickListener {
	private static final String LOG_TAG = SignUp.class.getSimpleName();
	
	private EditText emailTxt;
	private EditText passwordTxt;
	private EditText passwordWdhTxt;
	private EditText nameTxt;
	private EditText surnameTxt;
	private CheckBox agbCheckbox;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		return inflater.inflate(R.layout.signup, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		emailTxt = (EditText) view.findViewById(R.id.signup_email);		
		passwordTxt = (EditText) view.findViewById(R.id.signup_password);
		passwordWdhTxt = (EditText) view.findViewById(R.id.signup_confirmpassword);
		nameTxt = (EditText) view.findViewById(R.id.signup_name);
		surnameTxt = (EditText) view.findViewById(R.id.signup_firstname);
		agbCheckbox = (CheckBox) view.findViewById(R.id.signup_agreeAgb);
		view.findViewById(R.id.signup_submit).setOnClickListener(this);	
		
		final ActionBar actionBar = getActivity().getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.removeAllTabs();
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case (R.id.signup_submit):
				signup(view);
				break;
			default:
				break;
		}
	}

	private void signup(View view) {
		final Context ctx = view.getContext();
		
		final String emailStr = emailTxt.getText().toString();
		final String passwordStr = passwordTxt.getText().toString();
		final String passwordWdhStr = passwordWdhTxt.getText().toString();
		final String nameStr = nameTxt.getText().toString();
		final String surnameStr = surnameTxt.getText().toString();
		final boolean agbAccepted = agbCheckbox.isChecked();
		
		if (TextUtils.isEmpty(emailStr)) {
			emailTxt.setError(getString(R.string.error_email_blank));
			return;
		}
		if (TextUtils.isEmpty(passwordStr)) {
			passwordTxt.setError(getString(R.string.error_password_blank));
			return;
		}
		if (TextUtils.isEmpty(nameStr)) {
			nameTxt.setError(getString(R.string.error_name_blank));
			return;
		}
		if (TextUtils.isEmpty(surnameStr)) {
			surnameTxt.setError(getString(R.string.error_surname_blank));
			return;
		}
		if (!agbAccepted) {
			agbCheckbox.setError(getString(R.string.error_agb_not_accepted));
			return;
		}
		if (!passwordStr.equals(passwordWdhStr)) {
			passwordTxt.setError(getString(R.string.error_password_not_same));
			return;
		}
		
		User user = new User(emailStr, passwordStr, nameStr, surnameStr);
		Log.v(LOG_TAG, user.toString());
		
		final Main mainActivity = (Main) getActivity();
		final HttpResponse<User> result = mainActivity.getUserServiceBinder().createUser(user, ctx);
		Log.v(LOG_TAG, result.toString());
		
		if (result.responseCode != HTTP_CREATED) {
			final String msg = getString(R.string.error_http);
			emailTxt.setError(msg);
			return;
		}
		
		user = result.resultObject;
		username = user.email;
		password = user.password;
		
		final Bundle args = new Bundle(1);
		args.putSerializable(USER_LOGGED_IN_KEY, user);
		Log.v(LOG_TAG, user.toString());
		
		final Fragment newFragment = new MyEvents();
		newFragment.setArguments(args);
		
		getFragmentManager().beginTransaction()
							.replace(R.id.details, newFragment)
							.addToBackStack(null)
							.commit();
	}

}
