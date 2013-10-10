package com.sophiesepp.shared;

import com.google.gwt.user.client.rpc.IsSerializable;


public class TimeAge extends HeatmapObject implements IsSerializable {
	
	public TimeAge() {
		// TODO Auto-generated constructor stub
	}
	
	public TimeAge(int publication, int age,
			double percent) {
		super();
	
		
		this.publication = publication;
		this.age = age;
		this.percent = percent;
		
	}
	


	public int age;
	public int publication;
	public double percent;
	
}
