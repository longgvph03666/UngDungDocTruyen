package com.group1.app.ungdungdoctruyen.OtherActivity;

import com.group1.app.ungdungdoctruyen.MainActivity;
import com.group1.app.ungdungdoctruyen.R;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;
import android.widget.Toast;

@SuppressLint("NewApi")
public class ActivitySetting extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.activity_setting);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		MainActivity.mDrawerLayout.closeDrawer(GravityCompat.START);
	}
	@Override
	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
		Toast.makeText(getApplicationContext(), "Đã lưu thay đổi", Toast.LENGTH_SHORT).show();
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
