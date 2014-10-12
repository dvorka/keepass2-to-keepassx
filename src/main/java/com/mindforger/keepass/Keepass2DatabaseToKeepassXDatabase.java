package com.mindforger.keepass;

import java.io.File;

import com.mindforger.keepass.beans.KeepassDatabase;
import com.mindforger.keepass.keepass2.Keepass2Parser;
import com.mindforger.keepass.keepassx.KeepassXSerializer;

public class Keepass2DatabaseToKeepassXDatabase {

	public Keepass2DatabaseToKeepassXDatabase(File keepass2, File keepassx) throws Exception {
		Keepass2Parser keepass2Parser = new Keepass2Parser();
		KeepassDatabase database = keepass2Parser.fromXml(keepass2);
		if(database!=null) {
			KeepassXSerializer keepassXSerializer = new KeepassXSerializer(database);
			keepassXSerializer.toXml(keepassx);
			System.out.println("KeePassX database written to "+keepassx.getAbsolutePath());
		}		
	}
	
	public static void main(String args[]) throws Exception {
		if(args.length==2) {
			new Keepass2DatabaseToKeepassXDatabase(
					new File(args[0]), 
					new File(args[1]));			
		} else {
			System.out.print("\nUsage: keepass2-to-keepassX [KeePass2 XML export] [KeePassX XML file to import]");
			System.out.print("\nKeePass2 to KeePassX database convertor.");
			System.out.print("\nExample:");
			System.out.print("\n   $ keepass2-to-keepassX keepass2-database-export.xml new-keepassx-database.xml");
			System.out.println();
		}
	}
}
