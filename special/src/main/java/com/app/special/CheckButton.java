package com.app.special;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
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

public class CheckButton extends TextView implements View.OnClickListener {

    //默认是选中状态
    public final static boolean DEFAULT_CAN_CLICK = true;
    //默认能点击
    public final static boolean DEFAULT_CHECK = true;
    public final static @DrawableRes
    int DEFAULT_CHECK_BACKGROUND_ID = android.R.color.transparent;
    public final static @DrawableRes
    int DEFAULT_UNCHECK_BACKGROUND_ID = android.R.color.transparent;
    protected Drawable mCheckDrawableTop;
    protected Drawable mCheckDrawableLeft;
    protected Drawable mCheckDrawableBottom;
    protected Drawable mCheckDrawableRight;
    protected Drawable mUncheckDrawableTop;
    protected Drawable mUncheckDrawableLeft;
    protected Drawable mUncheckDrawableBottom;
    protected Drawable mUncheckDrawableRight;
    protected OnClickListener mOnClickListener;
    protected CharSequence mCheckText;
    protected CharSequence mUnCheckText;
    //是否能点击
    protected boolean isCheck;
    protected boolean mClickable;
    protected @DrawableRes
    int checkBackgroundId;
    protected int checkColor;
    protected @DrawableRes
    int uncheckBackgroundId;
    protected int uncheckColor;
    protected OnCheckChangeListener mOnCheckChangeListener;
    public CheckButton(Context context) {
        this(context, null);
    }
    public CheckButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public CheckButton(Context context, AttributeSet attrs, int defStyleAttr) {
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
        TypedArray type = getContext().obtainStyledAttributes(attrs, R.styleable.CheckButton);
        isCheck = type.getBoolean(R.styleable.CheckButton_check, DEFAULT_CHECK);
        mClickable = type.getBoolean(R.styleable.CheckButton_clickable, DEFAULT_CAN_CLICK);
        checkBackgroundId = type.getResourceId(R.styleable.CheckButton_checkBackground,
                DEFAULT_CHECK_BACKGROUND_ID);
        uncheckBackgroundId = type.getResourceId(R.styleable.CheckButton_uncheckBackground,
                DEFAULT_UNCHECK_BACKGROUND_ID);
        checkColor = type.getColor(R.styleable.CheckButton_checkTextColor, getCurrentTextColor());
        uncheckColor = type.getColor(R.styleable.CheckButton_uncheckTextColor,
                getCurrentTextColor());
        mCheckText = type.getString(R.styleable.CheckButton_checkText);
        mUnCheckText = type.getString(R.styleable.CheckButton_uncheckText);
        setCheckDrawableTop(type.getDrawable(R.styleable.CheckButton_checkDrawableTop));
        setCheckDrawableLeft(type.getDrawable(R.styleable.CheckButton_checkDrawableLeft));
        setCheckDrawableBottom(type.getDrawable(R.styleable.CheckButton_checkDrawableBottom));
        setCheckDrawableRight(type.getDrawable(R.styleable.CheckButton_checkDrawableRight));
        setUncheckDrawableTop(type.getDrawable(R.styleable.CheckButton_uncheckDrawableTop));
        setUncheckDrawableLeft(type.getDrawable(R.styleable.CheckButton_uncheckDrawableLeft));
        setUncheckDrawableBottom(type.getDrawable(R.styleable.CheckButton_uncheckDrawableBottom));
        setUncheckDrawableRight(type.getDrawable(R.styleable.CheckButton_uncheckDrawableRight));

        if (mCheckText == null || mCheckText.length() == 0) {
            mCheckText = getText();
            if (mCheckText == null || mCheckText.length() == 0) {
                mCheckText = getDefaultCheckText();
            }
        }
        if (mUnCheckText == null || mUnCheckText.length() == 0) {
            mUnCheckText = getDefaultUnCheckText();
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

    public void setCheckBackground(@DrawableRes int checkBackgroundId) {
        this.checkBackgroundId = checkBackgroundId;
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

    public void setUncheckBackground(@DrawableRes int uncheckBackgroundId) {
        this.uncheckBackgroundId = uncheckBackgroundId;
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
            setBackgroundResource(checkBackgroundId);
            super.setCompoundDrawables(mCheckDrawableLeft, mCheckDrawableTop, mCheckDrawableRight,
                    mCheckDrawableBottom);
        } else {
            setTextColor(uncheckColor);
            setText(mUnCheckText);
            setBackgroundResource(uncheckBackgroundId);
            super.setCompoundDrawables(mUncheckDrawableLeft, mUncheckDrawableTop,
                    mUncheckDrawableRight, mUncheckDrawableBottom);
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

    public Drawable getCheckDrawableTop() {
        return mCheckDrawableTop;
    }

    public void setCheckDrawableTop(Drawable checkDrawableTop) {
        mCheckDrawableTop = checkDrawableTop;
        if (mCheckDrawableTop != null) {
            mCheckDrawableTop.setBounds(0, 0, mCheckDrawableTop.getMinimumWidth(),
                    mCheckDrawableTop.getMinimumHeight());
        }
    }

    public Drawable getCheckDrawableLeft() {
        return mCheckDrawableLeft;
    }

    public void setCheckDrawableLeft(Drawable checkDrawableLeft) {
        mCheckDrawableLeft = checkDrawableLeft;
        if (mCheckDrawableLeft != null) {
            mCheckDrawableLeft.setBounds(0, 0, mCheckDrawableLeft.getMinimumWidth(),
                    mCheckDrawableLeft.getMinimumHeight());
        }
    }

    public Drawable getCheckDrawableBottom() {
        return mCheckDrawableBottom;
    }

    public void setCheckDrawableBottom(Drawable checkDrawableBottom)
    {
        mCheckDrawableBottom = checkDrawableBottom;
        if (mCheckDrawableBottom != null) {
            mCheckDrawableBottom.setBounds(0, 0, mCheckDrawableBottom.getMinimumWidth(),
                    mCheckDrawableBottom.getMinimumHeight());
        }
    }

    public Drawable getCheckDrawableRight() {
        return mCheckDrawableRight;
    }

    public void setCheckDrawableRight(Drawable checkDrawableRight)
    {
        mCheckDrawableRight = checkDrawableRight;
        if (mCheckDrawableRight != null) {
            mCheckDrawableRight.setBounds(0, 0, mCheckDrawableRight.getMinimumWidth(),
                    mCheckDrawableRight.getMinimumHeight());
        }
    }

    public Drawable getUncheckDrawableTop() {
        return mUncheckDrawableTop;
    }

    public void setUncheckDrawableTop(Drawable uncheckDrawableTop)
    {
        mUncheckDrawableTop = uncheckDrawableTop;
        if (mUncheckDrawableTop != null) {
            mUncheckDrawableTop.setBounds(0, 0, mUncheckDrawableTop.getMinimumWidth(),
                    mUncheckDrawableTop.getMinimumHeight());
        }
    }

    public Drawable getUncheckDrawableLeft() {
        return mUncheckDrawableLeft;
    }

    public void setUncheckDrawableLeft(Drawable uncheckDrawableLeft)
    {
        mUncheckDrawableLeft = uncheckDrawableLeft;
        if (mUncheckDrawableLeft != null) {
            mUncheckDrawableLeft.setBounds(0, 0, mUncheckDrawableLeft.getMinimumWidth(),
                    mUncheckDrawableLeft.getMinimumHeight());
        }
    }

    public Drawable getUncheckDrawableBottom() {
        return mUncheckDrawableBottom;
    }

    public void setUncheckDrawableBottom(Drawable uncheckDrawableBottom)
    {
        mUncheckDrawableBottom = uncheckDrawableBottom;
        if (mUncheckDrawableBottom != null) {
            mUncheckDrawableBottom.setBounds(0, 0, mUncheckDrawableBottom.getMinimumWidth(),
                    mUncheckDrawableBottom.getMinimumHeight());
        }
    }

    public Drawable getUncheckDrawableRight() {
        return mUncheckDrawableRight;
    }

    public void setUncheckDrawableRight(Drawable uncheckDrawableRight)
    {
        mUncheckDrawableRight = uncheckDrawableRight;
        if (mUncheckDrawableRight != null) {
            mUncheckDrawableRight.setBounds(0, 0, mUncheckDrawableRight.getMinimumWidth(),
                    mUncheckDrawableRight.getMinimumHeight());
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
