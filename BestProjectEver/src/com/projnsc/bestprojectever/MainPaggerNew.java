package com.projnsc.bestprojectever;

import tabFragment.*;
import historyDatabase.HistoryDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import foodDatabase.FoodDatabase;

import preferenceSetting.PetUniqueDate;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;

/**
 * The <code>TabsViewPagerFragmentActivity</code> class implements the Fragment
 * activity that maintains a TabHost using a ViewPager.
 * 
 * @author jitesh
 */
public class MainPaggerNew extends FragmentActivity implements
		TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

	private TabHost mTabHost;
	private ViewPager mViewPager;
	private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, MainPaggerNew.TabInfo>();
	private PagerAdapterNew mPagerAdapter;
	HorizontalScrollView horizontalScroll;

	/**
	 * 
	 * @author mwho Maintains extrinsic info of a tab's construct
	 */
	private class TabInfo {
		private String tag;
		private Class<?> clss;
		private Bundle args;
		private Fragment fragment;

		TabInfo(String tag, Class<?> clazz, Bundle args) {
			this.tag = tag;
			this.clss = clazz;
			this.args = args;
		}

	}

	/**
	 * A simple factory that returns dummy views to the Tabhost
	 * 
	 * @author mwho
	 */
	class TabFactory implements TabContentFactory {

		private final Context mContext;

		/**
		 * @param context
		 */
		public TabFactory(Context context) {
			mContext = context;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
		 */
		public View createTabContent(String tag) {
			View v = new View(mContext);
			v.setMinimumWidth(0);
			v.setMinimumHeight(0);
			return v;
		}

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Inflate the layout
		setContentView(R.layout.main_pagger_new);
		// Initialise the TabHost
		this.initialiseTabHost(savedInstanceState);
		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab")); // set
																				// the
																				// tab
																				// as
																				// per
																				// the
																				// saved
																				// state
		}
		// Intialise ViewPager
		horizontalScroll = (HorizontalScrollView) findViewById(R.id.horizontalscroll);
		this.intialiseViewPager();

		if (getIntent().getExtras() != null
				&& getIntent().getExtras().getBoolean(
						getString(R.string.intentkey_isfromevolution))) {
			new AlertDialog.Builder(this)
					.setTitle("Congratulation")
					.setMessage(
							"You pet has change its form but you still have to take care of it")
					.setPositiveButton("OK", null).show();
		}

		if (getIntent().getExtras() != null
				&& getIntent().getExtras().getBoolean(
						getString(R.string.intentkey_isfromstatup))) {
			new AlertDialog.Builder(this)
					.setTitle("Congratulation")
					.setMessage(
							"Some Pet stat has increase because of your food. Your pet seem Happy")
					.setPositiveButton("OK", null).show();
		}

		// Set server IP Address
		try {
			if (PetUniqueDate.isServerIPEmpty()) {

				PetUniqueDate.SetServerIP("10.73.10.65");
			}
		} catch (NullPointerException E) {
			PetUniqueDate.setContext(getApplicationContext());
			PetUniqueDate.SetServerIP("10.73.10.65");
		}
		// Initial Food Database
		FoodDatabase.fd = new FoodDatabase(this);
		FoodDatabase.fd.getWritableDatabase();

		FoodDatabase.insertFood(1, "�����ѹ��", 619, 10.9, 28, 80.9, 16, 27,
				213, 1251, 95, 3, 8, 1, 5, 0, 2);
		FoodDatabase.insertFood(2, "������ٷʹ�������", 441, 13.9, 11.2, 71,
				16, 22, 243, 608, 201, 3, 10, 0, 4, 2, 4);
		FoodDatabase.insertFood(3, "���ǫ��", 154, 5.8, 10.6, 8.9, 25, -1, -1,
				-1, 61, 1, 5, 1, 5, 0, 1);
		FoodDatabase.insertFood(4, "����չ��������", 308, 18.9, 5.6, 45.6, 70,
				56, 648, 1862, 162, 3, 9, 0, 4, 0, 2);
		FoodDatabase.insertFood(5, "�������ᴧ", 521, 21.9, 16.5, 71.3, 17, 30,
				473, 1307, 357, 4, 9, 0, 4, 1, 3);
		FoodDatabase.insertFood(6, "���ǼѴ", 581, 22.7, 25.2, 65.8, 37, 28,
				212, 906, 243, 3, 10, 1, 4, 2, 4);
		FoodDatabase.insertFood(7, "�����", 85, 3.7, 2.1, 12.8, 71, -1, -1, -1,
				64, 2, 7, 1, 5, 0, 1);
		FoodDatabase.insertFood(8, "�Ѵ������", 633, 16, 26.8, 81.9, 118, 51,
				594, 1592, 292, 1, 6, 0, 3, 0, 2);
		FoodDatabase.insertFood(9, "��繵��", 381, 17.5, 15.9, 42, 40, 65.8,
				391, 2313, 164, 1, 8, 0, 4, 0, 1);
		FoodDatabase.insertFood(10, "������١�ͺ", 490, 20.4, 21.8, 54.6, -1,
				-1, -1, -1, -1, 2, 6, 0, 1, 0, 1);
		FoodDatabase.insertFood(11, "�����Ҵ������", 469, 24.2, 14.8, 59.9,
				31, 50.6, 379, 1880, 225, 3, 9, 2, 4, 0, 1);
		FoodDatabase.insertFood(12, "��·ʹ", 812, 20.2, 65.6, 35.1, 91, 67,
				284, 1450, 462, 1, 7, 0, 2, 1, 5);
		FoodDatabase.insertFood(13, "���������", 619, 10.9, 28, 80.9, 16, 27,
				213, 1251, 95, 2, 5, 1, 6, 0, 2);
		FoodDatabase.insertFood(14, "�����Ҵ�Ѵ�ѡ", 332, 8.8, 5.9, 60.9, 29,
				26.3, 239, 1352, 100, 3, 12, 0, 3, 2, 4);
		FoodDatabase.insertFood(15, "��ٻ��", 72, 6.8, 2.5, 5.6, -1, -1, -1,
				-1, -1, 1, 4, 0, 1, 0, 1);
		FoodDatabase.insertFood(16, "�Ѵ��", 486, 20.9, 19.9, 55.7, 201, 95,
				471, 1060, 317, 2, 8, 0, 4, 1, 3);
		FoodDatabase.insertFood(17, "���Ǣ����", 152, 6.7, 5.7, 18.5, 236, -1,
				-1, -1, 43, 3, 9, 1, 2, 0, 3);
		FoodDatabase.insertFood(18, "���Ǥ�ء�л�", 565, 20.5, 19.5, 76.7, 147,
				56.7, 519, 1999, 260, 1, 6, 0, 2, 0, 1);
		FoodDatabase.insertFood(19, "��Ѵ�ѡ", 92, 1, 6.4, 7.6, -1, -1, -1, -1,
				-1, 3, 12, 0, 1, 0, 2);
		FoodDatabase.insertFood(20, "�Ҵ˹��", 506, 16.5, 21.8, 60.9, 89, 61.1,
				497, 1753, 190, 2, 7, 0, 3, 1, 3);
		FoodDatabase.insertFood(21, "����", 160, 12.3, 11.7, 1.4, 126, -1,
				-1, -1, 204, 3, 3, 0, 0, 1, 1);

		// Initial History Database
		HistoryDatabase.hd = new HistoryDatabase(this);
		HistoryDatabase.hd.getWritableDatabase();
		HistoryDatabase.SelectAllData();

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onSaveInstanceState(android.os.Bundle)
	 */
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("tab", mTabHost.getCurrentTabTag()); // save the tab
																// selected
		super.onSaveInstanceState(outState);
	}

	/**
	 * Initialise ViewPager
	 */
	private void intialiseViewPager() {

		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(this, HomeFragment.class.getName()));
		fragments.add(Fragment.instantiate(this,
				HistogramFragment.class.getName()));
		fragments.add(Fragment.instantiate(this,
				HistoryFragment.class.getName()));
		fragments
				.add(Fragment.instantiate(this, QuestFragment.class.getName()));
		fragments.add(Fragment.instantiate(this,
				SettingFragment.class.getName()));
		fragments
				.add(Fragment.instantiate(this, ServerFragment.class.getName()));
		this.mPagerAdapter = new PagerAdapterNew(
				super.getSupportFragmentManager(), fragments);
		//
		this.mViewPager = (ViewPager) super.findViewById(R.id.viewpager);
		this.mViewPager.setAdapter(this.mPagerAdapter);
		this.mViewPager.setOnPageChangeListener(this);
	}

	/**
	 * Initialise the Tab Host
	 */
	private void initialiseTabHost(Bundle args) {
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		TabInfo tabInfo = null;
		MainPaggerNew.AddTab(this, this.mTabHost,
				this.mTabHost.newTabSpec("Tab1").setIndicator("Home"),
				(tabInfo = new TabInfo("Tab1", HomeFragment.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		MainPaggerNew.AddTab(this, this.mTabHost,
				this.mTabHost.newTabSpec("Tab2").setIndicator("Histogram"),
				(tabInfo = new TabInfo("Tab2", HistogramFragment.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		MainPaggerNew.AddTab(this, this.mTabHost,
				this.mTabHost.newTabSpec("Tab3").setIndicator("History"),
				(tabInfo = new TabInfo("Tab3", HistoryFragment.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);

		MainPaggerNew.AddTab(this, this.mTabHost,
				this.mTabHost.newTabSpec("Tab4").setIndicator("Quest"),
				(tabInfo = new TabInfo("Tab4", QuestFragment.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);

		MainPaggerNew.AddTab(this, this.mTabHost,
				this.mTabHost.newTabSpec("Tab5").setIndicator("Setting"),
				(tabInfo = new TabInfo("Tab5", SettingFragment.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);

		MainPaggerNew.AddTab(this, this.mTabHost,
				this.mTabHost.newTabSpec("Tab6").setIndicator("Server"),
				(tabInfo = new TabInfo("Tab6", ServerFragment.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		// Default to first tab
		// this.onTabChanged("Tab1");
		//
		mTabHost.setOnTabChangedListener(this);
	}

	/**
	 * Add Tab content to the Tabhost
	 * 
	 * @param activity
	 * @param tabHost
	 * @param tabSpec
	 * @param clss
	 * @param args
	 */
	private static void AddTab(MainPaggerNew activity, TabHost tabHost,
			TabHost.TabSpec tabSpec, TabInfo tabInfo) {
		// Attach a Tab view factory to the spec
		tabSpec.setContent(activity.new TabFactory(activity));
		tabHost.addTab(tabSpec);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.widget.TabHost.OnTabChangeListener#onTabChanged(java.lang.String)
	 */
	public void onTabChanged(String tag) {
		// TabInfo newTab = this.mapTabInfo.get(tag);
		int pos = this.mTabHost.getCurrentTab();
		this.mViewPager.setCurrentItem(pos);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled
	 * (int, float, int)
	 */

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		View tabView = mTabHost.getTabWidget().getChildAt(position);
		if (tabView != null) {
			final int width = horizontalScroll.getWidth();
			final int scrollPos = tabView.getLeft()
					- (width - tabView.getWidth()) / 2;
			horizontalScroll.smoothScrollTo(scrollPos, 0);
		} else {
			horizontalScroll.smoothScrollBy(positionOffsetPixels, 0);
			// horizontalScroll.scrollBy(positionOffsetPixels, 0);
		}
	}

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		this.mTabHost.setCurrentTab(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#
	 * onPageScrollStateChanged(int)
	 */
	@Override
	public void onPageScrollStateChanged(int state) {
		// TODO Auto-generated method stub

	}
}
