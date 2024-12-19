package com.example.android_proiect_final_version.InterfaceDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.android_proiect_final_version.models.Idee;

import java.util.List;

@Dao
public interface IdeeDAO {
    @Query("select * from idei")
    List<Idee> getAllIdei();

    @Insert
    void insertIdee(Idee idee);

    @Update
    void updateIdee(Idee idee);

    @Delete
    void deleteIdee(Idee idee);

    @Query("select * from idei where autorUsername=:username")
    List<Idee> getIdeiOfAutor(String username);
}
