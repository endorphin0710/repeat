package com.behemoth.repeat.util;

import android.content.Context;

public class Util {

    public static int dpToPx(Context ctx, int dp) {
        float density = ctx.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

}
