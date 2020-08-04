package com.app.special;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * The type Twofold view.
 *
 * @项目名： MiParkAdmin
 * @包名： com.vooda.parkadmin.ui.view
 * @创建者: Noah.冯
 * @时间: 14 :48
 * @描述： 双重字体的自定义view
 */
public class TwofoldView extends TextView {
	
	private CharSequence mOneText;
	private CharSequence mTwoText;
	private int mOneTextColor;
	private int mTwoTextColor;
	private float mOneSize;
	private float mTwoSize;
	
	/**
	 * Instantiates a new Twofold view.
	 *
	 * @param context the context
	 */
	public TwofoldView(Context context) {
		this(context,null);
	}
	
	/**
	 * Instantiates a new Twofold view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public TwofoldView(Context context,AttributeSet attrs) {
		this(context,attrs,0);
	}
	
	/**
	 * Instantiates a new Twofold view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 * @param defStyleAttr the def style attr
	 */
	public TwofoldView(Context context,AttributeSet attrs,int defStyleAttr) {
		super(context,attrs,defStyleAttr);
		initAttrs(attrs);
	}
	
	/**
	 * Instantiates a new Voidappend one text.
	 * 追加文字
	 *
	 * @param str the str
	 */
	public void appendOneText(CharSequence str) {
		StringBuffer buffer = new StringBuffer(mOneText);
		buffer.append(str);
		mOneText = buffer;
		buildText();
	}
	
	/**
	 * Init attrs.
	 *
	 * @param attrs the attrs
	 */
	protected void initAttrs(AttributeSet attrs) {
		TypedArray type = this.getContext().obtainStyledAttributes(attrs,R.styleable.TwofoldView);
		if (type != null) {
			CharSequence oneText = type.getText(R.styleable.TwofoldView_one_text);
			mOneText = noNull(oneText);
			CharSequence twoText = type.getText(R.styleable.TwofoldView_two_text);
			mTwoText = noNull(twoText);
			
			mOneTextColor = type.getColor(R.styleable.TwofoldView_one_text_color,getCurrentTextColor());
			mTwoTextColor = type.getColor(R.styleable.TwofoldView_two_text_color,getCurrentTextColor());
			
			mOneSize = type.getDimension(R.styleable.TwofoldView_one_text_size,getTextSize());
			mTwoSize = type.getDimension(R.styleable.TwofoldView_two_text_size,getTextSize());
			
			type.recycle();
		}
		buildText();
	}
	
	/**
	 * Sets one text size.
	 *
	 * @param size the size
	 */
	public void setOneTextSize(int size) {
		mOneSize = size;
		buildText();
	}
	
	/**
	 * Sets two text size.
	 *
	 * @param size the size
	 */
	public void setTwoTextSize(int size) {
		mTwoSize = size;
		buildText();
	}
	
	/**
	 * Gets one text.
	 *
	 * @return the one text
	 */
	public CharSequence getOneText() {
		return mOneText;
	}
	
	/**
	 * Sets one text.
	 *
	 * @param str the str
	 */
	public void setOneText(CharSequence str) {
		mOneText = str;
		buildText();
	}
	
	/**
	 * Gets two text.
	 *
	 * @return the two text
	 */
	public CharSequence getTwoText() {
		return mTwoText;
	}
	
	/**
	 * Sets two text.
	 *
	 * @param str the str
	 */
	public void setTwoText(CharSequence str) {
		mTwoText = noNull(str);
		buildText();
	}
	
	/**
	 * Append two text.
	 * 追加文字
	 *
	 * @param str the str
	 */
	public void appendTwoText(CharSequence str) {
		StringBuffer buffer = new StringBuffer(mTwoText);
		buffer.append(str);
		mTwoText = buffer;
		buildText();
	}
	
	
	/**
	 * Gets one text color.
	 *
	 * @return the one text color
	 */
	public int getOneTextColor() {
		return mOneTextColor;
	}
	
	/**
	 * Sets one text color.
	 *
	 * @param oneTextColor the one text color
	 */
	public void setOneTextColor(int oneTextColor) {
		mOneTextColor = oneTextColor;
	}
	
	/**
	 * Gets two text color.
	 *
	 * @return the two text color
	 */
	public int getTwoTextColor() {
		return mTwoTextColor;
	}
	
	/**
	 * Sets two text color.
	 *
	 * @param twoTextColor the two text color
	 */
	public void setTwoTextColor(int twoTextColor) {
		mTwoTextColor = twoTextColor;
	}
	
	/**
	 * Gets one size.
	 *
	 * @return the one size
	 */
	public float getOneSize() {
		return mOneSize;
	}
	
	/**
	 * Gets two size.
	 *
	 * @return the two size
	 */
	public float getTwoSize() {
		return mTwoSize;
	}
	
	/**
	 * Build text.
	 */
	protected void buildText() {
		SpannableStringBuilder builder = new SpannableStringBuilder();
		builder.append(mOneText);
		builder.append(mTwoText);
		if (!TextUtils.isEmpty(mOneText)) {
			AbsoluteSizeSpan oneSpan = new AbsoluteSizeSpan((int)mOneSize,false);
			ForegroundColorSpan oneColorSpan = new ForegroundColorSpan(mOneTextColor);
			builder.setSpan(oneSpan,0,mOneText.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			builder.setSpan(oneColorSpan,0,mOneText.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			if (!TextUtils.isEmpty(mTwoText)) {
				AbsoluteSizeSpan twoSpan = new AbsoluteSizeSpan((int)mTwoSize,false);
				ForegroundColorSpan twoColorSpan = new ForegroundColorSpan(mTwoTextColor);
				builder.setSpan(twoSpan,mOneText.length(),builder.length(),
				                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				builder.setSpan(twoColorSpan,mOneText.length(),builder.length(),
				                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}
		else {
			if (!TextUtils.isEmpty(mTwoText)) {
				AbsoluteSizeSpan twoSpan = new AbsoluteSizeSpan((int)mTwoSize,false);
				ForegroundColorSpan twoColorSpan = new ForegroundColorSpan(mTwoTextColor);
				builder.setSpan(twoSpan,0,builder.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				builder.setSpan(twoColorSpan,0,builder.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}
		
		setText(builder);
	}
	
	
	/**
	 * dp转px
	 *
	 * @param dpValue dp值
	 * @return px值
	 */
	public int dp2px(float dpValue) {
		final float scale = getResources().getDisplayMetrics().density;
		return (int)(dpValue * scale + 0.5f);
	}
	
	/**
	 * px转dp
	 *
	 * @param pxValue px值
	 * @return dp值
	 */
	public int px2dp(float pxValue) {
		final float scale = getResources().getDisplayMetrics().density;
		return (int)(pxValue / scale + 0.5f);
	}
	/**
	 * null转为长度为0的字符串
	 *
	 * @param s 待转字符串
	 * @return s为null转为长度为0字符串，否则不改变
	 */
	public CharSequence noNull(CharSequence s) {
		return s == null ? "" : s;
	}
	//    @Override
	//    public void setText(CharSequence text,BufferType type){
	//        String filter = stringFilter(text.toString());
	//        super.setText(filter,type);
	//    }
	//
	//    /**
	//     * 去除特殊字符或将所有中文标号替换为英文标号
	//     *
	//     * @param str
	//     * @return
	//     */
	//    public static String stringFilter(String str){
	//        str = str.replaceAll("【","[").replaceAll("】","]").replaceAll("！","!").replaceAll("：",":");// 替换中文标号
	//        String regEx = "[『』]"; // 清除掉特殊字符
	//        Pattern p = Pattern.compile(regEx);
	//        Matcher m = p.matcher(str);
	//        return m.replaceAll("").trim();
	//    }
}
