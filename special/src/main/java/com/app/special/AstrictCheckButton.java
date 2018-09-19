package com.app.special;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * @项目名： xiaoyujia
 * @包名： com.vooda.smartHome.view
 * @创建者: Noah.冯
 * @时间: 10:24
 * @描述： 限制几秒内再次点击的Button
 */

public class AstrictCheckButton extends CheckButton implements Runnable {

    protected long mAstrictClickTime = 2500L;//限制点击时长


    public AstrictCheckButton(Context context){
        super(context);
    }

    public AstrictCheckButton(Context context,AttributeSet attrs){
        super(context,attrs);
    }

    public AstrictCheckButton(Context context,AttributeSet attrs,
                              int defStyleAttr)
    {
        super(context,attrs,defStyleAttr);
    }

    @Override
    public void onClick(View v){
        if(mClickable){
            if(mOnClickListener != null){
                mOnClickListener.onClick(v);
            }
            closeCheck();
            toggle();
            postDelayed(this,mAstrictClickTime);
        }
    }

    @Override
    public void run(){
        openCheck();
    }

    public void setAstrictClickTime(long astrictClickTime){
        mAstrictClickTime = astrictClickTime;
    }

    public long getAstrictClickTime(){
        return mAstrictClickTime;
    }
}
