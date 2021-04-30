package com.example.evidence;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PickerDialog.PickerDialogListener, NoticeDialog.NoticeDialogListener
{
    private EvidenceViewModel evidenceViewModel;
    final MyAdapter recyclerAdapter = new MyAdapter();
    private List<String> allWorkTime = new ArrayList<>();
    private RecyclerView recyclerView;
    private Button addButton;
    private TextView allWork;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        allWork = findViewById(R.id.allWork);
        addButton = findViewById(R.id.addingButton);

        addButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                openDialog();
            }
        });

        recyclerView.setAdapter(recyclerAdapter);
        evidenceViewModel = ViewModelProviders.of(this).get(EvidenceViewModel.class);
        evidenceViewModel.getWorkHours().observe(this, new Observer<List<String>>()
        {
            @Override
            public void onChanged(@Nullable final List<String> strings)
            {
                Integer allHours = 0;
                Integer allMinutes = 0;

                assert strings != null;
                for(String item : strings)
                {
                    String hours = "";
                    String minutes = "";
                    boolean addingHours = true;
                    for(int i=0; i<item.length(); i++)
                    {
                        if(item.charAt(i)==':')
                        {
                            addingHours = false;
                            continue;
                        }
                        if(addingHours)
                        {
                            hours += item.charAt(i);
                        }
                        else
                        {
                            minutes += item.charAt(i);
                        }
                    }
                    allHours += Integer.parseInt(hours);
                    allMinutes += Integer.parseInt(minutes);
                }
                if(allMinutes>=60)
                {
                    allHours += allMinutes/60;
                    allMinutes = allMinutes%60;
                }
                String workTime;
                if(allMinutes<10)
                {
                    workTime = allHours.toString() + ":0" + allMinutes.toString();
                }
                else workTime = allHours.toString() + ':' + allMinutes.toString();
                allWork.setText(workTime);
            }
        });
        evidenceViewModel.getEvidenceList().observe(this, new Observer<List<Evidence>>()
        {
            @Override
            public void onChanged(List<Evidence> evidences)
            {
                recyclerAdapter.setEvidence(evidences);
            }
        });
    }

    public void openDialog()
    {
        PickerDialog dialog = new PickerDialog();
        dialog.show(getSupportFragmentManager(), "picker dialog");
    }
    public void openNoticeDialog()
    {
        NoticeDialog dialog = new NoticeDialog();
        dialog.show(getSupportFragmentManager(), "notice dialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.delete_all:
                openNoticeDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
        {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target)
            {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
            {
                evidenceViewModel.delete(recyclerAdapter.getEvidenceAt(viewHolder.getAdapterPosition()));
            }
        });
        touchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void insertData(String workTime)
    {

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        Evidence evidence = new Evidence(currentDate, workTime);
        evidenceViewModel.insert(evidence);
        Toast.makeText(MainActivity.this, "evidence saved", Toast.LENGTH_SHORT).show();

    }
    public void insertData(String workDate, String workTime)
    {

        Evidence evidence = new Evidence(workDate, workTime);
        evidenceViewModel.insert(evidence);
        Toast.makeText(MainActivity.this, "evidence saved", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog)
    {
        evidenceViewModel.deleteAllEvidence();
        Toast.makeText(MainActivity.this, "evidence deleted", Toast.LENGTH_SHORT)
                .show();
    }
}
