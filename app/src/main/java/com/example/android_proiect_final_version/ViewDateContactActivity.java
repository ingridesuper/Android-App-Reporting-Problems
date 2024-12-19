package com.example.android_proiect_final_version;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ViewDateContactActivity extends AppCompatActivity {
    private EditText etEntitate;
    private EditText etAdresaMail;
    private EditText etTelefon;
    private Button btnGolireDate;
    private Button btnSave;
    private Button btnDelete;
    private ListView lvDateContact;
    private List<DateContact> dateContactList=new ArrayList<>();
    private FirebaseService firebaseService;
    private int indexDateContactSelectat=-1;
    ArrayAdapter<DateContact> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_date_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initComponente();
        firebaseService=FirebaseService.getInstance();
        firebaseService.addDateContactListener(dataChangeCallBack());
    }

    private Callback<List<DateContact>> dataChangeCallBack() {
        return rezultat -> {
            dateContactList.clear();
            dateContactList.addAll(rezultat);
            adapter.notifyDataSetChanged();
            golireText();
        };
    }

    private void initComponente(){
        etEntitate=findViewById(R.id.etEntitate);
        etAdresaMail=findViewById(R.id.etAdresaMail);
        etTelefon=findViewById(R.id.etTelefon);
        btnGolireDate=findViewById(R.id.btnGolireDate);
        btnDelete=findViewById(R.id.btnDelete);
        btnSave=findViewById(R.id.btnSave);
        lvDateContact=findViewById(R.id.lvDateContact);
        adapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, dateContactList);
        lvDateContact.setAdapter(adapter);

        btnGolireDate.setOnClickListener(golireDateEventListener());
        btnSave.setOnClickListener(saveEventListener());
        btnDelete.setOnClickListener(deleteEventListener());

        lvDateContact.setOnItemClickListener(dateContactSelectatEventListener());
    }

    private AdapterView.OnItemClickListener dateContactSelectatEventListener() {
        return (adapterView, view, i, l) -> {
            indexDateContactSelectat=i;
            DateContact dateContact=dateContactList.get(i);
            etEntitate.setText(dateContact.getEntitate());
            etAdresaMail.setText(dateContact.getMail());
            etTelefon.setText(dateContact.getTelefon());
        };
    }

    private View.OnClickListener deleteEventListener() {
        return view -> {
            if(indexDateContactSelectat!=-1){
                firebaseService.delete(dateContactList.get(indexDateContactSelectat));
            }
        };
    }

    private View.OnClickListener saveEventListener() {
        return view -> {
            if(validare()){
                if(indexDateContactSelectat==-1){ //inserare
                    DateContact dateContact=actualizareDateContactFromUI(null);
                    firebaseService.insert(dateContact);
                }
                else {
                    //suntem pe update
                    DateContact dateContact=actualizareDateContactFromUI(dateContactList.get(indexDateContactSelectat).getId());
                    firebaseService.update(dateContact);
                }
            }
        };
    }

    private DateContact actualizareDateContactFromUI(String id) {
        DateContact dateContact=new DateContact();
        dateContact.setId(id);
        dateContact.setEntitate(etEntitate.getText().toString());
        dateContact.setMail(etAdresaMail.getText().toString());
        dateContact.setTelefon(etTelefon.getText().toString());
        return dateContact;
    }

    private boolean validare() {
        if(etEntitate.getText()==null || etEntitate.getText().toString().isEmpty() || etAdresaMail.getText()==null || etAdresaMail.getText().toString().isEmpty() || etTelefon.getText()==null || etTelefon.getText().toString().isEmpty() ){
            Toast.makeText(getApplicationContext(), "Completati toate câmpurile", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private View.OnClickListener golireDateEventListener() {
        return view -> golireText();
    }

    private void golireText() {
        etEntitate.setText(null);
        etAdresaMail.setText(null);
        etTelefon.setText(null);
        indexDateContactSelectat=-1;
    }
}