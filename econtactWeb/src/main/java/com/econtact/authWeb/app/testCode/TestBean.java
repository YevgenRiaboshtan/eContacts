package com.econtact.authWeb.app.testCode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "testBean")
@ViewScoped
public class TestBean implements Serializable {
	private static final long serialVersionUID = -292856430946906320L;

	public void test() {
		System.out.println("blank");
		Map<String, String> res = decode("");
		if (res != null) {
			for(Entry<String, String> entry : res.entrySet()) {
				System.out.println(entry.getKey() + " " + entry.getValue());
			}
		}
		System.out.println("null");
		res = decode(null);
		if (res != null) {
			for(Entry<String, String> entry : res.entrySet()) {
				System.out.println(entry.getKey() + " " + entry.getValue());
			}
		}
		System.out.println("one=1&two=");
		res = decode("one=1&two=");
		if (res != null) {
			for(Entry<String, String> entry : res.entrySet()) {
				System.out.println(entry.getKey() + " " + entry.getValue());
			}
		}
		System.out.println("one=1&=2");
		res = decode("one=1&=2");
		if (res != null) {
			for(Entry<String, String> entry : res.entrySet()) {
				System.out.println(entry.getKey() + " " + entry.getValue());
			}
		}
		System.out.println("one=1&two2");
		try{
			res = decode("one=1&two=2=1");
			if (res != null) {
				for(Entry<String, String> entry : res.entrySet()) {
					System.out.println(entry.getKey() + " " + entry.getValue());
				}
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Exception");
		}
		
		/*System.out.println(findArray(new int[] {}, new int[] {}));
		System.out.println(findArray(new int[] { 1 }, new int[] {}));
		System.out.println(findArray(new int[] {}, new int[] { 1 }));
		System.out.println(findArray(new int[] { 4, 9, 3, 7, 8 }, new int[] { 3, 7 }));
		System.out.println(findArray(new int[] { 1, 3, 5 }, new int[] { 1 }));
		System.out.println(findArray(new int[] { 7, 8, 9 }, new int[] { 8, 9, 10 }));
		System.out.println(findArray(new int[] { 4, 9, 3, 7, 8, 3, 7, 1 }, new int[] { 3, 7 }));
		System.out.println(findArray(new int[] { 11, 3, 3, 3, 1, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 1, 3, 3, 3, 3, 3, 3, 1,
				3, 3, 3, 1, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 1, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 1, 3, 3, 3, 3, 3, 3, 1, 3,
				3, 3, 1, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 1, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 1, 3, 3, 3, 3, 3, 31, 3, 3, 3,
				1, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 1, 3, 3, 3, 3, 3, 31, 3, 3, 3, 1, 3, 3, 3, 3, 3, 31, 3, 3, 3, 1, 3, 3,
				3, 3, 3, 31, 3, 3, 3, 1, 3, 3, 3, 3, 3, 31, 3, 3, 3, 1, 3, 3, 3, 3, 3, 31, 3, 3, 3, 1, 3, 3, 3, 3, 3,
				31, 3, 3, 3, 1, 3, 3, 3, 3, 3, 3 }, new int[] { 11, 3 }));*/
	}

	public int findArray(int[] array, int[] subArray) {
		if (array.length < subArray.length || array.length == 0 || subArray.length == 0) {
			return -1;
		}

		int subArrayCheckInxed = subArray.length - 1;

		for (int i = array.length - 1; i >= 0; i--) {
			if (array[i] == subArray[subArrayCheckInxed]) {
				if (subArrayCheckInxed == 0) {
					return i;
				} else {
					subArrayCheckInxed--;
				}
			} else {
				i += subArray.length - subArrayCheckInxed - 1;
				subArrayCheckInxed = subArray.length - 1;
			}
		}
		return -1;
	}

	public Map<String, String> decode(String s) {
		if (s == null) {
			return null;
		}
		String[] pair;
		Map<String, String> result = new HashMap<String, String>();
		if (s.trim().isEmpty()) {
			return result;
		}
		for (String pairItem : s.split("&")) {
			pair = pairItem.split("=");
			if (!pairItem.contains("=")
					|| pair.length > 2) {
				throw new IllegalArgumentException();
			}
			if (pair.length == 2) {
				result.put(pair[0], pair[1]);
			} else if (pairItem.startsWith("=")){
				result.put("", pair[0]);
			} else {
				result.put(pair[0], "");
			}
		}
		
		return result;
	}
}
