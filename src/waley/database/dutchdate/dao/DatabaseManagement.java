package waley.database.dutchdate.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import waley.database.dutchdate.model.Action;
import waley.database.dutchdate.model.Detail;
import waley.database.dutchdate.model.Payment;
import waley.database.dutchdate.model.Person;
import waley.database.dutchdate.model.Consume;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;
import android.widget.Toast;

public class DatabaseManagement {
	private DatabaseHelper helper;
	private SQLiteDatabase db;
	
	public DatabaseManagement(Context context){
		System.out.println("DatabaseManagement()");
		helper = new DatabaseHelper(context);
	}	

	public boolean AddAction(Action action){
		db = helper.getWritableDatabase();		
				
		//create a new event into action table
		ActionTableDAO.add(db,action);
		
		//update detail table
		for(int i=0; i<action.getHeadcount(); i++){
			Detail detail = new Detail();
			int actionID = ActionTableDAO.getActionIDFromName(db, action);
			int personID = PersonTableDAO.getPersonIDFromName(db, action.getAttendees(i));
			
			if ((actionID == -1)||(personID == -1))
			{
				ActionTableDAO.delete(db, (int)ActionTableDAO.getCount(db)-1);
				return false;
			}
			detail.setActionID(actionID);
			detail.setPersonID(personID);
			detail.setConsume(action.getConsume()/action.getHeadcount());
			DetailTableDAO.add(db,detail);
		}
				
		//update person table for consume
		for (int i=0; i<action.getHeadcount(); i++){
			Person person = PersonTableDAO.find(db,action.getAttendees(i));
			person.addConsume(action.getConsume()/action.getHeadcount());
			PersonTableDAO.update(db,person);
		}
		
		//update person table for payer
		Person person = PersonTableDAO.find(db,action.getPayer());
		person.addPayment(action.getConsume());
		PersonTableDAO.update(db,person);
		
		db.close();
		return true;
	}

	
	//
	public void AddPerson(Person person){
		db = helper.getWritableDatabase();		

		PersonTableDAO.add(db,person);
		
		db.close();
	}
	
	public void AddPayment(String fromPerson,String toPerson,int money){
		db = helper.getWritableDatabase();		
		Payment payment = new Payment();
		Time mTime = new Time();
		
		PersonTableDAO.addPayment(db, fromPerson, money);
		PersonTableDAO.subPayment(db, toPerson, money);
		//PersonTableDAO.subConsume(db,fromPerson, money);
		//PersonTableDAO.subPayment(db,toPerson, money);		
		payment.setDate(mTime.year+"-"+mTime.month+"-"+mTime.monthDay);
		payment.setPayer(fromPerson);
		payment.setPayee(toPerson);
		payment.setMoney(money);
		PaymentTableDAO.add(db, payment);
		
		db.close();
	}
	
	public List<Action> getActionInfos(){
		db = helper.getWritableDatabase();		
		
		List<Action> actionInfos = new ArrayList<Action>();
		actionInfos = ActionTableDAO.getScrollData(db,0, (int)ActionTableDAO.getCount(db));

		db.close();
		return actionInfos;
	}
	
	
	public List<Person> getPersonInfos(){
		db = helper.getWritableDatabase();		
		
		List<Person> personInfos = new ArrayList<Person>();
		personInfos = PersonTableDAO.getScrollData(db,0, (int)PersonTableDAO.getCount(db));
		
		db.close();
		return personInfos;
	}
	
	public List<Person> getSummaryInfos(){
		db = helper.getWritableDatabase();		
		
		List<Person> persons = new ArrayList<Person>();
		persons = PersonTableDAO.getPersonInfosOrderByBalance(db);
		
		db.close();
		return persons;
	}
	
	public List<Consume> getConsumeInfos(String name){
		db = helper.getWritableDatabase();		
		List<Consume> consumes = new ArrayList<Consume>();
		consumes = DetailTableDAO.getConsumeInfos(db,name);
		db.close();
		return consumes;
	}
	
	public List<String> getPersonNames(){
		List<String> names = new ArrayList<String>();
		
		db = helper.getWritableDatabase();		
		
		names = PersonTableDAO.getPersonNames(db);
		
		db.close();
		return names;
		
	}
	
	public List<String> getActionNames(){
		List<String> actions = new ArrayList<String>();
		db = helper.getWritableDatabase();		
		
		actions = ActionTableDAO.getActionNames(db);
		
		db.close();
		return actions;
	}
	
	public List<String> getActionPlaces(){
		List<String> places = new ArrayList<String>();
		db = helper.getWritableDatabase();		

		places = ActionTableDAO.getActionPlaces(db);
		
		db.close();
		return places;
	}
	
	public void deleteAll(){
		db = helper.getWritableDatabase();
		
		ActionTableDAO.deleteAll(db);
		PersonTableDAO.deleteAll(db);
		DetailTableDAO.deleteAll(db);
		PaymentTableDAO.deleteAll(db);
		
		db.close();
	}
	
}
