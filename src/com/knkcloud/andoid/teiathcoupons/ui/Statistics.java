package com.knkcloud.andoid.teiathcoupons.ui;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.knkcloud.andoid.teiathcoupons.R;
import com.knkcloud.andoid.teiathcoupons.utils.Internet;



/** Presents Users Stats
  * @author Karpouzis Koutsourakis Ntinopoulos
 *
 */
public class Statistics extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		new fetchData().execute();
		super.onCreate(savedInstanceState);
	}

	private View buildList(String serverData) throws Exception {
		
		LinearLayout llWindow = new LinearLayout(Statistics.this);
		llWindow.setOrientation(LinearLayout.VERTICAL);
		llWindow.setWeightSum(7.0f);
		llWindow.setBackgroundResource(R.drawable.bg);		
		
		ListView listView = new ListView(Statistics.this);
		listView.setAdapter(new ListAdaptrer(buildObjectList(serverData)));
		listView.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, 0, 6.3f));	
		listView.setScrollingCacheEnabled(false);
		llWindow.addView(listView);

		ImageView llFooter = new ImageView(Statistics.this);
		llFooter.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, 0, 0.7f));
		llFooter.setBackgroundResource(R.drawable.foooterdown);
		llFooter.setImageResource(R.drawable.footer_logo);
		llFooter.setScaleType(ImageView.ScaleType.CENTER);
		llWindow.addView(llFooter);

		return llWindow;
	}

	private List<listEnrty> buildObjectList(String serverData) throws Exception {
		List<listEnrty> objects = new ArrayList<Statistics.listEnrty>();
		JSONObject serverJson = new JSONObject(serverData);
		// my stats
		if (serverJson.has("my_stats")) {
			if (serverJson.getJSONObject("my_stats").getString("coupon_count") != null) {
				objects.add(new listEnrty("Τα κουπόνια μου", serverJson
						.getJSONObject("my_stats").getString("coupon_count")));
			}
		}

		// categories stats
		if (serverJson.has("categories")) {
			JSONArray categoriesArray = serverJson.getJSONArray("categories");
			for (int i = 0; i < categoriesArray.length(); i++) {
				objects.add(new listEnrty(categoriesArray.getJSONObject(i)
						.getString("name"), categoriesArray.getJSONObject(i)
						.getString("offer_count")));
			}
		}

		// types stats
		if (serverJson.has("types")) {
			JSONArray categoriesArray = serverJson.getJSONArray("types");
			for (int i = 0; i < categoriesArray.length(); i++) {
				objects.add(new listEnrty(categoriesArray.getJSONObject(i)
						.getString("name"), categoriesArray.getJSONObject(i)
						.getString("offer_count")));
			}
		}
		return objects;
	}

	private class fetchData extends AsyncTask<Void, Void, String> {

		Dialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(Statistics.this,
					getString(R.string.app_name), getString(R.string.plzwait));
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Void... params) {
			String result = null;
			try {
				result = getRequest(
						"https://offers.teiath.gr/api/offers/statistics",
						null, "");
			} catch (Exception e) {
				Log.e("karp", "Statistics.doInBackground: " + e.getMessage());
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();
			try {
				setContentView(buildList(result));
			} catch (Exception e) {
				Log.e("karp", "Statistics.onPostExecute: " + e.getMessage());
				e.printStackTrace();
			}
			super.onPostExecute(result);
		}

	}

	private class listEnrty {
		private String name;
		private String value;

		private listEnrty(String name, String value) {
			this.name = name;
			this.value = value;
		}

		private String getName() {
			return name;
		}

		private String getValue() {
			return value;
		}
	}

	private class ListAdaptrer extends ArrayAdapter<listEnrty> {
		private List<listEnrty> objects;

		public ListAdaptrer(List<listEnrty> objects) {
			super(Statistics.this, R.layout.stats_list_entry, objects);
			this.objects = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			LayoutInflater inflater = ((Activity) Statistics.this)
					.getLayoutInflater();
			row = inflater.inflate(R.layout.stats_list_entry, parent, false);

			((TextView) row.findViewById(R.id.stats_title)).setText(objects
					.get(position).getName());
			((TextView) row.findViewById(R.id.stats_value)).setText(objects
					.get(position).getValue());
			return row;
		}

	}

	public String getRequest(String url, List<NameValuePair> params,
			String nvSep) throws Exception {
		String getParams = "";
		if (params != null) {
			if (params.size() > 0) {
				for (int i = 0; i < params.size(); i++) {
					getParams += params.get(i).getName() + "nvSep"
							+ params.get(i).getValue();
					if (i != params.size() - 1) {
						getParams += "/";
					}
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
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);
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
