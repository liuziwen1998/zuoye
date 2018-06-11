package com.bookStore.commons.beans;

import java.sql.Timestamp;

public class Notice {
	private Integer n_id;
	private String title;
	private String details;
	private Timestamp n_time;
	public Integer getN_id() {
		return n_id;
	}
	public void setN_id(Integer n_id) {
		this.n_id = n_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public Timestamp getN_time() {
		return n_time;
	}
	public void setN_time(Timestamp n_time) {
		this.n_time = n_time;
	}
	@Override
	public String toString() {
		return "Notice [n_id=" + n_id + ", title=" + title + ", details=" + details + ", n_time=" + n_time + "]";
	}
	
	
	

}
