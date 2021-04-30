package com.example.evidence;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Evidence.class}, version = 1)
public abstract class EvidenceDatabase extends RoomDatabase
{
    public abstract EvidenceDao evidenceDao();
    private static EvidenceDatabase instance;

    public static synchronized EvidenceDatabase getInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    EvidenceDatabase.class, "evidence_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db)
        {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private EvidenceDao evidenceDao;

        private PopulateDbAsyncTask(EvidenceDatabase db)
        {
            evidenceDao = db.evidenceDao();
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            evidenceDao.insert(new Evidence("09-07-20", "3:08"));
            evidenceDao.insert(new Evidence("10-07-20", "4:20"));
            evidenceDao.insert(new Evidence("12-07-20", "3:30"));
            return null;
        }
    }
}
