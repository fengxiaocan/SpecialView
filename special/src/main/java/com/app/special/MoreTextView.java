package com.app.special;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;


/**
 * 结尾带“查看全部”的TextView，点击可以展开文字，展开后可收起。
 * <p/>
 * 目前存在一个问题：外部调用setText()时会造成界面该TextView下方的View抖动；
 * <p/>
 * 可以先调用getFullText()，当已有文字和要设置的文字不一样才调用setText()，可降低抖动的次数；
 * <p/>
 * 通过在onMeasure()中设置高度已经修复了该问题了。
 * <p/>
 */
public class MoreTextView extends TextView {
	
	public static final int MAX_LINE = 3;
	private boolean isFirst;
	private boolean isPackUp;
	private boolean isMaxLine = false;
	// 默认打点文字
	private String defaultEllipsize = "...";
	// 默认展开文字
	private String defaultUnfoldText = "查看更多>>";
	//收起来
	private String defaultFoldText = "收起↑↑";
	
	private int mUnlineColor;
	private int mContentTextColor;
	private CharSequence mContentText;
	private ForegroundColorSpan mTextColorSpan;
	private TextClickableSpan mTextClickableSpan = new TextClickableSpan();
	private NoUnlinesSpans mNoUnlinesSpans = new NoUnlinesSpans();
	
	public MoreTextView(Context context) {
		this(context,null);
	}
	
	public MoreTextView(Context context,AttributeSet attrs) {
		this(context,attrs,0);
	}
	
	public MoreTextView(Context context,AttributeSet attrs,int defStyleAttr) {
		super(context,attrs,defStyleAttr);
		initAttrs(attrs);
		isFirst = true;
		isPackUp = true;
	}
	
	protected void initAttrs(AttributeSet attrs) {
		TypedArray type = this.getContext().obtainStyledAttributes(attrs,R.styleable.MoreTextView);
		if (type != null) {
			mUnlineColor = type.getColor(R.styleable.MoreTextView_un_line_color,Color.rgb(0,0,0));
			defaultEllipsize = checkIsNull(type.getString(R.styleable.MoreTextView_ellipsizeText));
			defaultUnfoldText = checkIsNull(type.getString(R.styleable.MoreTextView_unfoldText));
			defaultFoldText = checkIsNull(type.getString(R.styleable.MoreTextView_foldText));
			type.recycle();
		}
	}
	
	private String checkIsNull(String text) {
		if (text == null) { return ""; }
		return text;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (!isMaxLine) {
			isMaxLine = getLineCount() > MAX_LINE;
		}
		if (isFirst) {
			isFirst = false;
			if (isPackUp) {
				if (isMaxLine) {
					if (getLineCount() > MAX_LINE) {
						//行数大于2
						setMaxLines(MAX_LINE);
						Layout layout = getLayout();
						//获取最后一行的字数
						int lineEnd = layout.getLineEnd(MAX_LINE - 1);
						String text = getText().toString();
						String substring = text.substring(0,lineEnd - 8);
						SpannableStringBuilder builder = new SpannableStringBuilder(
								substring + defaultEllipsize + defaultUnfoldText);
						//设置内容颜色
						builder.setSpan(mTextColorSpan,0,substring.length(),
						                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						//设置点击事件
						builder.setSpan(mTextClickableSpan,
						                substring.length() + defaultEllipsize.length(),
						                builder.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						builder.setSpan(mNoUnlinesSpans,
						                substring.length() + defaultEllipsize.length(),
						                builder.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						setMovementMethod(LinkMovementMethod.getInstance());
						setText(builder);
						setHighlightColor(Color.TRANSPARENT);
					}
				}
				
			}
			else {
				setMaxLines(Integer.MAX_VALUE);
				if (isMaxLine) {
					SpannableStringBuilder builder = new SpannableStringBuilder(
							mContentText + defaultFoldText);
					
					//设置内容颜色
					builder.setSpan(mTextColorSpan,0,mContentText.length(),
					                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					//设置点击事件
					builder.setSpan(mTextClickableSpan,mContentText.length(),builder.length(),
					                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					builder.setSpan(mNoUnlinesSpans,mContentText.length(),builder.length(),
					                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					setMovementMethod(LinkMovementMethod.getInstance());
					setText(builder);
					setHighlightColor(Color.TRANSPARENT);
				}
				else {
					setTextColor(mContentTextColor);
					setText(mContentText);
				}
			}
		}
	}
	
	/**
	 * 设置文本
	 *
	 * @param text
	 */
	public void setContextText(CharSequence text,@ColorInt int color) {
		mContentText = text;
		isFirst = true;
		isPackUp = true;
		mContentTextColor = color;
		mTextColorSpan = new ForegroundColorSpan(color);
		setTextColor(mContentTextColor);
		setText(text);
	}
	
	class TextClickableSpan extends ClickableSpan {
		
		@Override
		public void onClick(View widget) {
			isFirst = true;
			isPackUp = !isPackUp;
			postInvalidate();
		}
	}
	
	class NoUnlinesSpans extends UnderlineSpan {
		
		@Override
		public void updateDrawState(TextPaint ds) {
			ds.setColor(mUnlineColor);
			//设置可点击文本的字体颜色
			ds.setUnderlineText(false);
		}
	}
}
