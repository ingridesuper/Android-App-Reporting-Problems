package com.example.android_proiect_final_version.InterfaceDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.android_proiect_final_version.models.Semnatura;

import java.util.List;

@Dao
public interface SemnaturaDAO {
    @Query("select * from semnaturi")
    List<Semnatura> getAllSemnaturi();

    @Query("select semnaturi.* from semnaturi join utilizatori on semnaturi.username=utilizatori.username where utilizatori.username=:username")
    List<Semnatura> getSemnaturiOfUtilizator(String username);

    @Query("select semnaturi.* from semnaturi join probleme on semnaturi.problemaId=probleme.id where probleme.id=:id")
    List<Semnatura> getSemnaturiForProblema(Long id);

    @Insert
    void insertSemnatura(Semnatura semnatura);

    @Update
    void updateSemnatura(Semnatura semnatura);

    @Delete
    void deleteSemnatura(Semnatura semnatura);

    @Query("select count(*) from semnaturi where problemaId=:problemaId and username=:username")
    int utilizatorulASemnatProblema(Long problemaId, String username);

    @Query("delete from semnaturi where username=:username and problemaId=:problemaId")
    void deleteSemnaturaByUsernameAndProblemId(String username, Long problemaId);
}
