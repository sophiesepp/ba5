package com.sophiesepp.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class TimeCountry extends HeatmapObject implements IsSerializable {
	
	public TimeCountry() {
		// TODO Auto-generated constructor stub
	}
	
	public TimeCountry(int publication, String country,
			double percent) {
		super();
	
		
		this.publication = publication;
		this.country = country;
		this.percent = percent;
	}
	


	public String country;
	public int publication;
	public double percent;

	
}
