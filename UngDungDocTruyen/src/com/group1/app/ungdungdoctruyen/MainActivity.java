package com.group1.app.ungdungdoctruyen;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.group1.app.ungdungdoctruyen.OtherActivity.ActivityInfomation;
import com.group1.app.ungdungdoctruyen.OtherActivity.ActivitySetting;
import com.group1.app.ungdungdoctruyen.adapter.NavDrawerListAdapter;
import com.group1.app.ungdungdoctruyen.items.NavDrawerItem;
import com.group1.app.ungdungdoctruyen.objects.RssObject;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity {
	ViewPager viewPager;
	PagerTabStrip tab_strp;
	TabsSelector mapager;
	public static DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private int i = 0;
	SQLiteDatabase database;
	private ActionBarDrawerToggle mDrawerToggle;
	public static ArrayList<RssObject> arrlData = new ArrayList<RssObject>();
	public static boolean isCheckedTheme = false;
	Handler handler = new Handler();
	Runnable timedTask = new Runnable() {

		@Override
		public void run() {
			// getUrlText();
			handler.postDelayed(timedTask, 1000);
		}
	};
	final String[] fragments = { "com.group1.app.ungdungdoctruyen.Tab1",
			"com.example.navigationdrawer.FragmentTwo",
			"com.example.navigationdrawer.FragmentThree" };

	BroadcastReceiver broadcastReceiver;
	public static boolean network = false;
	// Edit swipstab
	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mapager = new TabsSelector(getSupportFragmentManager());
		viewPager = (ViewPager) findViewById(R.id.pager);
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Log.d("MyApp", "No SDCARD");
		} else {
			File directory = new File(Environment.getExternalStorageDirectory()
					+ File.separator + "DownloadTruyen");
			directory.mkdirs();
		}
		listenNetwork();
		getDatabase();
		Cursor c = database
				.query("tblLike", null, null, null, null, null, null);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			i++;
			c.moveToNext();
		}
		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));
		// Communities, Will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1), true, String.valueOf(i)));
		// Pages
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
				.getResourceId(4, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
				.getResourceId(5, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons
				.getResourceId(6, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons
				.getResourceId(7, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], navMenuIcons
				.getResourceId(8, -1)));
		// What's hot, We will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[9], navMenuIcons
				.getResourceId(9, -1), true, "50+"));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_menu, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				mDrawerList.bringToFront();
				mDrawerLayout.requestLayout();
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			// displayView(0);
			// Intent intent = new Intent(getBaseContext(),MainActivity.class);
			// startActivity(intent);
		}

		new RssLoadData().execute();
		arrlData = RssLoadData.arrlData;
		doSetting();
	}

	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			displayView(position);

		}
	}

	@SuppressLint("NewApi")
	private void displayView(int position) {
		// update the main content by replacing fragments
		switch (position) {
		case 0:
			mDrawerLayout.closeDrawer(GravityCompat.START);
			viewPager.setCurrentItem(1);
			break;
		case 1:

			break;
		case 2: {
			Intent intent = new Intent(getBaseContext(),
					ListDownloadActivity.class);
			startActivity(intent);
			break;
		}
		case 3: {
			Intent intent = new Intent(getBaseContext(),
					ListLikeMangaActivity.class);
			startActivity(intent);
			overridePendingTransition(R.animator.push_up_in,
					R.animator.push_up_out);
			break;
		}
		case 4:
			mDrawerLayout.closeDrawer(GravityCompat.START);
			viewPager.setCurrentItem(0);
			break;
		case 5: {
			Intent intent = new Intent(getBaseContext(), OpenFileActivity.class);
			startActivity(intent);
			overridePendingTransition(R.animator.push_up_in,
					R.animator.push_up_out);
			break;
		}
		case 6:
			doHelp();
			break;
		case 7: {
			Intent intent = new Intent(getBaseContext(), ActivitySetting.class);
			startActivity(intent);
			overridePendingTransition(R.animator.push_up_in,
					R.animator.push_up_out);
			break;
		}
		case 8:
			Intent intent = new Intent(getBaseContext(),
					ActivityInfomation.class);
			startActivity(intent);
			overridePendingTransition(R.animator.push_up_in,
					R.animator.push_up_out);
			break;
		case 9:
			Toast.makeText(getApplicationContext(), "Đang xây dựng ...",
					Toast.LENGTH_SHORT).show();
			break;
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			Toast.makeText(getApplicationContext(),
					"Đang xây dựng...\n Mời quay lại sau !!!",
					Toast.LENGTH_SHORT).show();
			return true;
		case R.id.action_search:

			viewPager.setCurrentItem(2);
			Tab3.edtSearch.requestFocus();
			return true;
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.drawable.item_search, menu);
		// SearchManager searchManager =
		// (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		// SearchView searchView =
		// (SearchView) menu.findItem(R.id.action_search).getActionView();
		// searchView.setSearchableInfo(
		// searchManager.getSearchableInfo(getComponentName()));
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnectedOrConnecting();
	}

	public Boolean checkTable(SQLiteDatabase database, String tableName) {
		Cursor c = database.rawQuery(
				"select DISTINCT tbl_name from sqlite_master where tbl_name = '"
						+ tableName + "'", null);

		if (c != null) {
			if (c.getCount() > 0) {
				c.close();
				return true;
			}
		}
		return false;
	}
   //khởi tạo cơ sở dữ liệu
	public SQLiteDatabase getDatabase() {
		database = openOrCreateDatabase("mydata.db",
				SQLiteDatabase.CREATE_IF_NECESSARY, null);
		if (database != null) {
			if (checkTable(database, "tblLike")) {
				return database;
			}
			database.setLocale(Locale.getDefault());
			database.setVersion(1);
			String lopString = "create table tblLike(Name NVARCHAR primary key,urlImg NVARCHAR,author NVARCHAR,type NVARCHAR,position NVARCHAR)";
			database.execSQL(lopString);
			String Student = "create table tblChapDownLoad(Chap text primary key)";
			database.execSQL(Student);
			String resume = "create table tblResume(Chap text primary key,pager text)";
			database.execSQL(resume);

			// Toast.makeText(getBaseContext(),"Tạo CSDL thành công",Toast.LENGTH_SHORT).show();
		}
		return database;
	}

	public void listenNetwork() {
		broadcastReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connectivityManager
						.getActiveNetworkInfo();

				if (networkInfo != null && networkInfo.isConnected()) {
					network = true;

					viewPager.setAdapter(mapager);
					tab_strp = (PagerTabStrip) findViewById(R.id.tab_strip);
					tab_strp.setTextColor(Color.WHITE);
					viewPager.setCurrentItem(1);
				} else {
					AlertDialog alertDialog = new AlertDialog.Builder(
							MainActivity.this).create();
					alertDialog.setTitle("Thông báo");
					alertDialog
							.setMessage("Bạn sẽ không thể đọc truyện online được nếu như không có mạng !");
					alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
									network = false;

									viewPager.setAdapter(mapager);
									tab_strp = (PagerTabStrip) findViewById(R.id.tab_strip);
									tab_strp.setTextColor(Color.WHITE);
									viewPager.setCurrentItem(1);
								}
							});
					alertDialog.show();

				}
			}
		};

		IntentFilter filter = new IntentFilter();
		filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		registerReceiver(broadcastReceiver, filter);
	}

	public void doHelp() {
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle("Trợ giúp");
		builder.setMessage("Liên hệ với chúng tôi để được trợ giúp ngay và luôn ?");
		builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				MyPhoneListener phoneListener = new MyPhoneListener();
				TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext()
						.getSystemService(Context.TELEPHONY_SERVICE);
				// receive notifications of telephony state changes
				telephonyManager.listen(phoneListener,
						PhoneStateListener.LISTEN_CALL_STATE);

				String uri = "tel:" + "0979579283";
				Intent callIntent = new Intent(Intent.ACTION_CALL, Uri
						.parse(uri));

				startActivity(callIntent);

			}
		});

		builder.setPositiveButton("Không",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		builder.create();
		builder.show();

		MainActivity.mDrawerLayout.closeDrawer(GravityCompat.START);
	}

	private class MyPhoneListener extends PhoneStateListener {

		private boolean onCall = false;

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {

			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:
				// phone ringing...
				Toast.makeText(MainActivity.this,
						incomingNumber + " calls you", Toast.LENGTH_LONG)
						.show();
				break;

			case TelephonyManager.CALL_STATE_OFFHOOK:
				// one call exists that is dialing, active, or on hold
				Toast.makeText(MainActivity.this, "Hãy chờ trong giây lát ...",
						Toast.LENGTH_LONG).show();
				// because user answers the incoming call
				onCall = true;
				break;

			case TelephonyManager.CALL_STATE_IDLE:
				// in initialization of the class and at the end of phone call

				// detect flag from CALL_STATE_OFFHOOK
				if (onCall == true) {
					Toast.makeText(MainActivity.this, "Đang chuyển hướng...",
							Toast.LENGTH_LONG).show();

					// restart our application
					Intent restart = getBaseContext().getPackageManager()
							.getLaunchIntentForPackage(
									getBaseContext().getPackageName());
					restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(restart);

					onCall = false;
				}
				break;
			default:
				break;
			}

		}
	}
	
	public void doSetting() {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());

		if (sharedPreferences.getBoolean("theme", false)) {
			isCheckedTheme = true;
		}else{
			isCheckedTheme = false;
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		doSetting();
	}

}
