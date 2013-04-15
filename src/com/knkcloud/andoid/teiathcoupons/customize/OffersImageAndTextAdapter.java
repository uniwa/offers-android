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
import android.widget.ImageView;
import android.widget.TextView;

import com.knkcloud.andoid.teiathcoupons.R;

/**
 * Creation of the custom list class
 * 
 * @author Karpouzis Koutsourakis Ntinopoulos
 * 
 */
public class OffersImageAndTextAdapter extends ArrayAdapter<JSONObject> {
	private ArrayList<Drawable> icons = new ArrayList<Drawable>();
	private ArrayList<JSONObject> jobjects = new ArrayList<JSONObject>();
	private LayoutInflater mInflater;
	private String[] mStrings;
	private int mViewResourceId;
	private Context cont;

	/**
	 * Creation of the custom list class
	 * 
	 * @param ctx
	 *            activity context
	 * @param viewResourceId
	 *            layout id
	 * @param obj
	 *            containing offers
	 * @param iconz
	 *            containing offer drawable
	 */
	public OffersImageAndTextAdapter(Context ctx, int viewResourceId,
			ArrayList<JSONObject> obj, ArrayList<Drawable> iconz) {
		super(ctx, viewResourceId);
		cont = ctx;
		mInflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		jobjects = obj;
		icons = iconz;
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

		// creates the GUI
		convertView.setPadding(dp2px(10), dp2px(10), 0, dp2px(10));
		ImageView iv = (ImageView) convertView.findViewById(R.id.option_icon);
		iv.setImageDrawable(icons.get(position));
		TextView tv = (TextView) convertView.findViewById(R.id.option_text);
		try {
			tv.setText(jobjects.get(position).getString("title"));
		} catch (JSONException e) {

		}
		tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		tv.setTextSize(16);
		tv.setTextColor(Color.BLACK);

		TextView offertype = (TextView) convertView
				.findViewById(R.id.textView2);
		try {
			offertype.setText(jobjects.get(position).getString("offer_type"));

			if (jobjects.get(position).getString("offer_type")
					.equals("happy hour")) {
				offertype.setTextColor(Color.rgb(30, 144, 255));
			}
			if (jobjects.get(position).getString("offer_type")
					.equals("coupons")) {
				offertype.setTextColor(Color.rgb(255, 165, 0));
			}
			if (jobjects.get(position).getString("offer_type")
					.equals("limited")) {
				offertype.setTextColor(Color.rgb(50, 205, 50));
			}

		} catch (JSONException e) {

		}

		TextView plus = (TextView) convertView.findViewById(R.id.textView1);
		plus.setTextColor(Color.GREEN);
		try {
			plus.setText("+" + jobjects.get(position).getString("vote_plus"));
		} catch (JSONException e) {

		}
		TextView minus = (TextView) convertView.findViewById(R.id.textView3);
		minus.setTextColor(Color.RED);
		try {
			minus.setText("-" + jobjects.get(position).getString("vote_minus"));
		} catch (JSONException e) {

		}

		TextView tag = (TextView) convertView.findViewById(R.id.textView4);
		tag.setTextColor(Color.BLUE);
		try {
			tag.setText(jobjects.get(position).getString("tags"));
		} catch (JSONException e) {

		}

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
