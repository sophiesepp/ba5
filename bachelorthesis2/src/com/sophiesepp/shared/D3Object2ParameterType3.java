package com.sophiesepp.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class D3Object2ParameterType3 implements D3Object, IsSerializable {
	
	public  D3Object2ParameterType3() {
		// TODO Auto-generated constructor stub
	}
	
	public  D3Object2ParameterType3(int x,
			double y) {
		super();
	
		
		this.x = x;
		this.y = y;
	}
	


	public int x;
	public double y;
	

	

}
