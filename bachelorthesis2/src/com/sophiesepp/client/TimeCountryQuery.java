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
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sophiesepp.client.ComposerNgramQuery.MyPopup;
import com.sophiesepp.shared.HeatmapObject;
import com.sophiesepp.shared.TimeCountry;

public class TimeCountryQuery extends Heatmap implements EntryPoint {
	
	

	static VerticalPanel  timecountryPanel = new VerticalPanel();
	static HorizontalPanel timecountrycontentPanel = new HorizontalPanel();
	HorizontalPanel lefttimecountryPanel = new HorizontalPanel();
	VerticalPanel righttimecountryPanel = new VerticalPanel();
	
	VerticalPanel seperationPanel = new VerticalPanel();
	
	HorizontalPanel timecountryPanel1= new HorizontalPanel();
	VerticalPanel  timecountryPanel2 = new VerticalPanel();
	VerticalPanel timecountryLabel1= new VerticalPanel();
	VerticalPanel timecountryLabel2= new VerticalPanel();
	VerticalPanel timecountryLabel3= new VerticalPanel();
	VerticalPanel timecountryLabel4= new VerticalPanel();
	
	private static Button showTimeCountryQueryButton = new Button("Run Query");
	
	private static Label timecountrygenreQuery = new Label("Genre");
	private static Label timecountrykeyQuery = new Label("Key");
	private static Label timecountryngramQuery = new Label("Melody");
	
	private static TextBox ngramTimeCountry = new TextBox();
	private static SuggestBox genreTimeCountry = new SuggestBox(Srsr.genre);
	private static SuggestBox keyTimeCountry = new SuggestBox(Srsr.key);
	private static int i = 0;
	private static int yPixelValue = 10;
	private static HashMap<String,Integer> timecountry = new HashMap<String,Integer>();
	
	  private static class MyPopup extends PopupPanel {

		    public MyPopup() {
		      // PopupPanel's constructor takes 'auto-hide' as its boolean parameter.
		      // If this is set, the panel closes itself automatically when the user
		      // clicks outside of it.
		      super(true);
		      
		      HTML contents = new HTML("This section represents the frequency of composing in countries over time.The red end of the color scale again represents the highestcounts. Each point shows the composing activity that was in a country at that point of time. Again you can limit the results if you want to see the composing activity regarding only one genre, key or ngram. If you for example specify a melody you can see when and where it appeared mostfrequently in composing history.");
			   
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
		  


		
		
		for (i=0;i<Srsr.countries.length;i++){
			
			timecountry.put(Srsr.countries[i],yPixelValue);
			yPixelValue+=20;
		}
		
		

		timecountryLabel1.addStyleName("label");
		timecountryLabel2.addStyleName("label");
		timecountryLabel3.addStyleName("label");
		timecountryLabel4.addStyleName("buttonlabel");

		timecountryPanel.addStyleName("timecountryPanel");
		timecountrycontentPanel.addStyleName("timecountrycontentPanel");
		timecountryPanel2.addStyleName("Panel2");
		lefttimecountryPanel.addStyleName("lefttimecountryPanel");
		righttimecountryPanel.addStyleName("righttimecountryPanel");
		
		
		timecountryngramQuery.addStyleName("text3");	
		timecountrygenreQuery.addStyleName("text3");
		timecountrykeyQuery.addStyleName("text3");	
		
		ngramTimeCountry.addStyleName("textfield1");
		genreTimeCountry.addStyleName("textfield1");
		keyTimeCountry.addStyleName("textfield1");
		
		showTimeCountryQueryButton.addStyleName("button1");
		b1.addStyleName("informationbutton");
		seperationPanel.addStyleName("seperationPanel");
		
		
		showTimeCountryQueryButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				TimeCountryQuery.queryTimeCountry();
			}
		});
		


		
		timecountryPanel.add(RootPanel.get("heading3"));
		timecountryPanel.add(timecountrycontentPanel);
		timecountryPanel.add(seperationPanel);
		
		timecountrycontentPanel.add(lefttimecountryPanel);
		timecountrycontentPanel.add(righttimecountryPanel);
		timecountrycontentPanel.add(b1);
		lefttimecountryPanel.add(timecountryPanel1);
		lefttimecountryPanel.add(timecountryPanel2);

		
		timecountryPanel1.add(RootPanel.get("heatmapTimeCountryCanvas"));	




		timecountryLabel1.add(timecountrygenreQuery);
		timecountryLabel1.add(genreTimeCountry);
		timecountryLabel2.add(timecountrykeyQuery);
		timecountryLabel2.add(keyTimeCountry);
		timecountryLabel3.add(RootPanel.get("KeyboardTimeCountryContainer"));
		timecountryLabel4.add(showTimeCountryQueryButton);


		timecountryPanel2.add(timecountryLabel1);
		timecountryPanel2.add(timecountryLabel2);
		timecountryPanel2.add(timecountryLabel3);
		timecountryPanel2.add(timecountryLabel4);
		
		
		
		
		
		

	}

	
	public static void queryTimeCountry() {

		greetingService.showQueryTimeCountry(buildQueryTimeCountry(),
				new AsyncCallback<List<TimeCountry>>() {
			public void onFailure(Throwable caught) {

			}

			public void onSuccess(List<TimeCountry> result) {
			
				int yValue;
				
				List<HeatmapObject> object = new ArrayList<HeatmapObject>();		
				
				for(TimeCountry s: result)
				{			
					String key = s.country;
					yValue = timecountry.get(key);
				
					HeatmapObject obj = new HeatmapObject(s.publication-1700+120,yValue+10,s.percent);	
					object.add(obj);
					
					
				}
	
				String json = createJson(object);
				displayDataTimeCountry(json);
			

			}
		});
	}
	
	
	public static String buildQueryTimeCountry(){

		String ngram="";
		String genre="";
		String key="";
		String query = "";
		int x=0;
		int y=0;
		int z=0;

		InputElement ngraminput = (InputElement)(Element)DOM.getElementById("ngramTimeCountry");

		if(ngraminput.getValue().length() > 0) {
			System.out.println("ngram true");
			x=1;
			ngram= ngraminput.getValue();			
		} 
		
		if(!(genreTimeCountry.getText().isEmpty()))
		{
			y=1;
			genre= genreTimeCountry.getText();			
		}
		if(!(keyTimeCountry.getText().isEmpty()))
		{
			z=1;
			key= keyTimeCountry.getText();			
		}

		if((x==0) &&  (y==0) && (z==0)){
			query="SELECT publication AS value1, country AS value2, count(country) AS value3 FROM workspace.composer AS composers JOIN (SELECT publication,personId FROM workspace.work) AS works ON composers.personId=works.personId GROUP BY value1, value2"; 

		}
		if((x==1) &&  (y==0) && (z==0)){
			query= "SELECT table3.publication AS value1, table4.country AS value2,count(table4.country) AS value3 FROM(SELECT table2.personId AS persona,table2.publication AS publication FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+= ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication FROM workspace.work) AS table2 ON table1.worka = table2.workb) AS table3 JOIN (SELECT personId AS personb, country AS country FROM workspace.composer) AS table4 ON table3.persona = table4.personb GROUP BY value1, value2";
		}
		if((x==0) &&  (y==1) && (z==0)){
			query= "SELECT table1.publication AS value1, table2.country AS value2, count(table2.country) AS value3 FROM(SELECT personId AS personId,publication AS publication,genre AS genre FROM workspace.work WHERE genre='";
			query+=genre;
			query+="') AS table1 JOIN(SELECT personId AS personId, country AS country FROM workspace.composer) AS table2 ON table1.personId = table2.personId GROUP BY value1, value2";
		}
		if((x==0) &&  (y==0) && (z==1)){
			query= "SELECT table1.publication AS value1, table2.country AS value2, count(table2.country) AS value3 FROM(SELECT personId AS personId,publication AS publication,key AS key FROM workspace.work WHERE key='";
			query+=key;
			query+="') AS table1 JOIN(SELECT personId AS personId, country AS country FROM workspace.composer) AS table2 ON table1.personId = table2.personId GROUP BY value1, value2";

		}
		if((x==1) &&  (y==1) && (z==0)){
			query= "SELECT table3.publication AS value1, table4.country AS value2,count(table4.country) AS value3 FROM(SELECT table2.personId AS persona,table2.publication AS publication FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre FROM workspace.work) AS table2 ON table1.worka = table2.workb WHERE table2.genre='";
			query+=genre;
			query+="') AS table3 JOIN (SELECT personId AS personb, country AS country FROM workspace.composer) AS table4 ON table3.persona = table4.personb GROUP BY value1, value2";
		}
		if((x==0) &&  (y==1) && (z==1)){
			query= "SELECT table1.publication AS value1, table2.country AS value2, count(table2.country) AS value3 FROM(SELECT personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work WHERE genre='";
			query+=genre;
			query+="' AND key='";
			query+=key;
			query+="') AS table1 JOIN(SELECT personId AS personId, country AS country FROM workspace.composer) AS table2 ON table1.personId = table2.personId GROUP BY value1, value2";
		}
		if((x==1) &&  (y==0) && (z==1)){
			query= "SELECT table3.publication AS value1, table4.country AS value2,count(table4.country) AS value3 FROM(SELECT table2.personId AS persona,table2.publication AS publication FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,key AS key FROM workspace.work) AS table2 ON table1.worka = table2.workb WHERE table2.key='";
			query+=key;
			query+="') AS table3 JOIN (SELECT personId AS personb, country AS country FROM workspace.composer) AS table4 ON table3.persona = table4.personb GROUP BY value1, value2";
		}
		if((x==1) &&  (y==1) && (z==1)){
			query= "SELECT table3.publication AS value1, table4.country AS value2,count(table4.country) AS value3 FROM(SELECT table2.personId AS persona,table2.publication AS publication FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work) AS table2 ON table1.worka = table2.workb WHERE table2.genre='";
			query+=genre;
			query+="' AND table2.key='";
			query+=key;
			query+="') AS table3 JOIN (SELECT personId AS personb, country AS country FROM workspace.composer) AS table4 ON table3.persona = table4.personb GROUP BY value1, value2";

		}

		System.out.println(query);

		return query;
	}


	public static native void displayDataTimeCountry(String data) /*-{


			var obj = eval('('+data+')');


			// call the heatmap's store's setDataSet method in order to set static data
			$wnd.xy.store.setDataSet(obj);

	}-*/;

	public static void displayMessage(){
		d.show();

	}
	
	public static void closeMessage(){
		d.setVisible(false);
		
	}


}
