package com.retails.retailswarranty;


import com.google.zxing.Result;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Scanner extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private static final String AUTO_FOCUS_STATE = "AUTO_FOCUS_STATE";
    private boolean mAutoFocus;
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        
        if(state!=null){
        	mAutoFocus = state.getBoolean(AUTO_FOCUS_STATE, true);
        }else {
            
            mAutoFocus = true;
           
        }
        
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
       
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }
    
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
       
        outState.putBoolean(AUTO_FOCUS_STATE, mAutoFocus);
       
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v("DEBUG", rawResult.getText()); // Prints scan results
        Log.v("DEBUG", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
        
        String text = rawResult.getText();
        String serialNo ="";
        String customerName = "";
        String showRoom = "";
        
        if(text.contains("SRNo")){
        	
        	String[] temp = text.split(",");
        	
        	if(temp.length==3){
        		
        		serialNo = temp[0].substring(5);
        		customerName = temp[1].substring(9);
        		showRoom = temp[2].substring(9);
        	}
        	
        
        	
        	Intent i = new Intent(Scanner.this, Manual.class);
        	i.putExtra("serialno", serialNo);
            i.putExtra("CN", customerName);
            i.putExtra("SR", showRoom);
            startActivity(i);
            finish();
        	
        }else{
        
        Intent i = new Intent(Scanner.this, Manual.class);
        startActivity(i);
        finish();
        Toast.makeText(getApplicationContext(), "Not a valid QR code", Toast.LENGTH_LONG).show();
        
        }
    }
    
  

}
