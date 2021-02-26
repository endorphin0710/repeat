package com.behemoth.repeat.stats.mpandroid;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.List;

public class CustomBarDataSet extends BarDataSet {

    public CustomBarDataSet(List<BarEntry> yVals, String label) {
        super(yVals, label);
    }

    @Override
    public int getColor(int index) {
        if(getEntryForIndex(index).getY() < 2) // less than 95 green
            return mColors.get(0);
        else if(getEntryForIndex(index).getY() < 3) // less than 100 orange
            return mColors.get(1);
        else if(getEntryForIndex(index).getY() < 4) // less than 100 orange
            return mColors.get(2);
        else if(getEntryForIndex(index).getY() < 5) // less than 100 orange
            return mColors.get(3);
        else
            return mColors.get(4);
    }

    @Override
    public BarEntry getEntryForIndex(int index) {
        return super.getEntryForIndex(index);
    }
}
