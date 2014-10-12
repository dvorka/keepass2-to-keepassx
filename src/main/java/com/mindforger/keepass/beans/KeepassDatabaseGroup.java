package com.mindforger.keepass.beans;

public class KeepassDatabaseGroup {

	public String id;
	public String name;
	public String comment;
	public String parent;

	public KeepassDatabaseGroup() {
	}

	@Override
	public String toString() {
		return "KeepassDatabaseGroup [id=" + id + ", name=" + name
				+ ", comment=" + comment + "]";
	}
}
