package de.hska.eb.service;

import java.util.ArrayList;

public class HttpResponse<T> {
	public int responseCode;
	public String content;
	
	public T resultObject;
	public ArrayList<T> resultList;
	
	public HttpResponse(int responseCode, String content) {
		super();
		this.responseCode = responseCode;
		this.content = content;
	}
	
	public HttpResponse(int responseCode, String content, T resultObject) {
		super();
		this.responseCode = responseCode;
		this.content = content;
		this.resultObject = resultObject;
	}
	
	public HttpResponse(int responseCode, String content, ArrayList<T> resultList) {
		super();
		this.responseCode = responseCode;
		this.content = content;
		this.resultList = resultList;
	}

	@Override
	public String toString() {
		return "HttpResponse [responseCode=" + responseCode + ", content="
				+ content + ", resultObject=" + resultObject + ", resultList="
				+ resultList + "]";
	}
}
