package ws.vannen.fiver.ui;

import java.util.ArrayList;
import java.util.List;

import ws.vannen.fiver.R;
import ws.vannen.fiver.data.Contact;
import ws.vannen.fiver.data.Contact.PhoneNumberType;
import ws.vannen.fiver.data.adapter.ContactAdapter;
import ws.vannen.fiver.data.adapter.ContactPageAdapter;
import ws.vannen.fiver.utils.MruPhoneNumberUtils;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.viewpagerindicator.TabPageIndicator;

public class MainFragmentActivity extends SherlockFragmentActivity
	implements LoaderManager.LoaderCallbacks<Cursor>{
	
	private ContactPageAdapter contactPageAdapter = null;
	private FragmentManager fragmentManager = null;
	private ViewPager viewPager = null;
	private ProgressDialog pDialog = null;
	
	public static List<Contact> unProcessedContacts = new ArrayList<Contact>();
	public static List<Contact> processedContacts = new ArrayList<Contact>();
	
	public static ContactAdapter contactUnprocessedAdapter = null;
	public static ContactAdapter contactProcessedAdapter = null;
	
	@Override
	protected void onCreate(Bundle bundle) {
		setTheme(R.style.Theme_Fiver);
		super.onCreate(bundle);
		setContentView(R.layout.fragmentactivity_main);
		
		ArrayList<Contact> t = new ArrayList<Contact>();
		t.add(new Contact("1", "hhe", "ewre"));
		t.add(new Contact("2", "hhe", "ewre"));
		
		fragmentManager = getSupportFragmentManager();
		List<Fragment> allFragments = new ArrayList<Fragment>();
		allFragments.add(new ContactsUnprocessedFragment());
		allFragments.add(new ContactsProcessedFragment());

		
		contactPageAdapter = new ContactPageAdapter(fragmentManager, allFragments);
		
		viewPager = (ViewPager)findViewById(R.id.viewPagerMain);
		viewPager.setAdapter(contactPageAdapter);
		
		TabPageIndicator tabPageIndicator = (TabPageIndicator)findViewById(R.id.tabPageIndicator);
		tabPageIndicator.setViewPager(viewPager);
		//tabPageIndicator.set
		//tabPageIndicator.set
		
		contactUnprocessedAdapter = new ContactAdapter(this, unProcessedContacts);
		
		getSupportLoaderManager().initLoader(0, null, this);
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
			pDialog = ProgressDialog.show(MainFragmentActivity.this, "", "Retrieving contacts");
		}
		
		baseUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

		
        return new CursorLoader(MainFragmentActivity.this, baseUri,
                CONTACTS_SUMMARY_PROJECTION, null, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		Log.e("test", data.getCount() + "");
		try {
			
			processedContacts.clear();
			unProcessedContacts.clear();
			
			if(contactUnprocessedAdapter != null){
				contactUnprocessedAdapter.notifyDataSetChanged();
			}
			
			while(data.moveToNext()){
				
				Contact contact = new Contact(data.getString(0),data.getString(1),data.getString(2));
				
				String contactPhoneNumber = data.getString(2);
				
				if(MruPhoneNumberUtils.isMobilePhoneNumber(contactPhoneNumber)){
					contact.setPhoneNumberType(MruPhoneNumberUtils.detectPhoneNumber(contactPhoneNumber));
					contact.setNewPhoneNumber(data.getString(2).replaceAll(
							MruPhoneNumberUtils.patternMauritianPhoneNumberThreeDigitsReplace, "5$0"));
					
					if(contact.getPhoneNumberType() != PhoneNumberType.Unknown){
						contact.setSelectedToProcess(true);
						unProcessedContacts.add(contact);
					}
					
					
				//}else if(MruPhoneNumberUtils.isPhoneNumber(contactPhoneNumber)){
				//	contact.setPhoneNumberType(PhoneNumberType.Unknown);
				//	contact.setNewPhoneNumber(data.getString(2).replaceAll(
				//			MruPhoneNumberUtils.patternMauritianPhoneNumberThreeDigitsReplace, "5$0"));
					//unProcessedContacts.add(contact);
					
				}else if(MruPhoneNumberUtils.isProcessedMobilePhoneNumber(contactPhoneNumber)){
					contact.setPhoneNumberType(PhoneNumberType.Mtml);
					processedContacts.add(contact);
				}
				
				Log.d("Debug", data.getString(0));
				Log.d("Debug", data.getString(1));
				Log.d("Debug", data.getString(2));
					
			}
			contactUnprocessedAdapter.notifyDataSetChanged();
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
		contactUnprocessedAdapter.notifyDataSetInvalidated();
		
	}

}
