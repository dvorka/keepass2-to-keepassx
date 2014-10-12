package com.mindforger.keepass.keepassx;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import com.mindforger.keepass.beans.KeepassDatabase;
import com.mindforger.keepass.beans.KeepassDatabaseEntry;
import com.mindforger.keepass.beans.KeepassDatabaseGroup;

public class KeepassXSerializer {

	public XmlSerializer serializer;
	public KeepassDatabase database;
	
	public KeepassXSerializer(KeepassDatabase database) throws Exception {
		this.database = database;
		serializer = XmlPullParserFactory.newInstance().newSerializer();
	}
	
	public void toXml(File file) throws Exception {
		FileWriter fileWriter = new FileWriter(file);
		serializer.setOutput(fileWriter);
		
		serializer.docdecl(" KEEPASSX_DATABASE");
		serializer.startTag("", "database");
		
		KeepassDatabaseGroup root=database.getRootGroup();
		if(root!=null) {
			groupToXml(database, root, serializer);
		}		

		serializer.endTag("", "database");
		serializer.flush();
	}
	
	private String nn(String s) {
		return s!=null?s:"";
	}
	
	private void groupToXml(KeepassDatabase database, KeepassDatabaseGroup group, XmlSerializer serializer) throws Exception {
		serializer.startTag(null, "group");
		
		serializer.startTag(null, "title");
		serializer.text(group.name);
		serializer.endTag(null, "title");
		
		serializer.startTag(null, "icon");
		serializer.text("1");
		serializer.endTag(null, "icon");

		List<KeepassDatabaseGroup> subgroups = database.getSubgroups(group);
		if(subgroups!=null) {
			for(KeepassDatabaseGroup subgroup:subgroups) {
				groupToXml(database, subgroup, serializer);
			}						
		}
		List<KeepassDatabaseEntry> entries=database.getEntries(group);
		for(KeepassDatabaseEntry entry:entries) {
			if(entry!=null) {
				serializer.startTag(null, "entry");

				serializer.startTag(null, "title");
				serializer.text(nn(entry.title));
				serializer.endTag(null, "title");

				serializer.startTag(null, "username");
				serializer.text(nn(entry.username));
				serializer.endTag(null, "username");
				
				serializer.startTag(null, "password");
				serializer.text(nn(entry.password));
				serializer.endTag(null, "password");
				
				serializer.startTag(null, "url");
				serializer.text(nn(entry.url));
				serializer.endTag(null, "url");

				serializer.startTag(null, "comment");
				serializer.text(nn(entry.notes));
				serializer.endTag(null, "comment");
				
				serializer.startTag(null, "icon");
				serializer.text("0");
				serializer.endTag(null, "icon");
				
				serializer.startTag(null, "creation");
				serializer.text("2014-06-30T09:20:17");
				serializer.endTag(null, "creation");
				
				serializer.startTag(null, "lastaccess");
				serializer.text("2014-06-30T09:20:23");
				serializer.endTag(null, "lastaccess");

				serializer.startTag(null, "expire");
				serializer.text("Never");
				serializer.endTag(null, "expire");
				
				serializer.endTag(null, "entry");				
			}
		}
		
		serializer.endTag(null,"group");
	}	
}
