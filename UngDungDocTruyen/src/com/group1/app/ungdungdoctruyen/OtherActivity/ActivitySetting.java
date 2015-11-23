package com.group1.app.ungdungdoctruyen.OtherActivity;

import com.group1.app.ungdungdoctruyen.R;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

@SuppressLint("NewApi")
public class ActivitySetting extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.activity_setting);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	@Override
	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
		
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.animator.push_down_in, R.animator.push_down_out);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (android.R.id.home == item.getItemId()) {
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}

	

}
