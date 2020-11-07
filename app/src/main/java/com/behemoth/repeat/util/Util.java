package com.behemoth.repeat.util;

import android.content.Context;

import java.util.Calendar;

public class Util {

    public static int dpToPx(Context ctx, int dp) {
        float density = ctx.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

    public static String dateFormatting(long timeStamp){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);

        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        return mYear +  "." + mMonth + "." + mDay;
    }

}
