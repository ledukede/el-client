package de.hska.eb.domain;

import java.io.Serializable;
import javax.json.JsonObject;
import de.hska.eb.util.EventsApp;
import de.hska.eb.util.JsonMappable;

public class User implements Serializable, JsonMappable{

	@Override
	public JsonObject toJsonObject() {
		return EventsApp.jsonBuilderFactory
		.createObjectBuilder()
		.add("id", userId)
		.add("version", version)
		.add("email", email)
		.add("agbaccepted", agbAccepted)
		.add("description", description)
		.add("password", password)
		.add("name", name)
		.add("surname", surName)
		.add("pic", pic.toJsonObject())
		.add("eventsUri", eventsUri)
		.build();
	}

	@Override
	public void fromJsonObject(JsonObject jsonObject) {
		userId = jsonObject.getInt("id");
		version = jsonObject.getInt("version");
		email = jsonObject.getString("email");
		name = jsonObject.getString("name");
		surName = jsonObject.getString("surname");
		description = jsonObject.getString("description");
		pic = new File();
		pic.fromJsonObject(jsonObject.getJsonObject("pic"));
	}

	private Integer userId;
	private int version;
	private String email;
	private String password;
	private String passwordWdh;
	private boolean agbAccepted;
	private String name;
	private String surName;
	private String description;
	private File pic;
	private String eventsUri;
	private Commentable commentable;
	
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordWdh() {
		return passwordWdh;
	}

	public void setPasswordWdh(String passwordWdh) {
		this.passwordWdh = passwordWdh;
	}

	public boolean isAgbAccepted() {
		return agbAccepted;
	}

	public void setAgbAccepted(boolean agbAccepted) {
		this.agbAccepted = agbAccepted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public File getPic() {
		return pic;
	}

	public void setPic(File pic) {
		this.pic = pic;
	}

	public Commentable getCommentable() {
		return commentable;
	}

	public void setCommentable(Commentable commentable) {
		this.commentable = commentable;
	}

	public String getEventsUri() {
		return eventsUri;
	}

	public void setEventsUri(String eventsUri) {
		this.eventsUri = eventsUri;
	}
}
