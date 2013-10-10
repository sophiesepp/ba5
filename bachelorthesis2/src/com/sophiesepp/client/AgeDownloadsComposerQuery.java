package com.sophiesepp.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sophiesepp.shared.AgeDownloadsComposer;
import com.sophiesepp.shared.D3Object2ParameterType1;


public class AgeDownloadsComposerQuery extends D3 implements EntryPoint{

	


	static VerticalPanel agedownloadscomposerPanel = new VerticalPanel();
	static HorizontalPanel agedownloadscomposercontentPanel = new HorizontalPanel();
	VerticalPanel leftagedownloadscomposerPanel = new VerticalPanel();
	VerticalPanel rightagedownloadscomposerPanel = new VerticalPanel();
	VerticalPanel seperationPanel = new VerticalPanel();
	
	  public static class Popup1 extends PopupPanel {

		    public Popup1() {
		      // PopupPanel's constructor takes 'auto-hide' as its boolean parameter.
		      // If this is set, the panel closes itself automatically when the user
		      // clicks outside of it.
		      super(true);


		      HTML contents = new HTML("The success of a composer over his working period is computed by the sum of downloaded works of each year. The image shows at which age a composer was most successful during his working period.");
		   
		      setWidget(contents);

		      setStyleName("popupPanel");
		      
		    }
		  }
	  
	

	
	public void onModuleLoad() {
		
		
		 final Button b1 = new Button("About");
		    final Popup1 g = new Popup1(); //create only one instance and reuse it.
		    g.setAutoHideEnabled(true);
		    g.setAnimationEnabled(true);
		    g.setGlassEnabled(true);
		    b1.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  g.center();//will show it and center it.
		       
		      }
		    });

		   

		
		agedownloadscomposerPanel.addStyleName("agedownloadscomposerPanel");
		agedownloadscomposercontentPanel.addStyleName("agedownloadscomposercontentPanel");
		leftagedownloadscomposerPanel.addStyleName("leftagedownloadscomposerPanel");
	
		b1.addStyleName("informationbutton");
	
		seperationPanel.addStyleName("seperationPanelComposer");

		agedownloadscomposerPanel.add(RootPanel.get("heading15"));
		agedownloadscomposerPanel.add(agedownloadscomposercontentPanel);
		agedownloadscomposerPanel.add(seperationPanel);
		agedownloadscomposercontentPanel.add(leftagedownloadscomposerPanel);
		agedownloadscomposercontentPanel.add(rightagedownloadscomposerPanel);
		leftagedownloadscomposerPanel.add(RootPanel.get("linechartAgeDownloadsComposer"));
		rightagedownloadscomposerPanel.add(b1);
		
		
 
	}
	
	
	
	
	public static void queryAgeDownloadsComposer() {

		greetingService.showQueryAgeDownloadsComposer(buildQueryAgeDownloadsComposer(),new AsyncCallback<List<AgeDownloadsComposer>>(){
			public void onFailure(Throwable caught) {


			}

			public void onSuccess(List<AgeDownloadsComposer> result) {
				
				List<D3Object2ParameterType1> object = new ArrayList<D3Object2ParameterType1>();
				
				
				for(AgeDownloadsComposer s: result)
				{			
					D3Object2ParameterType1 obj = new D3Object2ParameterType1(s.age,s.downloads);	
					object.add(obj);
					
				}
				
				String json = createJson2ParameterType1(object,"age","downloads");


				displayDataAgeDownloadsComposer(json);
			}
			
		});
	}
	
	public static void queryAgeDownloadsComposerBeginning() {

		greetingService.showQueryAgeDownloadsComposer("SELECT (table1.publication-table2.birth) AS age, sum(table1.downloads) AS downloads FROM(SELECT personId AS personId,publication AS publication,downloads AS downloads FROM workspace.work) AS table1 JOIN(SELECT personId AS personId, birth AS birth FROM workspace.composer WHERE personId='K1') AS table2 ON table1.personId = table2.personId GROUP BY age ORDER BY age ASC",new AsyncCallback<List<AgeDownloadsComposer>>(){
			public void onFailure(Throwable caught) {


			}

			public void onSuccess(List<AgeDownloadsComposer> result) {
				
				List<D3Object2ParameterType1> object = new ArrayList<D3Object2ParameterType1>();
				
				
				for(AgeDownloadsComposer s: result)
				{			
					D3Object2ParameterType1 obj = new D3Object2ParameterType1(s.age,s.downloads);	
					object.add(obj);
					
				}
				
				String json = createJson2ParameterType1(object,"age","downloads");


				displayDataAgeDownloadsComposer(json);
			}
			
		});
	}
	
	

	public static String buildQueryAgeDownloadsComposer(){

		String composer= Srsr.composerBox.getText();	
		String query ="";

		query= "SELECT (table1.publication-table2.birth) AS age, sum(table1.downloads) AS downloads FROM(SELECT personId AS personId,publication AS publication,downloads AS downloads FROM workspace.work) AS table1 JOIN(SELECT personId AS personId, birth AS birth FROM workspace.composer WHERE personId='";
		query+=composer;
		query+= "') AS table2 ON table1.personId = table2.personId GROUP BY age ORDER BY age ASC";

		return query;
	}


	public static native void displayDataAgeDownloadsComposer(String data) /*-{

	var obj = eval(data);
	$wnd.agedownloadscomposer(obj);	



	}-*/;	
	

}