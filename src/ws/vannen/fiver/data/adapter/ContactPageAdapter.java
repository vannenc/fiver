package ws.vannen.fiver.data.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ContactPageAdapter extends FragmentPagerAdapter {
	
	//private Context _context;
	private List<Fragment> _fragments;
	
	public ContactPageAdapter(FragmentManager fm, List<Fragment> allFragments) {
		super(fm);
		//this._context = context;
		this._fragments = allFragments;
	}

	@Override
	public Fragment getItem(int position) {
		return _fragments.get(position);
	}

	@Override
	public int getCount() {
		return _fragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return "Page " + position;
	}
}
