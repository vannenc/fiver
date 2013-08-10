package ws.vannen.fiver.ui;

import ws.vannen.fiver.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class ContactsUnprocessedFragment extends SherlockFragment {

	
	public static ContactsUnprocessedFragment newInstance(String test){
		
		ContactsUnprocessedFragment newFragment = new ContactsUnprocessedFragment();
		Bundle b = new Bundle();
		b.putString("test", test);
		
		newFragment.setArguments(b);
		
		return newFragment;
	}
	
	private View view = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
	
		view = inflater.inflate(R.layout.fragment_contactsunprocessed, container, false);
		return view;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		Bundle b = getArguments();
		TextView t1 = (TextView)view.findViewById(R.id.textView1);
		
		if(b.getString("test")!=null){
			t1.setText(b.getString("test"));
		}
	}
	
	
	

}
