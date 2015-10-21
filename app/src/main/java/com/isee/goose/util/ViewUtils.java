package com.isee.goose.util;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class ViewUtils {

    public static void fadeIn(final View view,Context cxt, final boolean animate) {
        if (view != null)
            if (animate)
                view.startAnimation(AnimationUtils.loadAnimation(cxt, android.R.anim.fade_in));
            else
                view.clearAnimation();
    }

    public static void fadeOut(final View view,Context cxt, final boolean animate) {
        if (view != null)
            if (animate)
                view.startAnimation(AnimationUtils.loadAnimation(cxt, android.R.anim.fade_out));
            else
                view.clearAnimation();
    }

	private static void changViewVisibility(View view, int visibility) {
		if (view == null) {
			return;
		}
		if (view.getVisibility()!=visibility) {
			view.setVisibility(visibility);
		}
	}

	public static void goneView(View view){
		changViewVisibility(view, View.GONE);
	}
	
	public static void setTextViewUnderline(TextView view) {
		if (view == null) {
			return;
		}
		view.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		view.getPaint().setAntiAlias(true);
	}

    public static void removeTextViewUnderline(TextView view) {
        if (view == null) {
            return;
        }
        view.getPaint().setFlags(Paint.ANTI_ALIAS_FLAG);
        view.getPaint().setAntiAlias(false);
    }
	
	public static void showView(View view){
		changViewVisibility(view, View.VISIBLE);
	}
	
	public static void transparentView(View view){
		changViewVisibility(view, View.INVISIBLE);
	}
	
	private ViewUtils() {
	}
}
