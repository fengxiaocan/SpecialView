package com.app.special;


import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;


/**
 * @项目名： xiaoyujia
 * @包名： com.vooda.smartHome.view
 * @创建者: Noah.冯
 * @时间: 10:24
 * @描述： 可以点击的Button
 */

public class ClickButton extends TextView implements View.OnClickListener {

    //默认能点击
    public final static boolean DEFAULT_CAN_CLICK = true;
    public final static @DrawableRes
    int DEFAULT_CLICK_BACKGROUND_ID = android.R.color.transparent;
    public final static @DrawableRes
    int DEFAULT_UNCLICK_BACKGROUND_ID = android.R.color.transparent;
    protected OnClickListener mOnClickListener;
    protected CharSequence mCurrentText;
    //是否能点击
    protected boolean isCanClick;
    protected @DrawableRes
    int clickBackgroundId;
    protected int clickColor;
    protected @DrawableRes
    int unclickBackgroundId;
    protected int unclickColor;
    private OnCanClickListener mOnCanClickListener;

    public ClickButton(Context context) {
        this(context, null);
    }

    public ClickButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClickButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ClickButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
            int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CharSequence getDefaultText() {
        return "点击";
    }

    protected void init(AttributeSet attrs) {
        mCurrentText = getText();
        if (mCurrentText == null || mCurrentText.length() == 0) {
            mCurrentText = getDefaultText();
            setText(mCurrentText);
        }
        TypedArray type = getContext().obtainStyledAttributes(attrs, R.styleable.ClickButton);
        isCanClick = type.getBoolean(R.styleable.ClickButton_click, DEFAULT_CAN_CLICK);
        clickBackgroundId = type.getResourceId(R.styleable.ClickButton_clickBackground,
                DEFAULT_CLICK_BACKGROUND_ID);
        unclickBackgroundId = type.getResourceId(R.styleable.ClickButton_unclickBackground,
                DEFAULT_UNCLICK_BACKGROUND_ID);
        clickColor = type.getColor(R.styleable.ClickButton_clickTextColor, getCurrentTextColor());
        unclickColor = type.getColor(R.styleable.ClickButton_unclickTextColor,
                getCurrentTextColor());
        type.recycle();
        super.setOnClickListener(this);
        initView();
    }

    protected void initView() {
        setCanClick(isCanClick);
    }

    public void setCurrentText(CharSequence currentText) {
        mCurrentText = currentText;
        setText(mCurrentText);
    }

    public boolean isCanClick() {
        return isCanClick;
    }

    /**
     * 是否能点击
     *
     * @param canClick
     */
    public void setCanClick(boolean canClick) {
        isCanClick = canClick;
        if (isCanClick) {
            setBackgroundResource(clickBackgroundId);
            setTextColor(clickColor);
        } else {
            setBackgroundResource(unclickBackgroundId);
            setTextColor(unclickColor);
        }
    }

    public void toggle() {
        setCanClick(isCanClick = !isCanClick);
    }

    @Override
    public void onClick(View v) {
        if (mOnClickListener != null){
            mOnClickListener.onClick(v);
        }
        if (mOnCanClickListener != null) {
            if (isCanClick) {
                setCanClick(mOnCanClickListener.canClick());
            }
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mOnClickListener = l;
        super.setOnClickListener(this);
    }

    /**
     * 关闭点击
     */
    public void closeClick() {
        setCanClick(false);
    }

    /**
     * 打开点击
     */
    public void openClick() {
        setCanClick(true);
    }

    public OnCanClickListener getOnCanClickListener() {
        return mOnCanClickListener;
    }

    public void setOnCanClickListener(OnCanClickListener onCanClickListener) {
        mOnCanClickListener = onCanClickListener;
    }

    public void setClickBackground(@DrawableRes int clickBackgroundId) {
        this.clickBackgroundId = clickBackgroundId;
        postInvalidate();
    }

    public void setClickColor(@ColorInt int clickColor) {
        this.clickColor = clickColor;
        postInvalidate();
    }

    public void setUnclickBackground(@DrawableRes int unclickBackgroundId) {
        this.unclickBackgroundId = unclickBackgroundId;
        postInvalidate();
    }

    public void setUnclickColor(@ColorInt int unclickColor) {
        this.unclickColor = unclickColor;
        postInvalidate();
    }
    public void postSetClick(final boolean isCheck,long time) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                setCanClick(isCheck);
            }
        },time);
    }
}
