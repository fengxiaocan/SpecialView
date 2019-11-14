package com.app.special;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;


/**
 * @项目名： xiaoyujia
 * @创建者: Noah.冯
 * @时间: 14:29
 * @描述： 点赞的button
 */

public class PraiseButton extends TextView implements View.OnClickListener, Runnable {

    //默认是选中状态
    public final static boolean DEFAULT_CAN_CLICK = true;
    //默认能点击
    public final static boolean DEFAULT_CHECK = true;
    public final static int DEFAULT_CHECK_BACKGROUND = android.R.color.transparent;
    public final static int DEFAULT_UNCHECK_BACKGROUND = android.R.color.transparent;
    protected Drawable mCheckDrawableTop;
    protected Drawable mCheckDrawableLeft;
    protected Drawable mCheckDrawableBottom;
    protected Drawable mCheckDrawableRight;
    protected Drawable mUncheckDrawableTop;
    protected Drawable mUncheckDrawableLeft;
    protected Drawable mUncheckDrawableBottom;
    protected Drawable mUncheckDrawableRight;
    /**
     * 点赞的数目
     */
    protected int mPraiseNumber;
    protected long mAstrictClickTime = 2000L;
    //是否能点击
    protected boolean clickable;
    //是否选择状态
    protected boolean isCheck;

    protected @DrawableRes
    int checkBackgroundId;
    protected int checkColor;
    protected @DrawableRes
    int uncheckBackgroundId;
    protected int uncheckColor;
    protected OnCheckChangeListener mOnCheckChangeListener;
    protected OnClickListener mOnClickListener;


    public PraiseButton(Context context) {
        this(context, null);
    }

    public PraiseButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PraiseButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mOnClickListener = l;
        super.setOnClickListener(this);
    }

    protected void init(AttributeSet attrs) {
        TypedArray type = getContext().obtainStyledAttributes(attrs, R.styleable.PraiseButton);
        isCheck = type.getBoolean(R.styleable.PraiseButton_praise, DEFAULT_CHECK);
        clickable = type.getBoolean(R.styleable.PraiseButton_praiseable, DEFAULT_CAN_CLICK);
        checkBackgroundId = type.getResourceId(R.styleable.PraiseButton_praiseBackground, DEFAULT_CHECK_BACKGROUND);
        uncheckBackgroundId = type.getResourceId(R.styleable.PraiseButton_unpraiseBackground, DEFAULT_UNCHECK_BACKGROUND);
        checkColor = type.getColor(R.styleable.PraiseButton_praiseTextColor, getCurrentTextColor());
        uncheckColor = type.getColor(R.styleable.PraiseButton_unpraiseTextColor,
                getCurrentTextColor());
        mPraiseNumber = type.getInteger(R.styleable.PraiseButton_praiseNumber, 0);
        setCheckDrawableTop(type.getDrawable(R.styleable.PraiseButton_praiseDrawableTop));
        setCheckDrawableLeft(type.getDrawable(R.styleable.PraiseButton_praiseDrawableLeft));
        setCheckDrawableBottom(type.getDrawable(R.styleable.PraiseButton_praiseDrawableBottom));
        setCheckDrawableRight(type.getDrawable(R.styleable.PraiseButton_praiseDrawableRight));
        setUncheckDrawableTop(type.getDrawable(R.styleable.PraiseButton_unpraiseDrawableTop));
        setUncheckDrawableLeft(type.getDrawable(R.styleable.PraiseButton_unpraiseDrawableLeft));
        setUncheckDrawableBottom(type.getDrawable(R.styleable.PraiseButton_unpraiseDrawableBottom));
        setUncheckDrawableRight(type.getDrawable(R.styleable.PraiseButton_unpraiseDrawableRight));

        type.recycle();
        super.setOnClickListener(this);
        initView();
    }

    protected void initView() {
        setCheck(isCheck);
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

    public void setUncheckBackground(@DrawableRes int uncheckBackgroundId) {
        this.uncheckBackgroundId = uncheckBackgroundId;
        postInvalidate();
    }


    public void setUncheckColor(@ColorInt int uncheckColor) {
        this.uncheckColor = uncheckColor;
        postInvalidate();
    }

    public void setCanClick(boolean canClick) {
        clickable = canClick;
    }

    public int getPraiseNumber() {
        return mPraiseNumber;
    }

    public void setPraiseNumber(int praiseNumber) {
        mPraiseNumber = praiseNumber;
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
            setText(String.valueOf(mPraiseNumber));
            if (checkBackgroundId != uncheckBackgroundId) {
                setBackgroundResource(checkBackgroundId);
            }
            super.setCompoundDrawables(mCheckDrawableLeft, mCheckDrawableTop, mCheckDrawableRight,
                    mCheckDrawableBottom);
        } else {
            setTextColor(uncheckColor);
            setText(String.valueOf(mPraiseNumber));
            if (checkBackgroundId != uncheckBackgroundId) {
                setBackgroundResource(uncheckBackgroundId);
            }
            super.setCompoundDrawables(mUncheckDrawableLeft, mUncheckDrawableTop,
                    mUncheckDrawableRight, mUncheckDrawableBottom);
        }
        if (mOnCheckChangeListener != null) {
            mOnCheckChangeListener.onChange(this.isCheck);
        }
    }

    public void toggle() {
        if (isCheck) {
            if (mPraiseNumber > 0) {
                mPraiseNumber--;
            }
        } else {
            mPraiseNumber++;
        }
        setCheck(isCheck = !isCheck);
    }

    @Override
    public void onClick(View v) {
        if (clickable) {
            if (mOnClickListener != null) {
                mOnClickListener.onClick(v);
            }
            clickable = false;
            postDelayed(this, mAstrictClickTime);
            toggle();
        }
    }

    @Override
    public void run() {
        clickable = true;
    }

    public long getAstrictClickTime() {
        return mAstrictClickTime;
    }

    public void setAstrictClickTime(long astrictClickTime) {
        mAstrictClickTime = astrictClickTime;
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

    /**
     * 监听器状态改变
     */
    public interface OnCheckChangeListener {

        void onChange(boolean check);
    }
}
