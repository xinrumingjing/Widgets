package com.mingjing.widget.barrage;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by mingjing on 2017/4/27.
 */

public class BarrageView extends View {

    private int mRows = 2;

    public BarrageView(Context context) {
        super(context);
    }

    public BarrageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
