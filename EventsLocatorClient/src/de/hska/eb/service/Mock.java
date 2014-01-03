package de.hska.eb.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.util.Log;
import de.hska.eb.util.EventsApp;
import de.hska.eb.util.InternalShopError;

final class Mock {
	private static final String LOG_TAG = Mock.class.getSimpleName();
	
	private static String read(int dateinameId) {
		final BufferedReader reader = new BufferedReader(
						new InputStreamReader(EventsApp.open(dateinameId)));
		final StringBuilder sb = new StringBuilder();
		try {
			for(;;) {
				final String line = reader.readLine();
				if (line == null) {
					break;
				}
				sb.append(line);
			}
		}
		catch (IOException e) {
			throw new InternalShopError(e.getMessage(), e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
				}
				catch (IOException e) {}
			}
				
		}
		
		final String jsonStr = sb.toString();
		Log.v(LOG_TAG, "jsonStr = " + jsonStr);
		return jsonStr;
	}
	
	private Mock() {}
}
