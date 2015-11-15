package com.group1.app.ungdungdoctruyen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class Tab2 extends Fragment {
	View v;
	Button btNewsPaper;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.layout_tab2, container, false);
		setView();
		
		btNewsPaper.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(getActivity(), RssBook_index.class);
//				startActivity(intent);
//				getActivity().overridePendingTransition(R.animator.push_left_in, R.animator.push_left_out);
			}
		});
		return v;
	}
	
	public void setView(){
		btNewsPaper = (Button) v.findViewById(R.id.btBao);
	}

}
