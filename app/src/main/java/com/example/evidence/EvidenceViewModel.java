package com.example.evidence;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EvidenceViewModel extends AndroidViewModel
{
    private EvidenceRepository repository;
    private LiveData<List<Evidence>> evidenceList;
    private LiveData<List<String>> workHours;

    public LiveData<List<Evidence>> getEvidenceList()
    {
        return evidenceList;
    }

    public LiveData<List<String>> getWorkHours() { return workHours; }

    public EvidenceViewModel(@NonNull Application application)
    {
        super(application);
        repository = new EvidenceRepository(application);
        evidenceList = repository.getAllEvidence();
        workHours = repository.getWorkHours();
    }

    public void insert(Evidence evidence)
    {
        repository.insert(evidence);
    }
    public void update(Evidence evidence)
    {
        repository.update(evidence);
    }
    public void delete(Evidence evidence)
    {
        repository.delete(evidence);
    }
    public void deleteAllEvidence()
    {
        repository.deleteAllEvidence();
    }
}
