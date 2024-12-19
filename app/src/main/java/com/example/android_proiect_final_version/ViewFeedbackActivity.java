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

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class ViewFeedbackActivity extends AppCompatActivity {
    private EditText etTitlu;
    private EditText etText;
    private Button btnGolireDate;
    private Button btnSave;
    private Button btnDelete;
    private ListView lvFeedback;
    private List<Feedback> feedbackList=new ArrayList<>();
    private FirebaseService firebaseService;
    private int indexFeedbackSelectat=-1;
    ArrayAdapter<Feedback> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_feedback);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initComponente();
        firebaseService=FirebaseService.getInstance();
        firebaseService.addFeedbackListener(dataChangeCallback());
    }

    private Callback<List<Feedback>> dataChangeCallback() {
        return rezultat -> {
            feedbackList.clear();
            feedbackList.addAll(rezultat);
            adapter.notifyDataSetChanged();
            golireText();
        };
    }

    private void initComponente() {
        etText=findViewById(R.id.etText);
        etTitlu=findViewById(R.id.etTitlu);
        btnGolireDate=findViewById(R.id.btnGolireDate);
        btnDelete=findViewById(R.id.btnDelete);
        btnSave=findViewById(R.id.btnSave);
        lvFeedback=findViewById(R.id.lvFeedback);
        adapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, feedbackList);
        lvFeedback.setAdapter(adapter);

        btnGolireDate.setOnClickListener(golireDateEventListener());
        btnSave.setOnClickListener(saveEventListener());
        btnDelete.setOnClickListener(deleteEventListener());

        lvFeedback.setOnItemClickListener(feedbackSelectatEventListener());
    }

    private AdapterView.OnItemClickListener feedbackSelectatEventListener() {
        return (adapterView, view, i, l) -> {
            indexFeedbackSelectat=i;
            Feedback feedback=feedbackList.get(i);
            etText.setText(feedback.getText());
            etTitlu.setText(feedback.getTitlu());
        };
    }

    private View.OnClickListener deleteEventListener() {
        return view -> {
            if(indexFeedbackSelectat!=-1){
                firebaseService.delete(feedbackList.get(indexFeedbackSelectat));
            }
        };
    }

    private View.OnClickListener saveEventListener() {
        return view -> {
            if(validare()){
                if(indexFeedbackSelectat==-1){ //inserare
                    Feedback feedback=actualizareFeedbackFromUI(null);
                    firebaseService.insert(feedback);
                }
                else {
                    //suntem pe update
                    Feedback feedback=actualizareFeedbackFromUI(feedbackList.get(indexFeedbackSelectat).getId());
                    firebaseService.update(feedback);
                }
            }
        };
    }

    private Feedback actualizareFeedbackFromUI(String id) {
        Feedback feedback=new Feedback();
        feedback.setId(id);
        feedback.setText(etText.getText().toString());
        feedback.setTitlu(etTitlu.getText().toString());
        return feedback;
    }

    private boolean validare() {
        if(etTitlu.getText()==null || etTitlu.getText().toString().isEmpty() || etText.getText()==null || etText.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Completati toate câmpurile", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private View.OnClickListener golireDateEventListener() {
        return view -> golireText();
    }

    private void golireText() {
        etText.setText(null);
        etTitlu.setText(null);
        indexFeedbackSelectat=-1;
    }
}