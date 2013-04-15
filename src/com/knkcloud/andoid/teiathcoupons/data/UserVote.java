package com.knkcloud.andoid.teiathcoupons.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.knkcloud.andoid.teiathcoupons.utils.Internet;

/**
 *  Gets the Vote for an Offer from server response
 * @author Karpouzis Koutsourakis Ntinopoulos
 *
 */
public class UserVote {
	
	
	
	 /**
	  *  *  Gets the Vote for each Offer from server response
	 * @author Karpouzis Koutsourakis Ntinopoulos
	 *
	 * @param Context
	 * @param url
	 * @param offerId
	 */
	public static void votingfunction (Context current,String url,String offerId ) {
		 
		 String finalurl =url+offerId;
				 
		 
		 Log.e("sk", finalurl);
		 	JSONObject job = new JSONObject();
			 HttpEntity entity=null;
			 HttpResponse response = null ;
			 HttpGet get = new HttpGet(finalurl);
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
	
			job=convertStreamToJSONObject(is);
		 		}
		 	
		   try {
			String status = job.getString("status_code");
			
			if(status.equals("200"))
			{
				String offernum = job.getJSONObject("vote").getString("offer_id");
				String statusmsg = job.getJSONObject("vote").getString("vote_type");
				
				if(statusmsg.equals("0"))
				{
					Toast.makeText(current, "H προσφορά " + offernum+  " βαθμολογήθηκε αρνητικά", Toast.LENGTH_LONG).show();
				}
				else if(statusmsg.equals("1"))
				{
					Toast.makeText(current, "H προσφορά " + offernum+  " βαθμολογήθηκε θετικά", Toast.LENGTH_LONG).show();
					
				}
				else
				{
					
					Toast.makeText(current, "H βαθμολήγηση της προσφοράς " + offernum+  " διαγράφηκε", Toast.LENGTH_LONG).show();
				}
					
				
			}
			else if(status.equals("403"))
			{
				Toast.makeText(current, "H Συγκεκριμένη ενέργεια δεν είναι εφικτή", Toast.LENGTH_LONG).show();
				
			}
			else
			{
				
				
				Toast.makeText(current, "Υπήρξε κάποιο σφάλμα, παρακαλώ ξαναπροσπαθήστε", Toast.LENGTH_LONG).show();
			}
			
			
		} catch (JSONException e) {
	
		}
		  
		}	


		
	 
	    /**convert an InputStream to JSON object
	     * @param InputStream
	     * @return JsonObject
	     */
	    public static JSONObject convertStreamToJSONObject(InputStream is) {
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
	      try{
	    	  String resp = sb.toString();
	    	  resp=StringEscapeUtils.unescapeJava(resp);
	    	  JOBJ = new JSONObject(StringEscapeUtils.unescapeJava(resp));
	    	 Log.e("test", resp);
	      }catch(JSONException e){}
	  	  return JOBJ;
	    }

}
