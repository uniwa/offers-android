package com.knkcloud.andoid.teiathcoupons.customize;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.knkcloud.andoid.teiathcoupons.R;

/**
 * ITS THE ADAPTER FOR offers LIST
 * 
 * @author Karpouzis Koutsourakis Ntinopoulos
 * 
 */
public class OffersImageAndTextAdapter2 extends ArrayAdapter<JSONObject> {
	private ArrayList<Drawable> icons = new ArrayList<Drawable>();
	private ArrayList<JSONObject> jobjects = new ArrayList<JSONObject>();
	private LayoutInflater mInflater;
	private String[] mStrings;
	private int mViewResourceId;
	private Context cont;

	/**
	 * ITS THE ADAPTER FOR offers LIST
	 * 
	 * @param ctx
	 *            the activity context
	 * @param viewResourceId
	 *            layout id
	 */
	public OffersImageAndTextAdapter2(Context ctx, int viewResourceId) {
		super(ctx, viewResourceId);
		cont = ctx;
		mInflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mViewResourceId = viewResourceId;
	}

	@Override
	public int getCount() {
		return 1;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		convertView = mInflater.inflate(mViewResourceId, null);
		convertView.setPadding(dp2px(10), dp2px(10), 0, dp2px(10));

		//generates GUI
		TextView tv1 = (TextView) convertView.findViewById(R.id.textView1);
		TextView tv2 = (TextView) convertView.findViewById(R.id.textView2);
		TextView tv3 = (TextView) convertView.findViewById(R.id.textView3);
		TextView tv4 = (TextView) convertView.findViewById(R.id.textView4);
		TextView tv5 = (TextView) convertView.findViewById(R.id.textView5);
		TextView tv6 = (TextView) convertView.findViewById(R.id.textView6);

		tv1.setText("Happy Hour");
		tv1.setTextColor(Color.rgb(30, 144, 255));
		tv2.setText("Οι προσφορές τύπου Happy Hour επαναλαμβάνονται κάθε εβδομάδα σε συγκεκριμένες μέρες και ώρες.");

		tv3.setText("Coupons");
		tv3.setTextColor(Color.rgb(255, 165, 0));
		tv4.setText("Οι προσφορές τύπου Coupons προσφέρουν περιορισμένο αριθμό κουπονιών, τα οποία μπορούν να δεσμέυσουν οι φοιτητές. Στη συνέχεια μπορούν να τα εξαργυρώσουν στην επιχείρηση, λαμβάνοντας το προϊόν ή την υπηρεσία που προσφέρει.");

		tv5.setText("Limited");
		tv5.setTextColor(Color.rgb(50, 205, 50));
		tv6.setText("Οι προσφορές τύπου Limited έχουν περιορισμένη διάρκεια.");

		return convertView;
	}

	/**
	 * convert dp into pixels function
	 * @param dp the dp value
	 * @return the calculated px value
	 */
	private int dp2px(int dp) {
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				cont.getResources().getDisplayMetrics());
		int x = (int) (px);
		return x;
	}
}
