package com.springhotel.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;

public class MediaUtil {
	
	private static Map<String, MediaType> mediaMap;
	static {
		mediaMap = new HashMap<String, MediaType>();		
		mediaMap.put("JPG", MediaType.IMAGE_JPEG);
		mediaMap.put("JPEG", MediaType.IMAGE_JPEG);
		mediaMap.put("GIF", MediaType.IMAGE_GIF);
		mediaMap.put("PNG", MediaType.IMAGE_PNG);
	}
	
	public static MediaType getMediaType(String type) {
		return mediaMap.get(type.toUpperCase());
	}
	
	public static boolean isImageFile(String filename) {
		String ext = filename.substring(filename.lastIndexOf(".")+1);
		MediaType mType = MediaUtil.getMediaType(ext);
		if (mType != null)
			return true;
		return false;
	}
}



