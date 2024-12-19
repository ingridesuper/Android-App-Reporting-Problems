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
import com.example.android_proiect_final_version.models.Idee;
import com.example.android_proiect_final_version.models.Problema;

import java.util.List;

public class AdapterIdee extends ArrayAdapter<Idee> {
    private Context context;
    private int layoutId;
    private List<Idee> ideiList;
    private LayoutInflater inflater;

    public AdapterIdee(@NonNull Context context, int resource, @NonNull List<Idee> ideiList, LayoutInflater inflater){
        super(context, resource, ideiList);
        this.context=context;
        this.layoutId=resource;
        this.ideiList=ideiList;
        this.inflater=inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=inflater.inflate(layoutId, parent, false);
        Idee idee=ideiList.get(position);
        TextView tvTitlu=view.findViewById(R.id.tvTitlu);
        TextView tvDescriere=view.findViewById(R.id.tvDescriere);
        tvTitlu.setText(idee.getTitlu());
        tvDescriere.setText(idee.getDescriere());
        return view;
    }
}
