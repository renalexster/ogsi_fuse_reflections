package com.example.utils.reader;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public abstract class SampleBean  implements Serializable{
	private static final long serialVersionUID = 6302469790759424835L;
	
	
	public String marshall() throws Exception {
		String json=null;
		
		// create the mapper
        ObjectMapper mapper = new ObjectMapper();
 
        // enable pretty printing
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
 
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // serialize the object
        mapper.writeValue(baos, this);
        
        json = new String(baos.toByteArray());
		
		return json;
	}
	
	public SampleBean unmarshall(String value) throws Exception {
		 // create the mapper
        ObjectMapper mapper = new ObjectMapper();
        
        // de-serialize JSON to object
        SampleBean obj = mapper.readValue(value, this.getClass());
        
		return obj;
	}
}
