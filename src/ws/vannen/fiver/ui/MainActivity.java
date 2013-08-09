package ws.vannen.fiver.ui;

import java.util.ArrayList;
import java.util.List;

import ws.vannen.fiver.R;
import ws.vannen.fiver.data.Contact;
import ws.vannen.fiver.data.adapter.ContactAdapter;
import ws.vannen.fiver.data.adapter.ContactCursorAdapter;
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
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class MainActivity extends SherlockFragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {
	
	private static String TAG = "MainActivity";
	
	//adapter
	private ListView listViewAllContacts;
	
	private ContactAdapter contactAdapter = null;
	private List<Contact> arrContacts = new ArrayList<Contact>();
	private ContactCursorAdapter mContactCursorAdapter;
	
	//other stuffs
	private ProgressDialog pDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listViewAllContacts = (ListView)findViewById(R.id.listView1);

		
		
		mContactCursorAdapter = new ContactCursorAdapter(this, null, CursorAdapter.NO_SELECTION);
		
		contactAdapter = new ContactAdapter(this, arrContacts);
		
		listViewAllContacts.setAdapter(mContactCursorAdapter);
		listViewAllContacts.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listViewAllContacts.setClickable(true);
		  
		//arrContacts.add(new Contact("1", "john Lennon", "21321321"));
		//arrContacts.add(new Contact("2", "john Lennon2", "21321321"));
		//contactAdapter.notifyDataSetChanged();
		
		listViewAllContacts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				
				
				CheckBox c = (CheckBox)view.findViewById(R.id.checkBoxSelected);
				c.toggle();
				
				Log.d(TAG, position + "");
				
			}
		});
		
		
		
		
		//listViewAllContacts.setAdapter(contactAdapter);
		
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
		
/*		if(mCurFiter != null){
			baseUri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI, Uri.encode(mCurFiter));
		}else{
			baseUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		}*/
		
		if(pDialog==null){
			pDialog = ProgressDialog.show(MainActivity.this, "", "Retrieving contacts");
		}
		
		baseUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String select = "((" + Contacts.DISPLAY_NAME + " NOTNULL) AND ("
                + Contacts.HAS_PHONE_NUMBER + "=1) AND ("
                + Contacts.DISPLAY_NAME + " != '' ))";
		
        return new CursorLoader(MainActivity.this, baseUri,
                CONTACTS_SUMMARY_PROJECTION, null, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		Log.e("test", data.getCount() + "");
		Cursor t = data;
	
		try {
			
			while(data.moveToNext()){
				
				arrContacts.add(new Contact(data.getString(0),data.getString(1),data.getString(2)));
				Log.d("Debug", data.getString(0));
				Log.d("Debug", data.getString(1));
				Log.d("Debug", data.getString(2));
				
				
			}
			contactAdapter.notifyDataSetChanged();
			//data.moveToFirst();
			
			if(pDialog != null && pDialog.isShowing()){
				pDialog.dismiss();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		mContactCursorAdapter.swapCursor(t);
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		mContactCursorAdapter.swapCursor(null);
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
