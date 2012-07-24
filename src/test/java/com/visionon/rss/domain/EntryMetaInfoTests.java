/**
 * 
 */
package com.visionon.rss.domain;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tv.visionon.rss.domain.EntryMetaInfo;

/**
 * @author administrator
 *
 */
public class EntryMetaInfoTests {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void entryShouldBeEqualToItself() {
		Date publishedDate = new Date();
		Date updatedDate = new Date();
		String title = "Title";
		String uri = "http://uri";
		EntryMetaInfo lhs = getTestEntry(title, publishedDate, updatedDate, uri, "desc");
		
		assertEquals(lhs, lhs);
	}
	
	@Test
	public void equalsShouldWorkWithMatchedObjects() {
		Date publishedDate = new Date();
		Date updatedDate = new Date();
		String title = "Title";
		String uri = "http://uri";
		String desc = "A description";
		EntryMetaInfo rhs = getTestEntry(title, publishedDate, updatedDate, uri, desc);
		EntryMetaInfo lhs = getTestEntry(title, publishedDate, updatedDate, uri, desc);
		
		assertEquals(lhs, rhs);
	}
	
	
	@Test
	public void equalsShouldWorkBothWays() {
		Date publishedDate = new Date();
		Date updatedDate = new Date();
		String title = "Title";
		String uri = "http://uri";
		String desc = "A description";
		EntryMetaInfo rhs = getTestEntry(title, publishedDate, updatedDate, uri, desc);
		EntryMetaInfo lhs = getTestEntry(title, publishedDate, updatedDate, uri, desc);
		
		assertEquals(lhs, rhs);
		assertEquals(rhs, lhs);
	}
	
	@Test
	public void equalsShouldNotMatchDifferentTypes() {
		Date publishedDate = new Date();
		Date updatedDate = new Date();
		String title = "Title";
		String uri = "http://uri";
		EntryMetaInfo lhs = getTestEntry(title, publishedDate, updatedDate, uri, "A description");
		
		assertFalse(lhs.equals(publishedDate));
	}
	
	@Test
	public void shouldNotBeEqualsToNull() {
		Date publishedDate = new Date();
		Date updatedDate = new Date();
		String title = "Title";
		String uri = "http://uri";
		EntryMetaInfo lhs = getTestEntry(title, publishedDate, updatedDate, uri, "desc");
		
		assertFalse(lhs.equals(null));
	}
	
	private EntryMetaInfo getTestEntry(String title, Date publishedDate, Date updatedDate, String uri, String description) {
		EntryMetaInfo entry = new EntryMetaInfo();
		entry.setTitle(title);
		entry.setPublishedDate(publishedDate);
		entry.setUpdatedDate(updatedDate);
		entry.setUri(uri);
		entry.setDescription(description);
		return entry;
	}
}
