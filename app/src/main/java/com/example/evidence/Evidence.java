package com.example.evidence;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "evidenceTable")
public class Evidence
{
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "work_day")
    private String workDay;

    @ColumnInfo(name = "work_hours")
    private String workHours;

    public Evidence(String workDay, String workHours)
    {
        this.workDay = workDay;
        this.workHours = workHours;
    }

    public int getId() { return id; }
    public String getWorkDay() { return workDay; }
    public String getWorkHours() { return workHours; }

    public void setId(int id) { this.id = id; }
}
