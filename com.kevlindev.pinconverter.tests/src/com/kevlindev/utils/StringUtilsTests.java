package com.kevlindev.utils;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.kevlindev.utils.StringUtils;

public class StringUtilsTests {
	@Test
	public void testJoinArray() {
		String result = StringUtils.join(",", "a", "b", "c");

		assertEquals("a,b,c", result);
	}

	@Test
	public void testJoinEmptyArray() {
		String result = StringUtils.join(",");

		assertEquals("", result);
	}

	@Test
	public void testNullDelimiterWithArray() {
		String result = StringUtils.join(null, "a", "b", "c");

		assertEquals("abc", result);
	}

	@Test
	public void testNullArray() {
		String result = StringUtils.join(":", (String[]) null);

		assertEquals("", result);
	}

	@Test
	public void testNullDelimeterNullArray() {
		String result = StringUtils.join(null, (String[]) null);

		assertEquals("", result);
	}

	@Test
	public void testNullDelimiterWithCollection() {
		List<String> items = new ArrayList<String>();

		items.add("a");
		items.add("b");
		items.add("c");

		String result = StringUtils.join(null, items);

		assertEquals("abc", result);
	}

	@Test
	public void testNullCollection() {
		String result = StringUtils.join(":", (Collection<String>) null);

		assertEquals("", result);
	}

	@Test
	public void testNullDelimeterNullCollection() {
		String result = StringUtils.join(null, (Collection<String>) null);

		assertEquals("", result);
	}

	@Test
	public void testWrap() {
		String text = "This is a test of a line that should wrap across multiple lines";
		int width = 8;
		List<String> expected = new ArrayList<String>();

		expected.add("This is");
		expected.add("a test");
		expected.add("of a");
		expected.add("line");
		expected.add("that");
		expected.add("should");
		expected.add("wrap");
		expected.add("across");
		expected.add("multiple");
		expected.add("lines");

		assertEquals(expected, StringUtils.wrap(text, width));
	}

	@Test
	public void testWrapWithinWord() {
		String text = "hypothetically easy to wrap";
		int width = 8;
		List<String> expected = new ArrayList<String>();

		expected.add("hypothet");
		expected.add("ically");
		expected.add("easy to");
		expected.add("wrap");

		assertEquals(expected, StringUtils.wrap(text, width));
	}
}
