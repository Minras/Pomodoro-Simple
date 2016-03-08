package com.minras.android.pomodorosimple;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

public class PomodoroView extends View {
    private static final int DIAMETER_PERCENTAGE = 62;
    private static final int STROKE_SIZE = 12;
    private static final int COLOR_TIMER_FG = Color.parseColor("#FFA500");
    private final Paint drawPaintFg;
    private static final int COLOR_TIMER_BG = Color.parseColor("#AAAAAA");
    private final Paint drawPaintBg;
    private float size;
    private float percentComplete = 0;
    RectF timerRectange = new RectF(0, 0, 1, 1);

    public PomodoroView(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        drawPaintFg = new Paint();
        drawPaintFg.setColor(COLOR_TIMER_FG);
        drawPaintFg.setAntiAlias(true);
        drawPaintFg.setStyle(Paint.Style.STROKE);
        drawPaintFg.setStrokeWidth(STROKE_SIZE);

        drawPaintBg = new Paint();
        drawPaintBg.setColor(COLOR_TIMER_BG);
        drawPaintBg.setAntiAlias(true);
        drawPaintBg.setStyle(Paint.Style.STROKE);
        drawPaintBg.setStrokeWidth(STROKE_SIZE);

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

        //canvas.drawCircle(size, size, size - STROKE_SIZE, drawPaintBg);
        float x = 360 * (1 - percentComplete);
        canvas.drawArc(timerRectange, -90, x, false, drawPaintBg);
        canvas.drawArc(timerRectange, -90 + x, 360 * percentComplete, false, drawPaintFg);
    }

    private void setOnMeasureCallback() {
        ViewTreeObserver vto = getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                removeOnGlobalLayoutListener(this);
                size = getMeasuredWidth() / 2;
                timerRectange.set(STROKE_SIZE, STROKE_SIZE, 2 * size - STROKE_SIZE, 2*size - STROKE_SIZE);
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

    public void updateTimer(long msTotal, long msLeft) {
        percentComplete = (float)(msTotal - msLeft) / msTotal;
        this.invalidate();
    }
}