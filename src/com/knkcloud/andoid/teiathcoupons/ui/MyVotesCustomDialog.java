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
import com.knkcloud.andoid.teiathcoupons.data.UserVote;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;



/**
*Custom Dialog that shows information about each vote
*
* @author Karpouzis Koutsourakis Ntinopoulos
* 
*
* 
*/


public class MyVotesCustomDialog extends Dialog  {
	
	ArrayList<JSONObject>Offer= new ArrayList<JSONObject>();
	ArrayList<JSONObject>Vote= new ArrayList<JSONObject>();
	int offerpoz;
    Context cont;
    String offerid;
    
    
	public MyVotesCustomDialog(Context context, ArrayList<JSONObject> offer ,ArrayList<JSONObject>vote, int offerposition) {
		super(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
		this.cont=context;
		this.Offer=offer;
		this.Vote=vote;
		this.offerpoz=offerposition;
	}
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.click_on_myvote_layout);
		
		JSONObject jb = new JSONObject();
		JSONObject jboffer = new JSONObject();
		
		jb=Vote.get(offerpoz);
		jboffer=Offer.get(offerpoz);
		
	
		
		Log.e("Vote", jb.toString());
		Log.e("Offer", jboffer.toString());
		
		TextView textView2 =(TextView)findViewById(R.id.textView2);
		TextView textView10 =(TextView)findViewById(R.id.textView10);
		TextView textView12 =(TextView)findViewById(R.id.textView12);
		TextView textView15 =(TextView)findViewById(R.id.textView15);
		TextView textView3=(TextView)findViewById(R.id.textView3);
		
		ImageButton imgbtnup = (ImageButton)findViewById(R.id.imgbtnup);
		ImageButton imgbtndown = (ImageButton)findViewById(R.id.imgbtndown);
		ImageButton imgbtncancel = (ImageButton)findViewById(R.id.imgbtncancel);

		
		imgbtnup.setOnClickListener(new clickup());
		imgbtndown.setOnClickListener(new clickdown());
		imgbtncancel.setOnClickListener(new clickcancel());
	

		
		try {

			textView12.setTextColor(Color.GREEN);
			textView12.setText("+"+jboffer.getString("vote_plus"));
			textView10.setTextColor(Color.RED);
			textView10.setText("-"+jboffer.getString("vote_minus"));
			textView15.setText("("+jboffer.getString("vote_count")+")");
			
			textView3.setText(jboffer.getString("title"));
			
			offerid=jboffer.getString("id");
			
			String myvote = jb.getString("vote");
			if(myvote.equals("1"))
			{
				textView2.setTextColor(Color.GREEN);
				textView2.setText("+"+myvote);
				
			}
			else
			{
				
				textView2.setTextColor(Color.RED);
				myvote="1";
				textView2.setText("-"+myvote);
			}
			
		

		} catch (JSONException e) {
		}
	}
	
	
	private class clickup implements android.view.View.OnClickListener {

		@Override
		public void onClick(View v) {
		
			 String url  = "https://offers.teiath.gr/api/vote/vote_up/";
			 UserVote.votingfunction(cont,url, offerid);

		}}	
	
	private class clickdown implements android.view.View.OnClickListener {

		@Override
		public void onClick(View v) {
		
			String url  = "https://offers.teiath.gr/api/vote/vote_down/";
			UserVote.votingfunction(cont,url, offerid);
		}}	
	
	private class clickcancel implements android.view.View.OnClickListener {

		@Override
		public void onClick(View v) {
		
			String url  = "https://offers.teiath.gr/api/vote/vote_cancel/";
			UserVote.votingfunction(cont,url, offerid);
		}}	

}
