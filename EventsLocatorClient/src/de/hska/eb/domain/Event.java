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

public class Event implements Serializable, JsonMappable{
	private static final long serialVersionUID = -7274153878455296440L;
	private static final String DATE_FORMAT = "yyyy-MM-dd";	
	
	public Long id;
	public int version;
	public String name;
	public String description;
	public String place;
	public Date date;
	public Date created;
	public int voting;
	public String createrUri;
	public String commentsUri;
	public String picUri;
	public String guestsUri;
	
	private JsonObjectBuilder getJsonObjectBuilder() {
		return jsonBuilderFactory.createObjectBuilder()
								 .add("eventId", id)
								 .add("version", version)
								 .add("name", name)
								 .add("description", description)
								 .add("place", place)
								 .add("date", new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(date))
								 .add("created", new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(created))
								 .add("voting", voting)
								 .add("createrUri", createrUri)
								 .add("commentableUri", commentsUri)
								 .add("picUri", picUri)
								 .add("guestsUri", guestsUri);
	}
	
	@Override
	public JsonObject toJsonObject() {
		return getJsonObjectBuilder().build();
	}


	@Override
	public void fromJsonObject(JsonObject jsonObject) {
		id = Long.valueOf(jsonObject.getJsonNumber("eventId").longValue());
		version = jsonObject.getInt("version");
		name = jsonObject.getString("name");
		description = jsonObject.getString("description");
		place = jsonObject.getString("place");
		try {
			date = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).parse(jsonObject.getString("date"));
			created = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).parse(jsonObject.getString("created"));
		}
		catch (ParseException e) {
			throw new InternalShopError(e.getMessage(), e);
		}
		voting = jsonObject.getInt("voting");
		createrUri = jsonObject.getString("createrUri");
		commentsUri = jsonObject.getString("commentableUri");
		picUri = jsonObject.getString("picUri");
		guestsUri = jsonObject.getString("guestsUri");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((place == null) ? 0 : place.hashCode());
		result = prime * result + version;
		result = prime * result + voting;
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
		Event other = (Event) obj;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (place == null) {
			if (other.place != null)
				return false;
		} else if (!place.equals(other.place))
			return false;
		if (version != other.version)
			return false;
		if (voting != other.voting)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", version=" + version + ", name=" + name
				+ ", description=" + description + ", place=" + place
				+ ", date=" + date + ", created=" + created + ", voting="
				+ voting + ", createrUri=" + createrUri + ", commentsUri="
				+ commentsUri + ", picUri=" + picUri + ", guestsUri="
				+ guestsUri + "]";
	}
	
	
}
