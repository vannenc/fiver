package ws.vannen.fiver.data.adapter;

import ws.vannen.fiver.R;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

public class ContactCursorAdapter extends CursorAdapter {
	
	static class ViewHolder{
		   String strContactId;
		   TextView textViewDisplayName;
		   TextView textViewPhoneNumber;
		   CheckBox checkboxSelected;
	}

	@SuppressWarnings("unused")
	private static String TAG = "ContactCursorAdapter";
	private static int POSITION_ID = 0;
	private static int POSITION_DISPLAYNAME = 1;
	private static int POSITION_PHONENUMBER = 2;
	//private ;

	public ContactCursorAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		
		ViewHolder viewHolder = (ViewHolder) view.getTag();
		
		if(viewHolder == null){
			viewHolder = new ViewHolder();
			
			//store references in viewholder
			viewHolder.textViewDisplayName = (TextView)view.findViewById(R.id.textViewDisplayName);
			viewHolder.textViewPhoneNumber = (TextView)view.findViewById(R.id.textViewPhoneNumber);
			viewHolder.checkboxSelected = (CheckBox)view.findViewById(R.id.checkBoxSelected);
			
			view.setTag(viewHolder);
		}
		
		viewHolder.strContactId = cursor.getString(POSITION_ID);
		viewHolder.textViewDisplayName.setText(cursor.getString(POSITION_DISPLAYNAME));
		viewHolder.textViewPhoneNumber.setText(cursor.getString(POSITION_PHONENUMBER));	
		
		viewHolder.checkboxSelected.setFocusable(false);
		
	
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		//inflate layout
		return LayoutInflater.from(context).inflate(R.layout.listitem_contact, parent,false);
	}

}
