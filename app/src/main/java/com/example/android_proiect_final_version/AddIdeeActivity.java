package com.example.android_proiect_final_version;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.android_proiect_final_version.models.Idee;
import com.example.android_proiect_final_version.models.Problema;

public class AddIdeeActivity extends AppCompatActivity {
    ArrayAdapter<CharSequence> adapterSector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_idee);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etTitlu=findViewById(R.id.etTitlu);
        EditText etDescriere=findViewById(R.id.etDescriere);
        Spinner spnSector=findViewById(R.id.spnSector);
        Button btnSave=findViewById(R.id.btnSave);

        SharedPreferences sp=getSharedPreferences("utilizatorCurent", MODE_PRIVATE);
        String currentUser=sp.getString("username", "nu exista");

        adapterSector=ArrayAdapter.createFromResource(getApplicationContext(), R.array.sectoare, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spnSector.setAdapter(adapterSector);

        btnSave.setOnClickListener(view -> {
            String titlu=etTitlu.getText().toString();
            String descriere=etDescriere.getText().toString();
            Sector sector=Sector.valueOf(spnSector.getSelectedItem().toString());
            AplicatieDB.getInstance(getApplicationContext()).getIdeeDAO().insertIdee(new Idee(titlu, descriere, currentUser, sector));
            Intent intent=getIntent();
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}