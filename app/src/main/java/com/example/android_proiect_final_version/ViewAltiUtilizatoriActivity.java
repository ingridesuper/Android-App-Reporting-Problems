package com.example.android_proiect_final_version;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_proiect_final_version.CustomAdapters.AdapterUtilizator;
import com.example.android_proiect_final_version.database.AplicatieDB;
import com.example.android_proiect_final_version.enums.Sector;
import com.example.android_proiect_final_version.models.Utilizator;

import java.util.ArrayList;
import java.util.List;

public class ViewAltiUtilizatoriActivity extends AppCompatActivity {
    List<Utilizator> utilizatori=new ArrayList<>();
    AdapterUtilizator adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_alti_utilizatori);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView lvUtilizatori=findViewById(R.id.lvUtilizatori);
        Button btnSeeVecini=findViewById(R.id.btnSeeVecini);
        Button btnSeeAllOtherUsers=findViewById(R.id.btnSeeAllOtherUsers);

        SharedPreferences sp=getSharedPreferences("utilizatorCurent", MODE_PRIVATE);
        String currentUser=sp.getString("username", "none");

        utilizatori= AplicatieDB.getInstance(getApplicationContext()).getUtilizatorDAO().getUtilizatoriOtherThanCurrentUser(currentUser);
        adapter=new AdapterUtilizator(getApplicationContext(), R.layout.view_utilizator, utilizatori, getLayoutInflater());
        lvUtilizatori.setAdapter(adapter);

        btnSeeVecini.setOnClickListener(view->{
            btnSeeVecini.setVisibility(View.GONE);
            btnSeeAllOtherUsers.setVisibility(View.VISIBLE);

            Sector sector=AplicatieDB.getInstance(getApplicationContext()).getUtilizatorDAO().getSectorOfUtilizator(currentUser);

            utilizatori.clear();
            utilizatori.addAll( AplicatieDB.getInstance(getApplicationContext()).getUtilizatorDAO().getVeciniDeSector(sector, currentUser));
            adapter.notifyDataSetChanged();
        });

        btnSeeAllOtherUsers.setOnClickListener(view->{
            btnSeeVecini.setVisibility(View.VISIBLE);
            btnSeeAllOtherUsers.setVisibility(View.GONE);

            utilizatori.clear();
            utilizatori.addAll( AplicatieDB.getInstance(getApplicationContext()).getUtilizatorDAO().getUtilizatoriOtherThanCurrentUser(currentUser));
            adapter.notifyDataSetChanged();
        });

        lvUtilizatori.setOnItemClickListener(((adapterView, view, i, l) -> {
            Intent intent=new Intent(getApplicationContext(), ViewAltProfilActivity.class);
            intent.putExtra("profil", utilizatori.get(i));
            startActivity(intent);
        }));

    }
}