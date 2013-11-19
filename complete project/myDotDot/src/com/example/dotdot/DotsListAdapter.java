package com.example.dotdot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public abstract class DotsListAdapter extends BaseAdapter implements ListAdapter{

	private static final class ViewHolder{
		TextView txtCorX;
		TextView txtCorY;
	}
	
	private Context mContext;
	
	public DotsListAdapter(Context mContext) {
		this.mContext = mContext;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = new ViewHolder();
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.dotrow, parent, false);
			viewHolder.txtCorX = (TextView) convertView.findViewById(R.id.txtXcor);
			viewHolder.txtCorY = (TextView) convertView.findViewById(R.id.txtYcor);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		//Update new Row
		Dot dot = (Dot) getItem(position);
		viewHolder.txtCorX.setText(dot.getCorX() + "");
		viewHolder.txtCorY.setText(dot.getCorY() + "");
		
		return convertView;
	}

}
