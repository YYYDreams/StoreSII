package com.example.administrator.store3.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义虚线
 * Created by Administrator on 2016/6/13.
 */
public class DashedLine extends View {
    private final String namespace = "http://com.smartmap.driverbook";
    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private Rect mRect;
    public DashedLine(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint=new Paint();
        paint.setStyle(Paint.Style.STROKE);
        //paint.setColor(Color.RED);
        paint.setColor(Color.GRAY);

        Path path=new Path();
        path.moveTo(0, 10);
        path.lineTo(480, 10);
        PathEffect effect=new DashPathEffect(new float[]{5,5,5,5},1);
        paint.setPathEffect(effect);
        canvas.drawPath(path,paint);
    }
}
