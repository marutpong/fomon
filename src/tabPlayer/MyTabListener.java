package tabPlayer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;

public class MyTabListener implements ActionBar.TabListener {
	private Fragment mFragment;
	private final Activity mActivity;
	private final String mFragName;

	public MyTabListener(Activity activity, String fragName) {
		mActivity = activity;
		mFragName = fragName;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		mFragment = Fragment.instantiate(mActivity, mFragName);
		ft.add(android.R.id.content, mFragment);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		ft.remove(mFragment);
		mFragment = null;
	}
}
