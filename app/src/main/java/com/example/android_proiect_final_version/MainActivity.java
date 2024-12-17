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
import com.example.android_proiect_final_version.database.AplicatieDB;
import com.example.android_proiect_final_version.models.Problema;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    String adresaURLProbleme="https://jsonkeeper.com/b/ABM8";

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

        fillProblemeTableFromRetea();


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
                SharedPreferences.Editor editortwo=sp.edit();
                editortwo.putString("username", username);
                editortwo.apply();
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

    void fillProblemeTableFromRetea(){
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
}