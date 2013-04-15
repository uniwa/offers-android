package com.knkcloud.andoid.teiathcoupons.ui;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.knkcloud.andoid.teiathcoupons.R;
import com.knkcloud.andoid.teiathcoupons.customize.MyCouponsImageAndTextAdapter4;
import com.knkcloud.andoid.teiathcoupons.utils.Internet;


/** The Activity showing users vote for each coupon
*
* @author Karpouzis Koutsourakis Ntinopoulos
* 
* 
*/
public class MyVotesActivity extends Activity{
	
	 JSONObject jobj;
	 TextView title;
	 ListView  lv;
	 
     ArrayList<JSONObject>Offer = new ArrayList<JSONObject>();
     ArrayList<JSONObject>Vote = new ArrayList<JSONObject>();
	 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.myvotedcoupons);
		
		  lv =(ListView)findViewById(android.R.id.list);

		   lv.setScrollingCacheEnabled(false);
		
		title=(TextView)findViewById(R.id.textView1);

		
		new mythread().execute();
	}
	

	
	
	private class mythread extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog pd ;

		@Override
		protected void onPreExecute() {
			 pd =ProgressDialog.show(MyVotesActivity.this, getResources().getString(R.string.plzwait), "",true,true);
			jobj = new JSONObject();	
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			testingfunction();
			return null;
		}
		
		
		@Override
		protected void onPostExecute(Void result) {
			
			pd.dismiss();
			
			manipulatejsonobject();
		}
		
	}
	
	
	
	public void manipulatejsonobject()
	{		

		try {
			if(jobj.toString().contains("status_code"))
			{
				String statuscode="";	
				statuscode=jobj.getString("status_code");
				
				if(statuscode.equals("200"))
				{
					JSONArray thearray = jobj.getJSONArray("votes");
								
					for(int x=0; x<thearray.length(); x++)
					{
						
						Vote.add(thearray.getJSONObject(x));
						Offer.add(thearray.getJSONObject(x).getJSONObject("offer"));
									
//												
					}

				
		lv.setAdapter(new MyCouponsImageAndTextAdapter4(getApplicationContext(), R.layout.custom_listview_myvotes, Offer , Vote));
		lv.setScrollingCacheEnabled(false);	
		lv.setOnItemClickListener(new ListViewItemClickListener());
				}
				else
				{
				Toast.makeText(getApplicationContext(), "Υπήρξε σφάλμα του διακομιστή", Toast.LENGTH_LONG).show();
				}

			}
			else
			{
				Toast.makeText(getApplicationContext(), "Υπήρξε σφάλμα του διακομιστή", Toast.LENGTH_LONG).show();
			}
			
		} catch (JSONException e) {
	
		}
	}
	

	 private class ListViewItemClickListener implements android.widget.AdapterView.OnItemClickListener {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				MyVotesCustomDialog dialog = new MyVotesCustomDialog(MyVotesActivity.this, Offer, Vote, position);
				dialog.show();
				
				dialog.setOnDismissListener(new OnDismissListener() {
					
					@Override
					public void onDismiss(DialogInterface dialog) {
						
						Offer.clear();
						Vote.clear();
						
						new mythread().execute();
			
					}
				});
			}}

	
	
	 public void testingfunction () {
		 String url  = "https://offers.teiath.gr/api/votes";		 		
			 HttpEntity entity=null;
			 HttpResponse response = null ;
			 HttpGet get = new HttpGet(url);
			 get.setHeader("Accept", "application/json");
			
		   try {
				response = Internet.client.execute(get);
				
			} catch (ClientProtocolException e) {

			} catch (IOException e) {
				
			}
		   
		    entity = response.getEntity();	
		 		if(!(entity.equals(null))){
				InputStream is = null;
				try {
					is = entity.getContent();
					
				} catch (IllegalStateException e) {
					
				} catch (IOException e) {				
				}
			 jobj= convertStreamToJSONObject(is);	
		 		}
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
	    	  String resp = sb.toString();
	    	  resp=StringEscapeUtils.unescapeJava(resp);
	    	  JOBJ = new JSONObject(StringEscapeUtils.unescapeJava(resp));
	    	 Log.e("voted", resp);
	      }catch(JSONException e){}
	  	  return JOBJ;
	    }
	
}
