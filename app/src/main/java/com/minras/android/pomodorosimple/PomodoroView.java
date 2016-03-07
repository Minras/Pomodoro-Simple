package com.minras.android.pomodorosimple;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

public class PomodoroView extends View {
    private static final int DIAMETER_PERCENTAGE = 62;
    private static final int STROKE_SIZE = 8;
    private static final String COLOR_HEX = "#FFA500";
    private final Paint drawPaint;
    private       float size;

    public PomodoroView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        drawPaint = new Paint();
        drawPaint.setColor(Color.parseColor(COLOR_HEX));
        drawPaint.setAntiAlias(true);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeWidth(STROKE_SIZE);
        setOnMeasureCallback();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int diameter = Math.min(
                MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        setMeasuredDimension(
                diameter * DIAMETER_PERCENTAGE / 100,
                diameter * DIAMETER_PERCENTAGE / 100);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(size, size, size - STROKE_SIZE, drawPaint);
    }

    private void setOnMeasureCallback() {
        ViewTreeObserver vto = getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                removeOnGlobalLayoutListener(this);
                size = getMeasuredWidth() / 2;
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void removeOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT < 16) {
            getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        } else {
            getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
    }
}