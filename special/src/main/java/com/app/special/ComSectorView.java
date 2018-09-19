package com.app.special;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;


import java.util.ArrayList;
import java.util.List;


/**
 * @项目名： Advertisement
 * @包名：
 * @创建者: Noah.冯
 * @时间: 15:52
 * @描述： 通用型扇形图
 */
public class ComSectorView extends View {

    public static final int DEFAULT_COLOR = Color.BLUE;
    protected Paint mPaint;
    protected float mSweepAngle = 0;//扇形角度
    protected RectF mRectF;
    protected float startAngle;//开始角度,即系从正中顶部开始
    protected float mDefaultStartAngle;//开始角度,即系从正中顶部开始
    protected int mBackgroundColor;
    protected int mTextColor;
    protected int mDefaultColor;
    protected List<SectorData> mSectorDatas = new ArrayList<>();
    protected int mAnnulusColor;//内环颜色
    protected float mAnnulusRadius;//内环大小
    private CharSequence mOneText;
    private CharSequence mTwoText;
    protected float mTextSize;
    private Paint mTextPaint;

    public ComSectorView(Context context,AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
        init(attrs);
    }

    public ComSectorView(Context context){
        this(context,null);
    }

    public ComSectorView(Context context,AttributeSet attrs){
        this(context,attrs,0);
    }

    protected void init(AttributeSet attrs){
        TypedArray type = this.getContext().obtainStyledAttributes(attrs,R.styleable.ComSectorView);
        mBackgroundColor = type.getColor(R.styleable.ComSectorView_backgroundColor,


                                         Color.TRANSPARENT);
        mDefaultColor = type.getColor(R.styleable.ComSectorView_circleColor,DEFAULT_COLOR);
        mAnnulusColor = type.getColor(R.styleable.ComSectorView_annulusColor,DEFAULT_COLOR);
        mAnnulusRadius = type.getFloat(R.styleable.ComSectorView_annulusRadius,0F);
        mTextColor = type.getColor(R.styleable.ComSectorView_textColor,Color.BLACK);
        mOneText = type.getText(R.styleable.ComSectorView_SectorOneText);
        mTwoText = type.getText(R.styleable.ComSectorView_SectorTwoText);
        mTextSize = type.getDimension(R.styleable.ComSectorView_textSize,30);
        startAngle = mDefaultStartAngle = type.getFloat(R.styleable.ComSectorView_startAngle,-90F);

        type.recycle();
        mPaint = new Paint();
        //画笔颜色
        mPaint.setColor(mDefaultColor);
        //抗锯齿
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas){
        //画布背景
        canvas.drawColor(mBackgroundColor);

        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        //计算圆中心
        int verticalCenter = measuredHeight / 2;
        int horizontalCenter = measuredWidth / 2;
        int circleRadius = measuredWidth / 2;
        mPaint.setColor(mDefaultColor);
        //画圆
        canvas.drawCircle(horizontalCenter,verticalCenter,circleRadius,mPaint);
        if(mRectF == null){
            mRectF = new RectF(0,0,measuredWidth,measuredHeight);
        }
        //        drawData(canvas);
        float allNum = 0;//总数
        for(SectorData data : mSectorDatas){
            allNum += data.getNumber();
        }
        mSweepAngle = 0;
        startAngle = mDefaultStartAngle;
        if(allNum > 0){
            for(SectorData data : mSectorDatas){
                mPaint.setColor(data.getColor());
                mSweepAngle = (data.getNumber() / allNum) * 360F;
                canvas.drawArc(mRectF,startAngle,mSweepAngle,true,mPaint);
                startAngle += mSweepAngle;
            }
        }
        if(mAnnulusRadius > 0F || mAnnulusRadius <= 1F){
            float annulusCircleRadius = circleRadius * mAnnulusRadius;
            mPaint.setColor(mAnnulusColor);
            //画圆
            canvas.drawCircle(horizontalCenter,verticalCenter,annulusCircleRadius,mPaint);
        }

        if(!TextUtils.isEmpty(mOneText)){
            if(mTextPaint == null){
                mTextPaint = new Paint();
                mTextPaint.setStrokeWidth(3);
                mTextPaint.setTextSize(mTextSize);
            }
            mTextPaint.setColor(mTextColor);
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            Rect bounds = new Rect();
            mTextPaint.getTextBounds(mOneText.toString(),0,mOneText.length(),bounds);
            canvas.drawText(mOneText.toString(),horizontalCenter,verticalCenter - bounds.height() / 2,mTextPaint);
        }

        if(!TextUtils.isEmpty(mTwoText)){
            if(mTextPaint == null){
                mTextPaint = new Paint();
                mTextPaint.setStrokeWidth(3);
                mTextPaint.setTextSize(mTextSize);
            }
            mTextPaint.setColor(mTextColor);
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            Rect bounds = new Rect();
            mTextPaint.getTextBounds(mTwoText.toString(),0,mTwoText.length(),bounds);
            canvas.drawText(mTwoText.toString(),horizontalCenter,verticalCenter + bounds.height(),mTextPaint);
        }
    }

    public Paint getPaint(){
        return mPaint;
    }

    public void setPaint(Paint paint){
        mPaint = paint;
        postInvalidate();
    }

    public float getStartAngle(){
        return startAngle;
    }

    public void setStartAngle(float startAngle){
        this.startAngle = startAngle;
        postInvalidate();
    }

    public int getBackgroundColor(){
        return mBackgroundColor;
    }

    public void setBackground(int backgroundColor){
        mBackgroundColor = backgroundColor;
        postInvalidate();
    }

    public int getDefaultColor(){
        return mDefaultColor;
    }

    public void setDefaultColor(int defaultColor){
        mDefaultColor = defaultColor;
        postInvalidate();
    }

    public List<SectorData> getSectorDatas(){
        return mSectorDatas;
    }

    public void setData(List<SectorData> datas){
        mSectorDatas = datas;
        float number = 0;
        for(SectorData data : datas){
            number += data.getNumber();
        }
        setTwoText(((int)number)+"");
        postInvalidate();
    }

    public static class SectorData{

        private @ColorInt
        int color;
        private float number;

        public SectorData(){
        }

        public SectorData(@ColorInt int color,int number){
            this.color = color;
            this.number = number;
        }

        public
        @ColorInt
        int getColor(){
            return color;
        }

        public void setColor(@ColorInt int color){
            this.color = color;
        }

        public float getNumber(){
            return number;
        }

        public void setNumber(float number){
            this.number = number;
        }
    }

    public CharSequence getOneText(){
        return mOneText;
    }

    public void setOneText(CharSequence oneText){
        mOneText = oneText;
        postInvalidate();
    }

    public CharSequence getTwoText(){
        return mTwoText;
    }

    public void setTwoText(CharSequence twoText){
        mTwoText = twoText;
        postInvalidate();
    }
}
