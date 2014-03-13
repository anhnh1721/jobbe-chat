package fu.se629.jobbe_chat.database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class JobbeChatDatabase extends SQLiteOpenHelper{
	
	private static String DB_PATH="data/data/fu.se629.jobbe_chat/databases/";
	private static String DB_NAME="jobbe_chat.sqlite";
	private static String DB_NAME_OUT="jobbe_chat.sqlite";
	private static SQLiteDatabase myDatabase;
	Context myContext;

	public JobbeChatDatabase(Context context) {
		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

	public void createDataBase() throws IOException{
		boolean dbExist = checkDataBase();
		if(!dbExist){
			this.getReadableDatabase();
			try{
				copyDataBase();
			}
			catch (IOException e){}
		}
	}
	
	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB= SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
		}
		catch (SQLiteException e){}
		
		if(checkDB != null) {
			checkDB.close();
		}
		return checkDB != null ? true: false;
	}

	public void copyDataBase() throws IOException {
		InputStream myInput = myContext.getAssets().open(DB_NAME);
		String outFileName = DB_PATH + DB_NAME_OUT;
		OutputStream myOutput = new FileOutputStream(outFileName);
		byte[] buffer= new byte[1024];
		int length;
		while ((length = myInput.read(buffer))>0)
		{
			myOutput.write(buffer,0,length);
		}
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}
	
	public void openDatabase() throws SQLException {
		String myPath = DB_PATH + DB_NAME;
		myDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
	}
	
	public synchronized void close(){
		if(myDatabase != null)
			myDatabase.close();
		super.close();
	}
	
	//=================================================================================

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//	//Add user
//	public void addUser(String username){
//		SQLiteDatabase db = this.getWritableDatabase();
//		
//		ContentValues cv = new ContentValues();
//        cv.put("username", username);
//        db.insert("user", null, cv);
//        
//        cv = new ContentValues();
//        cv.put("username", username);
//        db.insert("achievement", null, cv);
//        
//        cv = new ContentValues();
//        cv.put("user", username);
//        cv.put("coin", EncodeFF.encodeInteger(username+"coin", 0));
//        cv.put("grenade", EncodeFF.encodeInteger(username+"itemGrenade", 0));
//        cv.put("shovel", EncodeFF.encodeInteger(username+"itemShovel", 0));
//        cv.put("helmet", EncodeFF.encodeInteger(username+"itemHelmet", 0));
//        cv.put("clock", EncodeFF.encodeInteger(username+"itemClock", 0));
//        db.insert("useritem", null, cv);
//	}
//	
////	public void loadOneUser(String username){
////		SQLiteDatabase db = this.getReadableDatabase();
////		
////		ContentValues cv = new ContentValues();
////        cv.put("username", username);
////        db.insert("user", null, cv);
////        
////        cv.put("username", username);
////        db.insert("achievement", null, cv);
////	}
////	
//	//Load User
//	public void loadUser(){
//	   	SQLiteDatabase db = this.getReadableDatabase();
//	   	 
//	   	Cursor cur = db.rawQuery("SELECT * FROM user", null);
//	    int x = cur.getCount();
//	    
//	    if(x>0){
//	    	if(cur.moveToFirst()){
//	    		while(!cur.isAfterLast()){
//	    			String username = cur.getString(cur.getColumnIndex("username"));
//	    			String idFacebook = cur.getString(cur.getColumnIndex("idfacebook"));
//	    			int mapmax_lv1 = cur.getInt(cur.getColumnIndex("mapmax_lv1"));
//	    			int mapmax_lv2 = cur.getInt(cur.getColumnIndex("mapmax_lv2"));
//	    			int mapmax_lv3 = cur.getInt(cur.getColumnIndex("mapmax_lv3"));
//	    			int autoSave = cur.getInt(cur.getColumnIndex("autosave"));
//	    			
//	    			int now = cur.getInt(cur.getColumnIndex("now"));
//	    			
//	    			if(!Setting.USER.containsKey(username.toUpperCase())){
//	    				Setting.USER.put(username.toUpperCase(), new User(username, idFacebook, mapmax_lv1, mapmax_lv2, mapmax_lv3, autoSave));
//	    			}
//	    			
//	    			if(now == 1){
//	    				Setting.USER_NOW = username;
//	    			}
//	    			cur.moveToNext();
//	    		}
//	    	}
//	    	cur.close();
//	    }
//	}
//		
//	//Save User
//	public void saveUser(){
//		SQLiteDatabase db = this.getWritableDatabase();
//	   	
//		ContentValues args = new ContentValues();
//	    
//		ArrayList<User> arr = new ArrayList<User>( Setting.USER.values() );
//		
//		for(int i = 0; i < arr.size(); i++){
//			args.put("mapmax_lv1", arr.get(i).getMapmax_lv1());
//			args.put("mapmax_lv2", arr.get(i).getMapmax_lv2());
//			args.put("mapmax_lv3", arr.get(i).getMapmax_lv3());
//			args.put("autosave", arr.get(i).getAutoSave());
//			args.put("idfacebook", arr.get(i).getIdFacebook());
//			args.put("now", Setting.USER_NOW.equals(arr.get(i).getUsername()) ? 1 : 0);
//		    db.update("user", args, "username = '"+arr.get(i).getUsername()+"'", null);
//		}
//    }
//		
//	public void saveUserOne(String username){
//		SQLiteDatabase db = this.getWritableDatabase();
//	   	
//		ContentValues args = new ContentValues();
//	    
//		args.put("mapmax_lv1", Setting.USER.get(username.toUpperCase()).getMapmax_lv1());
//		args.put("mapmax_lv2", Setting.USER.get(username.toUpperCase()).getMapmax_lv2());
//		args.put("mapmax_lv3", Setting.USER.get(username.toUpperCase()).getMapmax_lv3());
//		args.put("autosave", Setting.USER.get(username.toUpperCase()).getAutoSave());
//		args.put("idfacebook", Setting.USER.get(username.toUpperCase()).getIdFacebook());
//		args.put("now", Setting.USER_NOW.equals(username) ? 1 : 0);
//	    db.update("user", args, "username = '"+username+"'", null);
//    }
//	
//	public void loadAchievement(){
//		SQLiteDatabase db = this.getReadableDatabase();
//	   	
//		Cursor cur = db.rawQuery("SELECT * FROM achievement", null);
//	    int x = cur.getCount();
//	    
//	    if(x>0){
//	    	if(cur.moveToFirst()){
//	    		while(!cur.isAfterLast()){
//	    			String username = cur.getString(cur.getColumnIndex("username"));
//	    			
//	    			int tank_lv1 = cur.getInt(cur.getColumnIndex("tankt1"));
//	    			int tank_lv2 = cur.getInt(cur.getColumnIndex("tankt2"));
//	    			int tank_lv3 = cur.getInt(cur.getColumnIndex("tankt3"));
//	    			int tank_lv4 = cur.getInt(cur.getColumnIndex("tankt4"));
//	    			int tank_lv5 = cur.getInt(cur.getColumnIndex("tankt5"));
//	    			
//	    			int score = cur.getInt(cur.getColumnIndex("score"));
//	    			int score_lv1 = cur.getInt(cur.getColumnIndex("scorelv1"));
//	    			int score_lv2 = cur.getInt(cur.getColumnIndex("scorelv2"));
//	    			int score_lv3 = cur.getInt(cur.getColumnIndex("scorelv3"));
//	    			
//	    			int win = cur.getInt(cur.getColumnIndex("win"));
//	    			int gameover = cur.getInt(cur.getColumnIndex("gameover"));
//	    			
//	    			int level_game_1 = cur.getInt(cur.getColumnIndex("level1"));
//	    			int level_game_2 = cur.getInt(cur.getColumnIndex("level2"));
//	    			int level_game_3 = cur.getInt(cur.getColumnIndex("level3"));
//	    			
//	    			if(Setting.USER.containsKey(username.toUpperCase())){
//	    				Setting.USER.get(username.toUpperCase()).setAchie(new Achievement(tank_lv1, tank_lv2, tank_lv3, tank_lv4, tank_lv5, 
//	    						score, score_lv1, score_lv2, score_lv3, win, gameover, level_game_1, level_game_2, level_game_3));
//	    			}
//	    			
//	    			cur.moveToNext();
//	    		}
//	    	}
//	    	cur.close();
//	    }
//	}
//	
//	public void saveAchievement(){
//		SQLiteDatabase db = this.getWritableDatabase();
//	   	
//		ContentValues args = new ContentValues();
//	    
//		ArrayList<User> arr = new ArrayList<User>( Setting.USER.values() );
//		
//		for(int i = 0; i < arr.size(); i++){
//			Achievement achi = arr.get(i).getAchie();
//			
//			args.put("tankt1", achi.getTank_lv1());
//			args.put("tankt2", achi.getTank_lv2());
//			args.put("tankt3", achi.getTank_lv3());
//			args.put("tankt4", achi.getTank_lv4());
//			args.put("tankt5", achi.getTank_lv5());
//			args.put("score", achi.getScore());
//			args.put("scorelv1", achi.getScoreLv1());
//			args.put("scorelv2", achi.getScoreLv2());
//			args.put("scorelv3", achi.getScoreLv3());
//			args.put("win", achi.getWin());
//			args.put("gameover", achi.getGameover());
//			args.put("level1", achi.getLevel_game_1());
//			args.put("level2", achi.getLevel_game_2());
//			args.put("level3", achi.getLevel_game_3());
//		    
//			db.update("achievement", args, "username = '"+arr.get(i).getUsername()+"'", null);
//		}
//	}
//	
//	public void saveAchievementOne(String user){
//		SQLiteDatabase db = this.getWritableDatabase();
//	   	
//		ContentValues args = new ContentValues();
//	    
//		Achievement achi = Setting.USER.get(user.toUpperCase()).getAchie();
//		
//		args.put("tankt1", achi.getTank_lv1());
//		args.put("tankt2", achi.getTank_lv2());
//		args.put("tankt3", achi.getTank_lv3());
//		args.put("tankt4", achi.getTank_lv4());
//		args.put("tankt5", achi.getTank_lv5());
//		args.put("score", achi.getScore());
//		args.put("scorelv1", achi.getScoreLv1());
//		args.put("scorelv2", achi.getScoreLv2());
//		args.put("scorelv3", achi.getScoreLv3());
//		args.put("win", achi.getWin());
//		args.put("gameover", achi.getGameover());
//		args.put("level1", achi.getLevel_game_1());
//		args.put("level2", achi.getLevel_game_2());
//		args.put("level3", achi.getLevel_game_3());
//	    
//		db.update("achievement", args, "username = '"+user+"'", null);
//	}
//	
//	public void loadStgResult(){
//		SQLiteDatabase db = this.getReadableDatabase();
//	   	
//		ArrayList<User> arr = new ArrayList<User>( Setting.USER.values() );
//		
//		for(int i = 0; i < arr.size(); i++){
//			Cursor cur = db.rawQuery("SELECT * FROM stageresult WHERE username = '"+arr.get(i).getUsername()+"'", null);
//		    int x = cur.getCount();
//		    
//		    if(x>0){
//		    	if(cur.moveToFirst()){
//		    		while(!cur.isAfterLast()){
//		    			String map = cur.getString(cur.getColumnIndex("map"));
//		    			
//		    			int time = cur.getInt(cur.getColumnIndex("time"));
//		    			int star = cur.getInt(cur.getColumnIndex("star"));
//		    			int level = cur.getInt(cur.getColumnIndex("level"));
//		    			
//	    				Setting.USER.get(arr.get(i).getUsername().toUpperCase()).addStgResul(new StageResult(map, time, star), level);
//		    			
//		    			cur.moveToNext();
//		    		}
//		    	}
//		    	cur.close();
//		    }
//			
//		}
//	}
//	
//	public void saveStgResult(){
//		SQLiteDatabase db = this.getWritableDatabase();
//	   	
//		ContentValues args = new ContentValues();
//	    
//		ArrayList<User> arr = new ArrayList<User>( Setting.USER.values() );
//		
//		for(int i = 0; i < arr.size(); i++){
//			
//			ArrayList<StageResult> arrStg_easy = new ArrayList<StageResult>( arr.get(i).listStgResult_easy.values() );
//			ArrayList<StageResult> arrStg_normal = new ArrayList<StageResult>( arr.get(i).listStgResult_normal.values() );
//			ArrayList<StageResult> arrStg_hard = new ArrayList<StageResult>( arr.get(i).listStgResult_hard.values() );
//			
//			int index = 0;
//			
//			for(int j = 0; j < arrStg_easy.size(); j++){
//				args = new ContentValues();
//				args.put("map", arrStg_easy.get(j).getMap());
//				args.put("time", arrStg_easy.get(j).getTime());
//				args.put("star", arrStg_easy.get(j).getStar());
//				
//				index = db.update("stageresult", args, "username = '"+arr.get(i).getUsername()+"' AND level = '" + LEVEL_EASY + "' AND map = '" + arrStg_easy.get(j).getMap() + "'", null);
//				
//				if(index == 0){
//					args = new ContentValues();
//					args.put("username", arr.get(i).getUsername());
//					args.put("map", arrStg_easy.get(j).getMap());
//					args.put("time", arrStg_easy.get(j).getTime());
//					args.put("star", arrStg_easy.get(j).getStar());
//					args.put("level", LEVEL_EASY);
//					
//			        db.insert("stageresult", null, args);
//				}
//			}
//			
//			for(int j = 0; j < arrStg_normal.size(); j++){
//				args = new ContentValues();
//				args.put("map", arrStg_normal.get(j).getMap());
//				args.put("time", arrStg_easy.get(j).getTime());
//				args.put("star", arrStg_easy.get(j).getStar());
//				
//				index = db.update("stageresult", args, "username = '"+arr.get(i).getUsername()+"' AND level = '" + LEVEL_NORMAL + "' AND map = '" + arrStg_normal.get(j).getMap() + "'", null);
//				
//				if(index == 0){
//					args = new ContentValues();
//					args.put("username", arr.get(i).getUsername());
//					args.put("map", arrStg_normal.get(j).getMap());
//					args.put("time", arrStg_normal.get(j).getTime());
//					args.put("star", arrStg_normal.get(j).getStar());
//					args.put("level", LEVEL_NORMAL);
//					
//			        db.insert("stageresult", null, args);
//				}
//				
//			}
//
//			for(int j = 0; j < arrStg_hard.size(); j++){
//				args = new ContentValues();
//				args.put("map", arrStg_hard.get(j).getMap());
//				args.put("time", arrStg_easy.get(j).getTime());
//				args.put("star", arrStg_easy.get(j).getStar());
//				
//				index = db.update("stageresult", args, "username = '"+arr.get(i).getUsername()+"' AND level = '" + LEVEL_HARD + "' AND map = '" + arrStg_hard.get(j).getMap() + "'", null);
//				
//				if(index == 0){
//					args = new ContentValues();
//					args.put("username", arr.get(i).getUsername());
//					args.put("map", arrStg_hard.get(j).getMap());
//					args.put("time", arrStg_hard.get(j).getTime());
//					args.put("star", arrStg_hard.get(j).getStar());
//					args.put("level", LEVEL_HARD);
//					
//			        db.insert("stageresult", null, args);
//				}
//			}
//		}
//	}
//	
//	public void saveStgResultOne(String user){
//		SQLiteDatabase db = this.getWritableDatabase();
//	   	
//		ContentValues args = new ContentValues();
//	    
//		ArrayList<StageResult> arrStg_easy = new ArrayList<StageResult>( Setting.USER.get(user.toUpperCase()).listStgResult_easy.values() );
//		ArrayList<StageResult> arrStg_normal = new ArrayList<StageResult>( Setting.USER.get(user.toUpperCase()).listStgResult_normal.values() );
//		ArrayList<StageResult> arrStg_hard = new ArrayList<StageResult>( Setting.USER.get(user.toUpperCase()).listStgResult_hard.values() );
//		
//		int index = 0;
//		
//		for(int j = 0; j < arrStg_easy.size(); j++){
//			args = new ContentValues();
//			args.put("map", arrStg_easy.get(j).getMap());
//			args.put("time", arrStg_easy.get(j).getTime());
//			args.put("star", arrStg_easy.get(j).getStar());
//			
//			index = db.update("stageresult", args, "username = '"+user+"' AND level = '" + LEVEL_EASY + "' AND map = '" + arrStg_easy.get(j).getMap() + "'", null);
//			
//			if(index <= 0){
//				args = new ContentValues();
//				args.put("username", user);
//				args.put("map", arrStg_easy.get(j).getMap());
//				args.put("time", arrStg_easy.get(j).getTime());
//				args.put("star", arrStg_easy.get(j).getStar());
//				args.put("level", LEVEL_EASY);
//				
//		        db.insert("stageresult", null, args);
//			}
//		}
//		
//		for(int j = 0; j < arrStg_normal.size(); j++){
//			args = new ContentValues();
//			args.put("map", arrStg_normal.get(j).getMap());
//			args.put("time", arrStg_easy.get(j).getTime());
//			args.put("star", arrStg_easy.get(j).getStar());
//			
//			index = db.update("stageresult", args, "username = '"+user+"' AND level = '" + LEVEL_NORMAL + "' AND map = '" + arrStg_normal.get(j).getMap() + "'", null);
//			
//			if(index <= 0){
//				args = new ContentValues();
//				args.put("username", user);
//				args.put("map", arrStg_normal.get(j).getMap());
//				args.put("time", arrStg_normal.get(j).getTime());
//				args.put("star", arrStg_normal.get(j).getStar());
//				args.put("level", LEVEL_NORMAL);
//				
//		        db.insert("stageresult", null, args);
//			}
//			
//		}
//
//		for(int j = 0; j < arrStg_hard.size(); j++){
//			args = new ContentValues();
//			args.put("map", arrStg_hard.get(j).getMap());
//			args.put("time", arrStg_easy.get(j).getTime());
//			args.put("star", arrStg_easy.get(j).getStar());
//			
//			index = db.update("stageresult", args, "username = '"+user+"' AND level = '" + LEVEL_HARD + "' AND map = '" + arrStg_hard.get(j).getMap() + "'", null);
//			
//			if(index <= 0){
//				args = new ContentValues();
//				args.put("username", user);
//				args.put("map", arrStg_hard.get(j).getMap());
//				args.put("time", arrStg_hard.get(j).getTime());
//				args.put("star", arrStg_hard.get(j).getStar());
//				args.put("level", LEVEL_HARD);
//				
//		        db.insert("stageresult", null, args);
//			}
//		}
//	}
//	
//	public boolean deleteUser(String username) {
//		SQLiteDatabase db = this.getReadableDatabase();
//		
//		boolean flag = true;
//		
//		flag = db.delete("user", "username = '" + username + "'", null) > 0;
//		
//		if(flag){
//			flag = db.delete("achievement", "username = '" + username + "'", null) > 0;
//			db.delete("stageresult", "username = '" + username + "'", null);
//			db.delete("savegame", "username = '" + username + "'", null);
//			db.delete("useritem", "user = '" + username + "'", null);
//		}
//		
//		return flag;
//	}
//	
//	//Load Save Game
//	public void loadSaveGame(){
//		SQLiteDatabase db = this.getReadableDatabase();
//	   	
//		ArrayList<User> arr = new ArrayList<User>( Setting.USER.values() );
//		
//		for(int i = 0; i < arr.size(); i++){
//			
//			String username = arr.get(i).getUsername();
//			Cursor cur = db.rawQuery("SELECT * FROM savegame WHERE username = '"+username+"'", null);
//		    int x = cur.getCount();
//		    
//		    if(x>0){
//		    	if(cur.moveToFirst()){
//		    		while(!cur.isAfterLast()){
//		    			
//		    			int index = cur.getInt(cur.getColumnIndex("indexs"));
//		    			int ship = cur.getInt(cur.getColumnIndex("ship"));
//		    			
//		    			int stage = EncodeFF.decodeInteger("yen"+index, cur.getString(cur.getColumnIndex("stage")));
//		    			int score = EncodeFF.decodeInteger("yen"+index, cur.getString(cur.getColumnIndex("score")));
//		    			int life = EncodeFF.decodeInteger("yen"+index, cur.getString(cur.getColumnIndex("life")));
//		    			int levelTank = EncodeFF.decodeInteger("yen"+index, cur.getString(cur.getColumnIndex("leveltank")));
//		    			int levelGame = EncodeFF.decodeInteger("yen"+index, cur.getString(cur.getColumnIndex("levelgame")));
//		    			int loop = EncodeFF.decodeInteger("yen"+index, cur.getString(cur.getColumnIndex("loop")));
//
//		    			Setting.USER.get(username.toUpperCase()).listSaveGame.set(index, new GameSingleInfo(stage, score, levelTank, levelGame, life, ship == 1, loop, username));
//		    			
//		    			cur.moveToNext();
//		    		}
//		    	}
//		    	cur.close();
//		    }
//			
//		}
//	}
//	
//	public void saveSaveGame(){
//		SQLiteDatabase db = this.getWritableDatabase();
//		SQLiteDatabase db_read = this.getReadableDatabase();
//		
//		ContentValues args = new ContentValues();
//	    
//		ArrayList<User> arr = new ArrayList<User>( Setting.USER.values() );
//		
//		for(int i = 0; i < arr.size(); i++){
//			String username = arr.get(i).getUsername();
//			db_read.delete("savegame", "username = '" + username + "'", null);
//			
//			for(int j = 0; j < 3; j++){
//				args = new ContentValues();
//				if(arr.get(i).listSaveGame.get(j) != null){
//					String stage = EncodeFF.encodeInteger("yen"+j, arr.get(i).listSaveGame.get(j).stage);
//					String score = EncodeFF.encodeInteger("yen"+j, arr.get(i).listSaveGame.get(j).score);
//					String life = EncodeFF.encodeInteger("yen"+j, arr.get(i).listSaveGame.get(j).lifeTank);
//					String leveltank = EncodeFF.encodeInteger("yen"+j, arr.get(i).listSaveGame.get(j).levelTank);
//					String levelgame = EncodeFF.encodeInteger("yen"+j, arr.get(i).listSaveGame.get(j).levelGame);
//					String loop = EncodeFF.encodeInteger("yen"+j, arr.get(i).listSaveGame.get(j).loopGame);
////					String levelgame = EncodeFF.encodeInteger("yen", 0);
////					String loop = EncodeFF.encodeInteger("yen", 0);
//					
//					int ship = arr.get(i).listSaveGame.get(j).ship? 1 : 0;
//					
//					args.put("username", username);
//					args.put("stage", stage);
//					args.put("score", score);
//					args.put("life", life);
//					args.put("leveltank", leveltank);
//					args.put("levelgame", levelgame);
//					args.put("ship", ship);
//					args.put("loop", loop);
//					args.put("indexs", j);
//					
//					db.insert("savegame", null, args);
//				}
//			}
//		}
//	}
//	
//	//Load Setting
//	public void loadSetting(){
//	   	SQLiteDatabase db = this.getReadableDatabase();
//	   	 
//	   	Cursor cur = db.rawQuery("SELECT * FROM setting", null);
//	    int x = cur.getCount();
//	    
//	    if(x>0){
//	    	if(cur.moveToFirst()){
//	    		while(!cur.isAfterLast()){
//	    			String name = cur.getString(cur.getColumnIndex("name"));
//	    			float data = cur.getFloat(cur.getColumnIndex("data"));
//	    			
//	    			Setting.loadSettingFromDatabase(name, data);
//	    			
//	    			cur.moveToNext();
//	    		}
//	    	}
//	    	cur.close();
//	    }
//	    
//	    //Check visible to add
//	    
//	    Cursor curAlpha = db.rawQuery("SELECT * FROM setting WHERE name = 'alphacontrol'", null);
//	    int xAl = curAlpha.getCount();
//	    if(xAl <= 0){
//	    	ContentValues args = new ContentValues();
//	    	args.put("name", "alphacontrol");
//			args.put("data", 0.5f);
//			db.insert("setting", null, args);
//	    }
//	    
//	    Cursor curVote = db.rawQuery("SELECT * FROM setting WHERE name = 'msgsv'", null);
//	    int xSV = curVote.getCount();
//	    if(xSV <= 0){
//	    	ContentValues args = new ContentValues();
//	    	args.put("name", "msgsv");
//			args.put("data", 0);
//			db.insert("setting", null, args);
//	    }
//	    
//	    Cursor curSnow = db.rawQuery("SELECT * FROM setting WHERE name = 'snow'", null);
//	    int xSnow = curSnow.getCount();
//	    if(xSnow <= 0){
//	    	ContentValues args = new ContentValues();
//	    	args.put("name", "snow");
//			args.put("data", Setting.SNOW_ENABLE);
//			db.insert("setting", null, args);
//	    }
//	    
//	    Cursor curLang = db.rawQuery("SELECT * FROM setting WHERE name = 'language'", null);
//	    int xLang = curLang.getCount();
//	    if(xLang <= 0){
//	    	ContentValues args = new ContentValues();
//	    	args.put("name", "language");
//			args.put("data", Setting.LANG_NULL);
//			db.insert("setting", null, args);
//	    }
//    }
//
//	//Save Setting
//	public void saveSetting(){
//		SQLiteDatabase db = this.getWritableDatabase();
//	   	
//		ContentValues args = new ContentValues();
//	    args.put("data", Setting.SOUND_ENABLE);
//	    db.update("setting", args, "name = 'soundall'", null);
//	    
//	    args = new ContentValues();
//	    args.put("data", Setting.SOUND_GAME_ENABLE);
//	    db.update("setting", args, "name = 'soundgame'", null);
//	    
//	    args = new ContentValues();
//	    args.put("data", Setting.SOUND_MOVE_ENABLE);
//	    db.update("setting", args, "name = 'soundmove'", null);
//	    
//	    args = new ContentValues();
//	    args.put("data", Setting.VIBRATE_ENABLE);
//	    db.update("setting", args, "name = 'vibrate'", null);
//	    
//	    args = new ContentValues();
//	    args.put("data", Setting.LEVEL_GAME);
//	    db.update("setting", args, "name = 'levelgame'", null);
//	    
//	    args = new ContentValues();
//	    args.put("data", Setting.SIZE_BTN_CONTROL);
//	    db.update("setting", args, "name = 'sizecontrol'", null);
//	    
//	    args = new ContentValues();
//	    args.put("data", Setting.POSI_BTN_CONTROL);
//	    db.update("setting", args, "name = 'posicontrol'", null);
//	    
//	    args = new ContentValues();
//	    args.put("data", Setting.STYLE_GAME);
//	    db.update("setting", args, "name = 'stylegame'", null);
//	    
//	    args = new ContentValues();
//	    args.put("data", Setting.ALPHA_BTN_CONTROL);
//	    db.update("setting", args, "name = 'alphacontrol'", null);
//	    
//	    args = new ContentValues();
//	    args.put("data", Setting.SNOW_ENABLE);
//	    db.update("setting", args, "name = 'snow'", null);
//    }
//	
//	public void saveSettingAds(int adsType){
//		SQLiteDatabase db = this.getWritableDatabase();
//	   	
//		ContentValues args = new ContentValues();
//	    args.put("data", adsType);
//	    db.update("setting", args, "name = 'adstype'", null);
//    }
//	
//	public void saveSettingVoted(){
//		SQLiteDatabase db = this.getWritableDatabase();
//	   	
//		ContentValues args = new ContentValues();
//	    args.put("data", 1);
//	    db.update("setting", args, "name = 'msgsv'", null);
//    }
//	
//	public void saveSettingShared(){
//		SQLiteDatabase db = this.getWritableDatabase();
//	   	
//		ContentValues args = new ContentValues();
//	    args.put("data", 2);
//	    db.update("setting", args, "name = 'msgsv'", null);
//    }
//	
//	public void saveSettingLanguage(){
//		SQLiteDatabase db = this.getWritableDatabase();
//	   	
//		ContentValues args = new ContentValues();
//	    args.put("data", Setting.LANG_NOW);
//	    db.update("setting", args, "name = 'language'", null);
//    }
//	
//	//Load Hiscore
//	public void loadHighScore(){
//	   	SQLiteDatabase db = this.getReadableDatabase();
//	   	 
//	   	Cursor cur = db.rawQuery("SELECT * FROM highscore", null);
//	    int x = cur.getCount();
//	    
//	    if(x>0){
//	    	if(cur.moveToFirst()){
//	    		while(!cur.isAfterLast()){
//	    			String user = cur.getString(cur.getColumnIndex("user"));
//	    			String score = cur.getString(cur.getColumnIndex("score"));
//	    			int rank = cur.getInt(cur.getColumnIndex("rank"));
//	    			int player = cur.getInt(cur.getColumnIndex("player"));
//	    			
//	    			Setting.loadHighScoreFromDatabase(user, score, rank, player);
//	    			
//	    			cur.moveToNext();
//	    		}
//	    	}
//	    	cur.close();
//	    }
//	}
//
//	//Save Highscore
//	public void saveHighscore(){
//		if(Setting.HIGH_SCORE == null || Setting.HIGH_SCORE.size() < 6) return;
//		
//		SQLiteDatabase db = this.getWritableDatabase();
//	   	
//		ContentValues args = new ContentValues();
//	    
//		for(int i = 0; i < 6; i++){
//			args = new ContentValues();
//			int player = 2;
//			int rank = i%3;
//
//			if(i < 3) player = 1;
//			
//			String user = Setting.HIGH_SCORE.get(i).getUsername();
//			String score = Setting.HIGH_SCORE.get(i).getScore();
//			
//			args.put("user", user);
//			args.put("score", score);
//		    db.update("highscore", args, " player = '" + player + "' AND rank = '" + rank +"'", null);
//		}
//    }
//	
//	//Load Map
//	public void loadMapOriginal(){
//		Setting.ORIGINAL_MAP = new ArrayList<Map>();
//		
//	   	SQLiteDatabase db = this.getReadableDatabase();
//	   	 
//	   	Cursor cur = db.rawQuery("SELECT * FROM map ORDER BY name ASC", null);
//	    int x = cur.getCount();
//	    
//	    if(x>0){
//	    	if(cur.moveToFirst()){
//	    		while(!cur.isAfterLast()){
//	    			String data = cur.getString(cur.getColumnIndex("data"));
//	    			
//	    			Setting.ORIGINAL_MAP.add(new Map(data));
//	    			
//	    			cur.moveToNext();
//	    		}
//	    	}
//	    	cur.close();
//	    }
//	}
//	
//	public void loadMyMap(){
//		Setting.MY_MAP = new ArrayList<Map>();
//		
//	   	SQLiteDatabase db = this.getReadableDatabase();
//	   	 
//	   	Cursor cur = db.rawQuery("SELECT * FROM mymap ORDER BY name ASC", null);
//	    int x = cur.getCount();
//	    
//	    if(x>0){
//	    	if(cur.moveToFirst()){
//	    		while(!cur.isAfterLast()){
//	    			String data = cur.getString(cur.getColumnIndex("data"));
//	    			String author = cur.getString(cur.getColumnIndex("author"));
//	    			
//	    			Setting.MY_MAP.add(new Map(data, author));
//	    			
//	    			cur.moveToNext();
//	    		}
//	    	}
//	    	cur.close();
//	    }
//	}
//	
//	public void saveMyMap(){
//		SQLiteDatabase db = this.getWritableDatabase();
//	   	
//		db.execSQL("delete from mymap");
//		
//		ContentValues args = new ContentValues();
//	    
//		for(int i = 0; i < Setting.MY_MAP.size(); i++){
//			args = new ContentValues();
//			Map map = Setting.MY_MAP.get(i);
//			
//			args.put("name", (i+1)+"");
//			args.put("data", map.getData());
//			args.put("author", map.getAuthor());
//			
//	        db.insert("mymap", null, args);
//		}
//	}
//	
//	//Load Campaign
//	public void loadCampaign(){
//		Setting.CAMPAIGN = new ArrayList<Campaign>();
//		
//	   	SQLiteDatabase db = this.getReadableDatabase();
//	   	 
//	   	Cursor cur = db.rawQuery("SELECT * FROM campaigninfo ORDER BY stage ASC", null);
//	    int x = cur.getCount();
//	    
//	    if(x>0){
//	    	if(cur.moveToFirst()){
//	    		while(!cur.isAfterLast()){
//	    			int useMap = cur.getInt(cur.getColumnIndex("map"));
//	    			String scoreMax = cur.getString(cur.getColumnIndex("score_max"));
//	    			String requirement = cur.getString(cur.getColumnIndex("requirement"));
//	    			
//	    			Setting.CAMPAIGN.add(new Campaign(new CPRequirement(requirement), useMap, scoreMax, (Setting.CAMPAIGN.size())));
//	    			
//	    			cur.moveToNext();
//	    		}
//	    	}
//	    	cur.close();
//	    }
//	}
//	
//	//Load - Save Campaign result
//	public void loadCampaignResult(){
//		SQLiteDatabase db = this.getReadableDatabase();
//	   	
//		ArrayList<User> arr = new ArrayList<User>( Setting.USER.values() );
//		
//		for(int i = 0; i < arr.size(); i++){
//			Cursor cur = db.rawQuery("SELECT * FROM campaignresult WHERE user = '"+arr.get(i).getUsername()+"' ORDER BY stage ASC", null);
//		    int x = cur.getCount();
//		    
//		    if(x>0){
//		    	if(cur.moveToFirst()){
//		    		while(!cur.isAfterLast()){
//		    			
//		    			String score = cur.getString(cur.getColumnIndex("score"));
//		    			int star = cur.getInt(cur.getColumnIndex("star"));
//		    			
//		    			if(score == null || score.length() < 2){
//		    				score = EncodeFF.encodeInteger(arr.get(i).getUsername(), 0);
//		    			}
//		    			
//	    				Setting.USER.get(arr.get(i).getUsername().toUpperCase()).listCPResult.add(new CPResult(score, star));
//		    			
//		    			cur.moveToNext();
//		    		}
//		    	}
//		    	cur.close();
//		    }
////		    for(int j = 0; j < 14; j++){
////				Setting.USER.get(arr.get(i).getUsername().toUpperCase()).listCPResult.add(new CPResult(EncodeFF.encodeInteger(arr.get(i).getUsername(), 30000), 3));
////			}
//		}
//	}
//	
//	public void saveCampaignResult(){
//		SQLiteDatabase db = this.getWritableDatabase();
//	   	
//		ContentValues args = new ContentValues();
//	    
//		ArrayList<User> arr = new ArrayList<User>( Setting.USER.values() );
//		
//		for(int i = 0; i < arr.size(); i++){
//			String username = arr.get(i).getUsername();
//			
//			ArrayList<CPResult> cpResults = arr.get(i).listCPResult;
//			
//			int index = 0;
//			
//			for(int j = 0; j < cpResults.size(); j++){
//				args = new ContentValues();
//				args.put("score", cpResults.get(j).getScore());
//				args.put("star", cpResults.get(j).getStar());
//				
//				index = db.update("campaignresult", args, "user = '"+username+"' AND stage = '" + (j+1) + "'", null);
//				
//				if(index == 0){
//					args = new ContentValues();
//					args.put("user", username);
//					args.put("stage", (j+1));
//					args.put("score", cpResults.get(j).getScore());
//					args.put("star", cpResults.get(j).getStar());
//					
//			        db.insert("campaignresult", null, args);
//				}
//			}
//		}
//	}
//	
//	public void saveCampaignResultOne(String user){
//		SQLiteDatabase db = this.getWritableDatabase();
//	   	
//		ContentValues args = new ContentValues();
//	    
//		ArrayList<CPResult> cpResults = Setting.USER.get(user.toUpperCase()).listCPResult;
//		
//		int index = 0;
//		
//		for(int j = 0; j < cpResults.size(); j++){
//			args = new ContentValues();
//			args.put("score", cpResults.get(j).getScore());
//			args.put("star", cpResults.get(j).getStar());
//			
//			index = db.update("campaignresult", args, "user = '"+user+"' AND stage = '" + (j+1) + "'", null);
//			
//			if(index == 0){
//				args = new ContentValues();
//				args.put("user", user);
//				args.put("stage", (j+1));
//				args.put("score", cpResults.get(j).getScore());
//				args.put("star", cpResults.get(j).getStar());
//				
//		        db.insert("campaignresult", null, args);
//			}
//		}
//	}
//	
//	//Load -save Campaign User Item
//	public void loadCPUserItem(){
//		SQLiteDatabase db = this.getReadableDatabase();
//	   	
//		ArrayList<User> arr = new ArrayList<User>( Setting.USER.values() );
//		
//		for(int i = 0; i < arr.size(); i++){
//			String username = arr.get(i).getUsername();
//			
//			Cursor cur = db.rawQuery("SELECT * FROM useritem WHERE user = '"+username+"'", null);
//		    int x = cur.getCount();
//		    
//		    if(x>0){
//		    	if(cur.moveToFirst()){
//		    		while(!cur.isAfterLast()){
//		    			
//		    			String coin = cur.getString(cur.getColumnIndex("coin"));
//		    			String life = cur.getString(cur.getColumnIndex("life"));
//		    			String tank = cur.getString(cur.getColumnIndex("tank"));
//		    			String grenage = cur.getString(cur.getColumnIndex("grenade"));
//		    			String shovel = cur.getString(cur.getColumnIndex("shovel"));
//		    			String helmet = cur.getString(cur.getColumnIndex("helmet"));
//		    			String clock = cur.getString(cur.getColumnIndex("clock"));
//		    			
//		    			if(coin == null || coin.length() < 2){
//		    				coin = EncodeFF.encodeInteger(username+"coin", 0);
//		    			}
//		    			if(life == null || life.length() < 2){
//		    				life = EncodeFF.encodeInteger(username+"itemLife", 0);
//		    			}
//		    			if(tank == null || tank.length() < 2){
//		    				tank = EncodeFF.encodeInteger(username+"itemTank", 0);
//		    			}
//		    			if(grenage == null || grenage.length() < 2){
//		    				grenage = EncodeFF.encodeInteger(username+"itemGrenade", 0);
//		    			}
//		    			if(shovel == null || shovel.length() < 2){
//		    				shovel = EncodeFF.encodeInteger(username+"itemShovel", 0);
//		    			}
//		    			if(helmet == null || helmet.length() < 2){
//		    				helmet = EncodeFF.encodeInteger(username+"itemHelmet", 0);
//		    			}
//		    			if(clock == null || clock.length() < 2){
//		    				clock = EncodeFF.encodeInteger(username+"itemClock", 0);
//		    			}
//		    			
//	    				Setting.USER.get(username.toUpperCase()).mCPUserItem = new CPUserItem(coin, life, tank, grenage, shovel, helmet, clock);
//		    			
//		    			cur.moveToNext();
//		    		}
//		    	}
//		    	cur.close();
//		    }
//			
//		}
//	}
//	
//	public void saveCPUserItem(){
//		SQLiteDatabase db = this.getWritableDatabase();
//	   	
//		ContentValues args = new ContentValues();
//	    
//		ArrayList<User> arr = new ArrayList<User>( Setting.USER.values() );
//		
//		for(int i = 0; i < arr.size(); i++){
//			args = new ContentValues();
//			CPUserItem cpUserItem = arr.get(i).mCPUserItem;
//			
//			args.put("coin", cpUserItem.getCoin());
//			args.put("life", cpUserItem.getItemLife());
//			args.put("tank", cpUserItem.getItemTank());
//			args.put("grenade", cpUserItem.getItemGrenade());
//			args.put("shovel", cpUserItem.getItemShovel());
//			args.put("helmet", cpUserItem.getItemHelmet());
//			args.put("clock", cpUserItem.getItemClock());
//		    
//			db.update("useritem", args, "user = '"+arr.get(i).getUsername()+"'", null);
//		}
//	}
//	
//	public void saveCPUserItemOne(String user){
//		SQLiteDatabase db = this.getWritableDatabase();
//	   	
//		ContentValues args = new ContentValues();
//
//		CPUserItem cpUserItem = Setting.USER.get(user.toUpperCase()).mCPUserItem;
//		
//		args.put("coin", cpUserItem.getCoin());
//		args.put("life", cpUserItem.getItemLife());
//		args.put("tank", cpUserItem.getItemTank());
//		args.put("grenade", cpUserItem.getItemGrenade());
//		args.put("shovel", cpUserItem.getItemShovel());
//		args.put("helmet", cpUserItem.getItemHelmet());
//		args.put("clock", cpUserItem.getItemClock());
//	    
//		db.update("useritem", args, "user = '"+user+"'", null);
//	}
//	
//	
	
	
	
}
