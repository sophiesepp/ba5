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
import com.sophiesepp.client.MostUsedNgramsQuery.MyPopup;
import com.sophiesepp.shared.D3Object3Parameter;
import com.sophiesepp.shared.MostUsedNgrams;

public class MostUsedNgramsQuery extends D3 implements EntryPoint {
	



	static VerticalPanel mostusedngramsPanel = new VerticalPanel();
	VerticalPanel mostusedngramsPanel1 = new VerticalPanel();
	VerticalPanel mostusedngramsPanel2 = new VerticalPanel();


	  public static class MyPopup extends PopupPanel {

		    public MyPopup() {
		      // PopupPanel's constructor takes 'auto-hide' as its boolean parameter.
		      // If this is set, the panel closes itself automatically when the user
		      // clicks outside of it.
		      super(true);

		      HTML contents = new HTML("This image shows the ten most used ngrams. It is computed by the number of publications in which a ngram appears over time.");
			   
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
		    
		mostusedngramsPanel.addStyleName("mostusedngramsPanel");
		mostusedngramsPanel1.addStyleName("mostusedngramsPanel1");
		b1.addStyleName("informationbutton");
		
		mostusedngramsPanel1.add(RootPanel.get("heading13"));
		mostusedngramsPanel1.add(b1);
		mostusedngramsPanel2.add(RootPanel.get("multilinechartMostUsedNgrams"));

		mostusedngramsPanel.add(mostusedngramsPanel1);
		mostusedngramsPanel.add(mostusedngramsPanel2);

	}
	
	public static void queryMostUsedNgrams() {

		greetingService.showQueryMostUsedNgrams(buildQueryMostUsedNgrams(),new AsyncCallback<List<MostUsedNgrams>>(){
			public void onFailure(Throwable caught) {


			}

			public void onSuccess(List<MostUsedNgrams> result) {

				List<D3Object3Parameter> object = new ArrayList<D3Object3Parameter>();
				
				
				for(MostUsedNgrams s: result)
				{			
					D3Object3Parameter obj = new D3Object3Parameter(s.publication,s.ngram,s.counts);	
					object.add(obj);
					
				}
				
				String json = createJson3Parameter(object,"publication","ngram","counts");


				displayDataMostUsedNgrams(json);
			}
		});
	}
	

	
	public static String buildQueryMostUsedNgrams(){

		String query = "SELECT ngram AS ngram, count(ngram) AS counts FROM workspace.ngram GROUP BY ngram ORDER BY counts DESC LIMIT 10";
		return query;
	}


	public static native void displayDataMostUsedNgrams(String data) /*-{

	var obj = eval(data);
	$wnd.mostusedngrams(obj);	



	}-*/;	

	


}
