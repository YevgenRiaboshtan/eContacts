package com.econtact.authWeb.app.testCode;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.Serializable;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.function.BiConsumer;
import java.util.TreeSet;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class TestBean implements Serializable {
	private static final long serialVersionUID = -292856430946906320L;

	public void test() {
        List<Integer> var = Arrays.asList(1,2,3,4,5,6);
        System.out.println(var.stream().map(e -> e * 2).reduce(0, (c, e) -> c + e));
        var.forEach(System.out::println);
        System.out.println();

	}
	
	
	public Map<Integer, String> fillMapInData(String[] values) {
        Map<Integer, String> result = new HashMap<Integer, String>();
        
        List<Integer> var = Arrays.asList(1,2,3,4,5,6);
        var.forEach(System.out::println);
        System.out.println(var.stream().map(e -> e * 2));
        Integer i = Integer.valueOf(0);
        for ( ; i < values.length; i++) {
            result.put(i, values[i]);
        }
        return result;
    }
	public int gcd(int a, int b) {
	      if ( a == 0 || b == 0) {
	          throw new  IllegalArgumentException();
	      }
	      while (a != b) {
	          if (a > b) {
	              a = a - b;
	          } else {
	              b = b - a;
	          }
	      }
	      return a;
	  }   

}
