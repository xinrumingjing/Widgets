package com.mingjing.widget.segment;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.mingjing.widget.R;

/**
 * Created by mingjing on 2017/4/26.
 */

public class SegmentView extends LinearLayout {

    private Path mClipPath;

    private float mRadianX;
    private float mRadianY;

    public SegmentView(Context context) {
        super(context);

        init(context, null);
    }

    public SegmentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SegmentView);
        mRadianX = typedArray.getDimension(R.styleable.SegmentView_radianX, 0);
        mRadianY = typedArray.getDimension(R.styleable.SegmentView_radianY, 0);
        typedArray.recycle();

        setWillNotDraw(false);

        setOrientation(HORIZONTAL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (mClipPath == null) {
            mClipPath = new Path();
            RectF rectF = new RectF();
            rectF.set(getPaddingLeft(), getPaddingTop(), w - getPaddingLeft() - getPaddingRight(), h - getPaddingTop() - getPaddingBottom());
            mClipPath.addRoundRect(rectF, mRadianX, mRadianY, Path.Direction.CCW);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.clipPath(mClipPath);
        canvas.drawColor(Color.TRANSPARENT);
    }
}
