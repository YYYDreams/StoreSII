package com.example.administrator.store3.addressactivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.store3.R;
import com.example.administrator.store3.addressbean.AddressInfo;
import com.example.administrator.store3.addressdb.AddressDB;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class BuyAddress extends Activity implements OnClickListener{

	private EditText jiequText;
	private EditText nameText;
	private EditText phoneText;
	private String provinces;
	private AddressInfo myAddress;
	private TextView shengText;
	private Button postBtn;
	private AddressInfo addressinfo;
	private CheckBox checkBox;
	private ImageView mImageView;
	private LinearLayout mLinearLayout,mLinearLayout1;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xinjianshouhuodizhi);

		myAddress = new AddressInfo();

		Intent i = getIntent();
		provinces = i.getStringExtra("address");
		Bundle b = i.getBundleExtra("address_id");
		mLinearLayout= (LinearLayout) findViewById(R.id.my_set_buyaddress_sheng_linear);
		mLinearLayout.setOnClickListener(this);
		mLinearLayout1= (LinearLayout) findViewById(R.id.contacts);
		mLinearLayout1.setOnClickListener(this);
		shengText = (TextView)findViewById(R.id.my_set_buyaddress_sheng);
		jiequText = (EditText)findViewById(R.id.my_set_buyaddress_jiequ);
		nameText  = (EditText)findViewById(R.id.my_set_buyaddress_name);
		phoneText = (EditText)findViewById(R.id.my_set_buyaddress_phone);
		mImageView= (ImageView) findViewById(R.id.image0);

		checkBox = (CheckBox) findViewById(R.id.my_set_address_checkbox);
		postBtn = (Button)findViewById(R.id.my_set_buyaddress_address_btn);
		if (provinces == null) {
		} else {
			myAddress.setProvinces(provinces);
			shengText.setText(provinces);
		}
		mImageView.setOnClickListener(this);
		postBtn.setOnClickListener(click);


		jiequText.setOnFocusChangeListener(focusChanger);
		nameText.setOnFocusChangeListener(focusChanger);
		phoneText.setOnFocusChangeListener(focusChanger);

		if (b != null) {
			addressinfo = (AddressInfo) b.get("address");

			phoneText.setVisibility(View.VISIBLE);
			phoneText.setText(addressinfo.getPhone());

			shengText.setText(addressinfo.getProvinces());
			jiequText.setText(addressinfo.getStreet());
			nameText.setText(addressinfo.getName());
			phoneText.setText(addressinfo.getPhone());
			jiequText.setVisibility(View.VISIBLE);
			nameText.setVisibility(View.VISIBLE);
			if(addressinfo.isStatus()){
				checkBox.setChecked(true);
			}else{
				checkBox.setChecked(false);
			}
			myAddress.setId(addressinfo.getId());
			myAddress.setProvinces(addressinfo.getProvinces());
			myAddress.setStreet(addressinfo.getStreet());
			myAddress.setName(addressinfo.getName());
			myAddress.setPhone(addressinfo.getPhone());
			myAddress.setStatus(addressinfo.isStatus());
		}

	}
	OnFocusChangeListener focusChanger = new OnFocusChangeListener() {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			myAddress.setStreet(jiequText.getText().toString());
			myAddress.setName(nameText.getText().toString());
			myAddress.setPhone(phoneText.getText().toString());
			switch (v.getId()) {
			case R.id.my_set_buyaddress_jiequ:
				if (!hasFocus && myAddress.getStreet().length() > 0) {
					jiequText.setVisibility(View.VISIBLE);
				}
				if (hasFocus) {
					jiequText.setSelectAllOnFocus(true);
				}
				break;
			case R.id.my_set_buyaddress_name:
				if (!hasFocus && myAddress.getName().length() > 0) {
					nameText.setVisibility(View.VISIBLE);
				}
				if (hasFocus) {
					nameText.setSelectAllOnFocus(true);
				}
				break;
			case R.id.my_set_buyaddress_phone:
				if (!hasFocus && myAddress.getPhone().length() > 0) {
					phoneText.setVisibility(View.VISIBLE);
				}
				if (hasFocus) {
					phoneText.setSelectAllOnFocus(true);
				}
				break;
			}
		}
	};
	OnClickListener click = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.my_set_buyaddress_address_btn:
				myAddress.setStreet(jiequText.getText().toString());
				myAddress.setName(nameText.getText().toString());
				myAddress.setPhone(phoneText.getText().toString());

				if (myAddress.getPhone().length() > 0) {
					phoneText.setVisibility(View.VISIBLE);
				}
				postBtn.requestFocus();
				postBtn.setFocusable(true);
				postBtn.setFocusableInTouchMode(true);

				if (myAddress.getProvinces().length() < 1 || myAddress.getStreet().length() < 1
						|| myAddress.getName().length() < 1 || myAddress.getPhone().length() < 1) {
					Toast.makeText(getBaseContext(), "请完整填写收货人资料", Toast.LENGTH_LONG).show();
					return;
				}
				myAddress.setStatus(checkBox.isChecked());
				AddressDB addressDB = AddressDB.getInstance(getBaseContext());
				if(checkBox.isChecked()){
					List<AddressInfo> list = addressDB.queryAddress();
					if(list!=null){
						Iterator<AddressInfo> iterator = list.iterator();
						while(iterator.hasNext()){
							AddressInfo a = iterator.next();
							a.setStatus(false);
							addressDB.updeteAddress(a);
						}
					}
				}
				if (addressinfo != null) {
					if(addressDB.updeteAddress(myAddress)){
						Toast.makeText(getBaseContext(), "修改收货地址成功", Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(getBaseContext(), "修改收货地址失败", Toast.LENGTH_LONG).show();
					}
				} else {
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
					Date date = new Date();
					String id = format.format(date);
					myAddress.setId(id);
					if(addressDB.insertAddress(myAddress)){
						Toast.makeText(getBaseContext(), "添加收货地址成功", Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(getBaseContext(), "添加收货地址失败", Toast.LENGTH_LONG).show();
					}
				}
				Intent intent = new Intent(BuyAddress.this, PersonAddress.class);
					startActivity(intent);
					finish();
				break;
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.my_set_buyaddress_sheng_linear:
				Intent i = new Intent(BuyAddress.this, AddressChoose.class);
				i.putExtra("Boolean", "aaa");
				startActivity(i);
				break;
			case R.id.contacts:
				Uri uri = ContactsContract.Contacts.CONTENT_URI;
				Intent intent = new Intent(Intent.ACTION_PICK, uri);
				startActivityForResult(intent,0);
				break;
			case R.id.image0:
				finish();
				break;
		}
	}
	/**
	 * 跳转联系人列表的回调函数
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode){
			case 0:
				if(data==null){
					return;
				}
				//处理返回的data,获取选择的联系人信息
				Uri  uri=data.getData();
				String[] contacts=getPhoneContacts(uri);
				nameText.setText(contacts[0]);
				phoneText.setText(contacts[1]);
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 手机的联系人和手机号并不再同一个数据库中,我们需要分别做处理
	 * @param uri
	 * @return
	 */
	private String[] getPhoneContacts(Uri uri){
		String[] contact=new String[2];
		//得到ContentResolver对象
		ContentResolver cr = getContentResolver();
		//取得电话本中开始一项的光标
		Cursor cursor=cr.query(uri,null,null,null,null);
		if(cursor!=null) {
			cursor.moveToFirst();
			//取得联系人姓名
			int nameFieldColumnIndex=cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
			contact[0]=cursor.getString(nameFieldColumnIndex);
			//取得电话号码
			String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
			Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
			if(phone != null){
				phone.moveToFirst();
				contact[1] = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			}
			phone.close();
			cursor.close();
		}
		else {
			return null;
		}
		return contact;
	}
}
