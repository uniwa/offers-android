package com.knkcloud.andoid.teiathcoupons.ui;

import com.knkcloud.andoid.teiathcoupons.R;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * presents terms and contitions and faq 
 * @author Karpouzis Koutsourakis Ntinopoulos
 *
 */
public class WebViewActivity extends Activity{
	
int x=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		Bundle intentextras = getIntent().getExtras();
      if (intentextras!=null){
      	x=intentextras.getInt("id");
		
      	if(x==0)
		setContentView(R.layout.couponsterms);		
      	else
      		setContentView(R.layout.couponsfaq);
	}
	}


}
