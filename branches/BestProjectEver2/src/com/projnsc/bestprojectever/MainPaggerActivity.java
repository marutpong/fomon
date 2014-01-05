package com.projnsc.bestprojectever;

import tabFragment.TheFragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class MainPaggerActivity extends FragmentActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_pagger);
  
    ViewPager viewPager = 
       (ViewPager) findViewById(R.id.pager);
  
    FragmentManager fm = getSupportFragmentManager();
  
    TheFragmentPagerAdapter adapter = 
        new TheFragmentPagerAdapter(fm);
    
    
    // Seta o adapter do ViewPager 
    viewPager.setAdapter(adapter);
  
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to 
    // the action bar if it is present.
    getMenuInflater().inflate(R.menu.pet_main, menu);
    return true;
  }

}
