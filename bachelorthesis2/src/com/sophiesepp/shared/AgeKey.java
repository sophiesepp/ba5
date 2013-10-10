package com.sophiesepp.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class AgeKey extends HeatmapObject implements IsSerializable {
	

	public AgeKey() {
		// TODO Auto-generated constructor stub
	}
	
	public AgeKey(int age, String key,
			double percent) {
		super();
	
		
		this.age = age;
		this.key = key;
		this.percent = percent;
		
	}
	


	public String key;
	public int age;
	public double percent;

	


}
