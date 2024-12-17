package com.example.android_proiect_final_version;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_proiect_final_version.CustomAdapters.AdapterProblema;
import com.example.android_proiect_final_version.database.AplicatieDB;
import com.example.android_proiect_final_version.models.Problema;
import com.example.android_proiect_final_version.models.Utilizator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ViewProfilActivity extends AppCompatActivity {
    Utilizator utilizator;
    List<Problema> probleme=new ArrayList<>();
    AdapterProblema adapterProblema;
    ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_profil);
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
        FloatingActionButton fabEditProfile=findViewById(R.id.fabEditProfile);
        TooltipCompat.setTooltipText(fabEditProfile, "Editeaza-ti profilul!");

        SharedPreferences sp=getSharedPreferences("utilizatorCurent", MODE_PRIVATE);
        String username=sp.getString("username", "none");
        utilizator= AplicatieDB.getInstance(getApplicationContext()).getUtilizatorDAO().getUtilizatorWithUsername(username);

        probleme=AplicatieDB.getInstance(getApplicationContext()).getProblemaDAO().getProblemeOfAutor(username);
        adapterProblema=new AdapterProblema(getApplicationContext(), R.layout.view_problema, probleme, getLayoutInflater());
        lvProbleme.setAdapter(adapterProblema);

        tvUsername.setText(username);
        tvNumePrenumeSector.setText(utilizator.getNume()+" "+utilizator.getPrenume()+", rezident in "+utilizator.getSector());
        int nrProblemeRaportate=AplicatieDB.getInstance(getApplicationContext()).getUtilizatorDAO().getNrProblemeRaportateDeUtilizator(username);
        tvNrProblemeRaportate.setText("Numar probleme raportate: "+nrProblemeRaportate);

        if(nrProblemeRaportate==0){
            tvSubtitlu.setVisibility(View.GONE);
        }

        launcher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result->{
            if(result.getResultCode()==RESULT_OK){
                utilizator=AplicatieDB.getInstance(getApplicationContext()).getUtilizatorDAO().getUtilizatorWithUsername(username);
                tvNumePrenumeSector.setText(utilizator.getNume()+" "+utilizator.getPrenume()+", rezident in "+utilizator.getSector());
            }
        });

        fabEditProfile.setOnClickListener(view -> {
            Intent intent=new Intent(getApplicationContext(), EditProfilActivity.class);
            launcher.launch(intent);
        });
    }
}