package com.sfumobile.wifilocator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertDialogBuilder {

	static AlertDialog alert;
	
	public static AlertDialog createDialog(Context context, String message){
		
		alert = new AlertDialog.Builder(context).create();
		alert.setButton("OK", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				alert.cancel();
			}
		});
		alert.setMessage(message);
		return alert;
	}
}
