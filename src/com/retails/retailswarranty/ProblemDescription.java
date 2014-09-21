package com.retails.retailswarranty;



import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;





import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ProblemDescription extends BaseActivity{
	private static final int RESULT_LOAD_IMAGE = 1;
	ImageButton camera, attach;
	Button upload;
	EditText pd;
	RelativeLayout listViewLayout;
	ListView list;
	List< String> listviewlist=new ArrayList<String>();
	Listadapter adapter;
	String images = "";
	HashMap<String, String> tMap = new HashMap<String, String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.problem_description);

		camera = (ImageButton) findViewById(R.id.capture);
		attach = (ImageButton) findViewById(R.id.attach);
		upload = (Button) findViewById(R.id.upload);
		pd = (EditText) findViewById(R.id.problem_description);
		listViewLayout = (RelativeLayout) findViewById(R.id.rl_list);
		list =(ListView) findViewById(R.id.list);

		adapter=new Listadapter(getApplicationContext(), R.id.list);


		upload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if(pd.getText().toString().trim().length()>0){

				}else{
					pd.setError("Please write the problem description");
					tMap.put("description", pd.getText().toString());
				}


			}
		});

		camera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				Intent i=new Intent(getApplicationContext(), Cam.class) ;
				startActivityForResult(i, 5);
				overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);

			}
		});
		attach.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openGallery();

			}
		});


		Intent intent = getIntent();
		if(intent!=null){

			tMap.put("serialno", intent.getStringExtra("serialno"));
			tMap.put("customername", intent.getStringExtra("contactperson"));
			tMap.put("customerno", intent.getStringExtra("contactno"));
			tMap.put("city", intent.getStringExtra("city"));
			tMap.put("showroom", intent.getStringExtra("showroom"));


		}



	}




	private void openGallery() {
		Intent i = new Intent(getApplicationContext(),MultiPhotoSelectActivity.class);

		startActivityForResult(i, RESULT_LOAD_IMAGE);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);




		if(requestCode==5){

			if(resultCode==6){

				if(data!=null && data.getStringExtra("res")!=null)
				{


					String data2 =data.getStringExtra("res");

					if(data2!=null){
						listviewlist.add(data.getStringExtra("res"));

						if(listViewLayout.getVisibility()==View.GONE){
							listViewLayout.setVisibility(View.VISIBLE);
							list.setAdapter(adapter);
						}else{
							adapter.notifyDataSetChanged();
						}

					}

				}else{
					Toast.makeText(getApplicationContext(), "NO Photo taken please try again", Toast.LENGTH_LONG).show();
				}
			}




		}
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {


			ArrayList<String> filePathColumn = data.getStringArrayListExtra("res");


			for(int i=0;i<filePathColumn.size();i++){
				
				

				listviewlist.add(filePathColumn.get(i));

			}




			if(listViewLayout.getVisibility()==View.GONE){
				listViewLayout.setVisibility(View.VISIBLE);
				list.setAdapter(adapter);
			}else{
				adapter.notifyDataSetChanged();
			}

		}





		// String picturePath contains the path of selected Image
	}




	public class Listadapter extends BaseAdapter {


		public Listadapter(Context context, int resource) {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) getApplicationContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			View rowView = inflater.inflate(R.layout.listviewxml, parent, false);
			TextView textView = (TextView) rowView.findViewById(R.id.text);
			textView.setText(listviewlist.get(position));

			ImageButton remove=(ImageButton) rowView.findViewById(R.id.remov);
			remove.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub


					AlertDialog.Builder builder1 = new AlertDialog.Builder(ProblemDescription.this,AlertDialog.THEME_HOLO_LIGHT);
					builder1.setMessage("Do you want to delete this image?");
					builder1.setCancelable(true);
					builder1.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {


							listviewlist.remove(position);
							adapter.notifyDataSetChanged();

							if(listviewlist.size()==0){
								listViewLayout.setVisibility(View.GONE);
							}

						}
					});
					builder1.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});

					AlertDialog alert11 = builder1.create();
					alert11.show();

				}
			});
			// Change the icon for Windows and iPhone


			return rowView;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub

			return listviewlist.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

	}

	public void onClick(View view) {


		InputMethodManager imm = (InputMethodManager) view.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}


	void constructArray(){


		for(int i =0;i< listviewlist.size();i++){

			images= images+listviewlist.get(i)+",";
		}
		images = images.replaceAll(",$", "");

		tMap.put("images", images);



	}
	
	
	
	void uplaodTransaction() throws JSONException{

		RequestParams params = new RequestParams();

		constructArray();

		Log.e("DETAILS", tMap.get("serialno")+"   "+tMap.get("description")+"  "+tMap.get("customername"));
		params.put("sync_master",tMap.get("serialno"));
		params.put("sync_photo",tMap.get("customername"));
		params.put("sync_checklist",tMap.get("customerno"));
		params.put("sync_master",tMap.get("city"));
		params.put("sync_photo",tMap.get("showroom"));
		params.put("sync_checklist",tMap.get("description"));



		CallNetwork.post(CallNetwork.SYNC_URL, params, new JsonHttpResponseHandler(){

			@Override
			public void onProgress(int bytesWritten, int totalSize) {
				// TODO Auto-generated method stub
				super.onProgress(bytesWritten, totalSize);
				Log.e("ONPROGRESS", bytesWritten+"");
				mBuilder.setProgress(100, (int) ((bytesWritten / (float) totalSize) * 100), false);
				// Displays the progress bar for the first time.
				mNotifyManager.notify(0, mBuilder.build());
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub
				Log.e("ONFALIURE", responseString+"aaaaaa");

				AppConstants.flag=0;
				mBuilder.setAutoCancel(true);
				mBuilder.setContentTitle("Failed to uplaod");
				mBuilder.setContentText("Please check the internet connection")
				// Removes the progress bar
				.setProgress(0,0,false);
				mNotifyManager.notify(0, mBuilder.build());

				addTransactions(tMap);

			}



			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// If the response is JSONObject instead of expected JSONArray
				AppConstants.flag=0;
				Log.e("SUCCESS", response+"");
				
				String result="";

				try{

					result = response.getString("result");


				}catch(Exception e){
					e.printStackTrace();
				}
			

				if(result.equals("true")){
					
					
					mBuilder.setAutoCancel(true);
					mBuilder.setContentTitle("Successfully uploaded");
					mBuilder.setContentText("Done")
					// Removes the progress bar
					.setProgress(0,0,false);
					mNotifyManager.notify(0, mBuilder.build());
					
					
				}else{
				
					addTransactions(tMap);
				mBuilder.setAutoCancel(true);
				mBuilder.setContentTitle("Failed to upload");
				mBuilder.setContentText("Please check your internet connection")
				// Removes the progress bar
				.setProgress(0,0,false);
				mNotifyManager.notify(0, mBuilder.build());
				
				}


				

			}


			@Override
			public void onStart() {

				AppConstants.flag=1;


			}




			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				AppConstants.flag=0;

				Toast toast =Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				toast.show();

				Log.e("FALIURE", errorResponse+"");
				mBuilder.setAutoCancel(true);
				mBuilder.setContentTitle("Failed to uplaod");
				mBuilder.setContentText("Please check the internet connection")
				// Removes the progress bar
				.setProgress(0,0,false);
				mNotifyManager.notify(0, mBuilder.build());

				addTransactions(tMap);
			}





		});
	}
	
	
	
	

}
