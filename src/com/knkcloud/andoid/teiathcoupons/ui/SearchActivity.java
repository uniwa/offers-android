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
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint.Join;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JsPromptResult;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.knkcloud.andoid.teiathcoupons.R;
import com.knkcloud.andoid.teiathcoupons.utils.Internet;


/** Searching for offers and companies 
 * @author Karpouzis Koutsourakis Ntinopoulos
 *
 */
public class SearchActivity extends Activity {

	private ArrayList<JSONObject> offers;
	private ArrayList<JSONObject> company;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
		tabHost.setup();

		TabSpec spec1 = tabHost.newTabSpec("Tab 1");
		spec1.setContent(R.id.tab1);
		spec1.setIndicator("Coupons",
				getResources().getDrawable(android.R.drawable.ic_dialog_map));

		TabSpec spec2 = tabHost.newTabSpec("Tab 2");
		spec2.setIndicator("companies",
				getResources().getDrawable(android.R.drawable.ic_dialog_map));
		spec2.setContent(R.id.tab2);

		tabHost.addTab(spec1);
		tabHost.addTab(spec2);

		findViewById(R.id.bnt_search).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(getCurrentFocus()
								.getWindowToken(), 0);

						((ListView) findViewById(R.id.list_companies))
								.setAdapter(null);
						((ListView) findViewById(R.id.list_coupons))
								.setAdapter(null);

						new fetchData()
								.execute(((EditText) (findViewById(R.id.et_query)))
										.getText().toString().trim());

					}
				});
	}

	private List<listEnrty> buildOffersList(String serverData) throws Exception {
		List<listEnrty> objects = new ArrayList<SearchActivity.listEnrty>();
		JSONObject serverJson = new JSONObject(serverData);

		// categories stats
		if (serverJson.has("offers")) {
			offers = new ArrayList<JSONObject>();
			offers.clear();
			JSONArray categoriesArray = serverJson.getJSONArray("offers");
			for (int i = 0; i < categoriesArray.length(); i++) {
				objects.add(new listEnrty(categoriesArray.getJSONObject(i)
						.getString("title"), categoriesArray.getJSONObject(i)
						.getString("description")));
				offers.add(categoriesArray.getJSONObject(i));
			}
		}

		return objects;
	}

	private List<listEnrty> buildCompaniesList(String serverData)
			throws Exception {
		List<listEnrty> objects = new ArrayList<SearchActivity.listEnrty>();
		JSONObject serverJson = new JSONObject(serverData);

		// categories stats
		if (serverJson.has("companies")) {

			company = new ArrayList<JSONObject>();
			company.clear();
			JSONArray categoriesArray = serverJson.getJSONArray("companies");
			for (int i = 0; i < categoriesArray.length(); i++) {
				objects.add(new listEnrty(categoriesArray.getJSONObject(i)
						.getString("name"), categoriesArray.getJSONObject(i)
						.getString("address")));
				company.add(categoriesArray.getJSONObject(i));
			}
		}

		return objects;
	}

	private class fetchData extends AsyncTask<String, Void, String> {

		Dialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(SearchActivity.this,
					getString(R.string.app_name), getString(R.string.plzwait));
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			String result = null;
			try {
				result = getRequest(
						"https://offers.teiath.gr/api/search/contains:"
								+ params[0], null, "");

				Log.d("karp", "SearchActivity.doInBackground: " + result);
			} catch (Exception e) {
				Log.e("karp",
						"SearchActivity.doInBackground: " + e.getMessage());
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();
			try {
				JSONObject serverJson = new JSONObject(result);
				if (serverJson.getString("status_code").equals("200")) {
					if (!serverJson.isNull("offers")) {
						ListView listView1 = (ListView) findViewById(R.id.list_coupons);
						listView1.setAdapter(new ListAdaptrer(
								buildOffersList(result)));
						listView1.setOnItemClickListener(new OfferClick());
					} else {
						// no offers found
						Toast.makeText(SearchActivity.this, "No offers found",
								0).show();
					}
					if (!serverJson.isNull("companies")) {
						ListView listView2 = (ListView) findViewById(R.id.list_companies);
						listView2.setAdapter(new ListAdaptrer(
								buildCompaniesList(result)));

						listView2.setOnItemClickListener(new CompanyClick());
					} else {
						Toast.makeText(SearchActivity.this,
								"No companies found", 0).show();
					}
				} else {
					Toast.makeText(SearchActivity.this, "Search failed", 0)
							.show();
				}

			} catch (Exception e) {
				Log.e("karp", "SearchActivity.onPostExecute: " + e.getMessage());
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
			super(SearchActivity.this, R.layout.stats_list_entry, objects);
			this.objects = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			LayoutInflater inflater = ((Activity) SearchActivity.this)
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

	private class OfferClick implements
			android.widget.AdapterView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			new OffersCustomDialog(SearchActivity.this, offers, company, arg2).show();
		}
	}

	private class CompanyClick implements
			android.widget.AdapterView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// convert ArrayList of JsonObjects To ArrayList of STrings
			ArrayList<String> Comp = new ArrayList<String>();
			ArrayList<String> Off = new ArrayList<String>();
			for (int x = 0; x < offers.size(); x++) {
				Off.add(offers.get(x).toString());
			}
			for (int x = 0; x < company.size(); x++) {
				Comp.add(company.get(x).toString());
			}

			Intent myintent = new Intent(SearchActivity.this, OffersCompanies.class);
			myintent.putStringArrayListExtra("companies", Comp);
			myintent.putStringArrayListExtra("offers", Off);
			try {
				myintent.putExtra("compid", company.get(arg2).getString("id"));
			} catch (JSONException e) {
				e.printStackTrace();
				Log.e("karp","companyid fail: "+e.getMessage());
			}
			SearchActivity.this.startActivity(myintent);

		}
	}

}
