package waley.database.dutchdate.dao;

import java.util.ArrayList;
import java.util.List;

import waley.database.dutchdate.model.Action;
import waley.database.dutchdate.model.Consume;
import waley.database.dutchdate.model.Detail;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DetailTableDAO {	
	public static void add(SQLiteDatabase db,Detail detail){
		db.execSQL("insert into detail(actionID,personID,consume) values (?,?,?)", 
				new Object[]{detail.getActionID(),detail.getPersonID(),detail.getConsume()});		
	}
	
	public static void update(SQLiteDatabase db,Detail detail){
		db.execSQL("update detail set actionID = ?, personID = ?, consume = ?  where id = ?", 
				new Object[]{detail.getActionID(),detail.getPersonID(),detail.getConsume(),detail.getId()});
	}
	
	public static void delete(SQLiteDatabase db,int id){
		db.execSQL("delete from detail where id=?", new String[]{Integer.toString(id)});		
	}
	
	public static void deleteAll(SQLiteDatabase db){
		db.execSQL("delete from detail");		
	}
	
	public static Detail find(SQLiteDatabase db,int id){
		Cursor cursor = null;
		Detail detail = null;
		try {
			cursor = db.rawQuery("select actionID,personID,consume from detail where id = ?",new String[]{Integer.toString(id)});
			if (cursor.moveToNext())
			{
				detail = new Detail();
				detail.setActionID(cursor.getInt(cursor.getColumnIndex("actionID")));
				detail.setPersonID(cursor.getInt(cursor.getColumnIndex("personID")));
				detail.setConsume(cursor.getInt(cursor.getColumnIndex("consume")));
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}		
		return detail;
	}
		
	public static List<Detail> getScrollData(SQLiteDatabase db,int personID){
		List<Detail> details = new ArrayList<Detail>();
		Cursor cursor = null;

		try {
			cursor = db.rawQuery("select * from detail where personID=?", new String[]{String.valueOf(personID)});
			while (cursor.moveToNext()){
				Detail detail = new Detail();
				detail.setId(cursor.getInt(cursor.getColumnIndex("id")));
				detail.setActionID(cursor.getInt(cursor.getColumnIndex("actionID")));
				detail.setPersonID(cursor.getInt(cursor.getColumnIndex("personID")));
				detail.setConsume(cursor.getInt(cursor.getColumnIndex("consume")));
				details.add(detail);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (cursor != null)
				cursor.close();
		}
		return details;
	}
	
	public static List<Consume> getConsumeInfos(SQLiteDatabase db,String name){
		List<Consume> consumes = new ArrayList<Consume>();
		Cursor cursor = null;
		
		try {
			cursor = db.rawQuery("select action.name as c_name,action.date as c_date,action.place as c_place,action.payer as c_payer," +
					"action.consume as c_consume,action.headcount as c_headcount,detail.consume as myconsume " +
					"from person,detail,action " +
					"where detail.actionID=action.actionID and detail.personID=person.personID and person.name=?"
					, new String[]{name});

			while (cursor.moveToNext()){
				System.out.println("find an item");
				System.out.println(cursor.getString(cursor.getColumnIndex("c_name"))
						+"|"+ cursor.getString(cursor.getColumnIndex("c_date"))
						+"|"+ cursor.getString(cursor.getColumnIndex("c_place"))
						+"|"+ cursor.getString(cursor.getColumnIndex("c_payer"))
						+"|"+ cursor.getInt(cursor.getColumnIndex("c_consume"))
						+"|"+ cursor.getInt(cursor.getColumnIndex("c_headcount"))
						+"|"+ cursor.getInt(cursor.getColumnIndex("myconsume"))
						);
				Consume consume = new Consume();
				consume.setActionName(cursor.getString(cursor.getColumnIndex("c_name")));
				consume.setActionDate(cursor.getString(cursor.getColumnIndex("c_date")));
				consume.setActionPlace(cursor.getString(cursor.getColumnIndex("c_place")));
				consume.setActionPayer(cursor.getString(cursor.getColumnIndex("c_payer")));
				consume.setActionConsume(cursor.getInt(cursor.getColumnIndex("c_consume")));
				consume.setActionHeadcount(cursor.getInt(cursor.getColumnIndex("c_headcount")));
				consume.setMyConsume(cursor.getInt(cursor.getColumnIndex("myconsume")));
				consumes.add(consume);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (cursor != null)
				cursor.close();
		}
		return consumes;	
	}
	
	public static long getCount(SQLiteDatabase db){
		Cursor cursor = null;
		long count = 0;
		
		try {
			cursor = db.rawQuery("select count(id) from detail", null);
			if (cursor.moveToNext())
			{
				count = cursor.getLong(0);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (cursor != null)
				cursor.close();
		}
		return count;
	}
}
