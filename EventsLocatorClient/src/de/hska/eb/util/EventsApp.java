package de.hska.eb.util;

import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonReaderFactory;

import android.app.Application;
import android.content.Context;

public class EventsApp extends Application{
	public static JsonReaderFactory jsonReaderFactory;
	public static JsonBuilderFactory jsonBuilderFactory;
	private static Context ctx;
	@Override
	public void onCreate(){
	jsonReaderFactory = Json.createReaderFactory(null);
	jsonBuilderFactory = Json.createBuilderFactory(null);
	ctx = this;
	}
	public static InputStream open(int dateinameId) {
	return ctx.getResources()
	.openRawResource(dateinameId);
	}
}
