package com.example.andrew.midterm;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by andrew on 10/24/16.
 */

public class GetDataAsyncTask extends AsyncTask<String, Void, ArrayList<App>> {

    private final MainActivity activity;

    public GetDataAsyncTask(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<App> apps) {
        super.onPostExecute(apps);
    }



    @Override
    protected ArrayList<App> doInBackground(String... params) {

        try{
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();

                while(line != null){
                    sb.append(line);
                    line = reader.readLine();
                }
                Log.d("demo", sb.toString());

                return AppUtil.AppJSONParser.parseApp(sb.toString());

            }
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(ProtocolException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }



        return null;
    }
}
