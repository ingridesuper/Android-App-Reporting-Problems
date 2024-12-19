package com.example.android_proiect_final_version;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_proiect_final_version.CustomAdapters.AdapterProblema;
import com.example.android_proiect_final_version.database.AplicatieDB;
import com.example.android_proiect_final_version.models.Problema;
import com.example.android_proiect_final_version.models.Utilizator;

import java.util.ArrayList;
import java.util.List;

public class ViewAltProfilActivity extends AppCompatActivity {
    Utilizator utilizator;
    List<Problema> probleme=new ArrayList<>();
    AdapterProblema adapterProblema;
    ActivityResultLauncher<Intent> launcherViewProblema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_alt_profil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView lvProbleme=findViewById(R.id.lvProbleme);
        TextView tvUsername=findViewById(R.id.tvUsername);
        TextView tvNumePrenumeSector=findViewById(R.id.tvNumePrenumeSector);
        TextView tvSubtitlu=findViewById(R.id.tvSubtitlu);
        TextView tvNrProblemeRaportate=findViewById(R.id.tvNrProblemeRaportate);

        Intent intent=getIntent();
        utilizator=(Utilizator)intent.getSerializableExtra("profil");

        tvUsername.setText(utilizator.getUsername());
        tvNumePrenumeSector.setText(utilizator.getNume()+" "+utilizator.getPrenume()+", rezident in Sectorul "+utilizator.getSector().toString().toLowerCase());
        int nrProblemeRaportate=AplicatieDB.getInstance(getApplicationContext()).getUtilizatorDAO().getNrProblemeRaportateDeUtilizator(utilizator.getUsername());
        tvNrProblemeRaportate.setText("Numar probleme raportate: "+nrProblemeRaportate);
        tvSubtitlu.setText("Acestea sunt problemele raportate de "+utilizator.getUsername()+": ");
        if(nrProblemeRaportate==0){
            tvSubtitlu.setVisibility(View.GONE);
        }

        probleme= AplicatieDB.getInstance(getApplicationContext()).getProblemaDAO().getProblemeOfAutor(utilizator.getUsername());
        adapterProblema=new AdapterProblema(getApplicationContext(), R.layout.view_problema, probleme, getLayoutInflater());
        lvProbleme.setAdapter(adapterProblema);


        lvProbleme.setOnItemClickListener(((adapterView, view, i, l) -> {
            Intent intent1=new Intent(getApplicationContext(), ViewProblemaActivity.class);
            intent1.putExtra("problema", probleme.get(i));
            startActivity(intent1);
        }));
    }
}