package com.econtact.authWeb.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;

public class FilterUtilsTest {

	@Test
	public void convertDateException() {
		Map<String, Calendar> correct = buildCorrectDate();
		Assert.assertFalse(correct.isEmpty());
		
		Calendar test = Calendar.getInstance();
		for(Entry<String, Calendar> entry : correct.entrySet()) {
			try {
				test.setTime(FilterUtils.convertDate(entry.getKey()));
			} catch (ParseException e) {
				test = null;
			}
			Assert.assertNotNull(test);
			Assert.assertEquals("Date String - " + entry.getKey(), entry.getValue().compareTo(test), 0);
		}	
		for (String value : buildIncorrectDate()) {
			try {
				FilterUtils.convertDate(value);
				Assert.assertNotNull("Date String - " + value, null);
			} catch (ParseException e) { 
			}
		}
	}
	
	private Map<String, Calendar> buildCorrectDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy".intern());
		Map<String, Calendar> correct = new HashMap<String, Calendar>();
		Calendar item = Calendar.getInstance();
		try {
			item.setTime(dateFormat.parse("1-1-2016"));
		} catch (ParseException e) {
			return correct;
		}
		correct.put("1.1", item);
		correct.put("0101", item);
		correct.put("1/1", item);
		correct.put("01/01", item);
		correct.put("1-1", item);
		correct.put("01-01", item);
		
		correct.put("1.1.16", item);
		correct.put("1.1.016", item);
		correct.put("1.1.2016", item);
		correct.put("01.1.16", item);
		correct.put("01.1.016", item);
		correct.put("01.1.2016", item);
		correct.put("1.01.16", item);
		correct.put("1.01.016", item);
		correct.put("1.01.2016", item);
		correct.put("01.01.16", item);
		correct.put("01.01.016", item);
		correct.put("01.01.2016", item);
		
		correct.put("1-1-16", item);
		correct.put("1-1-016", item);
		correct.put("1-1-2016", item);
		correct.put("01-1-16", item);
		correct.put("01-1-016", item);
		correct.put("01-1-2016", item);
		correct.put("1-01-16", item);
		correct.put("1-01-016", item);
		correct.put("1-01-2016", item);
		correct.put("01-01-16", item);
		correct.put("01-01-016", item);
		correct.put("01-01-2016", item);
		
		correct.put("1/1/16", item);
		correct.put("1/1/016", item);
		correct.put("1/1/2016", item);
		correct.put("01/1/16", item);
		correct.put("01/1/016", item);
		correct.put("01/1/2016", item);
		correct.put("1/01/16", item);
		correct.put("1/01/016", item);
		correct.put("1/01/2016", item);
		correct.put("01/01/16", item);
		correct.put("01/01/016", item);
		correct.put("01/01/2016", item);
		try {
			item = Calendar.getInstance();
			item.setTime(dateFormat.parse("29-02-2016"));
		} catch (ParseException e) {
			return correct;
		}
		correct.put("29.02.2016", item);
		
		return correct;
	}
	
	private List<String> buildIncorrectDate() {
		List<String> incorrect = new ArrayList<String>();
		incorrect.add("");
		incorrect.add("a");
		incorrect.add("!");
		incorrect.add(".");
		incorrect.add("/");
		incorrect.add("-");
		incorrect.add("1.");
		incorrect.add("1/");
		incorrect.add("1-");
		incorrect.add("1--1");
		incorrect.add("29.02.2015");
		incorrect.add("00.00.0000");
		incorrect.add("0");
		incorrect.add("3299999");
		return incorrect;
	}
}
