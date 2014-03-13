package fu.se629.jobbe_chat.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MessageReceiver extends BroadcastReceiver {

	public MessageReceiver(){
		
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
			Bundle bundle = intent.getExtras();
			Object[] objs = (Object[]) bundle.get("pdus");
			
			SmsMessage[] messages = new SmsMessage[objs.length];
			
			for (int i = 0; i < objs.length; i++) {
				messages[i] = SmsMessage.createFromPdu((byte[])objs[i]);
			}
			
			for (int i = 0; i < objs.length; i++) {
				Toast.makeText(context, messages[i].getMessageBody(), Toast.LENGTH_LONG).show();
			}
		}
	}
	
	
}
