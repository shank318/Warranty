package com.retails.retailswarranty;



import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Selection extends BaseActivity{
	Button inseption,installation;
	ArrayList<HashMap<String, String>> temp = new ArrayList<HashMap<String,String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selection);
		inseption=(Button) findViewById(R.id.idincept);
		installation=(Button) findViewById(R.id.idinstal);
		inseption.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Selection.this, Scanner.class);
				startActivity(i);
				
			}
		});
        installation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Selection.this, Manual.class);
				startActivity(i);
				
			}
		});
        
        
       
		
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		Log.e("DEBUG", "CHECKING FAILED TR..");
		temp = getFailedTransactions();

		if(temp.size()>0){
			try {
				uplaodTransaction();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	void uplaodTransaction() throws JSONException{

		RequestParams params = new RequestParams();

		Log.e("DETAILS", temp.get(0).get("serialno")+"   "+temp.get(0).get("customername")+"  "+temp.get(0).get("customerno"));
		params.put("sync_master",temp.get(0).get("serialno"));
		params.put("sync_photo",temp.get(0).get("customername"));
		params.put("sync_checklist",temp.get(0).get("customerno"));
		params.put("sync_master",temp.get(0).get("city"));
		params.put("sync_photo",temp.get(0).get("showroom"));
		params.put("sync_checklist",temp.get(0).get("description"));



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
				Log.e("ONFALIURE", responseString+statusCode);


				mBuilder.setAutoCancel(true);
				mBuilder.setContentTitle("Failed to uplaod");
				mBuilder.setContentText("Please check the internet connection")
				// Removes the progress bar
				.setProgress(0,0,false);
				mNotifyManager.notify(0, mBuilder.build());

				AppConstants.flag=0;

			}


			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// If the response is JSONObject instead of expected JSONArray




				String result="";

				try{

					result = response.getString("result");


				}catch(Exception e){
					e.printStackTrace();
				}



				AppConstants.flag=0;

				if(result.equals("true")){
					temp.remove(0);
					deleteAndAddTransactions(temp);

					mBuilder.setAutoCancel(true);
					mBuilder.setContentTitle("Successfully uploaded");
					mBuilder.setContentText("Done")
					// Removes the progress bar
					.setProgress(0,0,false);
					mNotifyManager.notify(0, mBuilder.build());
					
					
					if(temp.size()>0){
						try {
							uplaodTransaction();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					Log.e("DEBUG", "Failed Transaction left"+temp.size());
					
					
				}else{
					mBuilder.setAutoCancel(true);
					mBuilder.setContentTitle("Failed to uplaod");
					mBuilder.setContentText("Please check the internet connection")
					// Removes the progress bar
					.setProgress(0,0,false);
					mNotifyManager.notify(0, mBuilder.build());
				}

				

				Log.e("SUCCESS", response+"");	




			}


			@Override
			public void onStart() {

				AppConstants.flag=1;
			}


		});
	}

	
	
	
}
