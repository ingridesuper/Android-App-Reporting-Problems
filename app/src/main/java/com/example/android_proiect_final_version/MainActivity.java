package com.example.android_proiect_final_version;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_proiect_final_version.HttpsManager.HttpsManager;
import com.example.android_proiect_final_version.JsonParsers.ProblemaParser;
import com.example.android_proiect_final_version.JsonParsers.SemnaturaParser;
import com.example.android_proiect_final_version.JsonParsers.UtilizatorParser;
import com.example.android_proiect_final_version.database.AplicatieDB;
import com.example.android_proiect_final_version.models.Problema;
import com.example.android_proiect_final_version.models.Semnatura;
import com.example.android_proiect_final_version.models.Utilizator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String adresaURLProbleme="https://jsonkeeper.com/b/ABM8";
    String adresaURLUtilizatori="https://jsonkeeper.com/b/LMSU";
    String adresaURLSemnaturi="https://jsonkeeper.com/b/E68E";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fillTablesFromRetea();

        EditText etUsername=findViewById(R.id.etUsername);
        EditText etParola=findViewById(R.id.etParola);
        Button btnLogin=findViewById(R.id.btnLogin);
        Button btnSignup=findViewById(R.id.btnSignup);


        btnLogin.setOnClickListener(view -> {
            String username=etUsername.getText().toString();
            String parola=etParola.getText().toString();
            AplicatieDB aplicatieDB=AplicatieDB.getInstance(getApplicationContext());
            if(aplicatieDB.getUtilizatorDAO().validareLogin(username, parola)==1){
                Intent intent=new Intent(getApplicationContext(), HomePageActivity.class);
                startActivity(intent);
                SharedPreferences sp=getSharedPreferences("utilizatorCurent", MODE_PRIVATE);
                SharedPreferences.Editor editorUsername=sp.edit();
                editorUsername.putString("username", username);
                editorUsername.apply();
            }
            else {
                Toast.makeText(getApplicationContext(), "Username gresit sau parola incorecta.", Toast.LENGTH_SHORT).show();
                return;
            }
        });

        btnSignup.setOnClickListener(view-> {
            Intent intent=new Intent(getApplicationContext(), SignupActivity.class);
            startActivity(intent);
        });
    }

    private void fillTablesFromRetea(){
        fillProblemeTableFromRetea();
        fillUtilizatoriTableFromRetea();
        fillSemnaturiTableFromRetea();
    }

    private void fillProblemeTableFromRetea(){
        Thread thread=new Thread(){
            @Override
            public void run(){
                SharedPreferences prefs = getSharedPreferences("SyncedRetea", MODE_PRIVATE);
                boolean alreadySynced = prefs.getBoolean("problemeSynced", false);

                if(!alreadySynced){
                    HttpsManager httpsManager=new HttpsManager(adresaURLProbleme);
                    String rezultat=httpsManager.procesare();
                    List<Problema> problemeRetea= ProblemaParser.parseString(rezultat);
                    for(Problema problema:problemeRetea){
                        AplicatieDB.getInstance(getApplicationContext()).getProblemaDAO().insertProblema(problema);
                    }

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("problemeSynced", true);
                    editor.apply();
                }

            }
        };
        thread.start();
    }

    private void fillUtilizatoriTableFromRetea(){
        Thread thread=new Thread(){
            @Override
            public void run(){
                SharedPreferences prefs = getSharedPreferences("SyncedRetea", MODE_PRIVATE);
                boolean alreadySynced = prefs.getBoolean("utilizatoriSynced", false);

                if(!alreadySynced){
                    HttpsManager httpsManager=new HttpsManager(adresaURLUtilizatori);
                    String rezultat=httpsManager.procesare();
                    List<Utilizator> utilizatoriRetea= UtilizatorParser.parseString(rezultat);
                    for(Utilizator utilizator:utilizatoriRetea){
                        AplicatieDB.getInstance(getApplicationContext()).getUtilizatorDAO().insertUtilizator(utilizator);
                    }

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("utilizatoriSynced", true);
                    editor.apply();
                }

            }
        };
        thread.start();
    }

    private void fillSemnaturiTableFromRetea(){
        Thread thread=new Thread(){
            @Override
            public void run(){
                SharedPreferences prefs = getSharedPreferences("SyncedRetea", MODE_PRIVATE);
                boolean alreadySynced = prefs.getBoolean("semnaturiSynced", false);

                if(!alreadySynced){
                    HttpsManager httpsManager=new HttpsManager(adresaURLSemnaturi);
                    String rezultat=httpsManager.procesare();
                    List<Semnatura> semnaturiRetea= SemnaturaParser.parseString(rezultat);
                    for(Semnatura semnatura:semnaturiRetea){
                        AplicatieDB.getInstance(getApplicationContext()).getSemnaturaDAO().insertSemnatura(semnatura);
                    }

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("semnaturiSynced", true);
                    editor.apply();
                }

            }
        };
        thread.start();
    }
}