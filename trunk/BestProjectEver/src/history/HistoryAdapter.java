package history;

import java.io.File;

import textGetter.PetDBox;

import com.projnsc.bestprojectever.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public abstract class HistoryAdapter extends BaseAdapter implements ListAdapter {
	final int THUMBNAIL_SIZE = 84;

	private class NumHolder {
		public TextView list_foodName;
		public TextView list_DateTime;
		public ImageView showImgThumbnail;
	}

	private Context mContext;

	public HistoryAdapter(Context context) {
		mContext = context;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressLint("UseValueOf")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		NumHolder myHolder = new NumHolder();
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.activity_history_list, parent, false);
			myHolder.list_foodName = (TextView) convertView
					.findViewById(R.id.txtFoodname);
			myHolder.list_DateTime = (TextView) convertView
					.findViewById(R.id.txtDate);
			myHolder.showImgThumbnail = (ImageView) convertView
					.findViewById(R.id.imageThumbnail);
			convertView.setTag(myHolder);
		} else {
			myHolder = (NumHolder) convertView.getTag();
		}

		PetDBox NumBox = (PetDBox) getItem(position);
		myHolder.list_foodName.setText(NumBox.getFoodType() + "");
		File imgFile = new File(NumBox.getPicturePath());
		if (imgFile.exists()) {

			try {

				Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
						.getAbsolutePath());
				Float width = new Float(myBitmap.getWidth());
				Float height = new Float(myBitmap.getHeight());
				Float ratio = height / width;
				myBitmap = Bitmap.createScaledBitmap(myBitmap, THUMBNAIL_SIZE,
						(int) (THUMBNAIL_SIZE * ratio), false);
				Toast.makeText(mContext,
						String.valueOf(myBitmap.getHeight()) + "",
						Toast.LENGTH_LONG).show();
				myHolder.showImgThumbnail.setImageBitmap(myBitmap);
			} catch (Exception ex) {
				
			}
		}
		
		myHolder.list_DateTime.setText(NumBox.getDay() + "/" + NumBox.getMonth() + "/" + NumBox.getYear() + " " + NumBox.getHourString()+ ":" + NumBox.getMinutedString());

		return convertView;
	}

}
