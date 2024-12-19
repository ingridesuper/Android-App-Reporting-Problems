package com.example.android_proiect_final_version.InterfaceDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.example.android_proiect_final_version.enums.Sector;
import com.example.android_proiect_final_version.models.Utilizator;

@Dao
public interface UtilizatorDAO {
    @Update
    void updateUtilizator(Utilizator utilizator);
    @Query("select * from utilizatori")
    List<Utilizator> getUtilizatori();
    @Insert
    void insertUtilizator(Utilizator utilizator);

    @Delete
    void deleteUtilizator(Utilizator utilizator);

    @Query("select count(*) from utilizatori where username=:username")
    int countUtilizatoriUsername(String username);

    @Query("select count(*) from utilizatori where username=:username and parola=:parola")
    int validareLogin(String username, String parola);

    @Query("select * from utilizatori where username=:username")
    Utilizator getUtilizatorWithUsername(String username);

    @Query("select count(*) from probleme where autorUsername=:username")
    int getNrProblemeRaportateDeUtilizator(String username);

    @Query("select * from utilizatori where username not in (:username)")
    List<Utilizator> getUtilizatoriOtherThanCurrentUser(String username);

    @Query("select * from utilizatori where sector=:sector and username not in (:username)")
    List<Utilizator> getVeciniDeSector(Sector sector, String username);

    @Query("select sector from utilizatori where username=:username")
    Sector getSectorOfUtilizator(String username);
}
