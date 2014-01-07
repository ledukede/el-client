package de.hska.eb.util;

public final class Constants {
	
	public static final String PROTOCOL_DEFAULT = "http";
	public static final String HOST_EMULATOR = "10.0.2.2";
	public static final String HOST_DEFAULT = "iwi-w-eb06.hs-karlsruhe.de";
	public static final String PORT_DEFAULT = "8080";
	public static final String TIMEOUT_DEFAULT = "30";
	public static final String PATH_DEFAULT = "/friendslocator/rest";
	public static final boolean MOCK_DEFAULT = true;
	
	public static final String USER_PATH = "/users";
	public static final String EMAIL_PATH = USER_PATH + "/email" + "?email=";
	public static final String USER_EVENTS_PATH = "/events";
	public static final String USER_UPCOMING_PATH = "/upcoming";
	public static final String USER_PASSED_PATH = "/passed";
	public static final String USER_PIC_PATH = "/pic";
	
	public static final String EVENT_PATH = "/events";
	public static final String NAME_PATH = EVENT_PATH + "/name" +"?name=";
	public static final String PLACE_PATH = EVENT_PATH + "/place" + "?place=";
	public static final String RECENT_EVENTS_PATH = "/events" + "/recently";
	
	public static final String USER_LOGGED_IN_KEY = "loggedUser";
	public static final String EVENTS_KEY = "events";
	public static final String EVENT_KEY = "event";
	
	private Constants() {
		
	}
}
