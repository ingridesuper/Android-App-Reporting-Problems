package com.example.android_proiect_final_version.JsonParsers;

import com.example.android_proiect_final_version.enums.Sector;
import com.example.android_proiect_final_version.models.Utilizator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UtilizatorParser {
    private static final String USERNAME="username";
    private static final String PAROLA="parola";
    private static final String NUME="nume";
    private static final String PRENUME="prenume";
    private static final String SECTOR="sector";

    private static Utilizator parseObject(JSONObject jsonObject) throws JSONException {
        String username=jsonObject.getString(USERNAME);
        String parola=jsonObject.getString(PAROLA);
        String nume=jsonObject.getString(NUME);
        String prenume=jsonObject.getString(PRENUME);
        Sector sector=Sector.valueOf(jsonObject.getString(SECTOR));
        return new Utilizator(username, parola, nume, prenume, sector);
    }

    private static List<Utilizator> parseArray(JSONArray jsonArray) throws JSONException{
        List<Utilizator> list=new ArrayList<>();
        for(int i=0;i<jsonArray.length();i++){
            list.add(parseObject(jsonArray.getJSONObject(i)));
        }
        return list;
    }

    public static List<Utilizator> parseString(String json){
        try {
            JSONArray jsonArray=new JSONArray(json);
            return parseArray(jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
