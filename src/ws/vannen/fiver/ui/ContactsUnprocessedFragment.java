package ws.vannen.fiver.ui;

import ws.vannen.fiver.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class ContactsUnprocessedFragment extends SherlockFragment {

	
	
	private View view = null;
	private ListView listViewContactsUnprocessed = null;
	private TextView textViewEmptyList = null;
	
	private String absMenuProcess = "Process";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		view = inflater.inflate(R.layout.fragment_contactsunprocessed, container, false);
		return view;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	
		setHasOptionsMenu(true);
		textViewEmptyList = (TextView)view.findViewById(R.id.textViewEmptyListViewUnprocessed);
		listViewContactsUnprocessed = (ListView)view.findViewById(R.id.listViewContactsUnprocessed);
		listViewContactsUnprocessed.setAdapter(MainFragmentActivity.contactUnprocessedAdapter);
		
		listViewContactsUnprocessed.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listViewContactsUnprocessed.setClickable(true);
		  
		
		listViewContactsUnprocessed.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
			
				
				MainFragmentActivity.unProcessedContacts.get(position)
									.setSelectedToProcess(!MainFragmentActivity
															.unProcessedContacts.get(position).getSelectedToProcess());
				MainFragmentActivity.contactUnprocessedAdapter.notifyDataSetChanged();
				
			}
		});
		
		listViewContactsUnprocessed.setAdapter(MainFragmentActivity.contactUnprocessedAdapter);
		listViewContactsUnprocessed.setEmptyView(textViewEmptyList);
		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.add(absMenuProcess).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		String itemTitle = item.getTitle().toString();
		
		if(itemTitle.equals(absMenuProcess)){
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

}
