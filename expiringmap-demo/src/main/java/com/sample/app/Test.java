package com.sample.app;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	static String formExecutableQuery(String query, Map<String, String> params) {
		/*Pattern p = Pattern.compile("\\{.*?\\}");
		Matcher m = p.matcher(query);
		while (m.find()) {
			String val1 = m.group().replace("{", "").replace("}", "");
			query = (query.replace(m.group(), params.get(val1)));
		}
		return query;*/
		
		String queryResult = new String(query);
		
		for(String key : params.keySet()) {
			String val1 = params.get(key);
			String keyToReplace = "{" + key + "}";
			
			queryResult = queryResult.replace(keyToReplace, val1);
		}
		
		return queryResult;
	}

	public static void main(String args[]) {
		String query = "SELECT * FROM WHERE x = {param1} and y = {param1}";
		
		 Map<String, String> params = new HashMap<> ();
		 params.put("param1", "p1");
		 params.put("param2", "p2");
		 
		 System.out.println(formExecutableQuery(query, params));
		 
	}
}
