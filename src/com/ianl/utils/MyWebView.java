package com.ianl.utils;

import android.app.Activity;
import android.content.Context;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebView extends WebView {

	public MyWebView(final Context context) {
		super(context);
		initWebSettings();
		setupWebClients(context);
	}
	
	private void initWebSettings() {
		final WebSettings webSettings = getSettings();

		webSettings.setJavaScriptEnabled(true);
		webSettings.setLoadsImagesAutomatically(true);
		webSettings.setSupportZoom(true);
		webSettings.setBuiltInZoomControls(true);
		
		invokeZoomPicker();
	}
	
	private void setupWebClients(final Context context) {
		setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				// Activities and WebViews measure progress with different
				// scales.
				// The progress meter will automatically disappear when we reach
				// 100%
				((Activity) context).setProgress(progress * 1000);
			}
		});
		
		setWebViewClient(new WebViewClient() {
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// Toast.makeText(activity, "Oh no! " + description,
				// Toast.LENGTH_SHORT).show();
			}
		});
	}
	
}