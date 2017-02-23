package com.springhotel.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("env")
public class Env {
	private static Map<String, String> envMap = new HashMap<String, String>();
	public static synchronized void put(String key, String value) {
		envMap.put(key, value);
	}
	public static synchronized String get(String key) {
		return envMap.get(key);
	}
	
}
