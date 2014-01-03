package de.hska.eb.util;

import javax.json.JsonObject;


public interface JsonMappable {
	JsonObject toJsonObject();
	void fromJsonObject(JsonObject jsonObject);
}