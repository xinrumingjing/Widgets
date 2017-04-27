package com.mingjing.widget.indicator;

import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by mingjing on 2017/4/26.
 */

public interface PageIndicatorAdapter {

    int getItemCount();

    Drawable getItemView(int position, Drawable convertDrawable, View parent);

    Drawable getSelectView(int position, Drawable convertDrawable, View parent);

    int getSelectTypeCount();

    //返回的数据必须从0开始，如 0,1,2,而且必须大于0小于getSelectTypeCount
    int getSelectType(int position);

    void registerDataSetObserver(DataSetObserver observer);

    void unregisterDataSetObserver(DataSetObserver observer);
}
