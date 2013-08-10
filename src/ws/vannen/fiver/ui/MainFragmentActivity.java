package ws.vannen.fiver.ui;

import java.util.ArrayList;
import java.util.List;

import ws.vannen.fiver.R;
import ws.vannen.fiver.data.adapter.ContactPageAdapter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.viewpagerindicator.TitlePageIndicator;

public class MainFragmentActivity extends SherlockFragmentActivity {
	
	private ContactPageAdapter contactPageAdapter = null;
	private FragmentManager fragmentManager = null;
	private ViewPager viewPager = null;
	
	@Override
	protected void onCreate(Bundle bundle) {
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
		super.onCreate(bundle);
		setContentView(R.layout.fragmentactivity_main);
		
		fragmentManager = getSupportFragmentManager();
		List<Fragment> allFragments = new ArrayList<Fragment>();
		allFragments.add(ContactsUnprocessedFragment.newInstance("Page1"));
		allFragments.add(ContactsUnprocessedFragment.newInstance("Page2"));
		allFragments.add(ContactsUnprocessedFragment.newInstance("Page3"));
		
		contactPageAdapter = new ContactPageAdapter(fragmentManager, allFragments);
		
		viewPager = (ViewPager)findViewById(R.id.viewPagerMain);
		viewPager.setAdapter(contactPageAdapter);
		
		TitlePageIndicator titleIndicator = (TitlePageIndicator)findViewById(R.id.titles);
		titleIndicator.setViewPager(viewPager);
		titleIndicator.setTextColor(Color.RED);
		titleIndicator.setSelectedColor(Color.GREEN);
	}

}
