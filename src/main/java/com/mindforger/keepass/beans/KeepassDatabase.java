package com.mindforger.keepass.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class KeepassDatabase {

	public List<KeepassDatabaseEntry> entries;
	public List<KeepassDatabaseGroup> groups;
	
	public Stack<KeepassDatabaseGroup> groupStack;
	
	public KeepassDatabase() {		
		groups=new ArrayList<KeepassDatabaseGroup>();
		entries=new ArrayList<KeepassDatabaseEntry>();
		groupStack=new Stack<KeepassDatabaseGroup>();
	}
	
	public List<KeepassDatabaseEntry> getEntries(KeepassDatabaseGroup group) {
		List<KeepassDatabaseEntry> result=new ArrayList<KeepassDatabaseEntry>();
		for(KeepassDatabaseEntry entry:entries) {
			if(group.id==entry.groupId) {
				result.add(entry);
			}
		}
		return result;
	}
	
	public KeepassDatabaseGroup getRootGroup() {
		for(KeepassDatabaseGroup g:groups) {
			if(g.parent==null) {
				return g;
			}
		}		
		return null;
	}
	
	public List<KeepassDatabaseGroup> getSubgroups(KeepassDatabaseGroup group) {
		List<KeepassDatabaseGroup> result=new ArrayList<KeepassDatabaseGroup>();
		for(KeepassDatabaseGroup g:groups) {
			if(g.parent==group.id) {
				result.add(g);
			}
		}
		return result;		
	}
	
	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
		sb.append("KeepassDatabase [entries: ");
		sb.append(entries.size());
		sb.append(", groups: ");
		sb.append(groups.size());
		sb.append(", entries:");
		for(KeepassDatabaseEntry entry: entries) {
			sb.append("\n  ");
			sb.append(entry.title);
			sb.append(" # ");
			sb.append(entry.groupId);
		}
		sb.append(",\ngroups:"); 
		for(KeepassDatabaseGroup group: groups) {
			sb.append("\n  ");
			sb.append(group.name);
			sb.append(" > ");
			sb.append(group.parent);
		}
		sb.append("\n]");
		return sb.toString();
	}
}
