package com.mingjing.segmentdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by mingjing on 2017/4/27.
 */

public class TestFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(container.getContext());
        textView.setTextSize(32);
        textView.setText(getArguments().getString("test"));
        return textView;
    }

    public static TestFragment create(String text) {
        TestFragment fragment = new TestFragment();

        Bundle data = new Bundle();
        data.putString("test", text);
        fragment.setArguments(data);

        return fragment;

        //master branch changes
    }
}
