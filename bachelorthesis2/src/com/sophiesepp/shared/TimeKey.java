package com.sophiesepp.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class TimeKey extends HeatmapObject implements IsSerializable {
	

	public TimeKey() {
		// TODO Auto-generated constructor stub
	}
	
	public TimeKey(int publication, String key,
			double percent) {
		super();
	
		
		this.publication = publication;
		this.key = key;
		this.percent = percent;
		
	}
	


	public String key;
	public int publication;
	public double percent;


	
	

}
