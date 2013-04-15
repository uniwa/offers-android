package com.knkcloud.andoid.teiathcoupons.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

/**
 * Class that is used to Post geolocation (latitue longitude) to server
 * @author  Karpouzis Koutsourakis Dinopoulos
 *
 */
public class LocationService {

	private locationListener ll;
	private LocationManager lm;

	public LocationService(Context context) throws Exception {

		ll = new locationListener();
		lm = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, ll);

	}

	private class locationListener implements LocationListener {
		@Override
		public void onLocationChanged(Location loc) {
			if (loc != null) {
				new UpdateServer(loc).execute();
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}

	private class UpdateServer extends AsyncTask<Void, Void, String> {
		private Location location;

		public UpdateServer(Location location) {
			this.location = location;
		}

		@Override
		protected String doInBackground(Void... params) {
			String responce = null;
			try {

				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("lat", String
						.valueOf(location.getLatitude())));
				nameValuePairs.add(new BasicNameValuePair("lng", String
						.valueOf(location.getLongitude())));
				responce = getRequest(
						"https://offers.teiath.gr/api/users/coordinates/",
						nameValuePairs,":");
				Log.d("karp", "LocationService-set_location: " + responce);
				
				nameValuePairs.clear();
//				nameValuePairs.add(new BasicNameValuePair("radius","10"));+
				responce = getRequest(
						"https://offers.teiath.gr/api/users/radius/5",
						nameValuePairs,"/");
				Log.d("karp", "LocationService-set_radius: " + responce);				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return responce;
		}

		@Override
		protected void onPostExecute(String result) {
			onDestroy();
			super.onPostExecute(result);
		}

		public String getRequest(String url, List<NameValuePair> params, String nvSep)
				throws Exception {
			String getParams = "";
			if (params.size() > 0) {
				for (int i = 0; i < params.size(); i++) {
					getParams += params.get(i).getName() + "nvSep"
							+ params.get(i).getValue();
					if (i != params.size() - 1) {
						getParams += "/";
					}
				}
			}
			StringBuilder sb = null;
			HttpEntity entity = null;
			HttpResponse response = null;
			HttpGet get = new HttpGet(url + getParams);
			get.setHeader("Accept", "application/json");
			response = Internet.client.execute(get);

			entity = response.getEntity();
			if (!(entity.equals(null))) {
				InputStream is = null;
				is = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"), 8);
				sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				reader.close();
				is.close();
			}
			return sb.toString();
		}

	}

	private void onDestroy() {
		lm.removeUpdates(ll);
		ll=null;
		lm=null;
	}

}
