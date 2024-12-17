package com.example.android_proiect_final_version;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_proiect_final_version.database.AplicatieDB;

public class MainActivity extends AppCompatActivity {

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
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("username", username);
                editor.apply();
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

    }
}