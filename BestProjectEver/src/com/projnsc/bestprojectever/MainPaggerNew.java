package com.projnsc.bestprojectever;

import tabFragment.*;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

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
		
		if (getIntent().getExtras()!=null && getIntent().getExtras().getBoolean("isFromEvolution")) {
			new AlertDialog.Builder(this)
					.setTitle("Congratulation")
					.setMessage(
							"You pet has change its form but you still have to take care of it")
					.setPositiveButton("OK", null).show();
		}
		
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
		fragments.add(Fragment.instantiate(this, HistogramFragment.class.getName()));
		fragments.add(Fragment.instantiate(this, HistoryFragment.class.getName()));
		fragments.add(Fragment.instantiate(this, QuestFragment.class.getName()));
		fragments.add(Fragment.instantiate(this, SettingFragment.class.getName()));
		fragments.add(Fragment.instantiate(this, ServerFragment.class.getName()));
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
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	    View tabView = mTabHost.getTabWidget().getChildAt(position);
	    if (tabView != null)
	    {
	        final int width = horizontalScroll.getWidth();
	        final int scrollPos = tabView.getLeft() - (width - tabView.getWidth()) / 2;
	        horizontalScroll.smoothScrollTo(scrollPos, 0);
	    } else {
	    	horizontalScroll.smoothScrollBy(positionOffsetPixels, 0);
	    	//horizontalScroll.scrollBy(positionOffsetPixels, 0);
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
