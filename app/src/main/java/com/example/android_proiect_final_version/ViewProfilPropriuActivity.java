package com.example.android_proiect_final_version;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class ViewProfilPropriuActivity extends AppCompatActivity {
    Utilizator utilizator;
    List<Problema> probleme=new ArrayList<>();
    AdapterProblema adapterProblema;
    String username;
    ActivityResultLauncher<Intent> launcherEditProfil;
    ActivityResultLauncher<Intent> launcherEditProblema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_profil_propriu);
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
        Button btnViewDateContact=findViewById(R.id.btnSeeDateContact);

        SharedPreferences sp=getSharedPreferences("utilizatorCurent", MODE_PRIVATE);
        username=sp.getString("username", "none");
        utilizator= AplicatieDB.getInstance(getApplicationContext()).getUtilizatorDAO().getUtilizatorWithUsername(username);

        probleme=AplicatieDB.getInstance(getApplicationContext()).getProblemaDAO().getProblemeOfAutor(username);
        adapterProblema=new AdapterProblema(getApplicationContext(), R.layout.view_problema, probleme, getLayoutInflater());
        lvProbleme.setAdapter(adapterProblema);

        tvUsername.setText(username);
        tvNumePrenumeSector.setText(utilizator.getNume()+" "+utilizator.getPrenume()+", rezident in Sectorul "+utilizator.getSector().toString().toLowerCase());
        int nrProblemeRaportate=AplicatieDB.getInstance(getApplicationContext()).getUtilizatorDAO().getNrProblemeRaportateDeUtilizator(username);
        tvNrProblemeRaportate.setText("Numar probleme raportate: "+nrProblemeRaportate);

        if(nrProblemeRaportate==0){
            tvSubtitlu.setVisibility(View.GONE);
        }

        launcherEditProfil=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result->{
            if(result.getResultCode()==RESULT_OK){
                utilizator=AplicatieDB.getInstance(getApplicationContext()).getUtilizatorDAO().getUtilizatorWithUsername(username);
                tvNumePrenumeSector.setText(utilizator.getNume()+" "+utilizator.getPrenume()+", rezident in "+utilizator.getSector());
            }
        });

        fabEditProfile.setOnClickListener(view -> {
            Intent intent=new Intent(getApplicationContext(), EditProfilActivity.class);
            launcherEditProfil.launch(intent);
        });

        launcherEditProblema=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result->{
            if (result.getResultCode()==RESULT_OK){
                probleme.clear();
                probleme.addAll(AplicatieDB.getInstance(getApplicationContext()).getProblemaDAO().getProblemeOfAutor(username));
                adapterProblema.notifyDataSetChanged();
                int nrProblemeRaportate2=AplicatieDB.getInstance(getApplicationContext()).getUtilizatorDAO().getNrProblemeRaportateDeUtilizator(username);
                tvNrProblemeRaportate.setText("Numar probleme raportate: "+nrProblemeRaportate2);
            }
        });

        lvProbleme.setOnItemClickListener(((adapterView, view, i, l) -> {
            Intent intent=new Intent(getApplicationContext(), ViewProblemaActivity.class);
            intent.putExtra("isEditing", true);
            intent.putExtra("problema", probleme.get(i));
            launcherEditProblema.launch(intent);
        }));

        btnViewDateContact.setOnClickListener(view -> {
            Intent intent=new Intent(getApplicationContext(), ViewDateContactActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intentThatStartedThisActivity=getIntent();
        setResult(RESULT_OK, intentThatStartedThisActivity);
    }
}