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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sophiesepp.client.MostUsedGenresQuery.MyPopup;
import com.sophiesepp.shared.D3Object3Parameter;
import com.sophiesepp.shared.MostUsedGenres;

public class MostUsedGenresQuery extends D3 implements EntryPoint {
	
	static VerticalPanel mostusedgenresPanel = new VerticalPanel();
	VerticalPanel mostusedgenresPanel1 = new VerticalPanel();
	VerticalPanel mostusedgenresPanel2 = new VerticalPanel();
	  public static class MyPopup extends PopupPanel {

		    public MyPopup() {
		      // PopupPanel's constructor takes 'auto-hide' as its boolean parameter.
		      // If this is set, the panel closes itself automatically when the user
		      // clicks outside of it.
		      super(true);

		      HTML contents = new HTML("This image shows the ten most used genres. Is is computed by the absolute number of publications that have been made in a genre over time.");
			   
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

		mostusedgenresPanel.addStyleName("mostusedgenresPanel");
		mostusedgenresPanel1.addStyleName("mostusedgenresPanel1");
		b1.addStyleName("informationbutton");
		
		mostusedgenresPanel1.add(RootPanel.get("heading12"));
		mostusedgenresPanel1.add(b1);
		mostusedgenresPanel2.add(RootPanel.get("multilinechartMostUsedGenres"));
		
		mostusedgenresPanel.add(mostusedgenresPanel1);
		mostusedgenresPanel.add(mostusedgenresPanel2);
	
	}

	public static void queryMostUsedGenres() {

		greetingService.showQueryMostUsedGenres(buildQueryMostUsedGenres(),new AsyncCallback<List<MostUsedGenres>>(){
			public void onFailure(Throwable caught) {


			}

			public void onSuccess(List<MostUsedGenres> result) {
				
				List<D3Object3Parameter> object = new ArrayList<D3Object3Parameter>();
				
				
				for(MostUsedGenres s: result)
				{			
					D3Object3Parameter obj = new D3Object3Parameter(s.publication,s.genre,s.counts);	
					object.add(obj);
					
				}
				
				String json = createJson3Parameter(object,"publication","genre","counts");

				displayDataMostUsedGenres(json);
			}
		});
	}


	public static String buildQueryMostUsedGenres(){

		String query = "SELECT genre AS genre, count(genre) AS counts FROM workspace.work GROUP BY genre ORDER BY counts DESC LIMIT 10";
		return query;
	}

	
	public static native void displayDataMostUsedGenres(String data) /*-{

	var obj = eval(data);
	$wnd.mostusedgenres(obj);	



	}-*/;
	
	


}


