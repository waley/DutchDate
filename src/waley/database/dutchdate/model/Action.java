package waley.database.dutchdate.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Action{

	//private static final long serialVersionUID = -2039401054718600295L;
	private int actionID;
	private String name;
	private String date;
	private String place;
	private int consume;
	private int headcount;
	private String payer; 
	private List<String> attendees = new ArrayList<String>();

	
	public int getActionID() {
		return actionID;
	}

	public void setActionID(int actionID) {
		this.actionID = actionID;
	}
	
	public void addAttendee(String name){
		attendees.add(name);
	}
	
	public String getAttendees(int index){
		return attendees.get(index);
	}
		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public int getConsume() {
		return consume;
	}
	public void setConsume(int consume) {
		this.consume = consume;
	}
	public int getHeadcount() {
		return headcount;
	}
	public void setHeadcount(int headcount) {
		this.headcount = headcount;
	}
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	
	
}
