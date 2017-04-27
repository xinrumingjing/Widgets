package com.mingjing.widget.indicator;

import android.database.DataSetObserver;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mingjing on 2017/4/26.
 */

public abstract class AbsPageIndicatorAdapter implements PageIndicatorAdapter {

    private Set<DataSetObserver> mObservers = new HashSet<>();

    @Override
    public int getSelectTypeCount() {
        return 1;
    }

    @Override
    public int getSelectType(int position) {
        return 0;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mObservers.add(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mObservers.remove(observer);
    }
}
