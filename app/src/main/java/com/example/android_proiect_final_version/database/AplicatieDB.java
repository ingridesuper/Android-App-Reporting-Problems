package com.example.android_proiect_final_version.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.android_proiect_final_version.interfaceDAO.ProblemaDAO;
import com.example.android_proiect_final_version.interfaceDAO.SemnaturaDAO;
import com.example.android_proiect_final_version.interfaceDAO.UtilizatorDAO;
import com.example.android_proiect_final_version.models.Problema;
import com.example.android_proiect_final_version.models.Semnatura;
import com.example.android_proiect_final_version.models.Utilizator;

@Database(entities = {Utilizator.class, Problema.class, Semnatura.class}, version=3, exportSchema = false)
public abstract class AplicatieDB extends RoomDatabase {
    private static AplicatieDB instance;
    public static final String databaseName="database.db";
    public static AplicatieDB getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context, AplicatieDB.class, databaseName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract UtilizatorDAO getUtilizatorDAO();
    public abstract ProblemaDAO getProblemaDAO();
    public abstract SemnaturaDAO getSemnaturaDAO();
}
