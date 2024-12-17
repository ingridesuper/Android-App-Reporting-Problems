package com.example.android_proiect_final_version;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_proiect_final_version.CustomAdapters.AdapterProblema;
import com.example.android_proiect_final_version.database.AplicatieDB;
import com.example.android_proiect_final_version.models.Problema;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> launcherProblema;
    ListView lvProbleme;
    List<Problema> problemeList=new ArrayList<>();
    ArrayAdapter<Problema> adapterProblema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        problemeList= AplicatieDB.getInstance(getApplicationContext()).getProblemaDAO().getAllProbleme();
        lvProbleme=findViewById(R.id.lvProbleme);
        adapterProblema=new AdapterProblema(getApplicationContext(), R.layout.view_problema, problemeList, getLayoutInflater());
        lvProbleme.setAdapter(adapterProblema);

        launcherProblema=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result->{
            if(result.getResultCode()==RESULT_OK){
                problemeList.clear();
                problemeList.addAll(AplicatieDB.getInstance(getApplicationContext()).getProblemaDAO().getAllProbleme());
                adapterProblema.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Problema a fost adaugata cu succes!", Toast.LENGTH_SHORT).show();
            }
        });

        lvProbleme.setOnItemClickListener(((adapterView, view, i, l) -> {
            Intent intent=new Intent(getApplicationContext(), ViewProblemaActivity.class);
            intent.putExtra("problema", problemeList.get(i));
            startActivity(intent);
        }));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_topbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.miAddProblem){
            Intent intent=new Intent(getApplicationContext(), AddProblemActivity.class);
            launcherProblema.launch(intent);
        }
        else if(item.getItemId()==R.id.miProfile){
            Intent intent=new Intent(getApplicationContext(), ViewProfilActivity.class);
            launcherProblema.launch(intent);
        }
        return true;
    }
}