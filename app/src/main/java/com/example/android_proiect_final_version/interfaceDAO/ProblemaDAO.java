package com.example.android_proiect_final_version.interfaceDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.android_proiect_final_version.models.Problema;
import com.example.android_proiect_final_version.models.Utilizator;

import java.util.List;

@Dao
public interface ProblemaDAO {
    @Insert
    long insertProblema(Problema problema);
    @Query("select * from probleme")
    List<Problema> getAllProbleme();
    @Update
    void updateProblema(Problema problema);
    @Delete
    void deleteProblema(Problema problema);

    @Query("DELETE FROM Probleme")
    void deleteAllProbleme();

    @Query("select * from probleme where autorUsername=:username")
    List<Problema> getProblemeOfAutor(String username);
}
