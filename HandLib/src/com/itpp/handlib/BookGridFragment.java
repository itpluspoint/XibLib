package com.itpp.handlib;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BookGridFragment extends Fragment {
	ArrayList<Map<String, String>> listTrans = new ArrayList<Map<String, String>>();
	private int mPos = -1;
	private int mImgRes;
	String _id = null;

	public BookGridFragment() {
	}

	public BookGridFragment(int pos) {
		mPos = pos;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getCategoryList();
		if (mPos == -1 && savedInstanceState != null)
			mPos = savedInstanceState.getInt("mPos");
		TypedArray imgs = getResources().obtainTypedArray(R.array.birds_img);
		mImgRes = imgs.getResourceId(mPos, -1);

		GridView gv = (GridView) inflater.inflate(R.layout.list_grid, null);
		gv.setBackgroundResource(android.R.color.white);
		gv.setAdapter(new GridAdapter());
		gv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (getActivity() == null)
					return;
				Map<String, String> tran = listTrans.get(position);

				_id = tran.get("_id");
				if (tran.get("book_avl").equals("0")) {
					showDownloadAlert(tran.get("rem_url"), tran.get("loc_url"));

					updateDataBase();
				} else {
					File file = new File(tran.get("loc_url"));
					Intent target = new Intent(Intent.ACTION_VIEW);
					target.setDataAndType(Uri.fromFile(file), "application/pdf");
					target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

					Intent intent = Intent.createChooser(target, "Open File");
					try {
						startActivity(intent);
					} catch (ActivityNotFoundException e) {
					}
				}
			}
		});
		gv.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (getActivity() == null)
					return false;
				Map<String, String> tran = listTrans.get(position);

				showDownloadAlert(tran.get("rem_url"), tran.get("loc_url"));

				updateDataBase();

				return false;
			}

		});

		return gv;
	}

	private void showDownloadAlert(String rem_url, String loc_url) {
		// TODO Auto-generated method stub

		final String mLoc_url = loc_url;
		final String mRem_url = rem_url;
		// TODO Auto-generated method stub
		// super.onBackPressed();

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setTitle("Not Available in your self .");
		builder.setIcon(R.drawable.ic_launcher);
		builder.setMessage("Downlod from online Library?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								if (isNetworkAvailable()) {
									new Utility(getActivity(), mRem_url,
											mLoc_url).execute();
								} else {
									Toast.makeText(getActivity(),
											"Error in Network Connectivity.!!",
											Toast.LENGTH_SHORT).show();
								}
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();

	}

	private void updateDataBase() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		DBHelper dbhelper = null;
		SQLiteDatabase db = null;

		try {
			dbhelper = new DBHelper(getActivity());
			db = dbhelper.getWritableDatabase();
			db.execSQL("update books set book_avl=1 where _id=" + _id);
			db.close();
			dbhelper.close();
		} catch (Exception ex) {
			Log.d("Ctegory list error", ex.getMessage());
		} finally {
			db.close();
			dbhelper.close();
		}

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("mPos", mPos);
	}

	private class GridAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return listTrans.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			final int _position = position;
			Map<String, String> tran = listTrans.get(position);

			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.grid_item, null);
			}
			ImageView img = (ImageView) convertView
					.findViewById(R.id.grid_item_img);
			TextView first_line = (TextView) convertView
					.findViewById(R.id.tvNameOfTheBook);
			TextView sec_line = (TextView) convertView
					.findViewById(R.id.tvMonth);
			img.setImageResource(mImgRes);
			first_line.setText(tran.get("book_name"));
			sec_line.setText(tran.get("book_abbr"));
			Button btn_download = (Button) convertView
					.findViewById(R.id.bDownload);
			btn_download.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					/*Toast.makeText(getActivity(),
							"Item Clicked is" + _position, Toast.LENGTH_LONG)
							.show();*/
					
					Map<String, String> tran = listTrans.get(_position);

					showDownloadAlert(tran.get("rem_url"), tran.get("loc_url"));

					updateDataBase();
				}

			});

			return convertView;
		}

	}

	private void getCategoryList() {
		// TODO Auto-generated method stub
		DBHelper dbhelper = null;
		SQLiteDatabase db = null;

		try {
			dbhelper = new DBHelper(getActivity());
			db = dbhelper.getReadableDatabase();
			Cursor cursor = db.rawQuery("select * from books where category="
					+ (mPos + 1), null);
			if (cursor != null) {

				while (cursor.moveToNext()) {
					LinkedHashMap<String, String> tran = new LinkedHashMap<String, String>();
					tran.put("_id", String.valueOf(cursor.getInt(cursor
							.getColumnIndex("_id"))));

					tran.put("book_name", cursor.getString(cursor
							.getColumnIndex("book_name")));
					tran.put("book_abbr", cursor.getString(cursor
							.getColumnIndex("book_abbr")));
					tran.put("book_avl", String.valueOf(cursor.getInt(cursor
							.getColumnIndex("book_avl"))));
					tran.put("loc_url",
							cursor.getString(cursor.getColumnIndex("loc_url")));
					tran.put("rem_url",
							cursor.getString(cursor.getColumnIndex("rem_url")));
					listTrans.add(tran);

				}

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

	private boolean isNetworkAvailable() {
		// TODO Auto-generated method stub
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}
		return haveConnectedWifi || haveConnectedMobile;
	}

}
