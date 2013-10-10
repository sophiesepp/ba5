package com.sophiesepp.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sophiesepp.client.TimeKeyQuery.MyPopup;
import com.sophiesepp.shared.HeatmapObject;
import com.sophiesepp.shared.TimeKey;

public class TimeKeyQuery extends Heatmap implements EntryPoint {
	

	

	static VerticalPanel timekeyPanel = new VerticalPanel();
	static HorizontalPanel timekeycontentPanel = new HorizontalPanel();
	HorizontalPanel lefttimekeyPanel = new HorizontalPanel();
	VerticalPanel righttimekeyPanel = new VerticalPanel();
	

	VerticalPanel seperationPanel = new VerticalPanel();
	HorizontalPanel timekeyPanel1= new HorizontalPanel();
	VerticalPanel  timekeyPanel2 = new VerticalPanel();
	VerticalPanel timekeyLabel1= new VerticalPanel();
	VerticalPanel timekeyLabel2= new VerticalPanel();
	VerticalPanel timekeyLabel3= new VerticalPanel();
	VerticalPanel timekeyLabel4= new VerticalPanel();
	
	final Button showTimeKeyQueryButton = new Button("Run Query");
	
	final Label timekeygenreLabel = new Label("Genre");	
	final Label timekeyngramLabel = new Label("Melody");
	final Label timekeycountryLabel = new Label("Country");
	

	final static SuggestBox countryTimeKey = new SuggestBox(Srsr.country);
	final static SuggestBox genreTimeKey = new SuggestBox(Srsr.genre);
	final static TextBox ngramTimeKey = new TextBox();
	private static int i = 0;
	private static int yPixelValue = 10;
	private static HashMap<String,Integer> timekey = new HashMap<String,Integer>();
	
	
	  
	  public static class MyPopup extends PopupPanel {

		    public MyPopup() {
		      // PopupPanel's constructor takes 'auto-hide' as its boolean parameter.
		      // If this is set, the panel closes itself automatically when the user
		      // clicks outside of it.
		      super(true);
		      
		      HTML contents = new HTML("This section shows the use of keys over time. You can see for example when the key C was used most frequently. Again comparisons aren`t influenced by the fact that a year had more publications than another, as results are normalized by the sum of publications of each year.");
			   
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
		  

		  


		
		
		for (i=0;i<com.sophiesepp.client.Srsr.keys.length;i++){
			
			timekey.put(com.sophiesepp.client.Srsr.keys[i],yPixelValue);
			yPixelValue+=20;
		}
		
		
		timekeyPanel.addStyleName("timekeyPanel");
		timekeycontentPanel.addStyleName("timekeycontentPanel");
		lefttimekeyPanel.addStyleName("lefttimekeyPanel");
		righttimekeyPanel.addStyleName("righttimekeyPanel");
		timekeyPanel1.addStyleName("Panel1");
		timekeyPanel2.addStyleName("Panel2");
		

		timekeyLabel1.addStyleName("label");
		timekeyLabel2.addStyleName("label");
		timekeyLabel3.addStyleName("label");
		timekeyLabel4.addStyleName("buttonlabel");
		
		timekeygenreLabel.addStyleName("text3");		
		timekeyngramLabel.addStyleName("text3");
		timekeycountryLabel.addStyleName("text3");	
		
		countryTimeKey.addStyleName("textfield1");
		genreTimeKey.addStyleName("textfield1");
		ngramTimeKey.addStyleName("textfield1");
		
		showTimeKeyQueryButton.addStyleName("button1");
		b1.addStyleName("informationbutton");
		seperationPanel.addStyleName("seperationPanel");
		
		showTimeKeyQueryButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				TimeKeyQuery.queryTimeKey();
			}
		});
	
	
		
		
		
		
		timekeyPanel.add(RootPanel.get("heading7"));
		timekeyPanel.add(timekeycontentPanel);
		timekeyPanel.add(seperationPanel);
		
		timekeycontentPanel.add(lefttimekeyPanel);
		timekeycontentPanel.add(righttimekeyPanel);
		timekeycontentPanel.add(b1);
		
		lefttimekeyPanel.add(timekeyPanel1);
		lefttimekeyPanel.add(timekeyPanel2);
	
		timekeyPanel1.add(RootPanel.get("heatmapTimeKeyCanvas"));	
		

		timekeyLabel1.add(timekeygenreLabel);
		timekeyLabel1.add(genreTimeKey);
		timekeyLabel2.add(timekeycountryLabel);
		timekeyLabel2.add(countryTimeKey);
		timekeyLabel3.add(RootPanel.get("KeyboardTimeKeyContainer"));
		timekeyLabel4.add(showTimeKeyQueryButton);

		timekeyPanel2.add(timekeyLabel1);
		timekeyPanel2.add(timekeyLabel2);
		timekeyPanel2.add(timekeyLabel3);
		timekeyPanel2.add(timekeyLabel4);
		
	

	}
	
	public static void queryTimeKey() {

		greetingService.showQueryTimeKey(buildQueryTimeKey(),new AsyncCallback<List<TimeKey>>() {
			public void onFailure(Throwable caught) {

			}

			public void onSuccess(List<TimeKey> result) {
			
				int yValue;
			
				List<HeatmapObject> object = new ArrayList<HeatmapObject>();		
				
				for(TimeKey s: result)
				{			
					String key = s.key;
					yValue = timekey.get(key);
					
					HeatmapObject obj = new HeatmapObject(s.publication-1700+40,yValue+10,s.percent);	
					object.add(obj);
					
				}
	
				String json = createJson(object);
				displayDataTimeKey(json);
		
			}
		});
		
	}
	

	public static String buildQueryTimeKey(){

		String query="";
		String genre = "";
		String ngram = "";
		String country = "";
		int x = 0;
		int y = 0;
		int z = 0;

		if(!(genreTimeKey.getText().isEmpty()))
		{
			x=1;
			genre= genreTimeKey.getText();			
		}
		if(!(countryTimeKey.getText().isEmpty()))
		{
			y=1;
			country= countryTimeKey.getText();			
		}
		InputElement ngraminput = (InputElement)(Element)DOM.getElementById("ngramTimeKey");

		if(ngraminput.getValue().length() > 0) {
			System.out.println("ngram true");
			x=1;
			ngram= ngraminput.getValue();			
		} 
		

		if((x==0) &&  (y==0) && (z==0)){
			query="SELECT publication AS value1,key AS value2,count(key)AS value3 FROM workspace.work GROUP BY value1,value2"; 

		}
		if((x==0) &&  (y==0) && (z==1)){
			query= "SELECT table2.publication AS value1, table2.key AS value2, count(table2.key) AS value3 FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work) AS table2 ON table1.worka = table2.workb GROUP BY value1,value2";
		}
		if((x==1) &&  (y==0) && (z==0)){
			query="SELECT publication AS value1,key AS value2,count(key)AS counts FROM workspace.work WHERE genre='";
			query+=genre;
			query+="' GROUP BY value1,value2"; 

		}
		if((x==1) &&  (y==0) && (z==1)){
			query= "SELECT table2.publication AS value1, table2.key AS value2, count(table2.key) AS value3 FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work WHERE genre='";
			query+=genre;
			query+="') AS table2 ON table1.worka = table2.workb GROUP BY value1,value2";

		}
		if((x==0) &&  (y==1) && (z==0)){
			query= "SELECT table1.publication AS value1, table1.key AS value2, count(table1.key) AS value3 FROM(SELECT personId AS personId,publication AS publication,key AS key FROM workspace.work) AS table1 JOIN(SELECT personId AS personId, country AS country FROM workspace.composer WHERE country='";
			query+=country;
			query+="') AS table2 ON table1.personId = table2.personId GROUP BY value1,value2";
		}
		if((x==0) &&  (y==1) && (z==1)){
			query= "SELECT table3.publication AS value1, table3.key AS value2,count(table3.key) AS value3 FROM(SELECT table2.personId AS persona,table2.publication AS publication, table2.key AS key FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work) AS table2 ON table1.worka = table2.workb) AS table3 JOIN (SELECT personId AS personb, birth AS birth, country AS country FROM workspace.composer WHERE country='";
			query+= country;
			query+="') AS table4 ON table3.persona = table4.personb GROUP BY value1,value2";
		}
		
		if((x==1) &&  (y==1) && (z==0)){
			query= "SELECT table1.publication AS value1, table1.key AS value2, count(table1.key) AS value3 FROM(SELECT personId AS personId,publication AS publication,key AS key,genre AS genre FROM workspace.work WHERE genre='";
			query+=genre;
			query+="') AS table1 JOIN(SELECT personId AS personId, country AS country FROM workspace.composer WHERE country='";
			query+=country;
			query+="') AS table2 ON table1.personId = table2.personId GROUP BY value1,value2";
		}
		if((x==1) &&  (y==1) && (z==1)){
			query= "SELECT table3.publication AS value1, table3.key AS value2, count(table3.key) AS value3 FROM(SELECT table2.personId AS persona,table2.publication AS publication,table2.key AS key FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work WHERE genre='";
			query+=genre;
			query+="') AS table2 ON table1.worka = table2.workb) AS table3 JOIN (SELECT personId AS personb, birth AS birth, country AS country FROM workspace.composer WHERE country='";
			query+= country;
			query+="') AS table4 ON table3.persona = table4.personb GROUP BY value1,value2";
		}

		System.out.println(query);

		return query;
	}


	
	public static native void displayDataTimeKey(String data) /*-{

	var obj = eval('('+data+')');


	// call the heatmap's store's setDataSet method in order to set static data

	$wnd.yy.store.setDataSet(obj);
	}-*/;
	
	public static void displayMessage(){
		d.show();

	}
	
	public static void closeMessage(){
		d.setVisible(false);
		
	}


}
