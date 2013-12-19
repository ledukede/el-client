package de.hska.eb.util;

import javax.json.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;


public interface JsonMappable {
	JsonObject toJsonObject();
	void fromJsonObject(JSONObject jsonObject) throws JSONException;
}
