package com.retails.retailswarranty;


import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;


import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class BaseActivity extends Activity{

	public  SharedPreferences mSharedPreferences;
	SharedPreferences.Editor e;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	public Builder mBuilder;
	public NotificationManager mNotifyManager ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// Shared Preferences
		mSharedPreferences= getSharedPreferences(AppConstants.KEY, 0);
		e = mSharedPreferences.edit();
		mNotifyManager =
				(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mBuilder = new NotificationCompat.Builder(this);

		


	}


	



	public ArrayList<HashMap<String, String>> getFailedTransactions(){
		
		
		Type type = new TypeToken<ArrayList<HashMap<String, String>>>(){}.getType();
		Gson gson = new Gson();

		ArrayList<HashMap<String, String>> temp = new ArrayList<HashMap<String,String>>();

		
		String inspectionArray = mSharedPreferences.getString("failedtransactions", null);

		if(inspectionArray!=null  ){
			temp = gson.fromJson(inspectionArray, type);	

			Log.e("DEBUG", "getting failed transactions."+temp.size());

		}

		return temp;
	}


	public void addTransactions(HashMap<String, String> temp){
		Gson gson = new Gson();
		

		ArrayList<HashMap<String, String>> gettemp = getFailedTransactions();

		gettemp.add(temp);
		
		if(gettemp.size()>0){
			String a = gson.toJson(gettemp);
			e.putString("failedtransactions", a);
			e.apply();
		}



		Log.e("DEBUG", "Added transaction."+gettemp.size());
	}

	public void deleteAndAddTransactions(ArrayList<HashMap<String, String>> temp){
		Gson gson = new Gson();
		

			String a = gson.toJson(temp);
			e.putString("failedtransactions", a);
			e.apply();
		
			Log.e("DEBUG", "delected and Saved data.");
	}
	
	

}
