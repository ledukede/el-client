package de.hska.eb.service;

import org.apache.http.util.TextUtils;

final class WebServiceClient {
	private enum AuthType { BASIC, FORM };
	
	private static final String LOG_TAG = WebServiceClient.class.getSimpleName();
	
	private static final AuthType AUTH_TYPE = AuthType.BASIC;
	
	private static final String APPLICATION_JSON = "application/json";
	
	private static final String ACCEPT = "Accept";
	private static final String ACCEPT_LANGUAGE = "Accept-Language";
	private static final String CONTENT_TYPE = "Content-Type";
	private static final String AUTHORIZATION = "Authorization";
	private static final String LOCATION = "Location";
	
	private static final String PUT_METHOD = "PUT";
	private static final String DELETE_METHOD = "DELETE";
	
	private static String getBaseUrl() {
		if (TextUtils.isEmpty(port)) 
	}
	
}
