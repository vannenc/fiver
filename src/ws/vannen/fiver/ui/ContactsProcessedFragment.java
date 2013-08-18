package ws.vannen.fiver.ui;

import ws.vannen.fiver.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class ContactsProcessedFragment extends SherlockFragment {
	
	private View view = null;
	private TextView textViewEmptyList = null;
	private ListView listViewContactsProcessed = null;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_contactsprocessed, container, false);
		return view;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		setHasOptionsMenu(true);
		//Log.d("stuffs", b.getParcelableArrayList("stuffs").toString());
		//ArrayList<Contact> all = b.getParcelableArrayList("stuffs");
		
		textViewEmptyList = (TextView)view.findViewById(R.id.textViewEmptyListViewProcessed);
		listViewContactsProcessed = (ListView)view.findViewById(R.id.listViewContactsProcessed);
		//processedContactsAdapter = new ContactAdapter(getActivity(), all);
		listViewContactsProcessed.setAdapter(MainFragmentActivity.contactProcessedAdapter);
		listViewContactsProcessed.setEmptyView(textViewEmptyList);
		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.add("Undo").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	}
}
