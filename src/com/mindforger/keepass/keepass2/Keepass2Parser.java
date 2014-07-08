package com.mindforger.keepass.keepass2;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.mindforger.keepass.beans.KeepassDatabase;
import com.mindforger.keepass.beans.KeepassDatabaseEntry;
import com.mindforger.keepass.beans.KeepassDatabaseGroup;

public class Keepass2Parser {

    public Keepass2Parser() throws Exception {
    }

    public KeepassDatabase fromXml(File file) throws Exception {    	
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
            xpp.setInput(in, "UTF-8");
            int eventType = xpp.getEventType();
            if (eventType == XmlPullParser.START_DOCUMENT) {
            	
            	KeepassDatabase database=parseDatabase(xpp);            	
            	fastForwardToGroup(xpp);    

            	do {
                	parseLevel(xpp, database);
                	
            		String nextElement=xpp.getName();
            		if("Group".equals(nextElement)) {
            			if(XmlPullParser.START_TAG == xpp.getEventType()) {
            				parseLevel(xpp, database);
            			} else {
            				// continue            				
            			    if(!"Group".equals(xpp.getName()) && !"Entry".equals(xpp.getName())) {
            			    	break;
            			    }
            			}
            		} else {
            			// EOD or  DeletedObjects
            			break;
            		}                	
            	} while(true);
            	return database;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return null;
    }

	private void parseLevel(XmlPullParser xpp, KeepassDatabase database) throws Exception {
		KeepassDatabaseGroup group=parseGroup(xpp);
		if(group!=null) {
			if(database.groupStack.size()>0) {
				group.parent=database.groupStack.peek().id;
			}

			database.groups.add(group);
			database.groupStack.push(group);			
			
			if(fastForwardToEntry(xpp)) {
				KeepassDatabaseEntry entry;
				do {
					entry=parseEntry(xpp, group);
					if(entry!=null) {
						database.entries.add(entry);
						xpp.nextTag();
					} else {
						break;
					}
				} while("Entry".equals(xpp.getName()));			
			}			
		} else {
			database.groupStack.pop();
		}
	}

	private boolean fastForwardToEntry(XmlPullParser xpp) throws Exception {
		do {
			xpp.next();
			if("Group".equals(xpp.getName()) && xpp.getEventType()==XmlPullParser.END_TAG) {
				return false;
			}
		} while(!"Entry".equals(xpp.getName()));
		return true;
	}

	private void fastForwardToGroup(XmlPullParser xpp) throws XmlPullParserException, IOException {
		do {
			xpp.next();
		} while(!"Group".equals(xpp.getName()));
	}

	private KeepassDatabase parseDatabase(XmlPullParser xpp) throws XmlPullParserException, IOException {
		KeepassDatabase result=new KeepassDatabase();
		xpp.nextTag();
		xpp.require(XmlPullParser.START_TAG, "", "KeePassFile");
		xpp.nextTag();
		xpp.require(XmlPullParser.START_TAG, "", "Meta");
		return result;
	}

	private KeepassDatabaseGroup parseGroup(XmlPullParser xpp) throws Exception {
		if("Group".equals(xpp.getName()) && xpp.getEventType()==XmlPullParser.START_TAG) {
			KeepassDatabaseGroup result=new KeepassDatabaseGroup();

			xpp.nextTag();
			if("UUID".equals(xpp.getName())) {
				result.id=xpp.nextText();
			}
			xpp.nextTag();
			if("Name".equals(xpp.getName())) {
				result.name=xpp.nextText();
			}

			return result;
		} else {
			xpp.nextTag();
			return null;
		}
	}

    private KeepassDatabaseEntry parseEntry(XmlPullParser xpp, KeepassDatabaseGroup group) throws Exception {
		KeepassDatabaseEntry result = new KeepassDatabaseEntry();
		result.groupId=group.id;
    	
    	xpp.nextTag();
    	xpp.require(XmlPullParser.START_TAG, "", "UUID");
		result.id=xpp.nextText();
    	xpp.require(XmlPullParser.END_TAG, "", "UUID");
		
    	// fast forward to String (and loop the values there)
    	do {
    		xpp.next();
    	} while(!"String".equals(xpp.getName()));
    	
    	do {
        	if("String".equals(xpp.getName())) {
            	xpp.require(XmlPullParser.START_TAG, "", "String");
            	parseEntryInnards(xpp, result);
            	xpp.nextTag();
            	xpp.require(XmlPullParser.END_TAG, "", "String");
            	xpp.nextTag();
        	}
    	} while("String".equals(xpp.getName()));

    	// fast forward to History
    	do {
    		xpp.next();
    	} while(!("History".equals(xpp.getName()) && xpp.getEventType()==XmlPullParser.END_TAG));
    	
		xpp.nextTag();
    	xpp.require(XmlPullParser.END_TAG, "", "Entry");
    	
		return result;
	}

	private void parseEntryInnards(
			XmlPullParser xpp,
			KeepassDatabaseEntry result) throws XmlPullParserException, IOException {
    	xpp.nextTag();
		xpp.require(XmlPullParser.START_TAG, "", "Key");
		String key=xpp.nextText();
    	xpp.require(XmlPullParser.END_TAG, "", "Key");

    	xpp.nextTag();
    	xpp.require(XmlPullParser.START_TAG, "", "Value");
		String value=xpp.nextText();
    	xpp.require(XmlPullParser.END_TAG, "", "Value");
    	
    	if("Notes".equals(key)) {
    		result.notes=value;
    	} else {
        	if("Password".equals(key)) {
        		result.password=value;
        	} else {
            	if("Title".equals(key)) {
            		result.title=value;
            	} else {
                	if("URL".equals(key)) {
                		result.url=value;
                	} else {
                    	if("UserName".equals(key)) {
                    		result.username=value;
                    	}
                	}
            	}
        	}
    	}
	}
}
