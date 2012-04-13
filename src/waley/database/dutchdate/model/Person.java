package waley.database.dutchdate.model;

import java.io.Serializable;


public class Person{
	//private static final long serialVersionUID = 8328522082394627801L;
	private int personID;
	private String name;
	private String phone;
	private int payment;
	private int consume;
	private int balance;
	
	
	public Person() {
		super();
	}

	public Person(int personID, String name, String phone, int payment,
			int consume) {
		super();
		this.personID = personID;
		this.name = name;
		this.phone = phone;
		this.payment = payment;
		this.consume = consume;
		this.balance = payment - consume;
		
	}
	
	public void setPersonID(int personID){
		this.personID = personID;
	}
	
	public int getPersonID(){
		return personID;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getPayment() {
		return payment;
	}
	public int getBalance(){
		return balance;
	}
	public int getConsume() {
		return consume;
	}
	
	public void addPayment(int payment){
		if (payment<=0)
			return;
		
		this.payment = this.payment + payment;
		this.balance = this.payment - this.consume;
	}
	
	public void addConsume(int consume){
		if (consume <= 0)
			return;
		
		this.consume = this.consume + consume;
		this.balance = this.payment - this.consume;
	}
	
	public void subPayment(int money){
		if (money <=0)
			return;
		
		this.payment = this.payment-money;
		this.balance = this.payment-this.consume;
	}
	
	public void subConsume(int money){
		if (money <= 0)
			return;
		
		this.consume = this.consume - money;
		this.balance = this.payment-this.consume;
	}
	
	public void setPaymentConsume(int payment,int consume) {
		this.consume = consume;
		this.payment = payment;
		this.balance = payment - consume;
	}
	
	public void setPayment(int payment){
		this.payment = payment;
	}
	public void setConsume(int consume){
		this.consume = consume;
	}
	public void setBalance(int balance){
		this.balance = balance;
	}
}
