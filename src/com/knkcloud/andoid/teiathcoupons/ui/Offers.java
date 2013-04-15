package com.knkcloud.andoid.teiathcoupons.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.knkcloud.andoid.teiathcoupons.R;
import com.knkcloud.andoid.teiathcoupons.customize.OffersImageAndTextAdapter;
import com.knkcloud.andoid.teiathcoupons.customize.OffersImageAndTextAdapter2;
import com.knkcloud.andoid.teiathcoupons.utils.Internet;



/**
*Activity for All Offers
*
* @author Karpouzis Koutsourakis Ntinopoulos
* 
*
* 
*/

public class Offers extends Activity {
	
/*Ta monodiastata arraylist pou exoun mesa ta json object apo kathe selida*/ 
private	ArrayList<JSONObject>jsonobjectsComp;
private	ArrayList<JSONObject>jsonobjectsOff;	

private static String  offersurl="https://offers.teiath.gr/offers/index/page:";
private static String  couponsurl="https://offers.teiath.gr/offers/coupons/index/page:";
private static String  happyurl="https://offers.teiath.gr/offers/happyhour/index/page:";
private static String  limitedurl="https://offers.teiath.gr/offers/limited/index/page:";
private String WhichUrl= offersurl;

private int CurrentPage =1;
private int pageCount=0;

ListView  lv;

	
@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.offers);
	
			
       lv =(ListView)findViewById(android.R.id.list);
       //vazoume arxiko adapter to intro
       lv.setAdapter(new OffersImageAndTextAdapter2(getApplicationContext(), R.layout.initial_customadapter_view));
	   lv.setScrollingCacheEnabled(false);
		
	   
	   /*
	   kathe fora pou patiete ena apo ta epomena koubia allazei san parametros to url string kai ginode initialized
	   ta variables twn selidwn dialdi 1 gia tin prwti kai 0 gia ton counter twn selidwn
	   */
	   // koubi olwn ton prosforwn
		ImageButton all = (ImageButton)findViewById(R.id.all);
		all.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				WhichUrl=offersurl;
				//sto klick kane initialize ta pages kai fere tis prosfores
				CurrentPage =1;
				pageCount=0;
				new GetOffersThread().execute(WhichUrl); 
			}
		});
		
		
		// koubi typou coupons
		ImageButton coupons = (ImageButton)findViewById(R.id.coupons);
		//coupons.setBackgroundColor(Color.rgb(255, 165, 0));
		coupons.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				WhichUrl=couponsurl;
				//sto klick kane initialize ta pages kai fere tis prosfores
				CurrentPage =1;
				pageCount=0;
				new GetOffersThread().execute(WhichUrl); 	
			}
		});
		
		
		
		
		ImageButton limited = (ImageButton)findViewById(R.id.limited);
		//limited.setBackgroundColor(Color.rgb(50, 205, 50));
		limited.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				WhichUrl=limitedurl;
				//sto klick kane initialize ta pages kai fere tis prosfores
				CurrentPage =1;
				pageCount=0;
				new GetOffersThread().execute(WhichUrl); 	
			}
		});
		
		
		
		
		ImageButton happyhour = (ImageButton)findViewById(R.id.happyhour);
		//happyhour.setBackgroundColor(Color.rgb(30, 144, 255));
		happyhour.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				WhichUrl=happyurl;
				//sto klick kane initialize ta pages kai fere tis prosfores
				CurrentPage =1;
				pageCount=0;
				new GetOffersThread().execute(WhichUrl); 	
			}
		});
		
		
		
/*	elegxei ean i current page einai mikroteri apo ton arithmo sellidwn kai oso to patas auksanei paei diladi
	stin epomeni selida kai ektelei to url*/
		
		//koubi gia epomeni selida apo kathe url
		ImageButton next = (ImageButton)findViewById(R.id.imgbtnnext);
		next.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				if(CurrentPage<pageCount)
				{
					CurrentPage++;	
					new GetOffersThread().execute(WhichUrl); 
				}
				else
				{
				Toast.makeText(getApplicationContext(), "Δεν υπάρχει επόμενη σελίδα", Toast.LENGTH_LONG).show();	
				}
	
			}
		});
		
		
		//koubi gia proigoumeni selida apo kathe url
		ImageButton prev = (ImageButton)findViewById(R.id.imgbtnback);
		prev.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				if(CurrentPage>1)
				{
					CurrentPage--;	
					new GetOffersThread().execute(WhichUrl); 
				}
				else
				{
				Toast.makeText(getApplicationContext(), "Δεν υπάρχει επόμενη σελίδα", Toast.LENGTH_LONG).show();	
				
				}
		
			}
		});
		
	
	}
	
/*
ksekinaei xtupwdas ena url me page 1 kai sto idio response pairnei ton arithmo tis idias tis selidas diladi pali 1 kai twn arithmo
olwn twn selidwn
*
*
*/

//the massive json object to be returned 	
	public void couponoffers(String url) 
	{	
try{
	jsonobjectsComp= new ArrayList<JSONObject>();
	jsonobjectsOff= new ArrayList<JSONObject>();
			JSONObject jobj = new JSONObject();
			JSONObject pagination = new JSONObject();
			HttpEntity entity=null;
			 DefaultHttpClient httpclient = new DefaultHttpClient();
			 HttpResponse response = null;			 
			 url  = url+String.valueOf(CurrentPage);
			 HttpGet get = new HttpGet(url);
			 get.setHeader("Accept", "application/json");
	       try {	response = httpclient.execute(get);		} 
	       	catch (ClientProtocolException e) { } 
	       		catch (IOException e) {	}	   
	        entity = response.getEntity();	
	     		if(!(entity.equals(null)))  {
				InputStream is = null;
				try {
					is = entity.getContent();
					} 
					catch (IllegalStateException e){  }
						catch (IOException e) {		}
				//convert tthe stream from response to jsonobject
			 jobj= convertStreamToJSONObject(is);
			 pagination=jobj.getJSONObject("pagination");
			 CurrentPage=Integer.parseInt(pagination.getString("page"));
			 pageCount=Integer.parseInt(pagination.getString("pageCount"));
		/*Edw bainoun ta jsonobject apo kathe page se ena monodiastato arraylist */
			 parsejsonarrays(jobj, "companies", jsonobjectsComp);
			 parsejsonarrays(jobj, "offers", jsonobjectsOff);		
	       }		  		
	}
catch(Exception e){ }    		
	}
		
	
	
	
	//convert an InputStream to JSON object
    public JSONObject convertStreamToJSONObject(InputStream is) {
      JSONObject JOBJ = null;
  	  BufferedReader reader = new BufferedReader(new InputStreamReader(is));
  	  StringBuilder sb = new StringBuilder();
  	  String line = null;
  	  try {
  		  while ((line = reader.readLine()) != null) {
  			  sb.append(line + "\n");
  		  }
  	  }catch(IOException e) {
  	  }finally{
  		  try {
  			  is.close();
  		  }catch(IOException e){
  		  }
  	  }
  	  //try parse the string to a JSON object
      try{
    	  JOBJ = new JSONObject(sb.toString());
      }catch(JSONException e){}
  	  return JOBJ;
    }
    
    
    
    
    
   //vale kathe json object pou vriskete se ena json array se ena arraylist 
    public void parsejsonarrays (JSONObject jb,String jsonarrayname,ArrayList<JSONObject>jsonobjarraylist )
  {   	
    JSONArray thejsonarray;
    try {
    		thejsonarray = jb.getJSONArray(jsonarrayname);
    		for(int i=0; i<thejsonarray.length(); i++)
    			{
    			jsonobjarraylist.add(thejsonarray.getJSONObject(i));
    			}		
    	} catch (JSONException e) { }
  }

    
    
    
    
    
 private class GetOffersThread extends AsyncTask<String, Void, Void>
 {
	 ProgressDialog pd;

	 @Override
	protected void onPreExecute() {
		super.onPreExecute();
pd =ProgressDialog.show(Offers.this, "Παρακαλώ περιμένετε","Πραγματοποιείται φόρτωση προσφορών..", true, true);
	}
	  
		@Override
		protected Void doInBackground(String... params) {
			couponoffers(params[0]);
			return null;
		}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);	
		//osa jsonobject irthan diladi oses prosfores ftiakse toses eikones + 1
		 ArrayList<Drawable>mydr= new ArrayList<Drawable>();
		   for(int x=0; x<jsonobjectsOff.size()+1; x++)
		   {
			   mydr.add(getResources().getDrawable(R.drawable.logo1));  
		   }   
		   lv.setAdapter(new OffersImageAndTextAdapter(getApplicationContext(), R.layout.custom_listview_offers, jsonobjectsOff, mydr));
		   lv.setScrollingCacheEnabled(false);	
		   pd.dismiss();
		   lv.setOnItemClickListener(new ListViewItemClickListener());
			   
		   
	}
}
    
 
 
 
 private class ListViewItemClickListener implements android.widget.AdapterView.OnItemClickListener {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
	new OffersCustomDialog(Offers.this, jsonobjectsOff, jsonobjectsComp,position).show();
	
	}}

 

 
 
    			
}
