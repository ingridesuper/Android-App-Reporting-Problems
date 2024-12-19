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
import com.example.android_proiect_final_version.models.Utilizator;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterUtilizator extends ArrayAdapter<Utilizator> {
    private Context context;
    private int layoutId;
    private List<Utilizator> utilizatorList;
    private LayoutInflater inflater;

    public AdapterUtilizator(@NonNull Context context, int resource, @NonNull List<Utilizator> utilizatorList, LayoutInflater inflater){
        super(context, resource, utilizatorList);
        this.context=context;
        this.layoutId=resource;
        this.utilizatorList=utilizatorList;
        this.inflater=inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=inflater.inflate(layoutId, parent, false);
        Utilizator utilizator=utilizatorList.get(position);
        TextView tvUsername=view.findViewById(R.id.tvUsername);
        TextView tvNumePrenumeSector=view.findViewById(R.id.tvNumePrenumeSector);
        tvUsername.setText(utilizator.getUsername());
        tvNumePrenumeSector.setText(utilizator.getNume()+" "+utilizator.getPrenume()+", rezident in Sectorul "+utilizator.getSector().toString().toLowerCase());
        return view;
    }
}
