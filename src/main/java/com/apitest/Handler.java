package com.apitest;

import java.util.ArrayList;
import java.util.List;

public class Handler {

	public static void main(String[] args) {
		List<String> weekDays = new ArrayList<String>();
		weekDays.add("1");
		weekDays.add("2");
		weekDays.add("3");
		weekDays.add("4");
		weekDays.add("5");
		weekDays.add("6");
		
		String weekStr = weekDays.toString().replace(", ", ",").replaceAll("[\\[.\\]]", "");
		
		System.out.println(weekStr);
	}
}
