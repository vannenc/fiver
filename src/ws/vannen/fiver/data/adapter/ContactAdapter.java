package ws.vannen.fiver.data.adapter;

import java.util.List;

import ws.vannen.fiver.R;
import ws.vannen.fiver.data.Contact;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContactAdapter extends BaseAdapter {
	
	static class ViewHolder {
		  TextView textViewDisplayName;
		  TextView textViewPhoneNumber;
	}
	
	private List<Contact> arrContacts;
	private Context _context;
	
	public ContactAdapter(Context context, List<Contact> contacts){
		this._context = context;
		this.arrContacts = contacts;
	}

	@Override
	public int getCount() {
		return arrContacts.size();
	}

	@Override
	public Contact getItem(int id) {
		return arrContacts.get(id);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		//TextView textViewDisplayName = null;
		///TextView textViewPhoneNumber = null;
		
		
		ViewHolder viewHolder = null;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(_context).inflate(R.layout.listitem_contact, parent, false);
			viewHolder.textViewDisplayName = (TextView)convertView.findViewById(R.id.textViewDisplayName);
			viewHolder.textViewPhoneNumber = (TextView)convertView.findViewById(R.id.textViewPhoneNumber);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.textViewDisplayName.setText(arrContacts.get(position).getDisplayName());
		viewHolder.textViewPhoneNumber.setText(arrContacts.get(position).getPhoneNumber());
		
		return convertView;
	}

}
