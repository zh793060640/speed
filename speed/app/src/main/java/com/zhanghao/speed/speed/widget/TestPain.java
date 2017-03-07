package com.zhanghao.speed.speed.widget;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by PC on 2017/3/3.
 * 作者 ：张浩
 * 作用：
 */

public class TestPain extends View {

    private Paint textPaint;
    private int defaultHeight = 200;
    private int defaultWidth = 200;
    private float preyX = 0;
    private float preyY = 0;
    private Path mPath;
    private Canvas cacheCanvas = null;

    public TestPain(Context context) {
        this(context, null);
    }

    public TestPain(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public TestPain(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    private void init() {
        cacheCanvas = new Canvas();
        mPath = new Path();
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setDither(true);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setStrokeWidth(5);
        //textPaint.setShadowLayer(20, 50, 50, Color.parseColor("#c8000000"));
        BlurMaskFilter filter = new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID);

        textPaint.setMaskFilter(filter);
        textPaint.setColor(Color.parseColor("#c8EE4000"));
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            defaultWidth = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {

        } else if (widthMode == MeasureSpec.UNSPECIFIED) {

        }
        if (heightMode == MeasureSpec.EXACTLY) {
            defaultHeight = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {

        } else if (heightMode == MeasureSpec.UNSPECIFIED) {

        }
        setMeasuredDimension(defaultWidth, defaultHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawCircle(defaultWidth / 2, defaultHeight / 2, defaultWidth / 2, textPaint);
        canvas.drawPath(mPath, textPaint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x, y);
                preyX = x;
                preyY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.quadTo(preyX, preyY, x, y);
                preyX = x;
                preyY = y;
                break;

            case MotionEvent.ACTION_UP:
                cacheCanvas.drawPath(mPath, textPaint);
                invalidate();
//                mPath.reset();
                break;
        }

        invalidate();
        return true;

    }
}
