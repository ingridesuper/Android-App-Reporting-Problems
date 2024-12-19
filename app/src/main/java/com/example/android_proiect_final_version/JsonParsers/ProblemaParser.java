package com.example.android_proiect_final_version.JsonParsers;

import com.example.android_proiect_final_version.enums.CategorieProblema;
import com.example.android_proiect_final_version.enums.Sector;
import com.example.android_proiect_final_version.models.Problema;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProblemaParser {
    private static final String SECTOR = "sector";
    private static final String TITLU = "titlu";
    private static final String DESCRIERE = "descriere";
    private static final String ADRESA = "adresa";
    private static final String AUTOR = "autor";
    private static final String CATEGORIE = "categorie";

    private static Problema parseProblema(JSONObject jsonObject) throws JSONException {
        String titlu=jsonObject.getString(TITLU);
        String adresa=jsonObject.getString(ADRESA);
        String autor=jsonObject.getString(AUTOR);
        String descriere=jsonObject.getString(DESCRIERE);
        Sector sector=Sector.valueOf(jsonObject.getString(SECTOR));
        CategorieProblema categorieProblema=CategorieProblema.valueOf(jsonObject.getString(CATEGORIE));
        return new Problema(titlu, descriere, autor, sector, adresa, categorieProblema);
    }

    private static List<Problema> parseLista(JSONArray jsonArray) throws JSONException{
        List<Problema> probleme=new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++){
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            probleme.add(parseProblema(jsonObject));
        }
        return probleme;
    }

    public static List<Problema> parseString(String json){
        try {
            JSONArray jsonArray=new JSONArray(json);
            return parseLista(jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
