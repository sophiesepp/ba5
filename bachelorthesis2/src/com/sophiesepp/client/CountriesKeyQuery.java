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
import com.sophiesepp.shared.CountriesKey;
import com.sophiesepp.shared.HeatmapObject;


public class CountriesKeyQuery extends Heatmap implements EntryPoint {


	static VerticalPanel countrieskeyPanel = new VerticalPanel();
	static HorizontalPanel countrieskeycontentPanel = new HorizontalPanel();
	HorizontalPanel leftcountrieskeyPanel = new HorizontalPanel();
	VerticalPanel rightcountrieskeyPanel = new VerticalPanel();
	
	
	VerticalPanel seperationPanel = new VerticalPanel();
	VerticalPanel countrieskeyPanel1= new VerticalPanel();
	VerticalPanel  countrieskeyPanel2 = new VerticalPanel();
	VerticalPanel countrieskeyLabel1 = new VerticalPanel();
	VerticalPanel countrieskeyLabel2 = new VerticalPanel();
	VerticalPanel countrieskeyLabel3 = new VerticalPanel();
	VerticalPanel countrieskeyLabel4 = new VerticalPanel();
	
	static VerticalPanel countrykeyPanel = new VerticalPanel();
	static HorizontalPanel countrykeycontentPanel = new HorizontalPanel();
	VerticalPanel leftcountrykeyPanel = new VerticalPanel();
	VerticalPanel rightcountrykeyPanel = new VerticalPanel();
	

	
	HorizontalPanel countrykeyPanel1= new HorizontalPanel();
	VerticalPanel  countrykeyPanel2 = new VerticalPanel();	
	VerticalPanel countrykeyLabel1 = new VerticalPanel();
	VerticalPanel countrykeyLabel2 = new VerticalPanel();
	
	final Button showCountryKeyQueryButton = new Button("Run Query");
	
	final static SuggestBox keyscountry = new SuggestBox(Srsr.country);
	final Label countryLabel = new Label("Country");
	
	final Button showCountriesKeyQueryButton = new Button("Run Query");
	
	final Label countrieskeygenreLabel = new Label("Genre");

	
	final static SuggestBox genreCountriesKey = new SuggestBox(Srsr.genre);
	final static TextBox ngramCountriesKey = new TextBox();

	private static int i = 0;
	private static int xPixelValuecountries = 10;
	private static int yPixelValuecountries = 10;
	private static HashMap<String,Integer> countrieskeyX = new HashMap<String,Integer>();
	private static HashMap<String,Integer> countrieskeyY = new HashMap<String,Integer>();

	private static int y = 0;
	private static int yPixelValuecountry = 10;
	private static HashMap<String,Integer> countrykey = new HashMap<String,Integer>();
	
	  private static class MyPopup extends PopupPanel {

		    public MyPopup() {
		      // PopupPanel's constructor takes 'auto-hide' as its boolean parameter.
		      // If this is set, the panel closes itself automatically when the user
		      // clicks outside of it.
		      super(true);

		      HTML contents = new HTML("This section shows the use of keys in different countries. The results are normalized by the sum of published works in a country, so that the use of a key in different countries can be compared and is not influenced by a country that has more published works than another country. The results are the percentages a key is used in a certain country.");
			   
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

			countrieskeyX.put(Srsr.countries[i],xPixelValuecountries);
			xPixelValuecountries+=20;
		}
		for (i=0;i<Srsr.keys.length;i++){

			countrieskeyY.put(Srsr.keys[i],yPixelValuecountries);
			yPixelValuecountries+=20;
		}
	
		
		for (i=0;i<com.sophiesepp.client.Srsr.keys.length;i++){
			
			countrykey.put(com.sophiesepp.client.Srsr.keys[i],yPixelValuecountry);
			yPixelValuecountry+=20;
		}
	
		
		countrieskeyPanel.addStyleName("countrieskeyPanel");
		countrieskeycontentPanel.addStyleName("countrieskeycontentPanel");
		leftcountrieskeyPanel.addStyleName("leftcountrieskeyPanel");
		rightcountrieskeyPanel.addStyleName("rightcountrieskeyPanel");
		rightcountrieskeyPanel.add(b1);
		countrieskeyPanel1.addStyleName("countrieskeyPanel1");
		countrieskeyPanel2.addStyleName("countrieskeyPanel2");
		
		
		countrieskeyLabel1.addStyleName("about");
		countrieskeyLabel2.addStyleName("label");
		countrieskeyLabel2.addStyleName("label");
		countrieskeyLabel4.addStyleName("buttonlabel");
		
		countrieskeygenreLabel.addStyleName("text3");
		
		
		genreCountriesKey.addStyleName("textfield1");
		ngramCountriesKey.addStyleName("textfield1");
		
		showCountriesKeyQueryButton.addStyleName("button1");
		b1.addStyleName("informationbutton");
		seperationPanel.addStyleName("seperationPanel");
		
		
		showCountriesKeyQueryButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				queryCountriesKey();
			}
		});
		
		
	
		


	
		countrieskeyPanel.add(RootPanel.get("heading6"));
		countrieskeyPanel.add(b1);
		countrieskeyPanel.add(countrieskeycontentPanel);
		countrieskeyPanel.add(seperationPanel);
	
		countrieskeycontentPanel.add(leftcountrieskeyPanel);
		countrieskeycontentPanel.add(rightcountrieskeyPanel);
		leftcountrieskeyPanel.add(countrieskeyPanel1);
		leftcountrieskeyPanel.add(countrieskeyPanel2);

		
		
		countrieskeyPanel1.add(RootPanel.get("heatmapCountriesKeyCanvas"));
		
		countrieskeyPanel2.add(countrieskeyLabel1);
		countrieskeyPanel2.add(countrieskeyLabel2);
		countrieskeyPanel2.add(countrieskeyLabel3);
		
	
		countrieskeyLabel2.add(countrieskeygenreLabel);
		countrieskeyLabel2.add(genreCountriesKey);
		countrieskeyLabel3.add(RootPanel.get("KeyboardCountriesKeyContainer"));
		
		countrieskeyLabel4.add(showCountriesKeyQueryButton);


		
	
	
	}


	public static void queryCountriesKey() {

		greetingService.showQueryCountriesKey(buildQueryCountriesKey(),new AsyncCallback<List<CountriesKey>>() {
			public void onFailure(Throwable caught) {

			}

			public void onSuccess(List<CountriesKey> result) {

				int xValue;
				int yValue;

				List<HeatmapObject> object = new ArrayList<HeatmapObject>();		
				
				for(CountriesKey s: result)
				{			
					String country = s.country;
					String key = s.key;
		
					xValue = countrieskeyX.get(country);
					yValue = countrieskeyY.get(key);
					
					HeatmapObject obj = new HeatmapObject(xValue+30,yValue,(s.percent));	
				
					object.add(obj);
					
				}
	
				String json = createJson(object);
				displayDataCountriesKey(json);

			}
		});
	}
	

	
	public static String buildQueryCountriesKey(){


		String query="";
		String genre = "";
		String ngram = "";
		int x = 0;
		int y = 0;

		if(!(genreCountriesKey.getText().isEmpty()))
		{
			x=1;
			genre= genreCountriesKey.getText();			
		}
		InputElement ngraminput = (InputElement)(Element)DOM.getElementById("ngramCountriesKey");

		if(ngraminput.getValue().length() > 0) {
			System.out.println("ngram true");
			x=1;
			ngram= ngraminput.getValue();			
		} 
		

		if((x==0) &&  (y==0) ){
			query="SELECT table1.country AS value1, table2.key AS value2,count(table2.key) AS value3 FROM workspace.composer AS table1 JOIN(SELECT key AS key, personId AS personId FROM workspace.work) AS table2 ON table1.personId=table2.personId GROUP BY value1, value2";

		}
		if((x==1) &&  (y==0) ){
			query="SELECT table1.country  AS value1, table2.key AS value2,count(table2.key) AS value3 FROM workspace.composer AS table1 JOIN(SELECT key AS key, genre AS genre, personId AS personId FROM workspace.work WHERE genre='";
			query+=genre;
			query+="') AS table2 ON table1.personId=table2.personId GROUP BY value1, value2";

		}
		if((x==0) &&  (y==1) ){
			query= "SELECT table4.country  AS value1, table3.key AS value2, count(table3.key) AS value3 FROM(SELECT table2.personId AS persona,table2.publication AS publication, key AS key FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication, key AS key FROM workspace.work) AS table2 ON table1.worka = table2.workb) AS table3 JOIN (SELECT personId AS personb, birth AS birth,country AS country FROM workspace.composer) AS table4 ON table3.persona = table4.personb GROUP BY value1, value2";
		}
		if((x==1) &&  (y==1) ){
			query= "SELECT table4.country  AS value1, table3.key AS value2, count(table3.key) AS value3 FROM(SELECT table2.personId AS persona,table2.publication AS publication, key AS key FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication, key AS key, genre AS genre FROM workspace.work) AS table2 ON table1.worka = table2.workb WHERE table2.genre='";
			query+=genre;
			query+="') AS table3 JOIN (SELECT personId AS personb, birth AS birth, country AS country FROM workspace.composer) AS table4 ON table3.persona = table4.personb GROUP BY value1, value2";
		}
		
		System.out.println(query);

		return query;
	}



	
	
	public static native void displayDataCountriesKey(String data) /*-{

	var obj = eval('('+data+')');


	// call the heatmap's store's setDataSet method in order to set static data

		$wnd.bb.store.setDataSet(obj);
}-*/;

	public static void displayMessage(){
		d.show();

	}
	
	public static void closeMessage(){
		d.setVisible(false);
		
	}


}