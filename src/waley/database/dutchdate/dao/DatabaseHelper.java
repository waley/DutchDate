package waley.database.dutchdate.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper{
	private static final int VERSION = 1;
	private static final String DBNAME = "dutchdate";
	
	public DatabaseHelper(Context context){
		
		super(context, DBNAME, null, VERSION);	
	}
	 @Override
	public void onCreate(SQLiteDatabase db) {
		 db.execSQL("create table person (personID integer primary key autoincrement,name varchar(20)," +
		 		"phone varchar(20),payment integer,consume integer,balance integer)");

		 db.execSQL("create table action (actionID integer primary key autoincrement,name varchar(20)," +
			 		"date varchar(20),place varchar(20),consume integer,headcount integer, payer varchar(20))");

		 db.execSQL("create table detail (id integer primary key autoincrement,actionID integer," +
				 "personID integer,consume integer)");
		 
		 db.execSQL("create table payment (id integer primary key autoincrement," +
		 		"date varchar(20),payer varchar(20),payee varchar(20),money integer)");
		 
		 System.out.println("create table payment (id integer primary key autoincrement," +
			 		"date varchar(20),payer varchar(20),payee varchar(20),money integer)");
	}
	 @Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		
	}
	 @Override
	public void onOpen(SQLiteDatabase db) {

		super.onOpen(db);
	}
	
}
