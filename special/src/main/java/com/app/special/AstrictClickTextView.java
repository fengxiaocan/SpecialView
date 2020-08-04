package com.app.special;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * @项目名： BaseApp
 * @包名： com.dgtle.baselib.base.view
 * @创建者: Noah.冯
 * @时间: 11:19
 * @描述： 一个限制点击时间的View
 */

public class AstrictClickTextView extends TextView
        implements View.OnClickListener, Runnable
{
    protected boolean isCanClick = true;
    protected OnClickListener mOnClickListener;
    /**
     * 限制点击时间
     */
    protected long mAstrictClickTime = 2000;

    public AstrictClickTextView(Context context){
        super(context);
    }

    public AstrictClickTextView(Context context,@Nullable AttributeSet attrs)
    {
        super(context,attrs);
    }

    public AstrictClickTextView(Context context,@Nullable AttributeSet attrs,
                                int defStyleAttr)
    {
        super(context,attrs,defStyleAttr);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l){
        mOnClickListener = l;
        super.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(isCanClick){
            if(mOnClickListener != null){
                setCanClick(false);
                postDelayed(this,mAstrictClickTime);
                mOnClickListener.onClick(v);
            }
        }
    }

    public boolean isCanClick(){
        return isCanClick;
    }

    public void setCanClick(boolean canClick){
        isCanClick = canClick;
    }

    @Override
    public void run(){
        setCanClick(true);
    }

    public long getAstrictClickTime(){
        return mAstrictClickTime;
    }

    public void setAstrictClickTime(long astrictClickTime){
        mAstrictClickTime = astrictClickTime;
    }
}
