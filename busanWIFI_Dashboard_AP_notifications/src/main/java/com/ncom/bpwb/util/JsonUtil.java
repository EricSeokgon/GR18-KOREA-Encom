package com.ncom.bpwb.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	public static <T> T jsonStringToObject(String jsonString, Class<T> class1) throws JsonMappingException, JsonProcessingException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		return mapper.readValue(jsonString, class1);
	}

	
	public static   String  ObjectToJsonString(Object obj) throws JsonProcessingException {
		
		ObjectMapper mapper = new ObjectMapper();

		return mapper.writeValueAsString(obj);
	}
	

	
}
