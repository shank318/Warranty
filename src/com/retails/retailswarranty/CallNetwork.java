package com.retails.retailswarranty;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class CallNetwork {

	
	public static final String SYNC_URL = "http://49.50.76.122/Retails/Android/appWaranty.php";
	
	private static AsyncHttpClient client = new AsyncHttpClient();
    


	public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	
		Log.e("Executing", url+"");
		client.post(url, params, responseHandler);
		

	}

	

	// 
}
