package waley.database.dutchdate.dao;

import java.util.ArrayList;
import java.util.List;
import waley.database.dutchdate.model.Payment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PaymentTableDAO {
	public static void add(SQLiteDatabase db,Payment payment){
		db.execSQL("insert into payment(date,payer,payee,money) values (?,?,?,?)", 
				new Object[]{payment.getDate(),payment.getPayer(),payment.getPayee(),payment.getMoney()});		
	}
	
	public static void update(SQLiteDatabase db,Payment payment){
		db.execSQL("update payment set date = ?, payer = ?, payee = ? money = ? where id = ?", 
				new Object[]{payment.getDate(),payment.getPayer(),payment.getPayee(),payment.getMoney(),payment.getId()});
	}
	
	public static void delete(SQLiteDatabase db,int id){
		db.execSQL("delete from payment where id=?", new String[]{Integer.toString(id)});		
	}
	
	public static void deleteAll(SQLiteDatabase db){
		db.execSQL("delete from payment");		
	}
	
	public static Payment find(SQLiteDatabase db,int id){
		Cursor cursor = null;
		Payment payment = null;
		try {
			cursor = db.rawQuery("select date,payer,payee,money from payment where id = ?",new String[]{Integer.toString(id)});
			if (cursor.moveToNext())
			{
				payment = new Payment();
				payment.setDate(cursor.getString(cursor.getColumnIndex("date")));
				payment.setPayer(cursor.getString(cursor.getColumnIndex("payer")));
				payment.setPayee(cursor.getString(cursor.getColumnIndex("payee")));
				payment.setMoney(cursor.getInt(cursor.getColumnIndex("money")));
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}		
		return payment;
	}
		
	public static List<Payment> getScrollData(SQLiteDatabase db,int start, int count){
		List<Payment> payments = new ArrayList<Payment>();
		Cursor cursor = null;

		try {
			cursor = db.rawQuery("select * from payment limit ?,?", new String[]{String.valueOf(start),String.valueOf(count)});
			while (cursor.moveToNext()){
				Payment payment = new Payment();
				payment.setDate(cursor.getString(cursor.getColumnIndex("date")));
				payment.setPayer(cursor.getString(cursor.getColumnIndex("payer")));
				payment.setPayee(cursor.getString(cursor.getColumnIndex("payee")));
				payment.setMoney(cursor.getInt(cursor.getColumnIndex("money")));
				payments.add(payment);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (cursor != null)
				cursor.close();
		}
		return payments;
	}
	
	public static long getCount(SQLiteDatabase db){
		Cursor cursor = null;
		long count = 0;
		
		try {
			cursor = db.rawQuery("select count(id) from payment", null);
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
