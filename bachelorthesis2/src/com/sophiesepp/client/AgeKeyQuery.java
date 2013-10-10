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
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sophiesepp.shared.AgeKey;
import com.sophiesepp.shared.HeatmapObject;


public class AgeKeyQuery  extends Heatmap implements EntryPoint{



	static VerticalPanel agekeyPanel = new VerticalPanel();
	static HorizontalPanel agekeycontentPanel = new HorizontalPanel();
	HorizontalPanel leftagekeyPanel = new HorizontalPanel();
	VerticalPanel rightagekeyPanel = new VerticalPanel();
	
	
	VerticalPanel seperationPanel = new VerticalPanel();
	HorizontalPanel agekeyPanel1= new HorizontalPanel();
	VerticalPanel  agekeyPanel2 = new VerticalPanel();
	VerticalPanel agekeyLabel1= new VerticalPanel();
	VerticalPanel agekeyLabel2= new VerticalPanel();
	VerticalPanel agekeyLabel3= new VerticalPanel();
	VerticalPanel agekeyLabel4= new VerticalPanel();
	
	
	final Button showAgeKeyQueryButton = new Button("Run Query");
	

	final Label agekeygenreLabel = new Label("Genre");
	final Label agekeyngramLabel = new Label("Melody");
	final Label agekeycountryLabel = new Label("Country");
	

	final static SuggestBox countryAgeKey = new SuggestBox(Srsr.country);
	final static SuggestBox genreAgeKey = new SuggestBox(Srsr.genre);
	final static TextBox ngramAgeKey = new TextBox();
	private static int i = 0;	
	private static int yPixelValue = 10;
	private static HashMap<String,Integer> agekey = new HashMap<String,Integer>();
	
	  private static class MyPopup extends PopupPanel {

		    public MyPopup() {
		      // PopupPanel's constructor takes 'auto-hide' as its boolean parameter.
		      // If this is set, the panel closes itself automatically when the user
		      // clicks outside of it.
		      super(true);


		      HTML contents = new HTML("This section shows the use of keys among ages of composers. You can see for example at which age of a composer a key is used most frequently. To prevent results being influenced by a composers producing more works at a certain age than at another age, results are normalized by the sum of publications of each age.");
		   
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

			agekey.put(com.sophiesepp.client.Srsr.keys[i],yPixelValue);
			yPixelValue+=20;
		}

		
		agekeyPanel.addStyleName("agekeyPanel");
		agekeycontentPanel.addStyleName("agekeycontentPanel");
		leftagekeyPanel.addStyleName("leftagekeyPanel");
		rightagekeyPanel.addStyleName("rightagekeyPanel");
		agekeyPanel1.addStyleName("Panel1");
		agekeyPanel2.addStyleName("Panel2");
		
		agekeyLabel1.addStyleName("label");
		agekeyLabel2.addStyleName("label");
		agekeyLabel3.addStyleName("label");
		agekeyLabel4.addStyleName("buttonlabel");
		
		agekeygenreLabel.addStyleName("text3");
		agekeyngramLabel.addStyleName("text3");
		agekeycountryLabel.addStyleName("text3");	
		
		countryAgeKey.addStyleName("textfield1");
		genreAgeKey.addStyleName("textfield1");
		ngramAgeKey.addStyleName("textfield1");
		
		showAgeKeyQueryButton.addStyleName("button1");
		b1.addStyleName("informationbutton");
		seperationPanel.addStyleName("seperationPanel");
		
		showAgeKeyQueryButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				AgeKeyQuery.queryAgeKey();
			}
		});
		
		
		
	
		
		
		agekeyPanel.add(RootPanel.get("heading8"));
		agekeyPanel.add(agekeycontentPanel);
		agekeyPanel.add(seperationPanel);
		
		agekeycontentPanel.add(leftagekeyPanel);
		agekeycontentPanel.add(rightagekeyPanel);
		agekeycontentPanel.add(b1);
		leftagekeyPanel.add(agekeyPanel1);
		leftagekeyPanel.add(agekeyPanel2);
	
		
		
		agekeyPanel1.add(RootPanel.get("heatmapAgeKeyCanvas"));

		
		agekeyLabel1.add(agekeygenreLabel);
		agekeyLabel1.add(genreAgeKey);
		agekeyLabel2.add(agekeycountryLabel);
		agekeyLabel2.add(countryAgeKey);
		agekeyLabel3.add(RootPanel.get("KeyboardAgeKeyContainer"));
		agekeyLabel4.add(showAgeKeyQueryButton);

		agekeyPanel2.add(agekeyLabel1);
		agekeyPanel2.add(agekeyLabel2);
		agekeyPanel2.add(agekeyLabel3);
		agekeyPanel2.add(agekeyLabel4);



	




	}

	public static void queryAgeKey() {

		greetingService.showQueryAgeKey(buildQueryAgeKey(),new AsyncCallback<List<AgeKey>>() {
			public void onFailure(Throwable caught) {

			}

			public void onSuccess(List<AgeKey> result){

			
				int yValue;

				List<HeatmapObject> object = new ArrayList<HeatmapObject>();		
				
				for(AgeKey s: result)
				{			
					String key = s.key;
					yValue = agekey.get(key);
					
					HeatmapObject obj = new HeatmapObject((s.age*3)+40,yValue+10,s.percent);	
					object.add(obj);
					
				}
	
				String json = createJson(object);
				displayDataAgeKey(json);
			

			}
		});
	}
	
	public static String buildQueryAgeKey(){

		
		String country="";
		String genre="";
		String ngram="";
		String query = "";
		int x=0;
		int y=0;
		int z=0;
		
		if(!(countryAgeKey.getText().isEmpty()))
		{
			x=1;
			country= countryAgeKey.getText();			
		}
		if(!(genreAgeKey.getText().isEmpty()))
		{
			y=1;
			genre= genreAgeKey.getText();			
		}
		
		InputElement ngraminput = (InputElement)(Element)DOM.getElementById("ngramAgeKey");

		if(ngraminput.getValue().length() > 0) {
			System.out.println("ngram true");
			x=1;
			ngram= ngraminput.getValue();			
		} 
		
		
		if((x==0) &&  (y==0) && (z==0)){
			query= "SELECT table2.publication-table1.birth AS value1, table2.key AS value2,count(table2.key) AS value3 FROM workspace.composer AS table1 JOIN(SELECT publication AS publication,key AS key, personId AS personId FROM workspace.work) AS table2 ON table1.personId=table2.personId GROUP BY value1,value2"; 

		}
		if((x==0) &&  (y==0) && (z==1)){
			query= "SELECT (table3.publication-table4.birth) AS value1, table3.key AS value2, count(table3.key) AS value3 FROM(SELECT table2.personId AS persona,table2.publication AS publication,table2.key AS key FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work) AS table2 ON table1.worka = table2.workb) AS table3 JOIN (SELECT personId AS personb, birth AS birth, country AS country FROM workspace.composer) AS table4 ON table3.persona = table4.personb GROUP BY value1,value2";
		}
		if((x==1) &&  (y==0) && (z==0)){
			query= "SELECT (table1.publication-table2.birth) AS value1, table1.key AS value2, count(table1.key) AS value3 FROM  workspace.work AS table1 JOIN(SELECT personId AS personId,birth AS birth, country AS country FROM workspace.composer WHERE country='";
			query+=country;
			query+="') AS table2 ON table1.personId = table2.personId GROUP BY value1,value2";
		}
		if((x==1) &&  (y==0) && (z==1)){
			query= "SELECT (table3.publication-table4.birth) AS value1, table3.key AS value2, count(table3.key) AS value3 FROM(SELECT table2.personId AS persona,table2.publication AS publication,table2.key AS key FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work) AS table2 ON table1.worka = table2.workb) AS table3 JOIN (SELECT personId AS personb, birth AS birth, country AS country FROM workspace.composer WHERE country='";
			query+=country;
			query+="') AS table4 ON table3.persona = table4.personb GROUP BY value1,value2";
		}
		if((x==0) &&  (y==1) && (z==0)){
			query= "SELECT table2.publication-table1.birth AS value1, table2.key AS value2, count(table2.key) AS value3 FROM workspace.composer AS table1 JOIN(SELECT publication AS publication,key AS key,genre AS genre, personId AS personId FROM workspace.work WHERE genre='";
			query+=genre;
			query+="') AS table2 ON table1.personId=table2.personId GROUP BY value1,value2";  
		}
		if((x==0) &&  (y==1) && (z==1)){
			query= "SELECT (table3.publication-table4.birth) AS value1, table3.key AS value2, count(table3.key) AS value3 FROM(SELECT table2.personId AS persona,table2.publication AS publication,table2.key AS key,table2.genre AS genre FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work WHERE genre='";
			query+=genre;
			query+="') AS table2 ON table1.worka = table2.workb) AS table3 JOIN (SELECT personId AS personb, birth AS birth, country AS country FROM workspace.composer) AS table4 ON table3.persona = table4.personb GROUP BY value1,value2";
		}
		
		if((x==1) &&  (y==1) && (z==0)){
			query= "SELECT table2.publication-table1.birth AS value1, table2.key AS value2,count(table2.key) AS value3 FROM (SELECT personId AS personId, birth AS birth, country AS country FROM workspace.composer WHERE country='";
			query+=country;
			query+="')AS table1 JOIN(SELECT publication AS publication,key AS key,genre AS genre, personId AS personId FROM workspace.work WHERE genre='";
			query+=genre;
			query+="') AS table2 ON table1.personId=table2.personId GROUP BY value1,value2";  
		}
		if((x==1) &&  (y==1) && (z==1)){
			query= "SELECT (table3.publication-table4.birth) AS value1, table3.key AS value2, count(table3.key) AS value3 FROM(SELECT table2.personId AS persona,table2.publication AS publication,table2.key AS key,table2.genre AS genre FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work WHERE genre='";
			query+=genre;
			query+="') AS table2 ON table1.worka = table2.workb) AS table3 JOIN (SELECT personId AS personb, birth AS birth, country AS country FROM workspace.composer WHERE country='";
			query+=country;
			query+="') AS table4 ON table3.persona = table4.personb GROUP BY value1,value2";
		}

		
		System.out.println(query);

		return query;
	}


	public static native void displayDataAgeKey(String data) /*-{

	var obj = eval('('+data+')');


	// call the heatmap's store's setDataSet method in order to set static data

	$wnd.zz.store.setDataSet(obj);
	}-*/;
	
	public static void displayMessage(){
		d.show();

	}
	
	public static void closeMessage(){
		d.setVisible(false);
		
	}


}
