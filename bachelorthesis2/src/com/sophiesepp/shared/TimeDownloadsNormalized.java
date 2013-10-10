package com.sophiesepp.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class TimeDownloadsNormalized extends D3Object2ParameterType1 implements IsSerializable {
	

	public  TimeDownloadsNormalized() {
		// TODO Auto-generated constructor stub
	}
	
	public  TimeDownloadsNormalized(int publication, int percent) {
		super();
	
		this.publication = publication;
		this.percent = percent;
	}
	


	public int publication;
	public int percent;
	


}
