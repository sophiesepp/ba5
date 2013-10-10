package com.sophiesepp.client;

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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sophiesepp.client.NgramComposerQuery.MyPopup;

public class NgramComposerQuery extends TextResults implements EntryPoint{

	

	static VerticalPanel ngramcomposerPanel = new VerticalPanel();
	static HorizontalPanel ngramcomposercontentPanel = new HorizontalPanel();
	VerticalPanel leftngramcomposerPanel = new VerticalPanel();
	VerticalPanel rightngramcomposerPanel = new VerticalPanel();
	VerticalPanel ngramcomposerPanel1= new VerticalPanel(); 
	static VerticalPanel  ngramcomposerPanel2 = new VerticalPanel();
	VerticalPanel ngramcomposerLabel1 = new VerticalPanel();
	VerticalPanel ngramcomposerLabel2 = new VerticalPanel();
	
	final static TextArea dataNgramComposer = new TextArea();
	final static TextBox ngramBoxComposer = new TextBox();
	
	final Button showNgramComposerQueryButton = new Button("Run Query");
	
	  public static class MyPopup extends PopupPanel {

		    public MyPopup() {
		      // PopupPanel's constructor takes 'auto-hide' as its boolean parameter.
		      // If this is set, the panel closes itself automatically when the user
		      // clicks outside of it.
		      super(true);

		      HTML contents = new HTML("This section answers the question which composers used a certain ngram most frequently.");
			   
		      setWidget(contents);

		      setStyleName("popupPanel");
			   
		      
		    }
	  }
		  
	  public static class Popup2 extends PopupPanel {

		    public Popup2() {
		      // PopupPanel's constructor takes 'auto-hide' as its boolean parameter.
		      // If this is set, the panel closes itself automatically when the user
		      // clicks outside of it.
		      super(true);
		   
		      setWidget(new Label("Waiting for the results of the query"));

		      setStyleName("popupPanel2");
		      
		    }
		  }
	  
	  
	
	  static Popup2 d = new Popup2();
	
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


		
		ngramBoxComposer.addStyleName("ngramBox");
		
		ngramcomposerLabel1.addStyleName("label");
		ngramcomposerLabel2.addStyleName("buttonlabel");

		ngramcomposerPanel.addStyleName("ngramcomposerPanel");
		ngramcomposercontentPanel.addStyleName("ngramcomposercontentPanel");
		leftngramcomposerPanel.addStyleName("leftngramcomposerPanel");
		rightngramcomposerPanel.addStyleName("rightngramcomposerPanel");
		ngramcomposerPanel1.addStyleName("ngramcomposerPanel1");
		ngramcomposerPanel2.addStyleName("ngramcomposerPanel2");
		b1.addStyleName("informationbutton");
		
		showNgramComposerQueryButton.addStyleName("NgramComposerQueryButton");
		

		showNgramComposerQueryButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				NgramComposerQuery.queryNgramComposer();
			}
		});
		
		dataNgramComposer.setWidth("500px");
		dataNgramComposer.setVisibleLines(20);
		dataNgramComposer.setEnabled(false);
		dataNgramComposer.setVisible(false);
		
	
		
		ngramcomposerPanel.add(RootPanel.get("heading14"));
		ngramcomposerPanel.add(ngramcomposercontentPanel);
		
		ngramcomposercontentPanel.add(leftngramcomposerPanel);
		ngramcomposercontentPanel.add(rightngramcomposerPanel);
		ngramcomposercontentPanel.add(b1);
		leftngramcomposerPanel.add(ngramcomposerPanel1);
		leftngramcomposerPanel.add(ngramcomposerPanel2);
		
		rightngramcomposerPanel.add(dataNgramComposer);
		ngramcomposerLabel1.add(RootPanel.get("KeyboardNgramComposerContainer"));
		
		ngramcomposerLabel2.add(showNgramComposerQueryButton);

		ngramcomposerPanel1.add(ngramcomposerLabel1);
		ngramcomposerPanel1.add(ngramcomposerLabel2);
		
		
	

	}
	
	public static void queryNgramComposer() {

		greetingService.showQueryNgramComposer(buildQueryNgramComposer(),
				new AsyncCallback<String>(){
			public void onFailure(Throwable caught) {


			}

			public void onSuccess(String result) {
				
				dataNgramComposer.getElement().setInnerText(result);
				dataNgramComposer.setVisible(true);
			}
		});
	}

	


	public static String buildQueryNgramComposer(){

		String ngramComposer = (RootPanel.get("ngramKeyPercent")).getElement().getInnerText();

		 String query = "SELECT table3.ngram AS ngram, count(table3.ngram) AS counts FROM (SELECT ngram AS ngram, scoreId AS scoreId FROM workspace.ngram) AS table3 JOIN(SELECT scoreId FROM(SELECT works.workId AS workId FROM (SELECT personId AS personId FROM workspace.composer WHERE personId='";
		 query += ngramComposer;
		 query +="') AS composers JOIN EACH(SELECT workId AS workId,personId AS personId FROM workspace.work) AS works ON composers.personId=works.personId) AS table1 JOIN (SELECT scoreId AS scoreId, workId AS workId FROM workspace.score) AS table2 ON table1.workId=table2.workId) AS table4 ON table3.scoreId=table4.scoreId GROUP BY ngram DESC LIMIT 10";
		System.out.println(query);

		return query;
	}
	
	public static void displayMessage(){
		d.show();

	}
	
	public static void closeMessage(){
		d.setVisible(false);
		
	}


}
