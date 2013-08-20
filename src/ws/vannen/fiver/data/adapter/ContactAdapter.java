package ws.vannen.fiver.data.adapter;

import java.util.List;

import ws.vannen.fiver.R;
import ws.vannen.fiver.data.Contact;
import ws.vannen.fiver.data.Contact.PhoneNumberType;
import ws.vannen.fiver.ui.MainFragmentActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ContactAdapter extends BaseAdapter {
	
	static class ViewHolder {
		   String strContactId;
		   TextView textViewDisplayName;
		   TextView textViewPhoneNumber;
		   TextView textViewPhoneNumberTag;
		   TextView textViewNewPhoneNumber;
		   CheckBox checkboxSelected;
		   RelativeLayout relativeLayoutNumberType;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(_context).inflate(R.layout.listitem_contact, parent, false);
			viewHolder.textViewDisplayName = (TextView)convertView.findViewById(R.id.textViewDisplayName);
			viewHolder.textViewPhoneNumber = (TextView)convertView.findViewById(R.id.textViewPhoneNumber);
			viewHolder.textViewPhoneNumberTag = (TextView)convertView.findViewById(R.id.textViewNumberTag);
			viewHolder.checkboxSelected = (CheckBox)convertView.findViewById(R.id.checkBoxSelected);
			viewHolder.relativeLayoutNumberType = (RelativeLayout)convertView.findViewById(R.id.RelativeLayoutNumberType);
			viewHolder.textViewNewPhoneNumber = (TextView)convertView.findViewById(R.id.textViewNewPhoneNumber);
			convertView.setTag(viewHolder);
			
			if(MainFragmentActivity.robotoRegularFontTypeface != null){
				
				viewHolder.textViewDisplayName.setTypeface(MainFragmentActivity.robotoRegularFontTypeface);
				viewHolder.textViewNewPhoneNumber.setTypeface(MainFragmentActivity.robotoRegularFontTypeface);
				viewHolder.textViewPhoneNumber.setTypeface(MainFragmentActivity.robotoRegularFontTypeface);
				viewHolder.textViewPhoneNumberTag.setTypeface(MainFragmentActivity.robotoRegularFontTypeface);
			}
			
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.checkboxSelected.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				arrContacts.get(position).setSelectedToProcess(isChecked);
				
			}
		});
		
		viewHolder.textViewDisplayName.setText(arrContacts.get(position).getDisplayName());
		viewHolder.textViewPhoneNumber.setText(arrContacts.get(position).getPhoneNumber());
		viewHolder.checkboxSelected.setChecked(arrContacts.get(position).getSelectedToProcess());
		
		if(arrContacts.get(position).getAlreadyConverted()){
			viewHolder.textViewNewPhoneNumber.setText(arrContacts.get(position).getOldPhoneNumber());

		}else{
			viewHolder.textViewNewPhoneNumber.setText(arrContacts.get(position).getNewPhoneNumber());
		}
		
		if(arrContacts.get(position).getPhoneNumberType() == PhoneNumberType.Unknown){
			viewHolder.textViewPhoneNumberTag.setText("");
			viewHolder.relativeLayoutNumberType.setBackgroundColor(
					this._context.getResources().getColor(R.color.light_grey));
			
		}else if(arrContacts.get(position).getPhoneNumberType() == PhoneNumberType.Cellplus){
			viewHolder.textViewPhoneNumberTag.setText("");
			viewHolder.relativeLayoutNumberType.setBackgroundColor(
					this._context.getResources().getColor(R.color.orange));

			
		}else if(arrContacts.get(position).getPhoneNumberType() == PhoneNumberType.Emtel){
			viewHolder.textViewPhoneNumberTag.setText("");
			viewHolder.relativeLayoutNumberType.setBackgroundColor(
					this._context.getResources().getColor(R.color.emtel));
			
		}else if(arrContacts.get(position).getPhoneNumberType() == PhoneNumberType.Mtml){
			viewHolder.textViewPhoneNumberTag.setText("");
			viewHolder.relativeLayoutNumberType.setBackgroundColor(
					this._context.getResources().getColor(R.color.mtml));
		}
		
		return convertView;
	}

}
