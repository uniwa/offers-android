package com.knkcloud.andoid.teiathcoupons.ui;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;
import com.knkcloud.andoid.teiathcoupons.R;




/**
 * Map Activity for Companies and Offers
 * @author Karpouzis Koutsourakis Ntinopoulos
 *
 */

public class OffersCompanies extends MapActivity {
	
ArrayList<JSONObject>Companies= new ArrayList<JSONObject>();
ArrayList<JSONObject>Offers= new ArrayList<JSONObject>();
	
ArrayList<String>Comp= new ArrayList<String>();
ArrayList<String>Off= new ArrayList<String>();
	
String compid;
private MapView mapView;
private MapController mapController;
private int maxZoom, initZoom;
private List<Overlay> mapOverlays ;	
private Projection projection; 
private double companylat;
private double companylong;

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		setContentView(R.layout.map_mapactivity);
				
        try{
        	Bundle intentextras = getIntent().getExtras();
        if (intentextras!=null){
        	Comp=intentextras.getStringArrayList("companies");
        	Off=intentextras.getStringArrayList("offers");
        	compid=intentextras.getString("compid");
        	
        }
}catch (Exception e) {}
        
        
        mapView = (MapView) findViewById(R.id.map_view);    
        mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);
        

// metetrepse ta arraylist apo strings pou irthan apo to allo activity se arraylist apo json objects            
   for(int x=0; x<Comp.size(); x++)
   {
	   try {
		Companies.add(new JSONObject(Comp.get(x)));
	} catch (JSONException e) {

	} 
   }
   for(int x=0; x<Off.size(); x++)
   {
	   try {
		   Offers.add(new JSONObject(Off.get(x)));
	} catch (JSONException e) {

	} 
   }   


   try {
   TextView TextView15 = (TextView)findViewById(R.id.TextView15);
   TextView15.setTextColor(Color.BLACK);
   TextView15.setText(GetTheJsonObjectFromArrayList(compid).getString("name"));
   
 TextView TextView13 = (TextView)findViewById(R.id.TextView13);
 TextView13.setText(GetTheJsonObjectFromArrayList(compid).getString("afm"));
	
 TextView TextView12 = (TextView)findViewById(R.id.TextView12);
 TextView12.setText(GetTheJsonObjectFromArrayList(compid).getString("address"));
	
 TextView TextView02 = (TextView)findViewById(R.id.TextView02);
 TextView02.setText(GetTheJsonObjectFromArrayList(compid).getString("service_type"));
	
 TextView TextView04 = (TextView)findViewById(R.id.TextView04);
 TextView04.setText(GetTheJsonObjectFromArrayList(compid).getString("fax"));	
 
 TextView TextView06 = (TextView)findViewById(R.id.TextView06);
 TextView06.setText(GetTheJsonObjectFromArrayList(compid).getString("phone"));	
	
 TextView TextView08 = (TextView)findViewById(R.id.TextView08);
 TextView08.setText(GetTheJsonObjectFromArrayList(compid).getString("postalcode"));	
 
} catch (JSONException e) {}
   
   

 	  	CenterOnCompany();        

	}
	
	
	
	
	
	
	//vres to jsonobject tis etaireias apo to arraylist twn json object twn etaireiwn
	public JSONObject GetTheJsonObjectFromArrayList(String compid)
	{
		JSONObject Company = new JSONObject();
		for(int x=0; x<Companies.size(); x++)
		{
		try {
			if(Companies.get(x).getString("id").equals(compid))
			{
				Company=Companies.get(x);
				break;
			}
		} catch (JSONException e) {}	
		}
		return Company;
	}
	
	

	private void CenterOnCompany() {
		try {
			companylat=Double.parseDouble(GetTheJsonObjectFromArrayList(compid).getString("latitude"));
			companylong=Double.parseDouble(GetTheJsonObjectFromArrayList(compid).getString("longitude"));
			
		} catch (NumberFormatException e) {

		} catch (JSONException e) {

		}
	
		 GeoPoint point = new GeoPoint((int)(companylat*1e6),(int)(companylong*1e6)); 
		 mapController = mapView.getController();
	     mapController.setCenter(point);
	     maxZoom = mapView.getMaxZoomLevel();
	     initZoom = (int)(0.85*(double)maxZoom);
	     mapController.setZoom(initZoom);	
	     mapOverlays = mapView.getOverlays();        
		 projection = mapView.getProjection();
		 mapOverlays.add(new MyOverlay());  
	} 	
	
	class MyOverlay extends Overlay{
		Paint mPaint;
	    public void draw(Canvas canvas, MapView mapv, boolean shadow){
	        super.draw(canvas, mapv, shadow);
	        //set Paint
	        mPaint = new Paint();
	        mPaint.setDither(true);
	        mPaint.setStyle(Paint.Style.STROKE);
	        mPaint.setStrokeJoin(Paint.Join.ROUND);
	        mPaint.setStrokeCap(Paint.Cap.ROUND);
	        mPaint.setStrokeWidth(0);       
	        // draw my spot 
	        Drawable myspot_icon = getResources().getDrawable(R.drawable.marker);
	        drawMarker(canvas, myspot_icon, companylat, companylong);
	    }
	    
	    // Puts a marker on the specified location
	    private void drawMarker(Canvas canvas, Drawable drawable, double latitude, double longitude){	
	    	GeoPoint MarkerGeoPoint = new GeoPoint((int)(latitude * 1e6), (int)(longitude*1e6));
	    	Point MarkerPoint = new Point();
	    	projection.toPixels(MarkerGeoPoint, MarkerPoint);
	 	    Bitmap bitmap =  ((BitmapDrawable)drawable).getBitmap();
	    	canvas.drawBitmap(bitmap, MarkerPoint.x - bitmap.getWidth()/2, MarkerPoint.y - bitmap.getHeight(), mPaint);   
	    }
	} 	
	
	@Override
	protected boolean isRouteDisplayed() {


		return false;
	}
	
	
	
	
	

}
