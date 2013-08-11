package ws.vannen.fiver.ui;

import java.util.ArrayList;

import ws.vannen.fiver.R;
import ws.vannen.fiver.data.Contact;
import ws.vannen.fiver.data.adapter.ContactAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class ContactsUnprocessedFragment extends SherlockFragment {

	
	public static ContactsUnprocessedFragment newInstance(ArrayList<Contact> allContacts){
		
		ContactsUnprocessedFragment newFragment = new ContactsUnprocessedFragment();
		Bundle b = new Bundle();
		
		b.putParcelableArrayList("SDfds", allContacts);
		
		newFragment.setArguments(b);
		
		return newFragment;
	}
	
	private View view = null;
	private ListView listViewContactsUnprocessed = null;
	private ContactAdapter unprocessedContactsAdapter = null;
	
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
		
		Log.d("ASDASD", b.getParcelableArrayList("SDfds").toString());
		ArrayList<Contact> all = b.getParcelableArrayList("SDfds");
		
		listViewContactsUnprocessed = (ListView)view.findViewById(R.id.listViewContactsUnprocessed);
		unprocessedContactsAdapter = new ContactAdapter(getActivity(), all);
		
		listViewContactsUnprocessed.setAdapter(unprocessedContactsAdapter);
		
	}
	
	
	

}
