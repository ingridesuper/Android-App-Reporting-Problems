package com.example.android_proiect_final_version.InterfaceDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.android_proiect_final_version.models.SemnaturaIdee;

import java.util.List;

@Dao
public interface SemnaturaIdeeDAO {
    @Query("select * from semnaturi_idei")
    List<SemnaturaIdee> getAllSemnaturiIdei();

    @Query("select s.* from semnaturi_idei s join utilizatori u on s.username=u.username where u.username=:username")
    List<SemnaturaIdee> getSemnaturiIdeiOfUtilizator(String username);

    @Query("select s.* from semnaturi_idei s join idei i on s.ideeId=i.id where i.id=:id")
    List<SemnaturaIdee> getSemnaturiForIdee(Long id);

    @Insert
    void insertSemnaturaIdee(SemnaturaIdee semnaturaIdee);

    @Update
    void updateSemnaturaIdee(SemnaturaIdee semnaturaIdee);

    @Delete
    void deleteSemnaturaIdee(SemnaturaIdee semnaturaIdee);

    @Query("select count(*) from semnaturi_idei where ideeId=:ideeId and username=:username")
    int utilizatorulASemnatIdeea(Long ideeId, String username);

    @Query("delete from semnaturi_idei where username=:username and ideeId=:ideeId")
    void deleteSemnaturaIdeeByUsernameAndIdeeId(String username, Long ideeId);
}
