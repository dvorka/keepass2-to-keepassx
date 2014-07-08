package com.mindforger.keepass;

import java.io.File;

public class Keepass2DatabaseToKeepassXDatabaseTest {
	
	public static void main(String args[]) throws Exception {
		new Keepass2DatabaseToKeepassXDatabase(
				new File("/home/dvorka/p/keepassx/git/keepass2-to-keepassx/data/keepass2-dump.xml"), 
				new File("/home/dvorka/Desktop/keepass2-export.xml"));
	}
}
