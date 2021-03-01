package com.behemoth.repeat.stats.chapter;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.behemoth.repeat.R;
import com.behemoth.repeat.model.Chapter;
import com.behemoth.repeat.model.Repeat;
import com.behemoth.repeat.stats.mpandroid.CustomBarDataSet;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ChapterStatsFragment extends Fragment {

    private static final String ARG_CHAPTER = "chapter";
    private static final String ARG_REPEATS = "repeats";

    private Chapter chapter;
    private String jsonRepeats;
    private List<Repeat> repeats;
    private TreeMap<Integer, Integer> markData;
    private TreeMap<Integer, Integer> distribution;
    private int maxIncorrects;

    private PieChart pieChart;
    private BarChart barChart;

    public ChapterStatsFragment() { }

    public static final int[] REPEAT_COLORS = {
            Color.rgb(245, 245, 245),
            Color.rgb(213, 213, 213),
            Color.rgb(255, 213, 0),
            Color.rgb(248, 139, 6),
            Color.rgb(251, 35, 35)
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            chapter = getArguments().getParcelable(ARG_CHAPTER);
            jsonRepeats = getArguments().getString(ARG_REPEATS);
            getChapterData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_chapter_stats, container, false);

        pieChart = rootView.findViewById(R.id.pie_chart);
        setPieChart();

        barChart = rootView.findViewById(R.id.bar_chart);
        setBarChart();

        return rootView;
    }

    private void getChapterData(){
        Gson gson = new Gson();
        Type userListType = new TypeToken<ArrayList<Repeat>>(){}.getType();
        repeats = gson.fromJson(jsonRepeats, userListType);

        markData = new TreeMap<>();
        for(Repeat r : repeats){
            if(!r.isFinished()) continue;
            List<Integer> marks = r.getMark();
            for(int i = 0; i < marks.size(); i++){
                if(marks.get(i) <= 0){
                    int val;
                    if(markData.get(i+1)==null){
                        val = 1;
                    }else{
                        val = markData.get(i+1)+1;
                    }
                    markData.put(i+1, val);
                }
            }
        }

        maxIncorrects = Integer.MIN_VALUE;
        distribution = new TreeMap<>();
        for(Map.Entry<Integer, Integer> e : markData.entrySet()){
            if(e.getValue() > maxIncorrects) maxIncorrects = e.getValue();
            int val;
            if(distribution.get(e.getValue())==null){
                val = 1;
            }else{
                val = distribution.get(e.getValue())+1;
            }
            distribution.put(e.getValue(),val);
        }
    }

    private void setPieChart(){
        pieChart.setMinimumHeight(-50);
        pieChart.animateX(600, Easing.EaseInCubic); //애니메이션
        Description description = new Description();
        description.setText("오답 수 비중");
        description.setTextSize(10);
        pieChart.setDescription(description);

        ArrayList<PieEntry> values = new ArrayList<>();
        int total = markData.size();
        for(Map.Entry<Integer, Integer> e : distribution.entrySet()){
            float p = Math.round((e.getValue()/(float)total)*1000)/(float)10;
            values.add(new PieEntry(p, e.getKey()+"회"));
        }

        PieDataSet dataSet = new PieDataSet(values, "");
        dataSet.setSelectionShift(6f);
        dataSet.setColors(REPEAT_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return (value + "%").replace(".0", "");
            }
        });
        data.setValueTextSize(16f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
    }

    private void setBarChart(){
        barChart.setDescription(null);

        barChart.getAxisLeft().setLabelCount(maxIncorrects);
        barChart.getAxisLeft().setGranularity(1f);
        barChart.getAxisLeft().setDrawAxisLine(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getAxisRight().setDrawGridLines(false);

        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setLabelCount(markData.size());
        //barChart.getXAxis().setEnabled(false);
        //barChart.getXAxis().setDrawLabels(false);
        barChart.getXAxis().setDrawGridLines(false);

        ArrayList<BarEntry> values = new ArrayList<>();
        for(Map.Entry<Integer, Integer> e : markData.entrySet()){
            values.add(new BarEntry((float)e.getKey(), (float)e.getValue()));
        }

        CustomBarDataSet barDataSet = new CustomBarDataSet(values, null);
        barDataSet.setColors(REPEAT_COLORS);

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.5f);

        barChart.setData(barData);
    }

}