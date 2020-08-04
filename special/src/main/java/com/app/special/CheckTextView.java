package com.app.special;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * @项目名： xiaoyujia
 * @包名： com.vooda.smartHome.view
 * @创建者: Noah.冯
 * @时间: 10:24
 * @描述： 可以Check的Button
 */

public class CheckTextView extends TextView implements View.OnClickListener {

    //默认能点击
    public final static boolean DEFAULT_CHECK = true;
    protected OnClickListener mOnClickListener;
    protected CharSequence mCheckText;
    protected CharSequence mUnCheckText;
    //是否能点击
    protected boolean isCheck;
    protected boolean mClickable;
    protected Drawable checkBackground;
    protected int checkColor;
    protected Drawable uncheckBackground;
    protected int uncheckColor;
    protected OnCheckChangeListener mOnCheckChangeListener;

    public CheckTextView(Context context) {
        this(context, null);
    }

    public CheckTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public CheckTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    protected CharSequence getDefaultCheckText() {
        return getText();
    }

    protected CharSequence getDefaultUnCheckText() {
        return getText();
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mOnClickListener = l;
        super.setOnClickListener(this);
    }

    protected void init(AttributeSet attrs) {
        TypedArray type = getContext().obtainStyledAttributes(attrs, R.styleable.CheckTextView);
        isCheck = type.getBoolean(R.styleable.CheckTextView_check, DEFAULT_CHECK);
        mClickable = type.getBoolean(R.styleable.CheckTextView_clickable, isClickable());

        checkBackground = type.getDrawable(R.styleable.CheckTextView_checkBackground);
        uncheckBackground = type.getDrawable(R.styleable.CheckTextView_uncheckBackground);

        checkColor = type.getColor(R.styleable.CheckTextView_checkTextColor, getCurrentTextColor());
        uncheckColor = type.getColor(R.styleable.CheckTextView_uncheckTextColor,
                getCurrentHintTextColor());
        mCheckText = type.getString(R.styleable.CheckTextView_checkText);
        mUnCheckText = type.getString(R.styleable.CheckTextView_uncheckText);

        if (mCheckText == null || mCheckText.length() == 0) {
            mCheckText = getText();
        }
        if (mUnCheckText == null || mUnCheckText.length() == 0) {
            mUnCheckText = getText();
        }
        type.recycle();
        super.setOnClickListener(this);
        initView();
    }

    protected void initView() {
        setCheck(isCheck);
    }

    public CharSequence getCheckText() {
        return mCheckText;
    }

    public void setCheckText(CharSequence checkText) {
        mCheckText = checkText;
    }

    public CharSequence getUnCheckText() {
        return mUnCheckText;
    }

    public void setUnCheckText(CharSequence unCheckText) {
        mUnCheckText = unCheckText;
    }

    public OnCheckChangeListener getOnCheckChangeListener() {
        return mOnCheckChangeListener;
    }

    public void setOnCheckChangeListener(OnCheckChangeListener onCheckChangeListener)
    {
        mOnCheckChangeListener = onCheckChangeListener;
    }

    public void setCheckBackground(Drawable checkBackgroundId) {
        this.checkBackground = checkBackgroundId;
        postInvalidate();
    }


    public void setCheckColor(@ColorInt int checkColor) {
        this.checkColor = checkColor;
        postInvalidate();
    }

    public void closeCheck() {
        setCanClick(false);
    }

    public void openCheck() {
        setCanClick(true);
    }

    public void setUncheckBackground(Drawable uncheckBackgroundId) {
        this.uncheckBackground = uncheckBackgroundId;
        postInvalidate();
    }


    public void setUncheckColor(@ColorInt int uncheckColor) {
        this.uncheckColor = uncheckColor;
        postInvalidate();
    }

    public void setCanClick(boolean canClick) {
        mClickable = canClick;
    }

    public boolean isCheck() {
        return isCheck;
    }

    /**
     * 设置状态
     */
    public void setCheck(boolean isCheck) {
        this.isCheck = isCheck;
        if (this.isCheck) {
            setTextColor(checkColor);
            setText(mCheckText);
            setBackgroundDrawable(checkBackground);
        } else {
            setTextColor(uncheckColor);
            setText(mUnCheckText);
            setBackgroundDrawable(uncheckBackground);
        }
        if (mOnCheckChangeListener != null) {
            mOnCheckChangeListener.onChange(this.isCheck);
        }
    }

    public void toggle() {
        setCheck(isCheck = !isCheck);
    }

    @Override
    public void onClick(View v) {
        if (mClickable) {
            if (mOnClickListener != null) {
                mOnClickListener.onClick(v);
            }
            toggle();
        }
    }

    public void postSetCheck(final boolean isCheck,long time) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                setCheck(isCheck);
            }
        },time);
    }

    /**
     * 监听器状态改变
     */
    public interface OnCheckChangeListener {

        void onChange(boolean check);
    }
}
