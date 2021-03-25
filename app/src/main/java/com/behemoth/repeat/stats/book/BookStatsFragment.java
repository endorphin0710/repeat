package com.behemoth.repeat.stats.book;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.behemoth.repeat.R;
import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.model.Chapter;
import com.behemoth.repeat.model.Repeat;
import com.behemoth.repeat.util.Constants;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookStatsFragment extends Fragment {

    private static final String ARG_BOOK = "book";

    private List<Chapter> chapters;

    private LineDataSet avrDataSet;
    private LineDataSet minDataSet;
    private LineDataSet maxDataSet;

    private Book book;
    private boolean noStats;

    public BookStatsFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            book = getArguments().getParcelable(ARG_BOOK);
            chapters = book.getChapter();

            List<Entry> minData = new ArrayList<>();
            List<Entry> maxData = new ArrayList<>();
            List<Entry> avrData = new ArrayList<>();
            for(Chapter c : chapters){
                List<Integer> chapterScore = new ArrayList<>();
                List<Repeat> repeats = c.getRepeat();
                for(Repeat r : repeats){
                    if(!r.isFinished()) continue;
                    int total = r.getProblemCount();
                    int score = r.getScore();
                    int s = (int)((score/(double)total)*100);
                    chapterScore.add(s);
                }
                if(chapterScore.size() <= 0) continue;
                Collections.sort(chapterScore);

                int sum = 0;
                for(int i : chapterScore){
                    sum += i;
                }

                int avr = (int)((sum/(double)chapterScore.size()));
                int min = chapterScore.get(0);
                int max = chapterScore.get(chapterScore.size()-1);

                int chapterNumber = c.getChapterNumber();
                minData.add(new Entry(chapterNumber, min));
                maxData.add(new Entry(chapterNumber, max));
                avrData.add(new Entry(chapterNumber, avr));
            }

            avrDataSet = new LineDataSet(avrData, Constants.LABEL_AVERAGE_SCORE);
            maxDataSet = new LineDataSet(maxData, Constants.LABEL_MAX_SCORE);
            minDataSet = new LineDataSet(minData, Constants.LABEL_MIN_SCORE);

            if(avrData.size() <= 0) noStats = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_book_stats, container, false);

        if(noStats){
            ConstraintLayout noStats = rootView.findViewById(R.id.noStats);
            noStats.setVisibility(View.VISIBLE);
            return rootView;
        }

        int chapterNumber = chapters.size();
        // Inflate the layout for this fragment
        LineChart lineChart = rootView.findViewById(R.id.lineChart);
        lineChart.setMinimumWidth(chapterNumber * 150);

        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.setHorizontalScrollBarEnabled(true);

        lineChart.getAxisLeft().setAxisMaximum(100);
        lineChart.getAxisLeft().setAxisMinimum(0);
        lineChart.getAxisLeft().setGranularity(20f);
        lineChart.getAxisLeft().setDrawAxisLine(false);
        lineChart.getAxisLeft().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int p = Math.round(value);
                if(p <= 0){
                    return "단원";
                }else{
                    return Math.round(value) + "%";
                }
            }
        });

        lineChart.getAxisRight().setEnabled(false);
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.getAxisRight().setDrawGridLines(false);

        lineChart.getXAxis().setGranularityEnabled(true);
        lineChart.getXAxis().setAxisMaximum(book.getChapterCount());
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getXAxis().setLabelCount(book.getChapterCount(), true);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setGranularity(1f);
        lineChart.getXAxis().setLabelCount(book.getChapterCount());
        lineChart.getXAxis().setSpaceMax(0.6f);
        lineChart.getXAxis().setSpaceMin(0.6f);
        lineChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if((int)value <= 0) return "";
                else return String.valueOf((int)value);
            }
        });

        LineData lineData = new LineData();

        maxDataSet.setValueTextSize(10);
        maxDataSet.setValueTextColor(Color.parseColor("#FB3535"));
        maxDataSet.setColor(Color.parseColor("#00FFFFFF"));
        maxDataSet.setCircleColor(Color.parseColor("#FB3535"));
        maxDataSet.setCircleHoleColor(Color.parseColor("#FB3535"));
        maxDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf(Math.round(value));
            }
        });

        avrDataSet.setValueTextSize(10);
        avrDataSet.setValueTextColor(Color.parseColor("#F88B06"));
        avrDataSet.setColor(Color.parseColor("#F88B06"));
        avrDataSet.setCircleColor(Color.parseColor("#F88B06"));
        avrDataSet.setCircleHoleColor(Color.parseColor("#F88B06"));
        avrDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf(Math.round(value));
            }
        });

        minDataSet.setValueTextSize(10);
        minDataSet.setValueTextColor(Color.parseColor("#1469F5"));
        minDataSet.setColor(Color.parseColor("#00FFFFFF"));
        minDataSet.setCircleColor(Color.parseColor("#1469F5"));
        minDataSet.setCircleHoleColor(Color.parseColor("#1469F5"));
        minDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf(Math.round(value));
            }
        });

        lineData.addDataSet(minDataSet);
        lineData.addDataSet(maxDataSet);
        lineData.addDataSet(avrDataSet);

        lineChart.setData(lineData);
        lineChart.invalidate();

        return rootView;
    }


}