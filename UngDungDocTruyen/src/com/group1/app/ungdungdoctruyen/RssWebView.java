package com.group1.app.ungdungdoctruyen;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class RssWebView extends Activity {
	WebView webView;
	String url;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rss__webview);
		webView = (WebView) findViewById(R.id.webview_rss);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient());

		Intent it = getIntent();
		url = it.getStringExtra("link");
		webView.loadUrl(url);

		// Set nút back ở lable
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rss__webview, menu);
		return true;
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.animator.fadein, R.animator.fadeout);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent myIntent = new Intent(getApplicationContext(),
				EcomicType.class);
		startActivityForResult(myIntent, 0);
		overridePendingTransition(R.animator.right_current, R.animator.right_current_for_back);
		return true;
	}

}
