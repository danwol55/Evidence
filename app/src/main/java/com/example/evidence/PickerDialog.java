package com.example.evidence;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class PickerDialog extends DialogFragment
{
    private NumberPicker hourPicker;
    private NumberPicker minutePicker;
    private NumberPicker dayPicker;
    private NumberPicker monthPicker;
    private PickerDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.work_date_picker, null);

        builder.setView(view);
        builder.setTitle("podaj liczbę godzin");
        builder.setPositiveButton(R.string.adding, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String day="0", month="0";
                int minute = minutePicker.getValue();
                int month1 = monthPicker.getValue();
                String hours = Integer.toString(hourPicker.getValue());
                String minutes = Integer.toString(minutePicker.getValue());
                if(dayPicker.getValue()!=0 && monthPicker.getValue()!=0)
                {
                    day = Integer.toString(dayPicker.getValue());
                    month = Integer.toString(monthPicker.getValue());
                }
                String workTime = "", workDate = "";
                if(minute<10) workTime = hours + ":0" + minutes;
                else workTime = hours + ':' + minutes;
                if(month1==1) workDate = "Jan " + day + ", 2020";
                if(month1==2) workDate = "Feb " + day + ", 2020";
                if(month1==3) workDate = "Mar " + day + ", 2020";
                if(month1==4) workDate = "Apr " + day + ", 2020";
                if(month1==5) workDate = "May " + day + ", 2020";
                if(month1==6) workDate = "Jun " + day + ", 2020";
                if(month1==7) workDate = "Jul " + day + ", 2020";
                if(month1==8) workDate = "Aug " + day + ", 2020";
                if(month1==9) workDate = "Sep " + day + ", 2020";
                if(month1==10) workDate = "Oct " + day + ", 2020";
                if(month1==11) workDate = "Nov " + day + ", 2020";
                if(month1==12) workDate = "Dec " + day + ", 2020";

                if(day.equals("0") || month.equals("0")) listener.insertData(workTime);
                else listener.insertData(workDate, workTime);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Objects.requireNonNull(PickerDialog.this.getDialog()).cancel();
            }

        });

        hourPicker = view.findViewById(R.id.hour_picker);
        minutePicker = view.findViewById(R.id.minute_picker);
        dayPicker = view.findViewById(R.id.day_picker);
        monthPicker = view.findViewById(R.id.month_picker);

        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(12);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        dayPicker.setMinValue(0);
        dayPicker.setMaxValue(31);
        monthPicker.setMinValue(0);
        monthPicker.setMaxValue(12);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        try {
            listener = (PickerDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement PickerDialogListener");
        }
    }

    public interface PickerDialogListener
    {
        void insertData(String workTime);
        void insertData(String workDate, String workTime);
    }
}
