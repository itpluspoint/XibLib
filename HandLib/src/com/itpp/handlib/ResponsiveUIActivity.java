package com.itpp.handlib;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.itpp.databases.DBHelper;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class ResponsiveUIActivity extends SlidingFragmentActivity {

	private Fragment mContent;
	public static String myCustTitle = "Odisha Publication";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(myCustTitle);
		new DBHelper(this).getReadableDatabase().close();
		setContentView(R.layout.responsive_content_frame);

		// check if the content frame contains the menu frame
		if (findViewById(R.id.menu_frame) == null) {
			setBehindContentView(R.layout.menu_frame);
			getSlidingMenu().setSlidingEnabled(true);
			getSlidingMenu()
					.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			// show home as up so we can toggle
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			//getSupportActionBar().setLogo(R.drawable.op_logo);
			// getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.logo));

		} else {
			// add a dummy view
			View v = new View(this);
			setBehindContentView(v);
			getSlidingMenu().setSlidingEnabled(false);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}

		// set the Above View Fragment
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new BookGridFragment(0);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mContent).commit();

		// set the Behind View Fragment
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new BookMenuFragment(this)).commit();

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindScrollScale(0.25f);
		sm.setFadeDegree(0.25f);
		

		// show the explanation dialog
		/*
		 * if (savedInstanceState == null) new
		 * AlertDialog.Builder(this).setTitle("Hello")
		 * .setMessage("Nothing to display now..!!").show();
		 */
	}

	public void setCustomTitle(String title) {
		getSupportActionBar().setTitle(title);
		invalidateOptionsMenu();
		
	}

	

	/* (non-Javadoc)
	 * @see com.actionbarsherlock.app.SherlockFragmentActivity#invalidateOptionsMenu()
	 */
	@Override
	public void invalidateOptionsMenu() {
		// TODO Auto-generated method stub
		super.invalidateOptionsMenu();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			break;
		case R.id.menu_about_sansar:
			
			Intent intent = new Intent(this, About.class);
			intent.putExtra("about_of", 0);
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}

	public void switchContent(final Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			public void run() {
				getSlidingMenu().showContent();
			}
		}, 50);
	}

	public void onBookPressed(int pos) {
		Intent intent = ReadBookActivity.newInstance(this, pos);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.menu_book_grid, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuItem item = menu.findItem(R.id.menu_about_sansar);
		
		
		
		if(getSupportActionBar().getTitle().toString().equalsIgnoreCase("Magzines")){
			item.setVisible(true);
		}else{
			item.setVisible(false);
		}
		return super.onPrepareOptionsMenu(menu);
	}
	
	
}
