package com.example.android_proiect_final_version.JsonParsers;

import com.example.android_proiect_final_version.models.Semnatura;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SemnaturaParser {
    private static final String USERNAME="username";
    private static final String PROBLEMAID="problemaId";

    private static Semnatura parsareObiect(JSONObject jsonObject) throws JSONException{
        String username=jsonObject.getString(USERNAME);
        Long problemaId=jsonObject.getLong(PROBLEMAID);
        return new Semnatura(username, problemaId);
    }

    private static List<Semnatura> parsareArray(JSONArray jsonArray) throws JSONException{
        List<Semnatura> list=new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++){
            list.add(parsareObiect(jsonArray.getJSONObject(i)));
        }
        return list;
    }

    public static List<Semnatura> parseString(String json){
        try {
            JSONArray jsonArray=new JSONArray(json);
            return parsareArray(jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
