package com.knkcloud.andoid.teiathcoupons.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.knkcloud.andoid.teiathcoupons.R;
import com.knkcloud.andoid.teiathcoupons.utils.LocationService;

/**
 * 
 * @author Karpouzis Koutsourakis Ntinopoulos
 * 
 * Main UI of the application
 * 
 */

public class MainUI extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.mainui);

		ImageButton imgbtn1 = (ImageButton) findViewById(R.id.imgbtn1);
		imgbtn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainUI.this, Offers.class);
				startActivity(intent);
			}
		});

		ImageButton imgbtn2 = (ImageButton) findViewById(R.id.imgbtn2);
		imgbtn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainUI.this, MyCouponsActivity.class);
				startActivity(intent);
			}
		});

		ImageButton imgbtn3 = (ImageButton) findViewById(R.id.imgbtn3);
		imgbtn3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				try {
					new LocationService(getApplicationContext());
				} catch (Exception e) {

				}

			}
		});

		ImageButton imgbtn4 = (ImageButton) findViewById(R.id.imgbtn4);
		imgbtn4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(MainUI.this, MyVotesActivity.class);
				startActivity(intent);

			}
		});

		ImageButton imgbtn5 = (ImageButton) findViewById(R.id.imgbtn5);
		imgbtn5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(MainUI.this, Statistics.class);
				startActivity(intent);

			}
		});

		ImageButton imgbtn6 = (ImageButton) findViewById(R.id.imgbtn6);
		imgbtn6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(MainUI.this, SearchActivity.class);
				startActivity(intent);

			}
		});

	}

}
