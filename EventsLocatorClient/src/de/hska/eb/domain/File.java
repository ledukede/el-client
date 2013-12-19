package de.hska.eb.domain;

import java.io.Serializable;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.json.JSONObject;

import de.hska.eb.util.JsonMappable;

public class File implements Serializable, JsonMappable{

	private Integer id;
	private int version;
	private byte[] bytes;
	
	
	public JsonObjectBuilder getJsonObjectBuilder() {
		return null;
		//TODO
	}
	
	@Override
	public JsonObject toJsonObject() {
		return getJsonObjectBuilder().build();
	}

	@Override
	public void fromJsonObject(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

}
