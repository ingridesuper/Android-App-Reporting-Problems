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
import com.example.android_proiect_final_version.enums.Sector;
import com.example.android_proiect_final_version.models.Utilizator;

public class EditProfilActivity extends AppCompatActivity {
    Utilizator utilizator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etNume=findViewById(R.id.etNume);
        EditText etPrenume=findViewById(R.id.etPrenume);
        Spinner spnSector=findViewById(R.id.spnSector);
        Button btnSave=findViewById(R.id.btnSave);
        ArrayAdapter<CharSequence> adapterSector=ArrayAdapter.createFromResource(getApplicationContext(), R.array.sectoare, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spnSector.setAdapter(adapterSector);

        SharedPreferences sp=getSharedPreferences("utilizatorCurent", MODE_PRIVATE);
        String username=sp.getString("username", "none");
        utilizator= AplicatieDB.getInstance(getApplicationContext()).getUtilizatorDAO().getUtilizatorWithUsername(username);

        etNume.setText(utilizator.getNume());
        etPrenume.setText(utilizator.getPrenume());
        for(int i=0; i<adapterSector.getCount(); i++){
            if(utilizator.getSector().name().equals(adapterSector.getItem(i))){
                spnSector.setSelection(i);
            }
        }

        btnSave.setOnClickListener(view -> {
            String numeNou=etNume.getText().toString();
            String prenumeNou=etPrenume.getText().toString();
            Sector sectorNou=Sector.valueOf(spnSector.getSelectedItem().toString());

            utilizator.setNume(numeNou);
            utilizator.setPrenume(prenumeNou);
            utilizator.setSector(sectorNou);

            AplicatieDB.getInstance(getApplicationContext()).getUtilizatorDAO().updateUtilizator(utilizator);
            Intent intent=getIntent();
            setResult(RESULT_OK, intent);
            finish();
        });

    }
}