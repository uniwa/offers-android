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
import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;


import com.knkcloud.andoid.teiathcoupons.R;
import com.knkcloud.andoid.teiathcoupons.ui.OffersCustomDialog.sendtoserver;
import com.knkcloud.andoid.teiathcoupons.utils.Internet;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.util.EventLogTags.Description;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
*Custom Dialog that shows information about each coupon
*
* @author Karpouzis Koutsourakis Ntinopoulos
* 
*
* 
*/

public class MyCouponsOfferCustomDialog extends Dialog  {
	
	ArrayList<JSONObject>Coupons= new ArrayList<JSONObject>();
	ArrayList<JSONObject>Offers= new ArrayList<JSONObject>();
	int offerpoz;
    Context cont;
    String CompanyIDTobeSentToCompaniesMapACtivity;
    Button button1;
    String couponid;
	
    
	public MyCouponsOfferCustomDialog(Context context, ArrayList<JSONObject> offers,ArrayList<JSONObject>coupons, int offerposition) {
		super(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
		this.cont=context;
		this.Coupons=coupons;
		this.Offers=offers;
		this.offerpoz=offerposition;
	}
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.click_on_mycoupons_layout);
		JSONObject jb = new JSONObject();
		JSONObject jbcoup = new JSONObject();
		
		jb=Offers.get(offerpoz);
		jbcoup=Coupons.get(offerpoz);
		
		Log.e("Offers", jb.toString());
		Log.e("Coupons", jbcoup.toString());
		
		TextView offername =(TextView)findViewById(R.id.offername);
		//TextView offertype =(TextView)findViewById(R.id.textView1);
		//TextView textView2 =(TextView)findViewById(R.id.textView2);
		TextView textView13 =(TextView)findViewById(R.id.textView13);
//		TextView textView14 =(TextView)findViewById(R.id.textView14);
		TextView textView10 =(TextView)findViewById(R.id.textView10);
		TextView textView12 =(TextView)findViewById(R.id.textView12);
		TextView textView15 =(TextView)findViewById(R.id.textView15);
		TextView textView17 =(TextView)findViewById(R.id.textView17);
		TextView textView9=(TextView)findViewById(R.id.textView9);

		button1=(Button)findViewById(R.id.button1);
		button1.setOnClickListener(new dismisscoupon());
		
		
		
		try {
			offername.setText(jb.getString("title"));
			couponid=jbcoup.getString("id");
			Log.e("coup", couponid);
			textView13.setText(jb.getString("description"));
			//textView14.setText(jb.getString("offer_category"));
			textView12.setTextColor(Color.GREEN);
			textView12.setText("+"+jb.getString("vote_plus"));
			textView10.setTextColor(Color.RED);
			textView10.setText("-"+jb.getString("vote_minus"));
			textView15.setText("("+jb.getString("vote_count")+")");
			
			textView17.setText(jb.getString("coupon_terms"));
			if(jb.getString("coupon_terms").equals("null"))
			{
				textView17.setVisibility(View.GONE);
				textView9.setVisibility(View.GONE);
			}

		} catch (JSONException e) {
		}
	

		
	}
	
	
	private class dismisscoupon implements android.view.View.OnClickListener {

		@Override
		public void onClick(View v) {
		
			new sendtoserver().execute();

		}}	



	public class sendtoserver extends AsyncTask<Void, Void, Void>
	{
	 
		ProgressDialog pd;
		JSONObject jzon;
		
		@Override
		protected void onPreExecute() {
			pd =ProgressDialog.show(cont, "Παρακαλώ περιμένετε","", true, true);
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			jzon=	testingfunction(couponid);
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			pd.dismiss();
			try {
				Toast.makeText(cont, jzon.getString("message"), Toast.LENGTH_LONG).show();
			} catch (NotFoundException e) {
		
			} catch (JSONException e) {

				//e.printStackTrace();
			}
		}

	}

	public JSONObject testingfunction (String couponid) {
		 String url  = "https://offers.teiath.gr/api/coupons/reinsert/"+couponid;		 
		 JSONObject json= new JSONObject();
		 
		 
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
				json= convertStreamToJSONObject(is);	

		 		}
		 		return json;
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



}
