package com.jia.lollipop.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * <pre>
 *     desc  : 未归类工具类
 * </pre>
 */

public class UnclassifiedUtils {

	private UnclassifiedUtils() {
	}

	/**
	 * 获取服务是否开启
	 *
	 * @param context
	 *            上下文
	 * @param className
	 *            完整包名的服务类名
	 * @return {@code true}: 是<br>
	 *         {@code false}: 否
	 */
	public static boolean isRunningService(Context context, String className) {
		// 进程的管理者,活动的管理者
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		// 获取正在运行的服务，最多获取1000个
		List<RunningServiceInfo> runningServices = activityManager
				.getRunningServices(1000);
		// 遍历集合
		for (RunningServiceInfo runningServiceInfo : runningServices) {
			ComponentName service = runningServiceInfo.service;
			if (className.equals(service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @author 原viewutils里的几个方法 ，后期归类处理
	 *
	 */

	// 关闭软件盘
	public static void hideSoftKeyboard(Activity activity) {

		InputMethodManager inputMethodManager = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (inputMethodManager != null) {
			inputMethodManager.hideSoftInputFromWindow(activity.getWindow()
					.getDecorView().getWindowToken(), 0);
		}
	}

	public static void setupUI(final View view, final Activity activity) {
		// Set up touch listener for non-text box views to hide keyboard.
		if (!(view instanceof EditText)) {
			view.setOnTouchListener(new OnTouchListener() {
				@SuppressLint("ClickableViewAccessibility")
				public boolean onTouch(View v, MotionEvent event) {
					hideSoftKeyboard(activity); // Main.this是我的activity名
					view.requestFocus();
					return false;
				}
			});
		}
		if (view instanceof ViewGroup) {
			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
				View innerView = ((ViewGroup) view).getChildAt(i);
				setupUI(innerView, activity);

			}
		}
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			// int desiredWidth =
			// MeasureSpec.makeMeasureSpec(listView.getWidth(),
			// MeasureSpec.AT_MOST);
			// listItem.measure(desiredWidth, 0);
			listItem.measure(0, 0);
			LogUtil.i(
					"tag",
					"listItem.getMeasuredHeight();"
							+ listItem.getMeasuredHeight());
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	public static ProgressDialog createProgressDialog(Context mContext,
			String string) {
		// ProgressDialog dialog = new ProgressDialog(mContext, R.style.dialog);
		ProgressDialog dialog = new ProgressDialog(mContext);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setMessage(string);
		dialog.setIndeterminate(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(true);
		return dialog;
	}
}