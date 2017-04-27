package com.mingjing.widget.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.mingjing.widget.R;

/**
 * Created by mingjing on 2017/4/26.
 */

public class PageIndicatorView extends View implements PageChangeListener {

    private AbsPageIndicatorAdapter mAdapter;

    private Item[] mItemViews;
    private Drawable[] mSelectViews;

    private int mItemWidth = 30;
    private int mItemHeight = 30;

    private int mItemSpace = 10;

    private float mPageOffset;

    private int mPosition = 0;

    private DataSetObserver mObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            invalidate();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            invalidate();
        }
    };

    private OnItemClickListener mItemClickListener;

    private GestureDetector mTouchEventDetector;

    public interface OnItemClickListener {

        /**
         * @param pos the item position start from 0 to itemCount-1;
         *            when it is -1,it stands the whole view
         */
        void onItemClick(int pos);
    }

    public PageIndicatorView(Context context) {
        super(context);

        init(context, null);
    }

    public PageIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PageIndicatorView);
        mItemWidth = (int) typedArray.getDimension(R.styleable.PageIndicatorView_itemWidth, mItemWidth);
        mItemHeight = (int) typedArray.getDimension(R.styleable.PageIndicatorView_itemHeight, mItemHeight);
        mItemSpace = (int) typedArray.getDimension(R.styleable.PageIndicatorView_itemSpace, mItemSpace);
        typedArray.recycle();

        mTouchEventDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (mItemClickListener == null) {
                    return false;
                }

                boolean itemClicked = false;
                for (int i = 0, size = mItemViews.length; i < size; ++i) {
                    Item item = mItemViews[i];
                    if (item != null && touchOnItem(e, item.mPositionX, item.mPositionY)) {
                        mItemClickListener.onItemClick(i);
                        itemClicked = true;
                        break;
                    }
                }

                if (!itemClicked) {
                    mItemClickListener.onItemClick(-1);
                }
                return true;
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setAdapter(AbsPageIndicatorAdapter adapter) {
        if (mAdapter != null) {
            mAdapter.unregisterDataSetObserver(mObserver);
        }
        mAdapter = adapter;

        initConvertArray(adapter);
        mAdapter.registerDataSetObserver(mObserver);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mAdapter == null || mAdapter.getItemCount() < 2) {
            return;
        }

        drawHoritalzon(canvas);
    }

    private void drawVertical(Canvas canvas) {

    }

    private void drawHoritalzon(Canvas canvas) {
        int x = getPaddingLeft();
        int y = getPaddingTop();

        int count = mAdapter.getItemCount();
        int itemsWidth = count * mItemWidth + (count - 1) * mItemSpace;
        int viewWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        int viewHeight = getHeight() - getPaddingTop() - getPaddingBottom();

        x += (viewWidth - itemsWidth) / 2;
        y += (viewHeight - mItemHeight) / 2;

        for (int i = 0, size = mAdapter.getItemCount(); i < size; ++i) {
            Item item = mItemViews[i];
            if (item == null) {
                item = new Item();
            }
            Drawable itemDrawable = mAdapter.getItemView(i, item.mItemDrawable, this);
            item.mItemDrawable = itemDrawable;
            item.mPositionX = x;
            item.mPositionY = y;
            mItemViews[i] = item;

            itemDrawable.setBounds(x, y, mItemWidth + x, y + mItemHeight);
            itemDrawable.draw(canvas);

            x += mItemSpace + mItemWidth;
        }

        int selectIndex = mAdapter.getSelectType(mPosition);
        Drawable selectDrawable = mAdapter.getSelectView(mPosition, mSelectViews[selectIndex], this);
        mSelectViews[selectIndex] = selectDrawable;
        Item selectItem = mItemViews[mPosition];
        if (selectItem != null && selectDrawable != null) {
            x = (int) (selectItem.mPositionX + mPageOffset * (mItemWidth + mItemSpace));
            selectDrawable.setBounds(x, y, mItemWidth + x, y + mItemHeight);
            selectDrawable.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mTouchEventDetector.onTouchEvent(event);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        mPageOffset = positionOffset;

        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        mPosition = position;

        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void initConvertArray(AbsPageIndicatorAdapter adapter) {
        if (mItemViews == null) {
            mItemViews = new Item[adapter.getItemCount()];
        } else if (mItemViews.length != adapter.getItemCount()) {
            Item[] itemViews = new Item[adapter.getItemCount()];
            for (int i = 0, s1 = itemViews.length, s2 = mItemViews.length; i < s1 && i < s2; ++i) {
                itemViews[i] = mItemViews[i];
            }
            mItemViews = itemViews;
        }

        if (mSelectViews == null) {
            mSelectViews = new Drawable[adapter.getSelectTypeCount()];
        } else if (mSelectViews.length != adapter.getSelectTypeCount()) {
            Drawable[] selectViews = new Drawable[adapter.getSelectTypeCount()];
            for (int i = 0, s1 = selectViews.length, s2 = mSelectViews.length; i < s1 && i < s2; ++i) {
                selectViews[i] = mSelectViews[i];
            }
            mSelectViews = selectViews;
        }
    }

    private static class Item {
        Drawable mItemDrawable;
        int mPositionX;
        int mPositionY;

        public Item() {
        }

        public Item(Drawable mItemDrawable, int mPositionX, int mPositionY) {
            this.mItemDrawable = mItemDrawable;
            this.mPositionX = mPositionX;
            this.mPositionY = mPositionY;
        }
    }

    private boolean touchOnItem(MotionEvent event, int itemX, int itemY) {
        float touchX = event.getX();
        float touchY = event.getY();

        return touchX >= itemX && touchY >= itemY && touchX <= itemX + mItemWidth && touchY <= itemY + mItemHeight;
    }
}
