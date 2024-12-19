package com.example.android_proiect_final_version;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_proiect_final_version.database.AplicatieDB;
import com.example.android_proiect_final_version.models.Problema;
import com.example.android_proiect_final_version.models.Semnatura;

import org.w3c.dom.Text;

import java.util.List;

public class ViewProblemaActivity extends AppCompatActivity {
    Problema problema;
    ActivityResultLauncher<Intent> launcherEditProblema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_problema);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tvTitlu=findViewById(R.id.tvTitlu);
        TextView tvDescriere=findViewById(R.id.tvDescriere);
        TextView tvAdresaSector=findViewById(R.id.tvAdresaSector);
        TextView tvCategorie=findViewById(R.id.tvCategorie);
        TextView tvAutor=findViewById(R.id.tvAutor);
        TextView tvNrSemnaturi=findViewById(R.id.tvNrSemnaturi);
        Button btnSemneaza=findViewById(R.id.btnSemneaza);
        Button btnRetrageSemnatura=findViewById(R.id.btnRetrageSemnatura);
        Button btnEditeazaProblema=findViewById(R.id.btnEditeazaProblema);
        Button btnStergeProblema=findViewById(R.id.btnStergeProblema);

        Intent intent=getIntent();
        problema=(Problema) intent.getSerializableExtra("problema");

        tvTitlu.setText(problema.getTitlu());
        tvDescriere.setText(problema.getDescriere());
        tvAdresaSector.setText(problema.getSector().toString()+" - "+problema.getAdresa());
        tvCategorie.setText(problema.getCategorieProblema().toString());
        tvAutor.setText("Autor: "+problema.getAutorUsername());
        List<Semnatura> semnaturi= AplicatieDB.getInstance(getApplicationContext()).getSemnaturaDAO().getSemnaturiForProblema(problema.getId());
        int nrSemnaturi=semnaturi.size();
        tvNrSemnaturi.setText("Număr semnături: "+String.valueOf(nrSemnaturi));


        SharedPreferences sp=getSharedPreferences("utilizatorCurent", MODE_PRIVATE);
        String username=sp.getString("username", "none");
        if(intent.hasExtra("isEditing")){
            btnEditeazaProblema.setVisibility(View.VISIBLE);
            btnStergeProblema.setVisibility(View.VISIBLE);
            btnRetrageSemnatura.setVisibility(View.GONE);
            btnSemneaza.setVisibility(View.GONE);
        }
        else {
            boolean hasSigned=false;

            if(AplicatieDB.getInstance(getApplicationContext()).getSemnaturaDAO().utilizatorulASemnatProblema(problema.getId(), username)==1){
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
        }

        btnSemneaza.setOnClickListener(view -> {
            btnSemneaza.setVisibility(View.GONE);
            btnRetrageSemnatura.setVisibility(View.VISIBLE);
            AplicatieDB.getInstance(getApplicationContext()).getSemnaturaDAO().insertSemnatura(new Semnatura(username, problema.getId()));
            updateNrSemnaturi(tvNrSemnaturi);
        });

        btnRetrageSemnatura.setOnClickListener(view -> {
            btnSemneaza.setVisibility(View.VISIBLE);
            btnRetrageSemnatura.setVisibility(View.GONE);
            AplicatieDB.getInstance(getApplicationContext()).getSemnaturaDAO().deleteSemnaturaByUsernameAndProblemId(username, problema.getId());
            updateNrSemnaturi(tvNrSemnaturi);
        });

        launcherEditProblema=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result->{
            if(result.getResultCode()==RESULT_OK){
                Intent intentThatStartedActivity=getIntent();
                setResult(RESULT_OK, intentThatStartedActivity);
                finish();
            }
        });

        btnEditeazaProblema.setOnClickListener(view -> {
            Intent intentNou=new Intent(getApplicationContext(), AddProblemActivity.class);
            intentNou.putExtra("problemaDeEditat",problema);
            launcherEditProblema.launch(intentNou);
        });

        btnStergeProblema.setOnClickListener(view->{
            AplicatieDB.getInstance(getApplicationContext()).getProblemaDAO().deleteProblema(problema);
            Intent intentThatStartedActivity=getIntent();
            setResult(RESULT_OK, intentThatStartedActivity);
            finish();
        });
    }

    private void updateNrSemnaturi(TextView tvNrSemnaturi) {
        int nrSemnaturi = AplicatieDB.getInstance(getApplicationContext()).getSemnaturaDAO().getSemnaturiForProblema(problema.getId()).size();
        tvNrSemnaturi.setText("Număr semnături: " + nrSemnaturi);
    }
}