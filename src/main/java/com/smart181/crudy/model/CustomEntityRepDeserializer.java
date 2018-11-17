package com.smart181.crudy.model;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
public class CustomEntityRepDeserializer extends StdDeserializer<CustomEntityRep> {
	private static final long serialVersionUID = 3893305012382434731L;

    public CustomEntityRepDeserializer() { 
        this(null); 
    } 
    
	protected CustomEntityRepDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public CustomEntityRep deserialize(JsonParser jsonParser, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
	
	
		CustomEntityRep newEntityRep = new CustomEntityRep();
		
		Iterator<Entry<String, JsonNode>> iter = rootNode.fields();
		while(iter.hasNext())
		{
			Entry<String, JsonNode> currentEntry = iter.next();
			UpdateRepWithNodeValue(newEntityRep, currentEntry.getKey(), currentEntry.getValue());
		}
		
		return newEntityRep;
	}

	private CustomEntityRep UpdateRepWithNodeValue(CustomEntityRep currentRep, String nodeName, JsonNode node){
		if(node.isTextual())
			currentRep.addPropertie(nodeName, node.asText());
		else if(node.isInt())
			currentRep.addPropertie(nodeName, node.asInt());
		else if(node.isLong())
			currentRep.addPropertie(nodeName, node.asLong());
		else if(node.isDouble())
			currentRep.addPropertie(nodeName, node.asDouble());
		else if(node.isBoolean())
			currentRep.addPropertie(nodeName, node.asBoolean());
			
		return currentRep;
	}
	
}
