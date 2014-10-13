package com.mindforger.keepass;

import java.io.File;
import java.net.URL;

import org.junit.Ignore;
import org.junit.Test;

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

	@Test
	public void testEmptyGroup() throws Exception {
		testSkeleton("empty-group");
	}
	
	@Ignore
	@Test
	public void testNewDatabase() throws Exception {
		testSkeleton("new-database");
	}
	
	@Ignore
	@Test
	public void testLocator() throws Exception {
		testSkeleton("locator");
	}	
}
