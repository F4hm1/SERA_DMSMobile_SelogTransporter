package com.serasiautoraya.slimobiledrivertracking_training.module.CustomView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Randi Dwi Nandra on 25/04/2017.
 */

@SuppressLint("AppCompatCustomView")
public class SquareImageView extends ImageView {

    public SquareImageView(Context context) {
        super(context);
        initializeOnClickListener();
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeOnClickListener();
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeOnClickListener();
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }

    private void initializeOnClickListener() {
        OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        getDrawable().clearColorFilter();
                        invalidate();
                        break;
                    case MotionEvent.ACTION_CANCEL: {
                        getDrawable().clearColorFilter();
                        invalidate();
                        break;
                    }
                }
                return false;
            }
        };
        super.setOnTouchListener(onTouchListener);
    }

}
