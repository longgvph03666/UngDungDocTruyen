package com.group1.app.ungdungdoctruyen;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;

public class ActivityErrorNetwork extends Activity {
	BroadcastReceiver broadcastReceiver;
	boolean network = false;
	Button btTry;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_error_network);
		checkNetwork();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_error_network, menu);
		return true;
	}
	
	
	@Override
	public void onBackPressed() {
		Intent it = new Intent(ActivityErrorNetwork.this, MainActivity.class);
		startActivity(it);
		overridePendingTransition(R.animator.fadein, R.animator.fadeout);
	}
	
	public void checkNetwork(){
		broadcastReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connectivityManager
						.getActiveNetworkInfo();

				if (networkInfo != null && networkInfo.isConnected()) {
					network = true;
					finish();
				} 
				
			}
		};

		IntentFilter filter = new IntentFilter();
		filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		registerReceiver(broadcastReceiver, filter);
	}
}
