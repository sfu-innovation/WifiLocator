import net.rim.device.api.browser.field.RenderingOptions;
import net.rim.device.api.browser.field2.BrowserField;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.container.MainScreen;

import com.sfumobile.wifilocator.utils.BrowserFieldOAuthDialogWrapper;
import com.sfumobile.wifilocator.utils.TwitterConstants;
import com.twitterapime.rest.Credential;
import com.twitterapime.rest.UserAccountManager;
import com.twitterapime.search.Query;
import com.twitterapime.search.QueryComposer;
import com.twitterapime.search.SearchDevice;
import com.twitterapime.search.Tweet;
import com.twitterapime.xauth.Token;
import com.twitterapime.xauth.ui.OAuthDialogListener;
import com.twitterapime.xauth.ui.OAuthDialogWrapper;


	public class BrowserFieldScreen extends MainScreen implements OAuthDialogListener, FieldChangeListener {
		/**
		 * 
		 */
		private final String CONSUMER_KEY = TwitterConstants.CONSUMER_KEY;
		private final String CONSUMER_SECRET = TwitterConstants.CONSUMER_SECRET;
		private final String CALLBACK_URL = TwitterConstants.CALLBACK_URL;
		private BasicEditField pinField;
		private ButtonField pinButton;
		private OAuthDialogWrapper  pageWrapper;
		public BrowserFieldScreen() {
			//setTitle("Twitter API ME - OAuth");
			BrowserField browserMngr = new BrowserField();
			RenderingOptions rendOptions = browserMngr.getRenderingOptions();
		//	RenderingOptions rendOptions = browserMngr.getRenderingSession().getRenderingOptions();
			rendOptions.setProperty(
				RenderingOptions.CORE_OPTIONS_GUID,
				RenderingOptions.SHOW_IMAGES_IN_HTML,
				false);
			//
			add(browserMngr);
			
			pinField = new BasicEditField("Enter Pin", "");
			add(pinField);
			
			pinButton = new ButtonField("Login");
			pinButton.setChangeListener( this) ;
			add(pinButton);
			//
			 pageWrapper = 
				new BrowserFieldOAuthDialogWrapper(browserMngr);
			//
			pageWrapper.setConsumerKey(CONSUMER_KEY);
			pageWrapper.setConsumerSecret(CONSUMER_SECRET);
			//pageWrapper.setCallbackUrl(CALLBACK_URL);
			pageWrapper.setOAuthListener(this);
			//
			pageWrapper.login();
		}

		/**
		 * @see com.twitterapime.xauth.ui.OAuthDialogListener#onAuthorize(com.twitterapime.xauth.Token)
		 */
		public void onAuthorize(Token token) {
			Credential c = new Credential(CONSUMER_KEY,	CONSUMER_SECRET, token);
			UserAccountManager uam = UserAccountManager.getInstance(c);
			//
			try {
				if (uam.verifyCredential()) {
					SearchDevice sd = SearchDevice.getInstance();
					 Query q = QueryComposer.containHashtag("Galleria3_3");
					 Tweet[] ts = sd.searchTweets(q);
					 int length = ts.length;
					 for(int i = 0; i < length; i++){
						 System.out.println(ts[i].toString());
					 }
					
					///TweetER.getInstance(uam).post(new Tweet("test: " + System.currentTimeMillis()));
					//
					//System.out.println("Tweet posted!");
				}
			} catch (Exception e) {
				System.out.println("Error by posting tweet.");
			}
		}

		/**
		 * @see com.twitterapime.xauth.ui.OAuthDialogListener#onAccessDenied(java.lang.String)
		 */
		public void onAccessDenied(String message) {
			System.out.println("Access denied!");
		}

		/**
		 * @see com.twitterapime.xauth.ui.OAuthDialogListener#onFail(java.lang.String,
		 *      java.lang.String)
		 */
		public void onFail(String error, String message) {
			System.out.println("Error by authenticating user!");
		}

		public void fieldChanged(Field field, int context) {
			// TODO Auto-generated method stub
			pageWrapper.login( pinField.getText());
		}
		protected void triggerOnAuthorize(Token accessToken){
			Credential c = new Credential(CONSUMER_KEY,	CONSUMER_SECRET, accessToken);
			UserAccountManager uam = UserAccountManager.getInstance(c);
			//
			try {
				if (uam.verifyCredential()) {
					SearchDevice sd = SearchDevice.getInstance();
					 Query q = QueryComposer.containHashtag("Galleria3_3");
					 Tweet[] ts = sd.searchTweets(q);
					 int length = ts.length;
					 for(int i = 0; i < length; i++){
						 System.out.println(ts[i].toString());
					 }
					//TweetER.getInstance(uam).post(new Tweet("test: " + System.currentTimeMillis()));
					//
					System.out.println("Tweet posted!");
				}
			} catch (Exception e) {
				System.out.println("Error by posting tweet.");
			}
		}
	}

