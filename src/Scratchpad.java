package com.mathproblemconstructor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;

public class Scratchpad extends View
{
    private ArrayList<Paint> paints;
    private ArrayList<Path> paths;
    private float width;
    private int color, n;
    
    public Scratchpad(Context con, AttributeSet attrs)
    {
        super(con, attrs);
        paints = new ArrayList<Paint>();
        paths = new ArrayList<Path>();
        n = 0;
        color = Color.BLACK;
        width = 10f;
        setup();
    }

    public void clear()
    {
        paints.clear();
        paths.clear();
        n = 0;
        setup();
        invalidate();
    }
    public String getInitThickness()
    {
        return("" + paints.get(0).getStrokeWidth());
    }
    @Override
    protected void onDraw(Canvas c)
    {
        for(int i = 0; i < paints.size(); i++)
        {
            c.drawPath(paths.get(i), paints.get(i));
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent me)
    {
        float x = me.getX();
        float y = me.getY();
        switch(me.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                paths.get(n).moveTo(x, y);
                return(true);
            case MotionEvent.ACTION_MOVE:
                paths.get(n).lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                return(false);
        }
        invalidate();
        return(true);
    }
    public void setColor(int i)
    {
        color = i;
        n++;
        setup();
    }
    public void setThickness(String s)
    {
        width = Float.parseFloat(s);
        n++;
        setup();
    }
    private void setup()
    {
        paints.add(new Paint());
        paints.get(n).setAntiAlias(true);
        paints.get(n).setColor(color);
        paints.get(n).setStrokeJoin(Paint.Join.ROUND);
        paints.get(n).setStrokeWidth(width);
        paints.get(n).setStyle(Paint.Style.STROKE);
        paths.add(new Path());
    }
}