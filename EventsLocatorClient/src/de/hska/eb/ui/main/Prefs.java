package de.hska.eb.ui.main;

import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;

public class Prefs extends PreferenceFragment implements OnPreferenceChangeListener {
	
	private static final String LOG_TAG = Prefs.class.getSimpleName();
	
	public static String protocol;
	public static String host;
	public static String port;
	public static String path;
	private static String timeoutStr;
	public static long timeout;
	public static String username;
	public static String password;
	public static boolean mock;
	
	private static boolean initialized = false;
	
	private static final String PROTOCOL_KEY = "protocol";
	private static final String HOST_KEY = "host";
	private static final String PORT_KEY = "port";
	private static final String PATH_KEY = "path";
	private static final String TIMEOUT_KEY = "timeout";
	private static final String USERNAME_KEY = "username";
	private static final String PASSWORD_KEY = "password";
	private static final String MOCK_KEY = "mock";
	
	private ListPreference protocolPref;
	private EditTextPreference hostPref;
	private EditTextPreference portPref;
	private EditTextPreference pathPref;
	private EditTextPreference timeoutPref;
	private EditTextPreference usernamePref;
	private EditTextPreference passwordPref;
	private CheckBoxPreference mockPref;
	
	
	@Override
	public boolean onPreferenceChange(Preference arg0, Object arg1) {
		// TODO Auto-generated method stub
		return false;
	}

}
