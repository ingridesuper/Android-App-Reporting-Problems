package com.example.android_proiect_final_version;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_proiect_final_version.CustomAdapters.AdapterIdee;
import com.example.android_proiect_final_version.database.AplicatieDB;
import com.example.android_proiect_final_version.models.Idee;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ViewIdeiActivity extends AppCompatActivity {
    List<Idee> ideiList=new ArrayList<>();
    AdapterIdee adapterIdee;
    ActivityResultLauncher<Intent> launcherAddIdee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_idei);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView lvIdei=findViewById(R.id.lvIdei);
        FloatingActionButton fabAddIdee=findViewById(R.id.fabAddIdee);
        ideiList= AplicatieDB.getInstance(getApplicationContext()).getIdeeDAO().getAllIdei();
        adapterIdee=new AdapterIdee(getApplicationContext(), R.layout.view_idee, ideiList, getLayoutInflater());
        lvIdei.setAdapter(adapterIdee);

        launcherAddIdee=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result->{
            if(result.getResultCode()==RESULT_OK){
                Toast.makeText(getApplicationContext(), "Ideea ta a fost adăugată cu succes!", Toast.LENGTH_SHORT).show();
                ideiList.clear();
                ideiList.addAll(AplicatieDB.getInstance(getApplicationContext()).getIdeeDAO().getAllIdei());
                adapterIdee.notifyDataSetChanged();
            }
        });

        fabAddIdee.setOnClickListener(view -> {
            Intent intent=new Intent(getApplicationContext(), AddIdeeActivity.class);
            launcherAddIdee.launch(intent);
        });

        lvIdei.setOnItemClickListener(((adapterView, view, i, l) -> {
            Intent intent=new Intent(getApplicationContext(), ViewIdeeActivity.class);
            intent.putExtra("idee", ideiList.get(i));
            startActivity(intent);
        }));
    }
}