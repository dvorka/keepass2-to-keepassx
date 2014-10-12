package com.mindforger.keepass;

import java.io.File;
import java.net.URL;

public class Keepass2DatabaseToKeepassXDatabaseTest {

	public Keepass2DatabaseToKeepassXDatabaseTest() {
	}
	
	private String getFileLocation(String filename) {
		URL resource = Keepass2DatabaseToKeepassXDatabaseTest.class.getResource(filename);
		System.out.println("Loading Keepass2 XML database export from: "+resource.getPath());
		return resource.getPath();
	}

	private void testSkeleton(String keepass2DbName) throws Exception {
		File from = new File(getFileLocation(keepass2DbName+".xml"));
		File to=File.createTempFile(keepass2DbName+"-", ".xml");
		System.out.println("Writing: "+to.getAbsolutePath());
		new Keepass2DatabaseToKeepassXDatabase(from, to);				
	}
	
	public void testEmptyGroup() throws Exception {
		testSkeleton("empty-group");
	}
	
	public void testNewDatabase() throws Exception {
		testSkeleton("new-database");
	}
	
	public void testLocator() throws Exception {
		testSkeleton("locator");
	}
	
	public static void main(String args[]) throws Exception {
		Keepass2DatabaseToKeepassXDatabaseTest test = new Keepass2DatabaseToKeepassXDatabaseTest();
		test.testNewDatabase();
		test.testLocator();
		test.testEmptyGroup();
	}
}
