package com.example.tabcreate;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentB extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.frag2, container, false);
		TextView a = (TextView) v.findViewById(R.id.txt2);
		a.setText("Once upon a time");
		
		return v;
	}

}
