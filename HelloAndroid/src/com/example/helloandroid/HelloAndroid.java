package com.example.helloandroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HelloAndroid extends Activity {

    String Author = null;
    String Date = null;
    String Rap = null;
    int rap_ID;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
               
        GetRapInfo();        
        SetTextFields();        
    }
    
    public void ClearAll()
    {
    	 Author = null;
    	 Date = null;
    	 Rap = null;
    	 rap_ID = -1;
    	 TextView author = (TextView)findViewById(R.id.lbl_Author);
         TextView date = (TextView)findViewById(R.id.lbl_Date);
         TextView rap = (TextView)findViewById(R.id.lbl_Rap);
         author.setText("Author:");
         rap.setText("");
         date.setText("Date:");
    }
    
    public void UpdateRapSite(int newRating)
    {
    	UpdateWorker(newRating);
    	ClearAll();
    	GetRapInfo();        
        SetTextFields(); 
    }
    
    public void UpdateWorker(int newRating)
    {
    	 String result = "";

         //the year data to send

         ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
         nameValuePairs.add(new BasicNameValuePair("rapID",Integer.toString(rap_ID)));
         nameValuePairs.add(new BasicNameValuePair("rating",Integer.toString(newRating)));
         InputStream is = null;
         try{
                 HttpClient httpclient = new DefaultHttpClient();
                 HttpPost httppost = new HttpPost("http://ericbram.net/raps/RateAndroidRap.php");
                 httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                 HttpResponse response = httpclient.execute(httppost);
                 HttpEntity entity = response.getEntity();
                 is = entity.getContent();
         }catch(Exception e){
                 Log.e("log_tag", "Error in http connection "+e.toString());
         }

         try{
         		 BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                 StringBuilder sb = new StringBuilder();
                 String line = null;
                 while ((line = reader.readLine()) != null) {
                         sb.append(line + "n");
                 }
                 is.close();
                 result=sb.toString();
         }catch(Exception e){
                 Log.e("log_tag", "Error converting result "+e.toString());
         }
         
         String[] update_results = result.split(",");
         update_results = result.split(",");
    }
    
    public void SetTextFields()
    {
    	TextView author = (TextView)findViewById(R.id.lbl_Author);
        TextView date = (TextView)findViewById(R.id.lbl_Date);
        TextView rap = (TextView)findViewById(R.id.lbl_Rap);
        
        // 
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-d"); 
			Date dt = format.parse(Date);
			format = new SimpleDateFormat("MMMM d yyyy");
			String new_Date = format.format(dt);
			date.append("  " + new_Date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
                
        author.append("  " + Author);
        
        String new_rap = Rap.replaceAll("<br>", "\n");
        new_rap = new_rap.replaceAll("<br />", "\n");
        new_rap = new_rap.replaceAll("<br/>", "\n");
        rap.append("\n" + new_rap);
    }
    
    public void GetRapInfo()    
    {
        String result = "";

        //the year data to send

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        InputStream is = null;
        try{
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://ericbram.net/raps/GetAndroidRap.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
        }catch(Exception e){
                Log.e("log_tag", "Error in http connection "+e.toString());
        }

        try{
        		BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                        sb.append(line + "n");
                }
                is.close();
                result=sb.toString();
        }catch(Exception e){
                Log.e("log_tag", "Error converting result "+e.toString());
        }


        try{
                JSONArray jArray = new JSONArray(result);
                JSONObject json_data = jArray.getJSONObject(0);
                rap_ID = json_data.getInt("id");
                Author = json_data.getString("name");
                Date = json_data.getString("date");
                Rap = json_data.getString("rap");
                      	

        }
        catch(JSONException e){
                Log.e("log_tag", "Error parsing data "+e.toString());
        }  
    }
    
    public void SetRating1(View view) {
        UpdateRapSite(1);
    }
    public void SetRating2(View view) {
        UpdateRapSite(2);
    }
    public void SetRating3(View view) {
        UpdateRapSite(3);
    }
    public void SetRating4(View view) {
        UpdateRapSite(4);
    }
    public void SetRating5(View view) {
        UpdateRapSite(5);
    }
    public void SetRating6(View view) {
        UpdateRapSite(6);
    }
    public void SetRating7(View view) {
        UpdateRapSite(7);
    }
    public void SetRating8(View view) {
        UpdateRapSite(8);
    }
    public void SetRating9(View view) {
        UpdateRapSite(9);
    }
    public void SetRating10(View view) {
        UpdateRapSite(10);
    }
}

