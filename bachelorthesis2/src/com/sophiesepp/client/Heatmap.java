package com.sophiesepp.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.sophiesepp.client.GreetingService;
import com.sophiesepp.client.GreetingServiceAsync;
import com.sophiesepp.shared.D3Object3Parameter;
import com.sophiesepp.shared.HeatmapObject;


public class Heatmap {
	

	
	protected final static GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	
	
	protected static String createJson(List<HeatmapObject> object){
		
		String json = "{max:1, data:[";
		
		for(HeatmapObject o: object)
		{
			json += "{x:"+o.x+",y:"+o.y+",count:"+o.z+"}"+",";
			
		}
		json += "]}";
		
		return json;
	}
	
	protected static String createJsonForLegend(List<HeatmapObject> object, String parameter1,String parameter2,String parameter3){
		
		
		String json = "[";

		for(HeatmapObject o: object)
		{
			
			json += "{\""+parameter1+"\":\""+o.x+"\",\""+parameter2+"\":\""+o.y+"\",\""+parameter3+"\":\""+o.z+"\"},";
		}

		json += "]";
		
		return json;
		
	}

}
