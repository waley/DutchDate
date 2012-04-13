package waley.database.dutchdate.dao;

import java.util.ArrayList;
import java.util.List;

import waley.database.dutchdate.model.Action;
import android.R.integer;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ActionTableDAO {

	public static void add(SQLiteDatabase db,Action action){
		db.execSQL("insert into action(name,date,place,consume,headcount,payer) values (?,?,?,?,?,?)", 
				new Object[]{action.getName(),action.getDate(),action.getPlace(),
				action.getConsume(),action.getHeadcount(),action.getPayer()});		
	}
	
	public static void update(SQLiteDatabase db,Action action){
		db.execSQL("update action set name = ?, date = ?, place = ?, consume = ?, headcount = ?,payer = ?  where actionID = ?", 
				new Object[]{action.getName(),action.getDate(),action.getPlace(),
				action.getConsume(),action.getHeadcount(),action.getPayer()});
	}
	
	public static void delete(SQLiteDatabase db,int id){
		db.execSQL("delete from action where actionID=?", new String[]{Integer.toString(id)});		
	}
	
	public static void deleteAll(SQLiteDatabase db){
		db.execSQL("delete from action");		
	}
	
	public static Action find(SQLiteDatabase db,int id){
		Cursor cursor = null;
		Action action = null;
		try {
			cursor = db.rawQuery("select actionID,name,date,place,consume,headcount,payer from action where actionID = ?",new String[]{Integer.toString(id)});
			if (cursor.moveToNext())
			{
				action = new Action();
				
				action.setActionID(cursor.getInt(cursor.getColumnIndex("actionID")));
				action.setName(cursor.getString(cursor.getColumnIndex("name")));
				action.setDate(cursor.getString(cursor.getColumnIndex("date")));
				action.setPlace(cursor.getString(cursor.getColumnIndex("place")));
				action.setConsume(cursor.getInt(cursor.getColumnIndex("consume")));
				action.setHeadcount(cursor.getInt(cursor.getColumnIndex("headcount")));
				action.setPayer(cursor.getString(cursor.getColumnIndex("payer")));
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (cursor != null)
				cursor.close();
		}
		return action;
	}
		
	public static List<Action> getScrollData(SQLiteDatabase db,int start, int count){
		List<Action> actions = new ArrayList<Action>();
		Cursor cursor = null;
		
		try {
			cursor = db.rawQuery("select * from action limit ?,?", new String[]{String.valueOf(start),String.valueOf(count)});
			while (cursor.moveToNext()){
				Action action = new Action();
				action.setActionID(cursor.getInt(cursor.getColumnIndex("actionID")));
				action.setName(cursor.getString(cursor.getColumnIndex("name")));
				action.setDate(cursor.getString(cursor.getColumnIndex("date")));
				action.setPlace(cursor.getString(cursor.getColumnIndex("place")));
				action.setConsume(cursor.getInt(cursor.getColumnIndex("consume")));
				action.setHeadcount(cursor.getInt(cursor.getColumnIndex("headcount")));
				action.setPayer(cursor.getString(cursor.getColumnIndex("payer")));
				actions.add(action);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (cursor != null)
				cursor.close();
		}
		return actions;
	}
	
	public static List<Action> getMyPaymentData(SQLiteDatabase db,String name){
		List<Action> actions = new ArrayList<Action>();
		Cursor cursor = null;
		
		try {
			cursor = db.rawQuery("select * from action where payer=?", new String[]{name});
			while (cursor.moveToNext()){
				Action action = new Action();
				action.setActionID(cursor.getInt(cursor.getColumnIndex("actionID")));
				action.setName(cursor.getString(cursor.getColumnIndex("name")));
				action.setDate(cursor.getString(cursor.getColumnIndex("date")));
				action.setPlace(cursor.getString(cursor.getColumnIndex("place")));
				action.setConsume(cursor.getInt(cursor.getColumnIndex("consume")));
				action.setHeadcount(cursor.getInt(cursor.getColumnIndex("headcount")));
				action.setPayer(cursor.getString(cursor.getColumnIndex("payer")));
				actions.add(action);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (cursor != null)
				cursor.close();
		}
		return actions;		
	}
	
	public static List<String> getActionNames(SQLiteDatabase db){
		List<String> actionNames = new ArrayList<String>();
		Cursor cursor = null;
		
		try {
			cursor = db.rawQuery("select name from action group by name",null);
			while (cursor.moveToNext()){
				String name = new String();
				name = cursor.getString(cursor.getColumnIndex("name"));
				actionNames.add(name);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (cursor != null)
				cursor.close();
		}
		return actionNames;
	}
	
	public static List<String> getActionPlaces(SQLiteDatabase db){
		List<String> actionPlaces = new ArrayList<String>();
		Cursor cursor = null;
		
		try {
			cursor = db.rawQuery("select place from action group by place",null);
			while (cursor.moveToNext()){
				String name = new String();
				name = cursor.getString(cursor.getColumnIndex("place"));
				actionPlaces.add(name);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (cursor != null)
				cursor.close();
		}
		return actionPlaces;
	}
	
	public static long getCount(SQLiteDatabase db){
		Cursor cursor = null;
		long count=0;
		
		try {
			cursor = db.rawQuery("select count(actionID) from action", null);
			if (cursor.moveToNext())
			{
				count = cursor.getLong(0);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (cursor != null){
				cursor.close();
			}
		}
		return count;
	}
	
	public static int getActionIDFromName(SQLiteDatabase db,Action action){
		int actionId = -1;
		Cursor cursor = null;
		
		try {
			cursor = db.rawQuery("select actionID from action where name=? and date=? and place=? and consume=? and headcount=? and payer=?"
					, new String[]{action.getName(),action.getDate(),action.getPlace(),Integer.toString(action.getConsume()),Integer.toString(action.getHeadcount()),action.getPayer()});
			
			if (cursor.moveToNext())
			{
				actionId = (int)cursor.getInt(cursor.getColumnIndex("actionID"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (cursor != null)
				cursor.close();
		}
		return actionId;		
	}
	
}
