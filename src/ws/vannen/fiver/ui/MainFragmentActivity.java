package ws.vannen.fiver.ui;

import java.util.ArrayList;
import java.util.List;

import ws.vannen.fiver.data.Contact;
import ws.vannen.fiver.data.adapter.ContactPageAdapter;
import ws.vannen.fiver.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.viewpagerindicator.TabPageIndicator;

public class MainFragmentActivity extends SherlockFragmentActivity {
	
	private ContactPageAdapter contactPageAdapter = null;
	private FragmentManager fragmentManager = null;
	private ViewPager viewPager = null;
	
	@Override
	protected void onCreate(Bundle bundle) {
		setTheme(R.style.Theme_Fiver);
		super.onCreate(bundle);
		setContentView(R.layout.fragmentactivity_main);
		
		ArrayList<Contact> t = new ArrayList<Contact>();
		t.add(new Contact("1", "hhe", "ewre"));
		t.add(new Contact("2", "hhe", "ewre"));
		
		fragmentManager = getSupportFragmentManager();
		List<Fragment> allFragments = new ArrayList<Fragment>();
		allFragments.add(ContactsUnprocessedFragment.newInstance(t));
		allFragments.add(ContactsUnprocessedFragment.newInstance(t));

		
		contactPageAdapter = new ContactPageAdapter(fragmentManager, allFragments);
		
		viewPager = (ViewPager)findViewById(R.id.viewPagerMain);
		viewPager.setAdapter(contactPageAdapter);
		
		TabPageIndicator tabPageIndicator = (TabPageIndicator)findViewById(R.id.tabPageIndicator);
		tabPageIndicator.setViewPager(viewPager);
		//tabPageIndicator.set
		//tabPageIndicator.set
	}

}
