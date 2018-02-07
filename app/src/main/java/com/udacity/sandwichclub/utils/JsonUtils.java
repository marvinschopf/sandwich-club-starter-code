package com.udacity.sandwichclub.utils;

import android.util.Log;
import android.widget.Toast;

import com.udacity.sandwichclub.MainActivity;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        try {
            JSONObject root = new JSONObject(json);
            JSONObject names = root.getJSONObject("name");
            sandwich.setMainName(names.optString("mainName"));
            sandwich.setDescription(root.optString("description"));
            sandwich.setPlaceOfOrigin(root.optString("placeOfOrigin"));
            sandwich.setAlsoKnownAs(jsonArrayList(names.getJSONArray("alsoKnownAs")));
            sandwich.setIngredients(jsonArrayList(root.getJSONArray("ingredients")));
            sandwich.setImage(root.optString("image"));
        } catch (JSONException e) {
            Log.e(JsonUtils.class.getSimpleName(),"Problems occured while parsing", e);
        }

        return sandwich;
    }
    private static List<String> jsonArrayList(JSONArray jsonArray){
        List<String> list = new ArrayList<>(0);
        if (jsonArray!=null){
            for (int i=0; i<jsonArray.length();i++){
                try {
                    list.add(jsonArray.getString(i));
                } catch (JSONException e) {
                    Log.e(JsonUtils.class.getSimpleName(), "Problems occured while converting to ArrayList", e);
                }
            }
        }
        return list;
    }
}
