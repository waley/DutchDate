package waley.database.dutchdate.model;

import java.io.Serializable;

public class Detail{
	//private static final long serialVersionUID = -1997853305130197979L;
	private int id;
	private int actionID;
	private int personID;
	private int consume;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getActionID() {
		return actionID;
	}
	public void setActionID(int actionID) {
		this.actionID = actionID;
	}
	public int getPersonID() {
		return personID;
	}
	public void setPersonID(int personID) {
		this.personID = personID;
	}
	public int getConsume() {
		return consume;
	}
	public void setConsume(int consume) {
		this.consume = consume;
	}
}
