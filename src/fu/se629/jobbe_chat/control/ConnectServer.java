package fu.se629.jobbe_chat.control;

import android.util.Log;
import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;
import fu.se629.jobbe_chat.HomeActivity;
import fu.se629.jobbe_chat.entity.Message;

public class ConnectServer {
	private static final String TAG = "me.vunv.chat";
	private static final String websocketUri = "ws://chat.vunv.me:6969/";
	private final WebSocketConnection mConnection;

	private HomeActivity activity;

	public boolean isOpen = false;
	public ConnectServer(HomeActivity activity) {
		this.activity = activity;

		this.mConnection = new WebSocketConnection();
		startWebsocket();
	}

	public void startWebsocket() {
		try {
			mConnection.connect(websocketUri, new WebSocketHandler() {

				@Override
				public void onOpen() {
					isOpen = true;
					Log.d(TAG, "Status: Connected to " + websocketUri);
					mConnection.sendTextMessage("{\"action\": \"connect\", \"id\": \"" + activity.userId + "\"}");
				}

				@Override
				public void onTextMessage(String payload) {
					if(activity.fragmentChat != null){
						activity.fragmentChat.addNewMessage(new Message(true, payload, "10:10 PM"));
					}
					Log.d(TAG, "Got echo: " + payload);
				}

				@Override
				public void onClose(int code, String reason) {
					isOpen = false;
					Log.d(TAG, "Connection lost.");
				}
			});
		} catch (WebSocketException e) {
			isOpen = false;
			Log.d(TAG, e.toString());
		}
	}
	
	public boolean sendMessage(String msg){
		try {
			mConnection.sendTextMessage(msg);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
