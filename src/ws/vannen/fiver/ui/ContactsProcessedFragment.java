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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class ContactsProcessedFragment extends SherlockFragment {
	
	private View view = null;
	private TextView textViewEmptyList = null;
	private ListView listViewContactsProcessed = null;
	private String absUndo = "Undo";
	private ProgressDialog pDialog = null;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_contactsprocessed, container, false);
		return view;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
		//Log.d("stuffs", b.getParcelableArrayList("stuffs").toString());
		//ArrayList<Contact> all = b.getParcelableArrayList("stuffs");
		
		textViewEmptyList = (TextView)view.findViewById(R.id.textViewEmptyListViewProcessed);
		listViewContactsProcessed = (ListView)view.findViewById(R.id.listViewContactsProcessed);
		//processedContactsAdapter = new ContactAdapter(getActivity(), all)
		
		listViewContactsProcessed.setEmptyView(textViewEmptyList);
		listViewContactsProcessed.setAdapter(MainFragmentActivity.contactProcessedAdapter);
		
		listViewContactsProcessed.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listViewContactsProcessed.setClickable(true);
		  
		
		listViewContactsProcessed.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
			
				
				MainFragmentActivity.processedContacts.get(position)
									.setSelectedToProcess(!MainFragmentActivity
															.processedContacts.get(position).getSelectedToProcess());
				MainFragmentActivity.contactProcessedAdapter.notifyDataSetChanged();
				
			}
		});
		
		
		listViewContactsProcessed.setEmptyView(textViewEmptyList);
		listViewContactsProcessed.setAdapter(MainFragmentActivity.contactProcessedAdapter);
		
		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.add(absUndo).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String itemTitle = item.getTitle().toString();
		
		if(itemTitle.equals(absUndo)){
			new RevertContactBackOldFormatAsyncTask().execute();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	private class RevertContactBackOldFormatAsyncTask extends AsyncTask<Void, Void, Void>{
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = ProgressDialog.show(getActivity(), "", "Changing back to old format...");
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
		    ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>(); 
		    String selectPhone = ContactsContract.Data._ID + "=? AND " + ContactsContract.Data.MIMETYPE + "='"  + 
		    		ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'"; 
		    
		    for(int i =0; i < MainFragmentActivity.processedContacts.size();i++){
		    	
		    	if(MainFragmentActivity.processedContacts.get(i).getSelectedToProcess() == false){
		    		continue;
		    	}
		    	
	            String[] phoneArgs = new String[]{MainFragmentActivity
	            									.processedContacts
	            									.get(i)
	            									.get_id()};
	            ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
	                    .withSelection(selectPhone, phoneArgs)
	                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MainFragmentActivity
								.processedContacts
								.get(i)
								.getOldPhoneNumber())
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
