package com.retails.retailswarranty;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Manual extends BaseActivity {

	EditText serialNo, SN, city, CP,CN;
	Button next;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.form);

		serialNo = (EditText) findViewById(R.id.serial_no);
		SN = (EditText) findViewById(R.id.room_name);
		city = (EditText) findViewById(R.id.city);
		CP = (EditText) findViewById(R.id.contact_no);
		CN = (EditText) findViewById(R.id.contact_person);
		next = (Button) findViewById(R.id.next_button);


		/*
		 * To prevent user to press enter and new line in edittext
		 */
		serialNo.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
						(keyCode == KeyEvent.KEYCODE_ENTER)) {
					// Perform action on key press

					return true;
				}
				return false;
			}
		});

		SN.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
						(keyCode == KeyEvent.KEYCODE_ENTER)) {
					// Perform action on key press

					return true;
				}
				return false;
			}
		});
		CP.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
						(keyCode == KeyEvent.KEYCODE_ENTER)) {
					// Perform action on key press

					return true;
				}
				return false;
			}
		});

		CN.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
						(keyCode == KeyEvent.KEYCODE_ENTER)) {
					// Perform action on key press

					return true;
				}
				return false;
			}
		});

		city.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
						(keyCode == KeyEvent.KEYCODE_ENTER)) {
					// Perform action on key press

					return true;
				}
				return false;
			}
		});

		Intent i = getIntent();

		if(i!=null){
			
			serialNo.setText(i.getStringExtra("serialno"));
			SN.setText(i.getStringExtra("SR"));
			CN.setText(i.getStringExtra("CN"));

		}

		CP.setText(mSharedPreferences.getString("customername", ""));
		CN.setText(mSharedPreferences.getString("customerno", ""));
		
		
		
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				
				checkValues();
			}
		});

		
		CN.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView text, int actionId, KeyEvent arg2) {
				// TODO Auto-generated method stub
				if (actionId == EditorInfo.IME_ACTION_DONE) {

					checkValues();
					return true;
				}
				return false;
			}
		});



	}

	public void onClick(View view) {


		InputMethodManager imm = (InputMethodManager) view.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
	
	
	void checkValues(){
		String serialN = serialNo.getText().toString();
		String contactNo = CN.getText().toString();
		String contactPer = CP.getText().toString();
		String cityN = city.getText().toString();
		String room = SN.getText().toString();

		if(serialN.trim().length()==0){
			serialNo.setError("Enter the serial no");
		}else if (contactNo.trim().length()==0){
			CN.setError("Enter the Contact no");
		}else if (contactPer.trim().length()==0){
			CP.setError("Enter the name");

		}else if(cityN.trim().length()==0){
			city.setError("Enter the city name");

		}else if(room.trim().length()==0){
			SN.setError("Enter the showroom name");

		}else if(contactNo.trim().length()>0 && contactNo.trim().length()>0 &&contactPer.trim().length()>0  &&
				cityN.trim().length()>0 && room.trim().length()>0){

			
			e.putString("customername",contactPer);
			e.putString("customerno", contactNo);
			e.apply();
			
			Intent intent = new Intent(getApplicationContext(), ProblemDescription.class);

			intent.putExtra("serialno", serialN);
			intent.putExtra("contactno", contactNo);
			intent.putExtra("contactperson", contactPer);
			intent.putExtra("city", cityN);
			intent.putExtra("showroom", room);

			startActivity(intent);


		}
	}

}
