package de.hska.eb.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.json.JsonObject;

import de.hska.eb.util.EventsApp;
import de.hska.eb.util.JsonMappable;

public class Event implements Serializable, JsonMappable{

private Integer eventId;
	private int version;
	private String name;
	private String description;
	private String place;
	private Date date;
	private Date created;
	private int voting;
	private Commentable commentable;
	private File pic;
	private List<User> guests;
	
	
	@Override
	public JsonObject toJsonObject() {
		return EventsApp.jsonBuilderFactory
				.createObjectBuilder()
				.add("id", eventId)
				.add("version", version)
				.add("description", description)
				.add("name", name)
				.add("place", place)
				.add("date", new SimpleDateFormat("yyyy-MM-dd").format(date))
				.add("pic", pic.toJsonObject())
				.build();
	}

	@Override
	public void fromJsonObject(JsonObject jsonObject) {
		// TODO Auto-generated method stub
		
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public int getVoting() {
		return voting;
	}

	public void setVoting(int voting) {
		this.voting = voting;
	}

	public Commentable getCommentable() {
		return commentable;
	}

	public void setCommentable(Commentable commentable) {
		this.commentable = commentable;
	}

	public File getPic() {
		return pic;
	}

	public void setPic(File pic) {
		this.pic = pic;
	}

	public List<User> getGuests() {
		return guests;
	}

	public void setGuests(List<User> guests) {
		this.guests = guests;
	}

}
