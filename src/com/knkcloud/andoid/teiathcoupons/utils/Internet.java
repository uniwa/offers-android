package com.knkcloud.andoid.teiathcoupons.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.util.Log;

/**
 * utility class, handles HTTP Requests
 * 
 * @author Karpouzis Koutsourakis Ntinopoulos
 * 
 */
public class Internet {

	// public Cookie cook;

	public static CookieStore store;
	public static DefaultHttpClient client = null;

	public Internet() {

		if (!(client != null)) {
			Log.i("karp","creating new client!");
			client = new DefaultHttpClient();
			HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

			SchemeRegistry registry = new SchemeRegistry();
			SSLSocketFactory socketFactory = SSLSocketFactory
					.getSocketFactory();
			socketFactory
					.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
			registry.register(new Scheme("https", socketFactory, 443));
			SingleClientConnManager mgr = new SingleClientConnManager(
					client.getParams(), registry);
			client = new DefaultHttpClient(mgr, client.getParams());

			// Set verifier
			HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
		}

	}

	/**
	 * Sends a HTTP post request containing JSONObject data
	 * 
	 * @param url
	 *            the url of the request
	 * @param jsonObj
	 *            the JSONObject containing the request data
	 * @param timouts
	 *            requets timeout limits
	 * @return the server serponce
	 * @throws Exception
	 */
	public String JsonPostRequest(String url, JSONObject jsonObj, int[] timouts)
			throws Exception {
		String serverResponce = null;

		// HttpClient client = new DefaultHttpClient(timeOuts(timouts));
		HttpResponse response;

		HttpPost post = new HttpPost(url);
		StringEntity se = new StringEntity(jsonObj.toString());
		se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		se.setContentEncoding(new BasicHeader("Accept", "application/json"));

		post.setEntity(se);

		response = client.execute(post);
		HttpEntity entity = response.getEntity();
		

		// List<Cookie> cookies = ((AbstractHttpClient)
		// client).getCookieStore().getCookies();
		store = ((AbstractHttpClient) client).getCookieStore();

		List<Cookie> cookies = ((AbstractHttpClient) client).getCookieStore()
				.getCookies();
		// Log.e("cookie", cookies.get(0).toString());

		if (!(entity.equals(null))) {
			InputStream is = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			serverResponce = sb.toString();
			Log.d("karp", "serverResponce: "+serverResponce);
			serverResponce = StringEscapeUtils.unescapeJava(serverResponce);
			Log.e("cookiestore", ""
					+ ((AbstractHttpClient) client).getCookieStore()
							.getCookies().get(0).toString());
		}
		return serverResponce;
	}

	/**
	 * creates a HttpParams object with the timeouts
	 * 
	 * @param timouts
	 * @return
	 */
	public HttpParams timeOuts(int[] timouts) {
		HttpParams httpParameters = new BasicHttpParams();
		int timeoutConnection = timouts[0] * 1000;
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				timeoutConnection);
		int timeoutSocket = timouts[1] * 1000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		return httpParameters;
	}

}
