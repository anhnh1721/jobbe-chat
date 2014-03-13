package fu.se629.jobbe_chat;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener, TextWatcher{
	public final int MIN_LENGTH = 3;
	public final String STATUS_REGISTER = "status";
	public final String STATUS_LOGIN = "status";
	public final String MESSAGE = "message";
	public final String USER_ID_LOGIN = "userId";
	
	private EditText edtUsername;
	private EditText edtPassword;
	
	private TextView txtMessage;

	private Button btnLogin;
	private Button btnRegister;
	
//	private boolean DATABASE_ENABLE = true;
//	private JobbeChatDatabase mDbJobbe;
	
	private SharedPreferences pref;
	
	private String id = "null";
	
	ProgressDialog progressDialog;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
//        this.mDbJobbe = new JobbeChatDatabase(this);
//		try{
//			this.mDbJobbe.createDataBase();
//		}catch(IOException e){
//			this.DATABASE_ENABLE = false;
//			e.printStackTrace();
//		}
//		try{
//			this.mDbJobbe.openDatabase();
//		}catch(SQLException e){
//			this.DATABASE_ENABLE = false;
//			e.printStackTrace();
//		}
//		if(this.DATABASE_ENABLE) {
////			this.mDbTank.loadSetting();
////			this.mDbTank.loadHighScore();
//		}else{
//			this.showToast("Database error");
//		}
        
		if (getActionBar().isShowing()) getActionBar().hide();
        
		txtMessage = (TextView) findViewById(R.id.txtMessage);
        
		btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
		
		edtUsername = (EditText) findViewById(R.id.edtUsername);
		edtPassword = (EditText) findViewById(R.id.edtPassword);
		edtUsername.addTextChangedListener(this);
		edtPassword.addTextChangedListener(this);
		
        InputFilter filter = new InputFilter() { 
	        public CharSequence filter(CharSequence source, int start, int end, 
	        Spanned dest, int dstart, int dend) { 
	                for (int i = start; i < end; i++) { 
	                	if (!Character.isLetterOrDigit(source.charAt(i))) { 
                            return ""; 
	                	} 
//	                	if (source.charAt(i) == '\'' || source.charAt(i) == '@' || source.charAt(i) == '"') { 
//                            return ""; 
//	                	}  
	                } 
	                return null; 
	        } 
	    }; 
	    edtUsername.setFilters(new InputFilter[] {new InputFilter.LengthFilter(16), filter});
	    edtPassword.setFilters(new InputFilter[] {new InputFilter.LengthFilter(16), filter});
	    
	    pref = getSharedPreferences("status", MODE_PRIVATE);
	    id = pref.getString("id", "null");
	    
	    if(!id.equalsIgnoreCase("null")){
	    	goHomeActivity();
	    }
    }

    @Override
	public void onClick(View view) {
    	if(!this.isInternetAccess()){
    		this.showToast("No internet access");
    		return;
    	}else if(!validateEdtInput()){
			return;
		}
    	
    	txtMessage.setText("");
    	
    	if(view.equals(btnLogin)){
    		progressDialog = ProgressDialog.show(this, null, "Login... Please Wait...");
    		new LoginAsyncTask().execute();
    	}else if(view.equals(btnRegister)){
    		progressDialog = ProgressDialog.show(LoginActivity.this, null, "Register... Please Wait...");
    		new RegisterAsyncTask().execute();
    	}
	}
    
	@Override
	public void afterTextChanged(Editable arg0) {
		
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		String strUser = edtUsername.getText().toString();
		String strPass = edtPassword.getText().toString();
		
		if(strUser.length() > 0 && strPass.length() > 0){
			btnLogin.setEnabled(true);
			btnRegister.setEnabled(true);
		}else{
			btnLogin.setEnabled(false);
			btnRegister.setEnabled(false);
		}
	}
    
    private class LoginAsyncTask extends AsyncTask<Void, String, Boolean>{
    	private String strMessage = "";
    	
		@Override
		protected Boolean doInBackground(Void... params) {
			boolean ok = false;
			
			String username = edtUsername.getText().toString();
	    	String password = edtPassword.getText().toString();
	    	
	    	// Building post parameters, key and value pair
	    	List<NameValuePair> parameter = new ArrayList<NameValuePair>();
	    	parameter.add(new BasicNameValuePair("username", username));
	    	parameter.add(new BasicNameValuePair("password", password));
	    	
	    	HttpClient httpClient = new DefaultHttpClient();
	    	HttpPost httpPost = new HttpPost("http://chat.vunv.me:6969/login");
	    	
	    	// Making HTTP Request
	    	try {
	    		httpPost.setEntity(new UrlEncodedFormEntity(parameter));
	    	    HttpResponse response = httpClient.execute(httpPost);
	    	    HttpEntity entity = response.getEntity();
	    	    
	    	    String strJson = EntityUtils.toString(entity);
	    	    Log.d("me.vunv.chat", strJson);
	    	    
	    	    JSONObject jsonObj = new JSONObject(strJson.trim());
	    	    
	    	    try {
	    	    	ok = jsonObj.getBoolean(STATUS_LOGIN);
	    	    	strMessage = jsonObj.getString(MESSAGE);
	    	    	
	    	    	id = jsonObj.getString(USER_ID_LOGIN);
	    	    	
				} catch (Exception e) {
					strMessage = "Server error";
					 e.printStackTrace();
				}
	    	} catch (ClientProtocolException e) {
	    		strMessage = "Server error";
	    	    e.printStackTrace();
	    	} catch (Exception e) {
	    		strMessage = "Server error";
	    	    e.printStackTrace();
	    	}
	    	return ok;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			txtMessage.setText(strMessage);
			
			if (progressDialog.isShowing())
	            progressDialog.dismiss();
			
			if(result){
				txtMessage.setTextColor(Color.GREEN);
				
				goHomeActivity();
			}else{
				txtMessage.setTextColor(Color.RED);
			}
		}
    }
    
    private class RegisterAsyncTask extends AsyncTask<Void, String, Boolean>{
    	private String strMessage = "";
    	
		@Override
		protected Boolean doInBackground(Void... params) {
			boolean ok = false;
			
			String username = edtUsername.getText().toString();
	    	String password = edtPassword.getText().toString();
	    	
	    	// Building post parameters, key and value pair
	    	List<NameValuePair> parameter = new ArrayList<NameValuePair>();
	    	parameter.add(new BasicNameValuePair("username", username));
	    	parameter.add(new BasicNameValuePair("password", password));
	    	
	    	HttpClient httpClient = new DefaultHttpClient();
	    	HttpPost httpPost = new HttpPost("http://chat.vunv.me:6969/register");
	    	
	    	// Making HTTP Request
	    	try {
	    		httpPost.setEntity(new UrlEncodedFormEntity(parameter));
	    	    HttpResponse response = httpClient.execute(httpPost);
	    	    HttpEntity entity = response.getEntity();
	    	    
	    	    String strJson = EntityUtils.toString(entity);
	    	    Log.d("me.vunv.chat", strJson);
	    	    
	    	    JSONObject jsonObj = new JSONObject(strJson.trim());
	    	    
	    	    try {
	    	    	ok = jsonObj.getBoolean(STATUS_REGISTER);
	    	    	strMessage = jsonObj.getString(MESSAGE);
				} catch (Exception e) {
					 e.printStackTrace();
					 strMessage = "Server error";
				}
	    	} catch (ClientProtocolException e) {
	    		strMessage = "Server error";
	    	    e.printStackTrace();
	    	} catch (Exception e) {
	    		strMessage = "Server error";
	    	    e.printStackTrace();
	    	}
	    	return ok;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			txtMessage.setText(strMessage);
			
			if (progressDialog.isShowing())
	            progressDialog.dismiss();
			
			if(result){
				txtMessage.setTextColor(Color.GREEN);
			}else{
				txtMessage.setTextColor(Color.RED);
			}
		}
    }
    
    public boolean isInternetAccess() {
		try {
		    ConnectivityManager CManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo NInfo = CManager.getActiveNetworkInfo();
		    if (NInfo != null && NInfo.isConnectedOrConnecting()) {
		    	return true;
		    }
		} catch (Exception e) {
		}
		return false;
	}
    
    public void showToast(final String toast){
    	this.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(LoginActivity.this, toast, Toast.LENGTH_LONG).show();
			}
		});
//    	Toast.makeText(this, toast, Toast.LENGTH_LONG).show();
    }
    
    public boolean validateEdtInput(){
    	String username = edtUsername.getText().toString();
    	String password = edtPassword.getText().toString();
    	
    	if(username.length() < MIN_LENGTH || password.length() < MIN_LENGTH){
    		txtMessage.setText("Username and password length >= " + MIN_LENGTH);
    		txtMessage.setTextColor(Color.RED);
    		return false;
    	}
    	
    	return true;
    }
    
    public void goHomeActivity(){
    	SharedPreferences.Editor editor=pref.edit();
		editor.putString("id", id);
		editor.commit();
    	
    	Intent chatIntent = new Intent(this, HomeActivity.class);
    	startActivity(chatIntent);
    	
    	finish();
    }

    
}
