package com.app.special;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;


/**
 * @项目名： xiaofang
 * @包名： com.xiaofang.controlsystem.view
 * @创建者: Noah.冯
 * @时间: 17:11
 * @描述： 虚实线
 */

public class LineView extends View {

    protected Paint mPaint;
    protected float mLineWidth;//线的宽度
    protected float mLineSpace;//线的间隙
    protected int mLineColor;//线的间隙
    protected boolean isHorizontal;//是否是横虚线
    protected boolean isDashed;//是否是虚线

    public LineView(Context context){
        this(context,null);
    }

    public LineView(Context context,@Nullable AttributeSet attrs){
        this(context,attrs,0);
    }

    public LineView(Context context,@Nullable AttributeSet attrs,
                    int defStyleAttr){
        super(context,attrs,defStyleAttr);
        init(attrs);
    }

    protected void init(AttributeSet attrs){
        TypedArray type = this.getContext().obtainStyledAttributes(attrs,
                                                                   R.styleable.LineView);
        mLineWidth = type.getDimension(R.styleable.LineView_line_width,8);
        mLineSpace = type.getDimension(R.styleable.LineView_line_space,0);
        mLineColor = type.getColor(R.styleable.LineView_line_color,Color.GRAY);
        int anInt = type.getInt(R.styleable.LineView_line_orientation,LinearLayout.HORIZONTAL);
        int lineType = type.getInt(R.styleable.LineView_line_type,2);
        isHorizontal = anInt == LinearLayout.HORIZONTAL;
        /*默认为虚线*/
        isDashed = lineType == 2;
        type.recycle();
        if(mLineWidth < 0){
            mLineWidth = 3;
        }
        if(mLineSpace < 0){
            mLineSpace = 0;
            isDashed = false;
        }

        mPaint = new Paint();
        mPaint.setColor(mLineColor);
        //抗锯齿
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas){
        //        super.onDraw(canvas);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        Rect rect = new Rect();
        if(isHorizontal){
            if(isDashed){
                for(int i = 0; i < measuredWidth; i += mLineSpace){
                    rect.set(i,0,i += mLineWidth,measuredHeight);
                    canvas.drawRect(rect,mPaint);
                }
            }else{
                rect.set(0,(int) ((measuredHeight / 2) - (mLineWidth / 2)),
                         measuredWidth,
                         (int) ((measuredHeight / 2) + (mLineWidth / 2)));
                canvas.drawRect(rect,mPaint);
            }
        }else{
            if(isDashed){
                for(int i = 0; i < measuredHeight; i += mLineSpace){
                    rect.set(0,i,measuredWidth,i += mLineWidth);
                    canvas.drawRect(rect,mPaint);
                }
            }else{
                rect.set((int) (measuredWidth / 2 - mLineWidth / 2),0,
                         (int) (measuredWidth / 2 + mLineWidth / 2),
                         measuredHeight);
                canvas.drawRect(rect,mPaint);
            }
        }
    }

    public float getLineWidth(){
        return mLineWidth;
    }

    public LineView setLineWidth(float lineWidth){
        mLineWidth = lineWidth;
        if(mLineWidth < 0){
            mLineWidth = 3;
        }
        return this;
    }

    public float getLineSpace(){
        return mLineSpace;
    }

    public LineView setLineSpace(float lineSpace){
        mLineSpace = lineSpace;
        if(mLineSpace < 0){
            mLineSpace = 0;
            isDashed = false;
        }
        return this;
    }

    public int getLineColor(){
        return mLineColor;
    }

    public LineView setLineColor(@ColorInt int lineColor){
        mLineColor = lineColor;
        return this;
    }

    public boolean isHorizontal(){
        return isHorizontal;
    }

    public LineView setHorizontal(boolean horizontal){
        isHorizontal = horizontal;
        return this;
    }

    public boolean isDashed(){
        return isDashed;
    }

    public LineView setDashed(boolean dashed){
        isDashed = dashed;
        return this;
    }

    public void refresh(){
        postInvalidate();
    }
}
