package com.itpp.handlib;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.itpp.databases.DBHelper;
import com.itpp.handlib.R.drawable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class BookMenuFragment extends ListFragment {
	Context mContext = null;
	ArrayList<Map<String, String>> listTrans = new ArrayList<Map<String, String>>();
	SimpleAdapter adapter;
	String title="";
	int[] image;

	public BookMenuFragment(Context context) {
		// TODO Auto-generated constructor stub
		mContext = context;
		
	}

	public BookMenuFragment() {
		// TODO Auto-generated constructor stub

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	//	setBackgroundColor(Color.parseColor("#4a3826"))
		
		return inflater.inflate(R.layout.list, null);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getListView().setCacheColorHint(Color.TRANSPARENT) ;
		//PaintDrawable sage = new PaintDrawable(R.drawable.divider_horiz);
		//getListView().setDivider(new ColorDrawable(Color.parseColor("#7C8381"))) ;
		//getListView().setDividerHeight(0);

		image = new int[] { R.drawable.cat_1,
                R.drawable.cat_2, R.drawable.cat_3,
                R.drawable.cat_4};
		/*
		 * String[] birds = getResources().getStringArray(R.array.birds);
		 * ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(
		 * getActivity(), android.R.layout.simple_list_item_1,
		 * android.R.id.text1, birds); setListAdapter(colorAdapter);
		 */
		getCategoryList();
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {

	/*	setSelection(position);
		lv.setItemChecked(position, true);*/
		Map<String, String> tran = listTrans.get(position);
		
		title=tran.get("cat_name");
		Fragment newContent = new BookGridFragment(position);
		
		if (newContent != null)
			switchFragment(newContent);
		
	}

	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof ResponsiveUIActivity) {
			ResponsiveUIActivity ra = (ResponsiveUIActivity) getActivity();
			ra.switchContent(fragment);
			ra.setCustomTitle(title);
		}
	}

	private void getCategoryList() {
		// TODO Auto-generated method stub
		DBHelper dbhelper = null;
		SQLiteDatabase db = null;

		try {
			dbhelper = new DBHelper(mContext);
			db = dbhelper.getReadableDatabase();
			Cursor cursor = db.query("category", null, null, null, null, null,
					null);
			if (cursor != null) {
int i=0;
				while (cursor.moveToNext()) {
					LinkedHashMap<String, String> tran = new LinkedHashMap<String, String>();
					tran.put("_id",
							cursor.getString(cursor.getColumnIndex("_id")));

					tran.put("cat_name",
							cursor.getString(cursor.getColumnIndex("cat_name")));
					tran.put("cat_image",Integer.toString(image[i]));
					listTrans.add(tran);
					i++;

				}
				adapter = new SimpleAdapter(mContext, listTrans,
						R.layout.menu_row,
						new String[] { "cat_name","cat_image" },
						new int[] { R.id.tvCategoryName ,R.id.ivMenuIcon});
				
		
				 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				setListAdapter(adapter);
				;
			}
			cursor.close();
			db.close();
			dbhelper.close();
		} catch (Exception ex) {
			Log.d("Ctegory list error", ex.getMessage());
		} finally {
			db.close();
			dbhelper.close();
		}

	}

}
