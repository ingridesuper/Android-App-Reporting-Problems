package com.example.android_proiect_final_version;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_proiect_final_version.database.AplicatieDB;
import com.example.android_proiect_final_version.models.Idee;
import com.example.android_proiect_final_version.models.Semnatura;
import com.example.android_proiect_final_version.models.SemnaturaIdee;

import java.util.List;

public class ViewIdeeActivity extends AppCompatActivity {
    Idee idee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_idee);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent=getIntent();
        idee=(Idee)intent.getSerializableExtra("idee");

        TextView tvTitlu=findViewById(R.id.tvTitlu);
        TextView tvDescriere=findViewById(R.id.tvDescriere);
        TextView tvSector=findViewById(R.id.tvSector);
        TextView tvAutor=findViewById(R.id.tvAutor);
        TextView tvNrSemnaturi=findViewById(R.id.tvNrSemnaturi);
        Button btnSemneaza=findViewById(R.id.btnSemneaza);
        Button btnRetrageSemnatura=findViewById(R.id.btnRetrageSemnatura);

        tvTitlu.setText(idee.getTitlu());
        tvDescriere.setText(idee.getDescriere());
        tvSector.setText("Sectorul "+idee.getSector().toString().toLowerCase());
        tvAutor.setText("Autor: "+idee.getAutorUsername());
        List<SemnaturaIdee> semnaturi= AplicatieDB.getInstance(getApplicationContext()).getSemnaturaIdeeDAO().getSemnaturiForIdee(idee.getId());
        int nrSemnaturi=semnaturi.size();
        tvNrSemnaturi.setText("Număr semnături: "+String.valueOf(nrSemnaturi));

        SharedPreferences sp=getSharedPreferences("utilizatorCurent", MODE_PRIVATE);
        String username=sp.getString("username", "none");

        boolean hasSigned=false;

        if(AplicatieDB.getInstance(getApplicationContext()).getSemnaturaIdeeDAO().utilizatorulASemnatIdeea(idee.getId(), username)==1){
            hasSigned=true;
        }
        if(!hasSigned){
            btnSemneaza.setVisibility(View.VISIBLE);
            btnRetrageSemnatura.setVisibility(View.GONE);
        }
        else {
            btnSemneaza.setVisibility(View.GONE);
            btnRetrageSemnatura.setVisibility(View.VISIBLE);
        }

        btnSemneaza.setOnClickListener(view -> {
            btnSemneaza.setVisibility(View.GONE);
            btnRetrageSemnatura.setVisibility(View.VISIBLE);
            AplicatieDB.getInstance(getApplicationContext()).getSemnaturaIdeeDAO().insertSemnaturaIdee(new SemnaturaIdee(username, idee.getId()));
            updateNrSemnaturi(tvNrSemnaturi);
        });

        btnRetrageSemnatura.setOnClickListener(view -> {
            btnSemneaza.setVisibility(View.VISIBLE);
            btnRetrageSemnatura.setVisibility(View.GONE);
            AplicatieDB.getInstance(getApplicationContext()).getSemnaturaIdeeDAO().deleteSemnaturaIdeeByUsernameAndIdeeId(username, idee.getId());
            updateNrSemnaturi(tvNrSemnaturi);
        });
    }

    private void updateNrSemnaturi(TextView tvNrSemnaturi) {
        int nrSemnaturi = AplicatieDB.getInstance(getApplicationContext()).getSemnaturaIdeeDAO().getSemnaturiForIdee(idee.getId()).size();
        tvNrSemnaturi.setText("Număr semnături: " + nrSemnaturi);
    }
}