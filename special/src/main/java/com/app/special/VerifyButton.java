package com.app.special;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;


/**
 * @项目名： xiaoyujia
 * @包名： com.vooda.smartHome.view
 * @创建者: Noah.冯
 * @时间: 10:24
 * @描述： 点击发送验证码的按钮
 */

public class VerifyButton extends ClickButton {
    //最大时间
    private static long sMaxTime = 60;
    private static long sStartTime;
    //正在计时时间
    private static boolean isRunning = false;
    private static boolean sStop = false;

    boolean isSaveLocal;//是否保存在本地;

    //时间格式化规则
    private String TimeRegex = "##s";
    private OnSendVerifyCallback mOnSendVerifyCallback;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (sStop) {
                return;
            }
            long timing = getTiming();
            setText(TimeRegex.replace("##", String.valueOf(timing)));
            requestLayout();
            if (timing > 0) {
                isCanClick = false;
                isRunning = true;
                runTime();
            } else {
                isCanClick = true;
                setCanClick(isCanClick);
                isRunning = false;
            }
        }
    };

    public VerifyButton(Context context) {
        super(context);
    }

    public VerifyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerifyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public VerifyButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
            int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public CharSequence getDefaultText() {
        return "获取验证码";
    }

    @Override
    protected void initView() {

    }

    @Override
    public void setCurrentText(CharSequence currentText) {
        mCurrentText = currentText;
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
            setText(mCurrentText);
            requestLayout();
        } else {
            setBackgroundResource(unclickBackgroundId);
            setTextColor(unclickColor);
        }
    }

    /**
     * 设置时间格式化
     *
     * @param timeRegex
     */
    public void setTimeRegex(String timeRegex) {
        if (!TextUtils.isEmpty(timeRegex) || timeRegex.contains("##")) {
            TimeRegex = timeRegex;
        }
    }

    /**
     * 设置时间格式化
     *
     * @param maxTime
     */
    public void setMaxTime(long maxTime) {
        if (maxTime > 0) {
            sMaxTime = maxTime;
        }
    }

    private void runTime() {
        postDelayed(mRunnable, 999);
    }

    //发送成功
    public void sendSuccess() {
        if (isRunning) {
            isCanClick = false;
            setCanClick(isCanClick);
            sStartTime = System.currentTimeMillis();
            post(new Runnable() {
                @Override
                public void run() {
                    setText(TimeRegex.replace("##", String.valueOf(sMaxTime)));
                    requestLayout();
                    runTime();
                }
            });
        }
    }

    //发送失败
    public void sendFailure() {
        isCanClick = true;
        setCanClick(isCanClick);
    }

    /**
     * 重置验证码
     */
    public void resetVerify(boolean defaults) {
        sStop = true;
        sStartTime = 0;
        if (isSaveLocal) {
            saveLocal();
        }
        isCanClick = defaults;
        setCanClick(isCanClick);
    }

    public void setOnSendVerifyCallback(OnSendVerifyCallback onSendVerifyCallback) {
        mOnSendVerifyCallback = onSendVerifyCallback;
    }

    private long getTiming() {
        return sMaxTime - (System.currentTimeMillis() - sStartTime) / 1000;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        sStop = false;
        long verifyCodeTime = getSp().getLong("VerifyCodeTime", 0L);

        long lTime = (System.currentTimeMillis() - verifyCodeTime) / 1000;
        if (lTime < sMaxTime) {
            sStartTime = verifyCodeTime;
            //获取上次的时间，判断时间是否执行完成
            long timing = getTiming();
            setText(TimeRegex.replace("##", String.valueOf(timing)));
            requestLayout();
            isRunning = true;
            isCanClick = false;
            setCanClick(isCanClick);
            runTime();
        } else {
            isRunning = false;
            setCanClick(isCanClick);
        }
    }

    private SharedPreferences getSp() {
        return getContext().getSharedPreferences(getContext().getPackageName(),
                Context.MODE_PRIVATE);
    }

    /**
     * 本地持久化
     */
    protected void saveLocal() {
        SharedPreferences.Editor edit = getSp().edit();
        edit.putLong("VerifyCodeTime", sStartTime);
        edit.commit();
    }

    @Override
    protected void onDetachedFromWindow() {
        if (isRunning) {
            sStop = true;
            if (isSaveLocal) {
                saveLocal();
            }
        }
        super.onDetachedFromWindow();
    }

    @Override
    public void onClick(View v) {
        if (isCanClick) {
            if (mOnClickListener != null) {
                mOnClickListener.onClick(v);
            }
            sStop = false;
            toggle();
            isRunning = true;
            if (mOnSendVerifyCallback == null) {
                throw new NullPointerException("请实现OnSendVerifyCallback的startSend()方法");
            } else {
                mOnSendVerifyCallback.startSend();
            }
        }
    }

    public interface OnSendVerifyCallback {

        void startSend();
    }
}
