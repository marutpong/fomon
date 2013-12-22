package tabFragment;

import histogramDraw.HistogramModule;
import histogramDraw.HistogramPanel;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.projnsc.bestprojectever.R;

public class HistogramFragment extends Fragment {

	View mView;
	HistogramPanel mHPanel;
	HistogramModule mHModule;
	Button btnModeChange;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.hist_show_fragment, container, false);
		
		btnModeChange = (Button) mView.findViewById(R.id.btnChMode);
		mHPanel = (HistogramPanel) mView.findViewById(R.id.histogramPanel1);
		mHModule = new HistogramModule();
		mHModule.setOnStateChangeListener(mHPanel);
		btnModeChange.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mHModule.toggleSTATUS();
			}
		});
		
		return mView;
	}

}
