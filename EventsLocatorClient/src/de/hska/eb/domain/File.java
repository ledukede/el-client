package de.hska.eb.domain;

import static de.hska.eb.util.EventsApp.jsonBuilderFactory;

import java.io.Serializable;
import java.util.Arrays;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import de.hska.eb.util.JsonMappable;

public class File implements Serializable, JsonMappable{
	private static final long serialVersionUID = 9057903070652261551L;
	
	public Long id;
	public int version;
	public byte[] bytes;
	public String filename;
	public String mimeType;
	public String multimediaType;

	private JsonObjectBuilder getJsonObjectBuilder() {
		return jsonBuilderFactory.createObjectBuilder()
								 .add("id", id)
								 .add("version", version)
								 .add("bytes", bytes.toString())
								 .add("filename", filename)
								 .add("mimeType", mimeType)
								 .add("multimediaType", multimediaType);
	}
	
	
	@Override
	public JsonObject toJsonObject() {
		return getJsonObjectBuilder().build();
	}

	@Override
	public void fromJsonObject(JsonObject jsonObject) {
		id = Long.valueOf(jsonObject.getJsonNumber("id").longValue());
		version = jsonObject.getInt("version");
		bytes = jsonObject.getString("bytes").getBytes();
		filename = jsonObject.getString("filename");
		mimeType = jsonObject.getString("mimeType");
		multimediaType = jsonObject.getString("multimediaType");
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((filename == null) ? 0 : filename.hashCode());
		result = prime * result
				+ ((mimeType == null) ? 0 : mimeType.hashCode());
		result = prime * result
				+ ((multimediaType == null) ? 0 : multimediaType.hashCode());
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
		File other = (File) obj;
		if (filename == null) {
			if (other.filename != null)
				return false;
		} else if (!filename.equals(other.filename))
			return false;
		if (mimeType == null) {
			if (other.mimeType != null)
				return false;
		} else if (!mimeType.equals(other.mimeType))
			return false;
		if (multimediaType == null) {
			if (other.multimediaType != null)
				return false;
		} else if (!multimediaType.equals(other.multimediaType))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "File [id=" + id + ", version=" + version + ", bytes="
				+ Arrays.toString(bytes) + ", filename=" + filename
				+ ", mimeType=" + mimeType + ", multimediaType="
				+ multimediaType + "]";
	}
}
