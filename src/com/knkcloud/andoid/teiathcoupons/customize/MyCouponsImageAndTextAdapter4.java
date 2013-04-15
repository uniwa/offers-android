package com.knkcloud.andoid.teiathcoupons.customize;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.knkcloud.andoid.teiathcoupons.R;

/**
 * ITS THE ADAPTER FOR MYVOTES LIST
 * 
 * @author Karpouzis Koutsourakis Ntinopoulos
 * 
 */
public class MyCouponsImageAndTextAdapter4 extends ArrayAdapter<JSONObject> {
	private ArrayList<JSONObject> jobjects = new ArrayList<JSONObject>();
	private LayoutInflater mInflater;
	private int mViewResourceId;
	private Context cont;

	/**
	 * ITS THE ADAPTER FOR MYVOTES LIST
	 * 
	 * @param ctx
	 *            activity context
	 * @param viewResourceId
	 *            layout id
	 * @param obj
	 *            containing offers
	 * @param obj2
	 *            containing votes
	 */
	public MyCouponsImageAndTextAdapter4(Context ctx, int viewResourceId,
			ArrayList<JSONObject> obj, ArrayList<JSONObject> obj2) {
		super(ctx, viewResourceId);
		cont = ctx;
		mInflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		jobjects = obj;
		// jobjects2=obj2;
		mViewResourceId = viewResourceId;
	}

	@Override
	public int getCount() {
		return jobjects.size();

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
		TextView tv = (TextView) convertView.findViewById(R.id.textView22);
		TextView tv1 = (TextView) convertView.findViewById(R.id.textView12);
		TextView tv2 = (TextView) convertView.findViewById(R.id.textView32);
		TextView tv4 = (TextView) convertView.findViewById(R.id.textView4);
		TextView tv5 = (TextView) convertView.findViewById(R.id.textView5);

		try {

			tv.setText(jobjects.get(position).getString("title"));
			tv1.setText(jobjects.get(position).getString("id"));
			tv2.setText(jobjects.get(position).getString("vote_count"));
			tv4.setTextColor(Color.GREEN);
			tv4.setText("+" + jobjects.get(position).getString("vote_plus"));
			tv5.setTextColor(Color.RED);
			tv5.setText("-" + jobjects.get(position).getString("vote_minus"));

		} catch (JSONException e) {

		}

		tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		tv.setTextSize(13);
		tv.setTextColor(Color.BLACK);

		tv1.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		tv1.setTextSize(13);
		tv1.setTextColor(Color.BLACK);

		tv2.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		tv2.setTextSize(13);
		tv2.setTextColor(Color.BLACK);

		return convertView;
	}

	/**
	 * convert dp into pixels function
	 * 
	 * @param dp
	 *            the dp value
	 * @return the calculated px value
	 */
	private int dp2px(int dp) {
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				cont.getResources().getDisplayMetrics());
		int x = (int) (px);
		return x;
	}
}
