package de.hska.eb.domain;

import static de.hska.eb.util.EventsApp.jsonBuilderFactory;

import java.io.Serializable;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import de.hska.eb.util.JsonMappable;

public class User implements Serializable, JsonMappable{
	private static final long serialVersionUID = 8697665641814551004L;
	
	public Long id;
	public int version;
	public String email;
	public String password;
	public String description;
	public String name;
	public String picUri;
	public String eventsUri;
	public String commentsUri;
	public String surname;

	private JsonObjectBuilder getJsonObjectBuilder() {
		return jsonBuilderFactory
		.createObjectBuilder()
		.add("id", id)
		.add("version", version)
		.add("email", email)
		.add("description", description)
		.add("password", password)
		.add("name", name)
		.add("surName", surname)
		.add("picUri", picUri)
		.add("commentableUri", commentsUri)
		.add("eventsUri", eventsUri);
	}
	
	@Override
	public JsonObject toJsonObject() {
		return getJsonObjectBuilder().build();
	}

	@Override
	public void fromJsonObject(JsonObject jsonObject) {
		id = Long.valueOf(jsonObject.getJsonNumber("id").longValue());
		version = jsonObject.getInt("version");
		email = jsonObject.getString("email");
		name = jsonObject.getString("name");
		surname = jsonObject.getString("surname");
		description = jsonObject.getString("description");
		password = jsonObject.getString("password");
		picUri = jsonObject.getString("picsUri");
		commentsUri = jsonObject.getString("commentableUri");
		eventsUri = jsonObject.getString("eventsUri");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", version=" + version + ", email=" + email
				+ ", password=" + password + ", description=" + description
				+ ", name=" + name + ", picUri=" + picUri + ", eventsUri="
				+ eventsUri + ", commentsUri=" + commentsUri + ", surname="
				+ surname + "]";
	}
}
