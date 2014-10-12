package com.mindforger.keepass.beans;

public class KeepassDatabaseEntry {

	public String id;
	public String title;
	public String username;
	public String password;
	public String notes;
	public String url;
	
	public String groupId;
	
	public KeepassDatabaseEntry() {		
	}

	@Override
	public String toString() {
		return "KeepassDatabaseEntry [id=" + id + ", title=" + title
				+ ", username=" + username + ", password=" + password
				+ ", comment=" + notes + ", url=" + url + ", groupId="
				+ groupId + "]";
	}
}
