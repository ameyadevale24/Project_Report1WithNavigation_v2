package com.example.a300288675.project_report1;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReportsFragment extends Fragment {

    Button btnGenerateReport;
    BarChart chart;
    DatabaseHelper db;
    Spinner spnMonth, spnYear;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_reports, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //spinner for month
        spnMonth = (Spinner)getView().findViewById(R.id.spnMonth);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spnMonth);
            popupWindow.setHeight(500);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {}

        //spinner for year
        spnYear = (Spinner)getView().findViewById(R.id.spnYear);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spnYear);
            popupWindow.setHeight(500);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {}

        //set current month and year by default for the spinners
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int defaultMonth = localDate.getMonthValue();
        int defaultYear = localDate.getYear();
        spnMonth.setSelection(defaultMonth-1);      //subtract 1 from month because index starts from 0
        spnYear.setSelection(defaultYear-2000);     //subtract 2000 from month because index starts from 0 (index 0 -> value 2000)

        //get button
        btnGenerateReport = (Button)getView().findViewById(R.id.btnGenerateReport);

        //get chart
        chart = (BarChart)getView().findViewById(R.id.barReport);

        db = new DatabaseHelper(this.getContext());

        //generate report for the month and year selected
        btnGenerateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double[] expenses;
                String[] days;

                //get values of spinners
                int reportMonth = getMonthNumber(spnMonth.getSelectedItem().toString());
                int reportYear = Integer.parseInt(spnYear.getSelectedItem().toString());

                DailyExpenseReport dailyExpenseReport = db.getSavingDebtForMonth("user1", reportMonth, reportYear);

                expenses = dailyExpenseReport.getDailyBalance();
                days = dailyExpenseReport.getDays();

                ArrayList<BarEntry> entries = new ArrayList<>();

                for (int i=0; i<expenses.length; i++){
                    entries.add(new BarEntry((float) expenses[i],i));
                }

                BarDataSet barDataSet = new BarDataSet(entries,"Savings (+ve savings and -ve debt");

                ArrayList<String> labels = new ArrayList<>();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mmm");
                Calendar cal = Calendar.getInstance();
                for (int i=0; i<days.length; i++){
                    //cal.set(reportYear,reportMonth-1, i+1);
                    //labels.add(simpleDateFormat.format(cal.getTime()));
                    labels.add(days[i]);
                }

                BarData barData = new BarData(labels,barDataSet);
                chart.setData(barData);

                chart.setDescription("Savings/Debt");

                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                chart.animateY(2000);
            }
        });
    }

    public int getMonthNumber(String monthName){
        //return month number from month name
        int monthNumber=0;
        switch (monthName){
            case "Jan":
                monthNumber = 1;
                break;
            case "Feb":
                monthNumber = 2;
            break;
            case "March":
                monthNumber = 3;
            break;
            case "April":
                monthNumber = 4;
            break;
            case "May":
                monthNumber = 5;
                break;
            case "June":
                monthNumber = 6;
            break;
            case "July":
                monthNumber = 7;
            break;
            case "Aug":
                monthNumber = 8;
            break;
            case "Sep":
                monthNumber = 9;
            break;
            case "Oct":
                monthNumber = 10;
            break;
            case "Nov":
                monthNumber = 11;
            break;
            case "Dec":
                monthNumber = 12;
            break;
        }
        return  monthNumber;
    }


    public String getMonthName(int monthNumber){
        //return month name from month number
        String monthName="";
        switch (monthNumber){
            case 1:
                monthName = "Jan";
                break;
            case 2:
                monthName = "Feb";
                break;
            case 3:
                monthName = "March";
                break;
            case 4:
                monthName = "April";
                break;
            case 5:
                monthName = "May";
                break;
            case 6:
                monthName = "June";
                break;
            case 7:
                monthName = "July";
                break;
            case 8:
                monthName = "Aug";
                break;
            case 9:
                monthName = "Sep";
                break;
            case 10:
                monthName = "Oct";
                break;
            case 11:
                monthName = "Nov";
                break;
            case 12:
                monthName = "Dec";
                break;
        }
        return  monthName;
    }
}
