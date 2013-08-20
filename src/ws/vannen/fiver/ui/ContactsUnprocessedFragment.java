package ws.vannen.fiver.ui;

import java.util.ArrayList;

import ws.vannen.fiver.R;
import android.app.ProgressDialog;
import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
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
	private ProgressDialog pDialog = null;
	
	private String absMenuProcess = "Convert";
	private String absMenuSelectAll = "Select all";
	private String absMenuSelectNone = "Select none";
	
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
		menu.add(absMenuSelectAll).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
		menu.add(absMenuSelectNone).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		String itemTitle = item.getTitle().toString();
		
		if(itemTitle.equals(absMenuProcess)){
			new ProcessContactsAsyncTask().execute();
			return true;
		}
		
		if(itemTitle.equals(absMenuSelectAll)){
			
			int contactSize = MainFragmentActivity.unProcessedContacts.size();
			
			if(contactSize > 0){
				
				for(int i = 0; i <contactSize; i++){
					
				}
			}
			return true;
		}
		
		if(itemTitle.equals(absMenuSelectNone)){
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
	private class ProcessContactsAsyncTask extends AsyncTask<Void, Void, Void>{
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			pDialog = ProgressDialog.show(getActivity(), "", "Converting...");
			
		}
		
		
		@Override
		protected Void doInBackground(Void... params) {
			
		    ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>(); 
		    String selectPhone = ContactsContract.Data._ID + "=? AND " + ContactsContract.Data.MIMETYPE + "='"  + 
		    		ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'"; 
		    
		    for(int i =0; i < MainFragmentActivity.unProcessedContacts.size();i++){
		    	
		    	if(MainFragmentActivity.unProcessedContacts.get(i).getSelectedToProcess() == false){
		    		continue;
		    	}
		    	
	            String[] phoneArgs = new String[]{MainFragmentActivity
	            									.unProcessedContacts
	            									.get(i)
	            									.get_id()};
	            ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
	                    .withSelection(selectPhone, phoneArgs)
	                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MainFragmentActivity
								.unProcessedContacts
								.get(i)
								.getNewPhoneNumber())
	                    .build()); 
		    	
		    }

	        
		    try {
	            getActivity().getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
	        } catch (RemoteException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (OperationApplicationException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }       

			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			if(pDialog != null && pDialog.isShowing()){
				pDialog.dismiss();
				pDialog = null;
			}
		}
	}


}
