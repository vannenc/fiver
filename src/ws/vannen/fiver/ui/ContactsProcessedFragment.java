package ws.vannen.fiver.ui;

import ws.vannen.fiver.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class ContactsProcessedFragment extends SherlockFragment {
	
	private View view;
	private ListView listViewContactsProcessed;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_contactsprocessed, container, false);
		return view;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
	}

}
