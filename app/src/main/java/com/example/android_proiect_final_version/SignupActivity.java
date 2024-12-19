package com.example.android_proiect_final_version;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_proiect_final_version.database.AplicatieDB;
import com.example.android_proiect_final_version.enums.Sector;
import com.example.android_proiect_final_version.models.Utilizator;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etUsername=findViewById(R.id.etUsername);
        EditText etParola=findViewById(R.id.etParola);
        EditText etNume=findViewById(R.id.etNume);
        EditText etPrenume=findViewById(R.id.etPrenume);
        Spinner spnSector=findViewById(R.id.spnSector);
        Button btnSignup=findViewById(R.id.btnSignup);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getApplicationContext(), R.array.sectoare, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spnSector.setAdapter(adapter);

        btnSignup.setOnClickListener(view -> {
            String username=etUsername.getText().toString();
            String parola=etParola.getText().toString();
            String nume=etNume.getText().toString();
            String prenume=etPrenume.getText().toString();
            Sector sector=Sector.valueOf(spnSector.getSelectedItem().toString());
            if(AplicatieDB.getInstance(getApplicationContext()).getUtilizatorDAO().countUtilizatoriUsername(username)==1){
                Toast.makeText(getApplicationContext(), "Exista deja un utilizator cu acest username.", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                AplicatieDB.getInstance(getApplicationContext()).getUtilizatorDAO().insertUtilizator(new Utilizator(username, parola, nume, prenume, sector));
                finish();
            }
        });
    }
}