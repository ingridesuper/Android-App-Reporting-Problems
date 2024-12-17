package com.example.android_proiect_final_version;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_proiect_final_version.database.AplicatieDB;
import com.example.android_proiect_final_version.enums.CategorieProblema;
import com.example.android_proiect_final_version.enums.Sector;
import com.example.android_proiect_final_version.models.Problema;

public class AddProblemActivity extends AppCompatActivity {
    ArrayAdapter<CharSequence> adapterSector;
    ArrayAdapter<CharSequence> adapterCategorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_problem);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etTitlu=findViewById(R.id.etTitlu);
        EditText etDescriere=findViewById(R.id.etDescriere);
        EditText etAdresa=findViewById(R.id.etAdresa);
        Spinner spnSector=findViewById(R.id.spnSector);
        Spinner spnCategorie=findViewById(R.id.spnCategorie);
        Button btnSave=findViewById(R.id.btnSave);

        SharedPreferences sp=getSharedPreferences("utilizatorCurent", MODE_PRIVATE);
        String currentUser=sp.getString("username", "nu exista");
        Log.i("currentUser", currentUser);

        adapterSector=ArrayAdapter.createFromResource(getApplicationContext(), R.array.sectoare, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spnSector.setAdapter(adapterSector);
        adapterCategorie=ArrayAdapter.createFromResource(getApplicationContext(), R.array.categoriiProbleme, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spnCategorie.setAdapter(adapterCategorie);

        btnSave.setOnClickListener(view -> {
            String titlu=etTitlu.getText().toString();
            String adresa=etAdresa.getText().toString();
            String descriere=etDescriere.getText().toString();
            Sector sector=Sector.valueOf(spnSector.getSelectedItem().toString());
            CategorieProblema categorie=CategorieProblema.valueOf(spnCategorie.getSelectedItem().toString());
            AplicatieDB.getInstance(getApplicationContext()).getProblemaDAO().insertProblema(new Problema(titlu, descriere, currentUser, sector, adresa, categorie));
            Intent intent=getIntent();
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}