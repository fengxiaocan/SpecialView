package com.app.special;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @项目名： xiaoyujia
 * @包名： com.vooda.smartHome.view
 * @创建者: Noah.冯
 * @时间: 10:24
 * @描述： 限制几秒内再次点击的Button
 */

public class AstrictClickButton extends ClickButton implements Runnable {
    protected long mAstrictClickTime = 2000L;//限制点击时长

    protected OnClickListener mOnClickListener;

    public AstrictClickButton(Context context){
        super(context);
    }

    public AstrictClickButton(Context context,AttributeSet attrs){
        super(context,attrs);
    }

    public AstrictClickButton(Context context,AttributeSet attrs,
                              int defStyleAttr)
    {
        super(context,attrs,defStyleAttr);
    }

    @Override
    public void onClick(View v){
        if(isCanClick){
            if(mOnClickListener != null){
                closeClick();
                postDelayed(this,mAstrictClickTime);
                mOnClickListener.onClick(v);
            }
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l){
        mOnClickListener = l;
        super.setOnClickListener(l);
    }

    @Override
    public void run(){
        openClick();
    }

    public void setAstrictClickTime(long astrictClickTime){
        mAstrictClickTime = astrictClickTime;
    }

    public long getAstrictClickTime(){
        return mAstrictClickTime;
    }
}
