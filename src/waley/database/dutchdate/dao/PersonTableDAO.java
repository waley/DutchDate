package waley.database.dutchdate.dao;

import java.util.ArrayList;
import java.util.List;

import waley.database.dutchdate.model.Person;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PersonTableDAO {
	
	public static void add(SQLiteDatabase db,Person person){
		db.execSQL("insert into person(name,phone,payment,consume,balance) values (?,?,?,?,?)", 
				new Object[]{person.getName(),person.getPhone(),
				person.getPayment(),person.getConsume(),person.getBalance()});		
	}
	
	public static void update(SQLiteDatabase db,Person person){
		db.execSQL("update person set name = ?, phone = ?, payment = ?, consume = ?, balance = ? where personID = ?", 
				new Object[]{person.getName(),person.getPhone(),person.getPayment(),person.getConsume()
				,person.getBalance(),person.getPersonID()});
	}
	
	public static void delete(SQLiteDatabase db,int id){
		db.execSQL("delete from person where personID=?", new String[]{Integer.toString(id)});		
	}
	
	public static void deleteAll(SQLiteDatabase db){
		db.execSQL("delete from person");		
	}

	public static boolean subPayment(SQLiteDatabase db,String name,int money){
		Person person = null;
		
		person = find(db,name);
		
		if (person != null){
			person.subPayment(money);			
			update(db,person);
		}else {
			return false;
		}
		return true;
	}
	
	public static boolean subConsume(SQLiteDatabase db,String name,int money){
		Person person = null;
		
		person = find(db,name);
		
		if (person != null){
			person.subConsume(money);
			update(db,person);
		}else {
			return false;
		}
		return true;
	}
	
	public static boolean addPayment(SQLiteDatabase db,String name,int payment){
		Person person = null;
		
		person = find(db,name);
		
		if (person != null){
			person.addPayment(payment);			
			update(db,person);
		}else {
			return false;
		}
		return true;
	}
	
	public static boolean addConsume(SQLiteDatabase db,String name,int consume){
		Person person = null;
		
		person = find(db,name);
		
		if (person != null){
			person.addConsume(consume);
			update(db,person);
		}else {
			return false;
		}
		return true;
	}
	
	public static Person find(SQLiteDatabase db,String name){
		Person person = null;
		Cursor cursor = null;
		try {
			cursor = db.rawQuery("select personID,phone,payment,consume,balance from person where name = ?",new String[]{name});
			if (cursor.moveToNext())
			{		
				person = new Person();
				person.setPersonID(cursor.getInt(cursor.getColumnIndex("personID")));
				person.setName(name);
				person.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
				person.setPaymentConsume(cursor.getInt(cursor.getColumnIndex("payment")),cursor.getInt(cursor.getColumnIndex("consume")));
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (cursor != null)
				cursor.close();
		}
		return person;
	}
	
	public static List<Person> getScrollData(SQLiteDatabase db,int start, int count){
		List<Person> persons = new ArrayList<Person>();
		Cursor cursor = null;
		
		try {
			cursor = db.rawQuery("select * from person limit ?,?", new String[]{String.valueOf(start),String.valueOf(count)});
			while (cursor.moveToNext()){
				Person person = new Person();
				person.setPersonID(cursor.getInt(cursor.getColumnIndex("personID")));
				person.setName(cursor.getString(cursor.getColumnIndex("name")));
				person.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
				person.setPayment(cursor.getInt(cursor.getColumnIndex("payment")));
				person.setConsume(cursor.getInt(cursor.getColumnIndex("consume")));
				person.setBalance(cursor.getInt(cursor.getColumnIndex("balance")));
				persons.add(person);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (cursor != null)
				cursor.close();
		}
		return persons;
	}
	
	public static List<Person> getPersonInfosOrderByBalance(SQLiteDatabase db){
		List<Person> persons = new ArrayList<Person>();
		Cursor cursor = null;
		
		try {
			cursor = db.rawQuery("select * from person order by balance desc",null);
			while (cursor.moveToNext()){
				Person person = new Person();
				person.setPersonID(cursor.getInt(cursor.getColumnIndex("personID")));
				person.setName(cursor.getString(cursor.getColumnIndex("name")));
				person.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
				person.setPayment(cursor.getInt(cursor.getColumnIndex("payment")));
				person.setConsume(cursor.getInt(cursor.getColumnIndex("consume")));
				person.setBalance(cursor.getInt(cursor.getColumnIndex("balance")));
				persons.add(person);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (cursor != null)
			{
				cursor.close();
			}
		}
		return persons;	
	}
	
	public static List<String> getPersonNames(SQLiteDatabase db){
		List<String> personNames = new ArrayList<String>();
		Cursor cursor = null;
		
		try {
			cursor = db.rawQuery("select name from person group by name",null);
			while (cursor.moveToNext()){
				String name = new String();
				name = cursor.getString(cursor.getColumnIndex("name"));
				personNames.add(name);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (cursor != null)
				cursor.close();
		}
		return personNames;
	}
	
	
	public static long getCount(SQLiteDatabase db){
		Cursor cursor = null;
		long count = 0;
		try {
			cursor = db.rawQuery("select count(personID) from person", null);
			if (cursor.moveToNext())
			{
				count = cursor.getLong(0);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (cursor!=null)
				cursor.close();
		}
		return count;
	}
	
	public static int getPersonIDFromName(SQLiteDatabase db,String personName){
		int personId = -1;
		Cursor cursor = null;
		
		try {
			cursor = db.rawQuery("select personID from person where name=?", new String[]{personName});
			
			if (cursor.moveToNext())
			{
				personId = (int)cursor.getInt(cursor.getColumnIndex("personID"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (cursor != null)
				cursor.close();
		}
		return personId;		
	}
	
	
}
