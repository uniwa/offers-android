package com.knkcloud.andoid.teiathcoupons.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
import com.knkcloud.andoid.teiathcoupons.customize.MyCouponsImageAndTextAdapter3;
import com.knkcloud.andoid.teiathcoupons.utils.Internet;




/** The Activity showing the available coupons
 *
 * @author Karpouzis Koutsourakis Ntinopoulos
 * 
 * 
 */

public class MyCouponsActivity extends Activity {

	 JSONObject jobj;
	 TextView title;
	 ListView  lv;
	 
     ArrayList<JSONObject>Coupons = new ArrayList<JSONObject>();
     ArrayList<JSONObject>Offers = new ArrayList<JSONObject>();
	 
     
// to sugekrimeno url twn kouponiwn
//epistrefei ena jsonarray pou exei mesa se kathe thesi px thesi 0 , 1 , 2 klp
// 2 json objects to json object coupon kai json object offer
// gi auto dimiourgoume ena arraylist apo json object gia ta coupon kai
// ena gia to jsonobject offer

     
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.mycoupons);
		
	       lv =(ListView)findViewById(android.R.id.list);
	       //vazoume arxiko adapter to intro
		   lv.setScrollingCacheEnabled(false);
		
		title=(TextView)findViewById(R.id.textView1);
		new mythread().execute();
	}
	
	
	private class mythread extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog pd ;

		@Override
		protected void onPreExecute() {
			 pd =ProgressDialog.show(MyCouponsActivity.this, getResources().getString(R.string.plzwait), "",true,true);
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
					JSONArray thearray = jobj.getJSONArray("coupons");
					
					//Log.e("1", thearray.toString());
					
					for(int x=0; x<thearray.length(); x++)
					{
						String reinserted = thearray.getJSONObject(x).getJSONObject("coupon").getString("reinserted");
						if(reinserted.equals("0"))
						{
							Coupons.add(thearray.getJSONObject(x).getJSONObject("coupon"));
							Offers.add(thearray.getJSONObject(x).getJSONObject("offer"));
						}
												
					}
					//osa jsonobject irthan diladi oses prosfores ftiakse toses eikones + 1
					/* ArrayList<Drawable>mydr= new ArrayList<Drawable>();
					   for(int x=0; x<Offers.size()+1; x++)
					   {
						   mydr.add(getResources().getDrawable(R.drawable.logo1));  
					   }   */
				
					 lv.setAdapter(new MyCouponsImageAndTextAdapter3(getApplicationContext(), R.layout.custom_listview_mycoupons, Offers , Coupons));
					   lv.setScrollingCacheEnabled(false);	
					   lv.setOnItemClickListener(new ListViewItemClickListener());
					   
					   //
				//	Log.e("st", jobj.getString(""));
////					Log.e("jsonarray", thearray.toString());
//////					Log.e("jsonarraysize",String.valueOf(thearray.length()));
//					Log.e("size 1 ", String.valueOf(Coupons.size()));
//					Log.e("size 1 ",String.valueOf(Offers.size()));
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
				
//				//Toast.makeText(getApplicationContext(), "Υπήρξε σφάλμα του διακομιστή", Toast.LENGTH_LONG).show();
//				
//			new MyCouponsOfferCustomDialog(MyCouponsActivity.this, Offers, Coupons, position).show();
//			
//			}}
	 
				MyCouponsOfferCustomDialog dialog = new MyCouponsOfferCustomDialog(MyCouponsActivity.this,Offers, Coupons, position);
				dialog.show();
		
		dialog.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				
				Offers.clear();
				Coupons.clear();
				
				new mythread().execute();
	
			}
		});
	}}
	
	
	
	
	
	
	 public void testingfunction () {
		 String url  = "https://offers.teiath.gr/api/coupons";		 
			
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
	  	Log.d("karp", "serverResponce: "+sb.toString());
	  	  //try parse the string to a JSON object
	      try{
	    	  JOBJ = new JSONObject(sb.toString());
	    	 // Log.e("karp", sb.toString());
	      }catch(JSONException e){}
	  	  return JOBJ;
	    }
}
