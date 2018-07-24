package com.example.andrew.midterm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by andrew on 10/24/16.
 */

public class AppUtil {
    static public class AppJSONParser{
        static ArrayList<App> parseApp(String in) throws JSONException{
            ArrayList<App> appList = new ArrayList<>();

            JSONObject root = new JSONObject(in);
            JSONArray appJSONArray = root.getJSONObject("feed").getJSONArray("entry");

            for(int i = 0; i < appJSONArray.length(); i++){
                JSONObject appJSONObject = appJSONArray.getJSONObject(i);

                App app = new App();

                app.setName(appJSONObject.getJSONObject("im:name").getString("label"));
                app.setPrice(appJSONObject.getJSONObject("im:price").getString("label").replace("$",""));
                app.setImg(appJSONObject.getJSONArray("im:image").getJSONObject(1).getString("label"));

                appList.add(app);
            }

            return appList;
        }
    }
}
