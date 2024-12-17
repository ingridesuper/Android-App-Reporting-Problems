package com.example.android_proiect_final_version.JsonParsers;

import com.example.android_proiect_final_version.models.Problema;

import org.json.JSONException;
import org.json.JSONObject;

public class ProblemaParser {
    private static final String SECTOR = "sector";
    private static final String TITLU = "titlu";
    private static final String DESCRIERE = "descriere";
    private static final String ID = "id";
    private static final String ADRESA = "adresa";
    private static final String AUTOR = "AUTOR";
    private static final String CATEGORIE = "categorie";

    private Problema parseProblema(JSONObject jsonObject) throws JSONException {
        String titlu=jsonObject.getString(TITLU);
        String adresa=jsonObject.getString(ADRESA);
        String autor=jsonObject.getString(AUTOR);
    }
}
