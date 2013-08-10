package ws.vannen.fiver.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import ws.vannen.fiver.R;
import ws.vannen.fiver.app.CoreApp;
import ws.vannen.fiver.data.Contact;
import ws.vannen.fiver.data.Contact.PhoneNumberType;
import ws.vannen.fiver.data.adapter.ContactAdapter;
import ws.vannen.fiver.utils.MruPhoneNumberUtils;
import android.app.ProgressDialog;
import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class MainActivity extends SherlockFragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {
	
	private static String TAG = "MainActivity";
	
	//adapter (\+230|\+230 )?(4|7|2|8|9)[0-9]{2}( |-)?[0-9]{2}( |-)?[0-9]{2}
	private ListView listViewAllContacts;
	
	private ContactAdapter contactAdapter = null;
	private List<Contact> unProcessedContacts = new ArrayList<Contact>();
	
	//other stuffs
	private ProgressDialog pDialog = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listViewAllContacts = (ListView)findViewById(R.id.listViewUnprocessedContacts);
		
		contactAdapter = new ContactAdapter(this, unProcessedContacts);
		listViewAllContacts.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listViewAllContacts.setClickable(true);
		  
		
		listViewAllContacts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
			
				
				unProcessedContacts.get(position).setSelectedToProcess(!unProcessedContacts.get(position).getSelectedToProcess());
				contactAdapter.notifyDataSetChanged();
				Log.d(TAG, position + "");
				
			}
		});
		
		
		
		
		listViewAllContacts.setAdapter(contactAdapter);
		

		getSupportLoaderManager().initLoader(0, null, this);
		
	
		
		
		//updateContact("1", "888-8888");
	}
	
	
    // These are the Contacts rows that we will retrieve.
    static final String[] CONTACTS_SUMMARY_PROJECTION = new String[] {
    	ContactsContract.CommonDataKinds.Phone._ID,
    	ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
    	ContactsContract.CommonDataKinds.Phone.NUMBER
    };

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Uri baseUri;

		if(pDialog==null){
			pDialog = ProgressDialog.show(MainActivity.this, "", "Retrieving contacts");
		}
		
		baseUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

		
        return new CursorLoader(MainActivity.this, baseUri,
                CONTACTS_SUMMARY_PROJECTION, null, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

		Log.e("test", data.getCount() + "");
		try {
			
			while(data.moveToNext()){
				
				Contact contact = new Contact(data.getString(0),data.getString(1),data.getString(2));
				
				String contactPhoneNumber = data.getString(2);
				
				if(MruPhoneNumberUtils.isMobilePhoneNumber(contactPhoneNumber)){
					contact.setPhoneNumberType(PhoneNumberType.Mobile);
					unProcessedContacts.add(contact);
					
				}else if(MruPhoneNumberUtils.isPhoneNumber(contactPhoneNumber)){
					contact.setPhoneNumberType(PhoneNumberType.Unknown);
					unProcessedContacts.add(contact);
				}
				
				Log.d("Debug", data.getString(0));
				Log.d("Debug", data.getString(1));
				Log.d("Debug", data.getString(2));
					
			}
			contactAdapter.notifyDataSetChanged();
			//data.moveToFirst();
			
			if(pDialog != null && pDialog.isShowing()){
				pDialog.dismiss();
				pDialog = null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		//mContactCursorAdapter.swapCursor(t);
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		//mContactCursorAdapter.swapCursor(null);
		unProcessedContacts.clear();
		contactAdapter.notifyDataSetChanged();
	}
	
	
	public void updateContact (String contactId,  String newNumber) {        


	    ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>(); 
	    String selectPhone = ContactsContract.Data._ID + "=? AND " + ContactsContract.Data.MIMETYPE + "='"  + 
	    		ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'"; 

	            String[] phoneArgs = new String[]{contactId};
	            ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
	                    .withSelection(selectPhone, phoneArgs)
	                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, newNumber)
	                    .build()); 
	            try {
	                getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
	            } catch (RemoteException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            } catch (OperationApplicationException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }       
	    }  

	
}
