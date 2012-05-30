package com.sfumobile.wifilocator.request;
import java.util.Vector;

import com.sfumobile.wifilocator.utils.WLANContext;

import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.util.StringUtilities;

public class WifiChangePollingService {
	

		private static WifiChangePollingService _self;
		private RequestThread _pollingThread;
		private int _pollingThreadID = 2;
		private Vector _actions;
		private long _currentBSSID;
		private WifiChangePollingService(){
			_pollingThread = new RequestThread();
			_actions = new Vector();
			_currentBSSID = 0L;
		}
		
		
		public void addRequest( RequestPackage t ){
			_actions.addElement(t);
		}
		public void removeRequest( RequestPackage t ) {
			_actions.removeElement( t );
		}
		
		public static WifiChangePollingService getInstance(){
			if (_self == null ){
				_self = new WifiChangePollingService();
			}
			
			return _self;
		}
		
		public boolean isActive(){
			return _pollingThreadID != -1;
		}
		
		public void startPolling(){
			_pollingThreadID = UiApplication.getUiApplication().invokeLater( _pollingThread, 1000, true );
		}
		
		public void stopPolling(){
			UiApplication.getUiApplication().cancelInvokeLater( _pollingThreadID );
			_pollingThreadID = -1;
		}
		
	class RequestThread extends Thread{
			
			public void run(){
				
				long newBSSID =  StringUtilities.stringHashToLong(WLANContext.getBSSID());
				if ( _currentBSSID != newBSSID) {
					for(int i = 0; i < _actions.size(); i++){
						RequestPackage currentRequest = (RequestPackage)_actions.elementAt( i );
						currentRequest.init();
					}
				}
				
			}
		}
	}
