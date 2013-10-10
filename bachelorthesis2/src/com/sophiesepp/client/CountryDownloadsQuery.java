package com.sophiesepp.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sophiesepp.client.CountryDownloadsQuery.MyPopup;
import com.sophiesepp.shared.CountryDownloads;
import com.sophiesepp.shared.D3Object2ParameterType2;

public class CountryDownloadsQuery extends D3 implements EntryPoint{


	
	static VerticalPanel countrydownloadsPanel = new VerticalPanel();
	VerticalPanel countrydownloadsPanel1 = new VerticalPanel();
	VerticalPanel countrydownloadsPanel2 = new VerticalPanel();
	
	  public static class MyPopup extends PopupPanel {

		    public MyPopup() {
		      // PopupPanel's constructor takes 'auto-hide' as its boolean parameter.
		      // If this is set, the panel closes itself automatically when the user
		      // clicks outside of it.
		      super(true);

		      HTML contents = new HTML("This image shows which countries have most downloaded works.");
			   
		      setWidget(contents);

		      setStyleName("popupPanel");
			   
		    }
		  }

	  
	public void onModuleLoad() {
		
		 final Button b1 = new Button("About");
		    final MyPopup g = new MyPopup(); //create only one instance and reuse it.
		    g.setAutoHideEnabled(true);
		    g.setAnimationEnabled(true);
		    g.setGlassEnabled(true);
		    b1.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  g.center();//will show it and center it.
		       
		      }
		    });

		      
		  



		countrydownloadsPanel.addStyleName("countrydownloadsPanel");
	
		
		countrydownloadsPanel1.addStyleName("countrydownloadsPanel1");
		b1.addStyleName("informationbutton");

		countrydownloadsPanel1.add(RootPanel.get("heading11"));
		countrydownloadsPanel1.add(b1);
		
		countrydownloadsPanel2.add(RootPanel.get("barchartCountryDownloads"));
		
	
		countrydownloadsPanel.add(countrydownloadsPanel1);
		countrydownloadsPanel.add(countrydownloadsPanel2);
	

	}

	public static void queryCountryDownloads() {

		greetingService.showQueryCountryDownloads(buildQueryCountryDownloads(),
				new AsyncCallback<List<CountryDownloads>>(){
			public void onFailure(Throwable caught) {


			}

			public void onSuccess(List<CountryDownloads> result) {
				
				List<D3Object2ParameterType2> object = new ArrayList<D3Object2ParameterType2>();
				
				
				for(CountryDownloads s: result)
				{			
					D3Object2ParameterType2 obj = new D3Object2ParameterType2(s.country,s.downloads);	
					object.add(obj);
					
				}
				
				String json = createJson2ParameterType2(object,"country","downloads");

				
				countrydownloads(json);
			}
		});
	}
	

	public static String buildQueryCountryDownloads(){

		String query = "SELECT country AS country, sum(downloads) AS percent FROM (SELECT country AS country, downloads AS downloads FROM workspace.composer AS composers JOIN (SELECT personId,downloads FROM workspace.work) AS works ON composers.personId=works.personId GROUP BY downloads,country) GROUP BY country";
		return query;
	}

	public static native void countrydownloads(String data) /*-{

	var obj = eval(data);
	$wnd.countrydownloads(obj);	
	
	}-*/;	


}
