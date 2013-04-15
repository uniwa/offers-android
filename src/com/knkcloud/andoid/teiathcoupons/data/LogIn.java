package com.knkcloud.andoid.teiathcoupons.data;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.knkcloud.andoid.teiathcoupons.R;
import com.knkcloud.andoid.teiathcoupons.utils.AStatus;
import com.knkcloud.andoid.teiathcoupons.utils.Internet;
import com.knkcloud.andoid.teiathcoupons.utils.Statuses;

/**
 * AsyncTask, handles login process.
 * 
 * @author Karpouzis Koutsourakis Ntinopoulos
 * 
 */
public abstract class LogIn extends AsyncTask<String, Void, AStatus> {

	private Context context;
	private ProgressDialog pd;
	private String serverResponce;

	/**
	 * AsyncTask, handles login process.
	 * 
	 * @param context
	 *            activity process
	 */
	public LogIn(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		pd = ProgressDialog.show(context, context.getString(R.string.download),
				context.getString(R.string.plzwait), true, true);
		super.onPreExecute();
	}

	@Override
	protected AStatus doInBackground(String... params) {
		AStatus result = Statuses.Generic.UNDEFINED;
		if (params.length == 1) {
			JSONObject jsonObj;
			try {
				jsonObj = new JSONObject(params[0]);
				Log.d("karp", "https://offers.teiath.gr/api/users/login");
				int[] GENERIC = { 3, 15 };
				serverResponce = new Internet().JsonPostRequest(
						"https://offers.teiath.gr/api/users/login", jsonObj,
						GENERIC);
				result = Statuses.Internet.SUCCESS;
			} catch (JSONException e) {
				result = Statuses.Misc.JSON_ERROR;
				Log.e("karp", "error login " + Log.getStackTraceString(e));
			} catch (Exception e) {
				result = Statuses.Internet.UNDEFINED;
				result.addMessage(e.getMessage());
				Log.e("karp", "error login " + Log.getStackTraceString(e));
			}
		} else {
			result = Statuses.Misc.PARAMETER_NUM;
		}
		Log.d("karp", "result= " + serverResponce);
		return result;
	}

	@Override
	protected void onPostExecute(AStatus result) {
		pd.dismiss();
		if ((result.equals(Statuses.Generic.SUCCESS))
				|| (result.equals(Statuses.Internet.SUCCESS))) {
			onSuccess(serverResponce);
		} else {
			onFail(result);
		}
		onWhatEver();
		super.onPostExecute(result);
	}

	/**
	 * 
	 * called on request success
	 * 
	 * @param result
	 *            server responce
	 */
	public abstract void onSuccess(String result);

	/**
	 * called on request fail
	 * 
	 * @param cause
	 *            the error code
	 */
	public abstract void onFail(AStatus cause);

	/**
	 * called on post execute
	 */
	public abstract void onWhatEver();

}
