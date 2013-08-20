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
import android.graphics.Typeface;
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
	
	public static Typeface robotoFontTypeface = null;
	public static int totalEmtelContacts = 0;
	public static int totalMtmlContacts = 0;
	public static int totalCellplusContacts = 0;
	
	private StartScreenFragment startScreenFragment = new StartScreenFragment();
	
	
	@Override
	protected void onCreate(Bundle bundle) {
		setTheme(R.style.Theme_Fiver);
		super.onCreate(bundle);
		setContentView(R.layout.fragmentactivity_main);
		
		robotoFontTypeface = Typeface.createFromAsset(getAssets(), "font/Roboto-Light.ttf");
		
		ArrayList<Contact> t = new ArrayList<Contact>();
		t.add(new Contact("1", "hhe", "ewre"));
		t.add(new Contact("2", "hhe", "ewre"));
		
		fragmentManager = getSupportFragmentManager();
		List<Fragment> allFragments = new ArrayList<Fragment>();
		
		allFragments.add(startScreenFragment);
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
		contactProcessedAdapter = new ContactAdapter(this, processedContacts);
		
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

		try {
			
			processedContacts.clear();
			unProcessedContacts.clear();
			
			totalEmtelContacts = 0;
			totalMtmlContacts = 0;
			totalCellplusContacts = 0;
			
			if(contactUnprocessedAdapter != null){
				contactUnprocessedAdapter.notifyDataSetChanged();
			}
			
			while(data.moveToNext()){
				
				Contact contact = new Contact(data.getString(0),data.getString(1),data.getString(2));
				
				String contactPhoneNumber = data.getString(2);
				
				if(MruPhoneNumberUtils.isMobilePhoneNumber(contactPhoneNumber)){
					
					PhoneNumberType phoneNumberType = MruPhoneNumberUtils.detectPhoneNumber(contactPhoneNumber);
					contact.setPhoneNumberType(phoneNumberType);
					contact.setNewPhoneNumber(data.getString(2).replaceAll(
							MruPhoneNumberUtils.patternPhoneNumberConvert, "5$0"));
					
					switch (phoneNumberType) {
						case Emtel:
							totalEmtelContacts +=1;
							break;
							
						case Cellplus:
							totalCellplusContacts +=1;
							break;
							
						case Mtml:
							totalMtmlContacts += 1;
							break;
					}
					
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
					contact.setPhoneNumberType(MruPhoneNumberUtils.detectConvertedPhoneNumber(contactPhoneNumber));
					contact.setAlreadyConverted(true);
					contact.setOldPhoneNumber(contactPhoneNumber.replaceAll(MruPhoneNumberUtils.patternPhoneNumberRevertOldFormat, ""));
					processedContacts.add(contact);
				}
				
				Log.d("Debug", data.getString(0));
				Log.d("Debug", data.getString(1));
				Log.d("Debug", data.getString(2));
				
				
					
			}
			contactUnprocessedAdapter.notifyDataSetChanged();
			contactProcessedAdapter.notifyDataSetChanged();
			startScreenFragment.updateNumbers();
			//data.moveToFirst();
			Log.e("test rpocessed", processedContacts.size() + "");
			
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
		processedContacts.clear();
		contactProcessedAdapter.notifyDataSetChanged();
		contactUnprocessedAdapter.notifyDataSetInvalidated();
		
	}

}
