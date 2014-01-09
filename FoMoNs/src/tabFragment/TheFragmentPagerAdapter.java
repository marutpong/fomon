package tabFragment;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TheFragmentPagerAdapter extends FragmentPagerAdapter {
 
  final int TOTAL_PAGINAS = 4;
  private static ArrayList<Fragment> allFragment = new ArrayList<Fragment>();
  public TheFragmentPagerAdapter(FragmentManager fm) {
	  super(fm);
	  if (allFragment.isEmpty()){
		 	allFragment.add(new HomeFragment());
		 	allFragment.add(new HistogramFragment());
		 	allFragment.add(new HistoryFragment());
		 	allFragment.add(new QuestFragment());
		 	allFragment.add(new SettingFragment());
		 	allFragment.add(new ServerFragment());
	  }
    // TODO Auto-generated constructor stub
  }

  @Override
  public Fragment getItem(int position) {
		Fragment theFragment = allFragment.get(position);
	    //Bundle data = new Bundle();
	    //data.putInt("pagina_atual", position + 1);  
	    //theFragment.setArguments(data);
	    return theFragment;
  }

  @Override
  public int getCount() {
    //return TOTAL_PAGINAS;
	  return allFragment.size();
  }
  @Override
  public CharSequence getPageTitle(int position) {
	  String classname = allFragment.get(position).getClass().getSimpleName();
	  classname = classname.replace("Fragment", "");
	  return classname;
  }

}
