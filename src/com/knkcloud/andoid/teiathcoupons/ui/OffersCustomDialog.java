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

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.knkcloud.andoid.teiathcoupons.R;
import com.knkcloud.andoid.teiathcoupons.data.UserVote;
import com.knkcloud.andoid.teiathcoupons.utils.Internet;



/** Custom Dialog For Offers tha manipulates an offer 
 * e.x. Grab a coupon / Release a Coupon etc.
 * 
 * @author Karpouzis Koutsourakis Ntinopoulos
 * 
 *
 */
public class OffersCustomDialog extends Dialog  {
	
	ArrayList<JSONObject>companiez= new ArrayList<JSONObject>();
	ArrayList<JSONObject>offerz= new ArrayList<JSONObject>();
	int offerpoz;
    Context cont;
    String CompanyIDTobeSentToCompaniesMapACtivity;
    String couponid;    
    Button btn1;
    		
	public OffersCustomDialog(Context context, ArrayList<JSONObject> offers,ArrayList<JSONObject>companies, int offerposition) {
		super(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
		this.cont=context;
		this.companiez=companies;
		this.offerz=offers;
		this.offerpoz=offerposition;
	}
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.click_on_offer_layout);
		JSONObject jb = new JSONObject();
		jb=offerz.get(offerpoz);
		TextView offername =(TextView)findViewById(R.id.offername);
		TextView offertype =(TextView)findViewById(R.id.textView1);
		TextView textView2 =(TextView)findViewById(R.id.textView2);
		TextView textView13 =(TextView)findViewById(R.id.textView13);
		TextView textView14 =(TextView)findViewById(R.id.textView14);
		TextView textView10 =(TextView)findViewById(R.id.textView10);
		TextView textView12 =(TextView)findViewById(R.id.textView12);
		TextView textView15 =(TextView)findViewById(R.id.textView15);
		TextView textView17 =(TextView)findViewById(R.id.textView17);
		TextView textView16 =(TextView)findViewById(R.id.textView16);
		TextView textView18 =(TextView)findViewById(R.id.textView18);
		TextView textView19 =(TextView)findViewById(R.id.textView19);
		TextView textView20 =(TextView)findViewById(R.id.textView20);
		TextView textView8=(TextView)findViewById(R.id.textView8);
		TextView textView9=(TextView)findViewById(R.id.textView9);
		TextView textView7=(TextView)findViewById(R.id.textView7);
		TextView TextView01=(TextView)findViewById(R.id.TextView01);
		TextView TextView02=(TextView)findViewById(R.id.TextView02);
	
		 btn1= (Button)findViewById(R.id.button1);
		 btn1.setOnClickListener(new grabcoupon());
		 
		 ImageButton imgbtnup =(ImageButton)findViewById(R.id.imgbtnup);
			imgbtnup.setOnClickListener(new clickup());
			ImageButton imgbtndown =(ImageButton)findViewById(R.id.imgbtndown);
			imgbtndown.setOnClickListener(new clickdown());
			ImageButton imgbtncancel =(ImageButton)findViewById(R.id.imgbtncancel);
			imgbtncancel.setOnClickListener(new clickcancel());
		
		
		
		
		try {
			offername.setText(jb.getString("title"));
			
			offertype.setText(jb.getString("offer_type"));
			if(jb.getString("offer_type").equals("happy hour"))
			{
				offertype.setTextColor(Color.rgb(30, 144, 255));
			}
			if(jb.getString("offer_type").equals("coupons"))
			{
				offertype.setTextColor(Color.rgb(255, 165, 0));	
				btn1.setVisibility(View.VISIBLE);
				
			}
			if(jb.getString("offer_type").equals("limited"))
			{
				offertype.setTextColor(Color.rgb(50, 205, 50));
			}
			//to id tis etairias pu tha perasei sto map activity
			CompanyIDTobeSentToCompaniesMapACtivity=jb.getString("company_id");
			
			//coupon id
			couponid=jb.getString("id");
			
			//pare apo to jsonobject offer to company id , vres to mesa sto jsonobject twn eteriwn kai epestrepse to onoma tis eterias
			textView2.setText(companiez.get(GetCompanyIndexFromArrayList(jb.getString("company_id"))).getString("name"));
			textView2.setTextColor(Color.BLUE);
			textView13.setText(jb.getString("description"));
			textView14.setText(jb.getString("offer_category"));
			textView12.setTextColor(Color.GREEN);
			textView12.setText("+"+jb.getString("vote_plus"));
			textView10.setTextColor(Color.RED);
			textView10.setText("-"+jb.getString("vote_minus"));
			textView15.setText("("+jb.getString("vote_count")+")");
			
			textView16.setTextColor(Color.BLUE);
			textView16.setText(jb.getString("tags"));
			
			//megistos arithmos ana spoudasti
			textView19.setText(jb.getString("coupon_count"));
			textView20.setText("από"+jb.getString("total_quantity"));
			if(jb.getString("total_quantity").equals("null"))
			{
				textView19.setVisibility(View.GONE);
				textView20.setVisibility(View.GONE);
				textView7.setVisibility(View.GONE);
			}
			//desmeumena kouponia
			textView18.setText(jb.getString("max_per_student"));
			if(jb.getString("max_per_student").equals("null"))
			{	textView8.setVisibility(View.GONE);
				textView18.setVisibility(View.GONE);
			}
			//oroi kouponiou
			textView17.setText(jb.getString("coupon_terms"));
			if(jb.getString("coupon_terms").equals("null"))
			{
				textView17.setVisibility(View.GONE);
				textView9.setVisibility(View.GONE);
			}
			//liksi prosforas
			TextView01.setText(jb.getString("autoend"));
			if(jb.getString("autoend").equals("null"))
			{
				TextView01.setVisibility(View.GONE);
				TextView02.setVisibility(View.GONE);
			}
		} catch (JSONException e) {
		}
	
		textView2.setOnClickListener(new SubmitClicklistener());
		
		
	}
	
	
	
private int GetCompanyIndexFromArrayList(String companyid)	{	
	int poz = 0;
	for(int x=0; x<companiez.size(); x++)
	{
	try {
		if(companyid.equals(companiez.get(x).getString("id")))
				{
			poz=x;
			break;
				}
	} catch (JSONException e) {
	}		
	}
	return poz;
}
	


private class SubmitClicklistener implements android.view.View.OnClickListener {

	@Override
	public void onClick(View v) {
		//convert ArrayList of JsonObjects To ArrayList of STrings
		ArrayList<String>Comp=new ArrayList<String>();
		ArrayList<String>Off=new ArrayList<String>();
		for(int x=0; x<offerz.size(); x++)
		{
			Off.add(offerz.get(x).toString());
		}
		for(int x=0; x<companiez.size(); x++)
		{
			Comp.add(companiez.get(x).toString());
		}
			
Intent myintent = new Intent(cont, OffersCompanies.class);
myintent.putStringArrayListExtra("companies", Comp);
myintent.putStringArrayListExtra("offers",Off);
myintent.putExtra("compid", CompanyIDTobeSentToCompaniesMapACtivity);
cont.startActivity(myintent);

	}}	





private class grabcoupon implements android.view.View.OnClickListener {

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

			e.printStackTrace();
		}
	}

}

public JSONObject testingfunction (String couponid) {
	 String url  = "https://offers.teiath.gr/api/coupon/"+couponid;		 
		
		 HttpEntity entity=null;
		 HttpResponse response = null ;
		 HttpPost post = new HttpPost(url);
		 post.setHeader("Accept", "application/json");
		 JSONObject json= new JSONObject();
		
	   try {
			response = Internet.client.execute(post);
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

private class clickup implements android.view.View.OnClickListener {

	@Override
	public void onClick(View v) {
	
		 String url  = "https://offers.teiath.gr/api/vote/vote_up/";
		 UserVote.votingfunction(cont,url, couponid);

	}}	

private class clickdown implements android.view.View.OnClickListener {

	@Override
	public void onClick(View v) {
	
		String url  = "https://offers.teiath.gr/api/vote/vote_down/";
		UserVote.votingfunction(cont,url, couponid);
	}}	

private class clickcancel implements android.view.View.OnClickListener {

	@Override
	public void onClick(View v) {
	
		String url  = "https://offers.teiath.gr/api/vote/vote_cancel/";
		UserVote.votingfunction(cont,url, couponid);
	}}	

}
