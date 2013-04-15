package com.knkcloud.andoid.teiathcoupons.customize;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.knkcloud.andoid.teiathcoupons.R;


/**
 * ITS THE ADAPTER FOR MYCOUPONSACTIVIYY LIST
 * @author Karpouzis Koutsourakis Ntinopoulos
 *
 */
public class MyCouponsImageAndTextAdapter3 extends ArrayAdapter<JSONObject> {
	  private ArrayList<Drawable>icons = new ArrayList<Drawable>();
	  private ArrayList<JSONObject>jobjects = new ArrayList<JSONObject>();
	  private ArrayList<JSONObject>jobjects2 = new ArrayList<JSONObject>();
      private LayoutInflater mInflater;
      private String[] mStrings;
      private int mViewResourceId;
      private Context cont;
            
      
      /**
       * ITS THE ADAPTER FOR MYCOUPONSACTIVIYY LIST
     * @param ctx activity context
     * @param viewResourceId layout id
     * @param obj	 containing offers
     * @param obj2  containing coupons 
     */
    public MyCouponsImageAndTextAdapter3(Context ctx, int viewResourceId,ArrayList<JSONObject> obj,ArrayList<JSONObject> obj2 ) {
          super(ctx, viewResourceId);
           cont=ctx;
           mInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           jobjects = obj;         
           jobjects2=obj2;
           mViewResourceId = viewResourceId;
   }
      
      
      @Override
      public int getCount() {
    	 return jobjects.size(); 
     // return mStrings.length;
      }


      
      
      @Override
      public JSONObject getItem(int position) {
      return jobjects.get(position);
      }

      @Override
      public long getItemId(int position) {
       return position;
      }

      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
              convertView = mInflater.inflate(mViewResourceId, null);
              convertView.setPadding(dp2px(10), dp2px(10), 0, dp2px(10));             
            //creates the GUI
              TextView spamtext = (TextView)convertView.findViewById(R.id.spam);
              TextView tv = (TextView)convertView.findViewById(R.id.textView22);
              TextView tv1 = (TextView)convertView.findViewById(R.id.textView12);
              TextView tv2 = (TextView)convertView.findViewById(R.id.textView32);
            
              
              try {
            	  
            	  String spam=jobjects.get(position).getString("is_spam");
            	if(spam.equals("1"))
            	{
            		spamtext.setText("Spam");
            		spamtext.setTextSize(13);
            		spamtext.setTextColor(Color.WHITE);
            		spamtext.setVisibility(View.VISIBLE);
            		spamtext.setBackgroundColor(Color.RED);
            	}
            	  
				tv.setText(jobjects.get(position).getString("title"));
				tv1.setText(jobjects2.get(position).getString("serial_number"));
				tv2.setText(jobjects2.get(position).getString("created"));
			} catch (JSONException e) {

			}
  
              tv.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
              tv.setTextSize(13);
              tv.setTextColor(Color.BLACK);
              
              tv1.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
              tv1.setTextSize(13);
              tv1.setTextColor(Color.BLACK);
              
              tv2.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
              tv2.setTextSize(13);
              tv2.setTextColor(Color.BLACK);
              
              
              return convertView;
      }
      
    
    	/**
    	 * convert dp into pixels function
    	 * @param dp the dp value
    	 * @return the calculated px value
    	 */
    	private int dp2px(int dp){
    		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, cont.getResources().getDisplayMetrics());
    		int x = (int)(px);
    		return x;
		}          
	}
