package com.example.android_proiect_final_version.CustomAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android_proiect_final_version.R;
import com.example.android_proiect_final_version.models.Problema;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterProblema extends ArrayAdapter<Problema> {
    private Context context;
    private int layoutId;
    private List<Problema> problemList;
    private LayoutInflater inflater;

    public AdapterProblema(@NonNull Context context, int resource, @NonNull List<Problema> problemList, LayoutInflater inflater){
        super(context, resource, problemList);
        this.context=context;
        this.layoutId=resource;
        this.problemList=problemList;
        this.inflater=inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=inflater.inflate(layoutId, parent, false);
        Problema problema=problemList.get(position);
        TextView tvTitlu=view.findViewById(R.id.tvTitlu);
        TextView tvDescriere=view.findViewById(R.id.tvDescriere);
        tvTitlu.setText(problema.getTitlu());
        tvDescriere.setText(problema.getDescriere());
        return view;
    }
}
