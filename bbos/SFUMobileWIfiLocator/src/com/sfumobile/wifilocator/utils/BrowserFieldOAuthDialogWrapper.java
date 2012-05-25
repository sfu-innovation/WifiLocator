/*
 * BrowserFieldOAuthDialogWrapper.java
 * 21/07/2011
 * Twitter API Micro Edition
 * Copyright(c) Ernandes Mourao Junior (ernandes@gmail.com)
 * All rights reserved
 */
package com.sfumobile.wifilocator.utils;

import net.rim.device.api.browser.field2.BrowserField;
import net.rim.device.api.browser.field2.BrowserFieldListener;

import org.w3c.dom.Document;

import com.twitterapime.xauth.ui.OAuthDialogListener;
import com.twitterapime.xauth.ui.OAuthDialogWrapper;

/**
 * <p>
 * This class defines a wrapper for a BrowserField component, which will hold
 * the authentication process to Twitter. It is responsible for informing to
 * Twitter all the parameters about the application that is trying to get 
 * access to a given user account.
 * </p>
 * <p>
 * In addition, this class also tracks the process in order to identify when the 
 * authorization is granted or denied, so it can notify the application. This 
 * notification is done throught a listener object that implements 
 * {@link OAuthDialogListener}. 
 * </p>
 * <p>
 * Before using this class, the developer must register an app on Twitter
 * Developer website.
 * </p>
 * @author Ernandes Mourao Junior (ernandes@gmail.com)
 * @version 1.0
 * @since 1.8
 */
public final class BrowserFieldOAuthDialogWrapper extends OAuthDialogWrapper {
	/**
	 * <p>
	 * BrowserField object.
	 * </p>
	 */
	private BrowserField browserField;
	
	/**
	 * <p>
	 * Create an instance of BrowserFieldOAuthDialogWrapper class.
	 * </p>
	 * @param browserField BrowserField to display Twitter's login page.
	 * @param consumerKey Consumer key.
	 * @param consumerSecret Consumer secret.
	 * @param callbackUrl Callback Url. (null for "Out-of-band" mode)
	 * @param oauthListener OAuth listener.
	 */
	public BrowserFieldOAuthDialogWrapper(BrowserField browserField, 
		String consumerKey, String consumerSecret, String callbackUrl, 
		OAuthDialogListener oauthListener) {
		super(consumerKey, consumerSecret, callbackUrl, oauthListener);
		//
		if (browserField == null) {
			throw new IllegalArgumentException(
				"BrowserField must not be null.");
		}
		//
		this.browserField = browserField;
		this.browserField.addListener(new BrowserFieldListenerOAuth());
	}
	
	/**
	 * <p>
	 * Create an instance of BrowserFieldOAuthDialogWrapper class.
	 * </p>
	 * @param browserField BrowserField to display Twitter's login page.
	 */
	public BrowserFieldOAuthDialogWrapper(BrowserField browserField) {
		this(browserField, null, null, null, null);
	}

	/**
	 * @see com.twitterapime.xauth.ui.OAuthDialogWrapper#loadUrl(java.lang.String)
	 */
	protected void loadUrl(String url) {
		browserField.requestContent(url);
	}
	
	/**
	 * @see com.twitterapime.xauth.ui.OAuthDialogWrapper#loadHTML(java.lang.String)
	 */
	protected void loadHTML(String htmlContent) {
		browserField.displayContent(htmlContent, callbackUrl);
	}
	
	/**
	 * @author ernandes@gmail.com
	 */
	private class BrowserFieldListenerOAuth extends BrowserFieldListener {
		/**
		 * @see net.rim.device.api.browser.field2.BrowserFieldListener#documentLoaded(net.rim.device.api.browser.field2.BrowserField, org.w3c.dom.Document)
		 */
		public void documentLoaded(BrowserField browserField, Document document)
			throws Exception {
			trackUrl(browserField.getDocumentUrl());
		}
	}
}
