package com.example.bloodpsrapp.viewmodels;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.text.DecimalFormat;

public class IntegerValueFormatter implements IValueFormatter {
    private final DecimalFormat formatData;

    //Constructor
    public IntegerValueFormatter () {
        formatData = new DecimalFormat("###,###,##0");
    }

    //Format data value
    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return "        " + formatData.format(value);
    }
}
