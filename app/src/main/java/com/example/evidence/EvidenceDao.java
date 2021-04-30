package com.example.evidence;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EvidenceDao
{

    @Insert
    void insert(Evidence evidence);

    @Update
    void update(Evidence evidence);

    @Delete
    void delete(Evidence evidence);

    @Query("DELETE FROM evidenceTable")
    void deleteAllEvidence();

    @Query("SELECT * FROM evidenceTable")
    LiveData<List<Evidence>> getAllEvidence();

    @Query("SELECT work_hours FROM evidenceTable")
    LiveData<List<String>> getWorkHours();

    @Query("SELECT * FROM evidenceTable WHERE id IN (:id)")
    LiveData<List<Evidence>> loadById(int[] id);
}
