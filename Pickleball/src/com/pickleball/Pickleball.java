package com.pickleball;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Pickleball extends Activity {
	
	Hashtable<String, Integer> players = new Hashtable<String, Integer>(); 
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        FillPlayers();        
    }
    
    public void singlesEntry(View view) {    	
        setContentView(R.layout.singles_p1);
        Spinner s = (Spinner)findViewById(R.id.spinner1);
        List<String> playerNames = new ArrayList<String>();
        
        Enumeration<String> e = players.keys();
        
      //iterate through Hashtable keys Enumeration
      while(e.hasMoreElements())
    	  playerNames.add(e.nextElement());
		ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, playerNames);
		s.setAdapter(adapter);        
    }
    public void doublesEntry(View view) {
        // Kabloey
    }
    
    public void FillPlayers()
    {   
        String result = "";

        //the year data to send

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        InputStream is = null;
        try{
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://ericbram.net/pickleball/GetAndroidPickleballPeople.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
        }catch(Exception e){
                Log.e("log_tag", "Error in http connection "+e.toString());
                String s = e.toString();
                s = e.toString();
                s = e.toString();
                s = e.toString();
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
                for(int i=0;i<jArray.length();i++){
	                JSONObject json_data = jArray.getJSONObject(i);
	                int pid = json_data.getInt("id");
	                String lastname = json_data.getString("lastname");
	                String firstname = json_data.getString("firstname");
	                String fullname = firstname + " " + lastname;
	                players.put(fullname, pid);
                }
        }
        catch(JSONException e){
                Log.e("log_tag", "Error parsing data "+e.toString());
        }  
    }
    
}