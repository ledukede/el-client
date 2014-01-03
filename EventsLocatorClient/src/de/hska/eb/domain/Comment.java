package de.hska.eb.domain;

import static de.hska.eb.util.EventsApp.jsonBuilderFactory;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;



import de.hska.eb.util.InternalShopError;
import de.hska.eb.util.JsonMappable;

public class Comment implements JsonMappable, Serializable {
	private static final long serialVersionUID = 8723221309698276152L;
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	
	public Long id;
	public String userUri; //poster
	public Date date;
	public String message;
	
	private JsonObjectBuilder getJsonObjectBuilder() {
		return jsonBuilderFactory.createObjectBuilder()
								 .add("commentId", id)
								 .add("userUri", userUri)
								 .add("date", new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(date))
								 .add("message", message);
	}
	
	@Override
	public JsonObject toJsonObject() {
		return getJsonObjectBuilder().build();
	}

	@Override
	public void fromJsonObject(JsonObject jsonObject) {
		id = Long.valueOf(jsonObject.getJsonNumber("commentId").longValue());
		userUri = jsonObject.getString("userUri");
		try {
			date = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).parse(jsonObject.getString("date"));
		}
		catch (ParseException e) {
			throw new InternalShopError(e.getMessage(), e);
		}
		message = jsonObject.getString("message");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((userUri == null) ? 0 : userUri.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (userUri == null) {
			if (other.userUri != null)
				return false;
		} else if (!userUri.equals(other.userUri))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id 
				+ ", userUri=" + userUri 
				+ ", date=" + date
				+ ", message=" + message + "]";
	}
}
