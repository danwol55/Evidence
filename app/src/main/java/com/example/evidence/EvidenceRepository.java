package com.example.evidence;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EvidenceRepository
{
    private EvidenceDao evidenceDao;
    private LiveData<List<Evidence>> evidenceList;
    private LiveData<List<String>> workHours;

    public EvidenceRepository(Application application)
    {
        EvidenceDatabase database = EvidenceDatabase.getInstance(application);
        evidenceDao = database.evidenceDao();
        evidenceList = evidenceDao.getAllEvidence();
        workHours = evidenceDao.getWorkHours();
    }

    public void insert(Evidence evidence)
    {
        new InsertEvidenceAsyncTask(evidenceDao).execute(evidence);
    }
    public void update(Evidence evidence)
    {
        new UpdateEvidenceAsyncTask(evidenceDao).execute(evidence);
    }
    public void delete(Evidence evidence)
    {
        new DeleteEvidenceAsyncTask(evidenceDao).execute(evidence);
    }
    public void deleteAllEvidence()
    {
        new DeleteAllEvidenceAsyncTask(evidenceDao).execute();
    }

    public LiveData<List<Evidence>> getAllEvidence()
    {
        return evidenceList;
    }

    public LiveData<List<String>> getWorkHours() { return workHours; }


    private static class InsertEvidenceAsyncTask extends AsyncTask<Evidence, Void, Void>
    {
        private EvidenceDao evidenceDao;

        private InsertEvidenceAsyncTask(EvidenceDao evidenceDao)
        {
            this.evidenceDao = evidenceDao;
        }

        @Override
        protected Void doInBackground(Evidence... evidences)
        {
            evidenceDao.insert(evidences[0]);
            return null;
        }
    }
    private static class UpdateEvidenceAsyncTask extends AsyncTask<Evidence, Void, Void>
    {
        private EvidenceDao evidenceDao;

        private UpdateEvidenceAsyncTask(EvidenceDao evidenceDao)
        {
            this.evidenceDao = evidenceDao;
        }

        @Override
        protected Void doInBackground(Evidence... evidences)
        {
            evidenceDao.update(evidences[0]);
            return null;
        }
    }
    private static class DeleteEvidenceAsyncTask extends AsyncTask<Evidence, Void, Void>
    {
        private EvidenceDao evidenceDao;

        private DeleteEvidenceAsyncTask(EvidenceDao evidenceDao)
        {
            this.evidenceDao = evidenceDao;
        }

        @Override
        protected Void doInBackground(Evidence... evidences)
        {
            evidenceDao.delete(evidences[0]);
            return null;
        }
    }
    private static class DeleteAllEvidenceAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private EvidenceDao evidenceDao;

        private DeleteAllEvidenceAsyncTask(EvidenceDao evidenceDao)
        {
            this.evidenceDao = evidenceDao;
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            evidenceDao.deleteAllEvidence();
            return null;
        }
    }
}
