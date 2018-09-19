package com.app.special;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * @项目名： Mibokids
 * @包名： com.mibokids.app.view
 * @创建者: feng
 * @时间: 18:57
 * @描述： 两边线，中间文字的TextView
 */
public class TwoLineTextView extends TextView {

    String mText = "数量统计";
    Paint mTextPaint;
    Paint mLinePaint;
    float mSize;

    public TwoLineTextView(Context context){
        super(context);
        init();
    }

    public TwoLineTextView(Context context,AttributeSet attrs){
        super(context,attrs);
        init();
    }

    private void init(){
        mTextPaint = new Paint();
        mTextPaint.setColor(getCurrentTextColor());
        mText = getText().toString();
        mSize = getTextSize();
        mTextPaint.setTextSize(mSize);
        mLinePaint = new Paint();
        mLinePaint.setColor(getCurrentHintTextColor());
    }

    @Override
    protected void onDraw(Canvas canvas){
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        int width2 = width / 2;
        int height2 = height / 2;
        float textWidth = mTextPaint.measureText(mText);
        canvas.drawText(mText,width2 - textWidth / 2,height2 + mSize / 2,mTextPaint);
        canvas.drawLine(0,height2,width2 - textWidth,height2,mLinePaint);
        canvas.drawLine(width2 + textWidth,height2,width,height2,mLinePaint);
    }
}
