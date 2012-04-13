package waley.database.dutchdate.model;

import java.io.Serializable;

public class Consume{

	//private static final long serialVersionUID = 7262668234500300700L;
	private String actionName;
	private String actionDate;
	private String actionPlace;
	private String actionPayer;
	private int actionConsume;
	private int actionHeadcount;
	private int myConsume;
	
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getActionDate() {
		return actionDate;
	}
	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}
	public String getActionPlace() {
		return actionPlace;
	}
	public void setActionPlace(String actionPlace) {
		this.actionPlace = actionPlace;
	}
	public int getActionConsume() {
		return actionConsume;
	}
	public void setActionConsume(int actionConsume) {
		this.actionConsume = actionConsume;
	}
	public int getActionHeadcount() {
		return actionHeadcount;
	}
	public void setActionHeadcount(int actionHeadcount) {
		this.actionHeadcount = actionHeadcount;
	}
	public int getMyConsume() {
		return myConsume;
	}
	public void setMyConsume(int myConsume) {
		this.myConsume = myConsume;
	}
	public String getActionPayer() {
		return actionPayer;
	}
	public void setActionPayer(String actionPayer) {
		this.actionPayer = actionPayer;
	}
}
