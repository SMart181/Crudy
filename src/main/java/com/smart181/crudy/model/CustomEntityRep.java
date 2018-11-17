package com.smart181.crudy.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = CustomEntityRepDeserializer.class)
public class CustomEntityRep {
	private Map<String, Object> content = new HashMap<String, Object>();

	public boolean hasPropertie(String name) {
		if(content.containsKey(name))
			return true;
		
		return false;
	}
	
	public void addPropertie(String name, Object value){
		if(value == null)
			content.remove(name);
		else
			content.put(name, value);
	}
	
	public Object getPropertie(String name){
		if(content.containsKey(name))
			return content.get(name);
		return null;
	}
	
	public Object getEntity() {
		return content;
	}	
}
