package com.example.android_proiect_final_version.JsonParsers;

import com.example.android_proiect_final_version.enums.Sector;
import com.example.android_proiect_final_version.models.Idee;
import com.example.android_proiect_final_version.models.Problema;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IdeeParser {
    private static final String SECTOR = "sector";
    private static final String TITLU = "titlu";
    private static final String DESCRIERE = "descriere";
    private static final String AUTOR = "autor";

    private static Idee parsareIdee(JSONObject jsonObject) throws JSONException {
        String titlu=jsonObject.getString(TITLU);
        String autor=jsonObject.getString(AUTOR);
        String descriere=jsonObject.getString(DESCRIERE);
        Sector sector=Sector.valueOf(jsonObject.getString(SECTOR));
        return new Idee(titlu, descriere, autor, sector);
    }

    private static List<Idee> parseLista(JSONArray jsonArray) throws JSONException{
        List<Idee> idei=new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++){
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            idei.add(parsareIdee(jsonObject));
        }
        return idei;
    }

    public static List<Idee> parseString(String json){
        try {
            JSONArray jsonArray=new JSONArray(json);
            return parseLista(jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
