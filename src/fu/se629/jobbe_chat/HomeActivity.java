package fu.se629.jobbe_chat;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import fu.se629.jobbe_chat.control.ConnectServer;
import fu.se629.jobbe_chat.control.ListFriendAdapter;
import fu.se629.jobbe_chat.control.ListMessageAdapter;
import fu.se629.jobbe_chat.entity.Friend;
import fu.se629.jobbe_chat.entity.Message;

public class HomeActivity extends Activity {

	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private LinearLayout mDrawerHome;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
	
    public PlanetFragment fragmentChat;
    
    public String userId = "null";
    
    private ConnectServer mConnectServer;
    
    private SharedPreferences pref;
    
    private Button btnLogout;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_friend);
        mDrawerHome = (LinearLayout) findViewById(R.id.home_tab);
        
        btnLogout = (Button) findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				logOut();
			}
		});
        
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        
        // set up the drawer's list view with items and click listener
        ListFriendAdapter listFriendAdapter = new ListFriendAdapter(this, friendsData());
        
        mDrawerList.setAdapter(listFriendAdapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
//        getActionBar().setDisplayUseLogoEnabled(false);
//        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#cccccc")));

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

//        if (savedInstanceState == null) {
//            selectItem(0);
//        }
        
        mDrawerLayout.openDrawer(mDrawerHome);
        
        //Get Id
        pref = getSharedPreferences("status", MODE_PRIVATE);
	    userId = pref.getString("id", "null");
	    
	    if(userId.equalsIgnoreCase("null")){
	    	showToast("User error");
	    	logOut();
	    	return;
	    }
        
        mConnectServer = new ConnectServer(this);

    }
    ArrayList<Friend> items;
    //xoa dc
    private ArrayList<Friend> friendsData(){
    	items = new ArrayList<Friend>();
        items.add(new Friend(8, "Friend 8", "", new Message(true, "Fists", "10:20AM")));
        items.add(new Friend(4, "Friend 4", "", new Message(true, "Fists 1", "10:00AM")));
        items.add(new Friend(8, "Friend 8", "", new Message(true, "Fists", "10:20AM")));
        items.add(new Friend(4, "Friend 4", "", new Message(true, "Fists 1", "10:00AM")));
        items.add(new Friend(8, "Friend 8", "", new Message(true, "Fists", "10:20AM")));
        items.add(new Friend(4, "Friend 4", "", new Message(true, "Fists 1", "10:00AM")));
        items.add(new Friend(8, "Friend 8", "", new Message(true, "Fists", "10:20AM")));
        items.add(new Friend(4, "Friend 4", "", new Message(true, "Fists 1", "10:00AM")));
        items.add(new Friend(8, "Friend 8", "", new Message(true, "Fists", "10:20AM")));
        items.add(new Friend(4, "Friend 4", "", new Message(true, "Fists 1", "10:00AM")));
        items.add(new Friend(8, "Friend 8", "", new Message(true, "Fists", "10:20AM")));
        items.add(new Friend(4, "Friend 4", "", new Message(true, "Fists 1", "10:00AM")));
        items.add(new Friend(8, "Friend 8", "", new Message(true, "Fists", "10:20AM")));
        items.add(new Friend(4, "Friend 4", "", new Message(true, "Fists 1", "10:00AM")));
        items.add(new Friend(8, "Friend 8", "", new Message(true, "Fists", "10:20AM")));
        items.add(new Friend(4, "Friend 4", "", new Message(true, "Fists 1", "10:00AM")));
        items.add(new Friend(8, "Friend 8", "", new Message(true, "Fists", "10:20AM")));
        items.add(new Friend(4, "Friend 4", "", new Message(true, "Fists 1", "10:00AM")));
        items.add(new Friend(8, "Friend 8", "", new Message(true, "Fists", "10:20AM")));
        items.add(new Friend(4, "Friend 4", "", new Message(true, "Fists 1", "10:00AM")));
//        items.add(new Friend(2, "Friend 3", "", new Message(true, "Fists 2", "10:30AM")));
//        items.add(new Friend("Item 2","Second Item on the list", "T3", ""));
//        items.add(new Friend("Item 3","Third Item on the list", "9H", ""));
 
        return items;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerHome);
        menu.findItem(R.id.action_add).setVisible(drawerOpen ? true : false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
        case R.id.action_add:
        	//Add new friend
        	
            // create intent to perform web search for this planet
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
            // catch event that there's no activity to handle intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
            }
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
    	fragmentChat = new PlanetFragment();
    	fragmentChat.setActivity(this);
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ID_FRIEND, ((Friend)items.get(position)).id);
        fragmentChat.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.screen_chat, fragmentChat).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle("Test Chat");
        mDrawerLayout.closeDrawer(mDrawerHome);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
    public static class PlanetFragment extends Fragment implements OnClickListener, TextWatcher{
        public static final String ID_FRIEND = "ID_FRIEND";

        private ListView listMessage;
        private ListMessageAdapter adapter;
        private EditText edtMessage;
        private Button btnSend;
        private HomeActivity activity;
        public int idFriend = 0;
        
        public PlanetFragment() {
        }
        
        public void setActivity(HomeActivity activity){
        	this.activity = activity;
        }
        
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
            
            btnSend = (Button) rootView.findViewById(R.id.btnSend);
            btnSend.setOnClickListener(this);
            
            edtMessage = (EditText) rootView.findViewById(R.id.edtMessage);
            edtMessage.addTextChangedListener(this);
            
            listMessage = (ListView) rootView.findViewById(R.id.list_message);

    		adapter = new ListMessageAdapter(getActivity(), R.layout.message_item);

    		listMessage.setAdapter(adapter);
    		
    		listMessage.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//					Message m = (Message)listMessage.getItemAtPosition(position);
//					Toast.makeText(getActivity(), m.message, Toast.LENGTH_LONG).show();//showToast(m.message);
				}
			
    		});
    		
    		
    		idFriend = getArguments().getInt(ID_FRIEND);
//            String planet = getResources().getStringArray(R.array.planets_array)[i];
//
//            int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
//                            "drawable", getActivity().getPackageName());
//            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
//            getActivity().setTitle(planet);
            return rootView;
        }

        @Override
		public void onClick(View v) {
        	if(v.equals(btnSend)){
        		String msg = edtMessage.getText().toString();
        		edtMessage.setText("");
        		
        		boolean ok = activity.mConnectServer.sendMessage("{\"action\": \"sendMessage\", \"toUserId\": \"" + idFriend + "\",\"message\": \"" + msg + "\"}");
        		if(ok){
        			adapter.add(new Message(false, msg, "10:10 PM"));
        		}else{
        			adapter.add(new Message(false, "Send message error", "10:10 PM"));
        		}
        	}
		}
        
        public void addNewMessage(Message msg) {
        	adapter.add(msg);
        }
        
        @Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			if(edtMessage.getText().toString().length() > 0){
				btnSend.setEnabled(true);
			}else{
				btnSend.setEnabled(false);
			}
		}
        
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			
		}
    }

    public void showToast(final String toast){
    	this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), 
		    			toast, Toast.LENGTH_LONG).show();
				
			}
		});
    	
    }
    
    public void logOut(){
    	SharedPreferences.Editor editor=pref.edit();
		editor.putString("id", "null");
		editor.commit();
		
		//Xoa database di
		Intent chatIntent = new Intent(this, LoginActivity.class);
    	startActivity(chatIntent);
    	finish();
    }
}
