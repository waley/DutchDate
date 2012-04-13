package waley.database.dutchdate.activity;

import java.util.ArrayList;
import java.util.List;
import waley.database.dutchdate.R;
import waley.database.dutchdate.dao.DatabaseManagement;
import waley.database.dutchdate.model.Action;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class NewActionActivity extends Activity{
	private DatabaseManagement dm=null;
	private List<String> nameList = new ArrayList<String>();
	private List<String> placeList = new ArrayList<String>();
	private String[]    actionNames;
	private String[]    actionPlaces;
	private AutoCompleteTextView actionName;
	private AutoCompleteTextView actionPlace;
	private DatePicker	actionDate;
	private EditText    actionHeadcount;
	private EditText    actionConsume;
	private Button      actionAttendeesButton;
	private Button      actionPayerButton;
	private Button      enterButton;
	private Button      cancelButton;
	private Action      action;
	private List<String> attendees = new ArrayList<String>();
	private List<String> personsList = new ArrayList<String>();
	private boolean[] personState;
	private String[]  persons;
	private ListView checkListView;
	private ListView radioListView;
	private String selectedPayer = new String();
	private int selectedPayerID = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.new_action);
        setTitle("New Action");

        dm = new DatabaseManagement(this);
        nameList = dm.getActionNames();
        placeList = dm.getActionPlaces();
        personsList = dm.getPersonNames();
        
        //for test only
        if (nameList.isEmpty())
        {
        	nameList.add("Name1");
        	nameList.add("Name2");
        	nameList.add("Name3");
        	nameList.add("Name4");
        	nameList.add("Name5");
        }
        if (placeList.isEmpty()){
        	placeList.add("Place1");
        	placeList.add("Place2");
        	placeList.add("Place3");
        	placeList.add("Place4");        	
        }
        if (personsList.isEmpty()){
        	personsList.add("Person1");
        	personsList.add("Person2");
        	personsList.add("Person3");
        	personsList.add("Person4");
        	personsList.add("Person5");        	
        	personsList.add("Person6");
        	personsList.add("Person7");
        }
        
        actionNames = new String[nameList.size()];
        for(int i=0; i<nameList.size(); i++){
        	actionNames[i] = nameList.get(i);
        }

        actionPlaces = new String[placeList.size()];
        for(int i=0; i<placeList.size(); i++){
        	actionPlaces[i] = placeList.get(i);
        }
        
        personState = new boolean[personsList.size()];
        persons = new String[personsList.size()];

        for (int i=0; i<personsList.size(); i++){
        	persons[i] = personsList.get(i);
        	personState[i] = false;
        }
        
        actionName = (AutoCompleteTextView)findViewById(R.id.actionNameEdit);
        actionPlace = (AutoCompleteTextView)findViewById(R.id.actionPlaceEdit);
        actionDate = (DatePicker)findViewById(R.id.actionDateEdit);
        actionHeadcount = (EditText)findViewById(R.id.actionHeadcountEdit);
        actionConsume = (EditText)findViewById(R.id.actionConsumeEdit);
        actionAttendeesButton = (Button)findViewById(R.id.actionAttendeesSelectButton);
        actionPayerButton = (Button)findViewById(R.id.actionPayerSelectButton);
        enterButton = (Button)findViewById(R.id.actionEnterButton);
        cancelButton = (Button)findViewById(R.id.actionCancelButton);
        
        actionHeadcount.setText("2");
        actionConsume.setText("0");
        
        actionAttendeesButton.setOnClickListener(new AttendeesButtonClick());
        actionPayerButton.setOnClickListener(new PayerButtonClick());
        enterButton.setOnClickListener(new EnterButtonClick());
        cancelButton.setOnClickListener(new CancelButtonClick());
        actionName.setOnLongClickListener(new NameEditLongClick());
        actionPlace.setOnLongClickListener(new PlaceEditLongClick());
        actionHeadcount.setOnLongClickListener(new HeadcountEditLongClick());
        actionConsume.setOnLongClickListener(new ConsumeEditLongClick());
        
        //ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,nameList);
        //actionName.setAdapter(nameAdapter);
        
        //ArrayAdapter<String> placeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,placeList);
        //actionPlace.setAdapter(placeAdapter);
        
        action = new Action();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
			return true;
		return super.onKeyDown(keyCode, event);
	}
	
	class HeadcountEditLongClick implements OnLongClickListener{
		public boolean onLongClick(View v) {
	        actionHeadcount.setText("");
			return true;
		}
	}

	class ConsumeEditLongClick implements OnLongClickListener{
		public boolean onLongClick(View v) {
			actionConsume.setText("");
			return true;
		}
	}
	
	class NameEditLongClick implements OnLongClickListener{
		public boolean onLongClick(View v) {
			AlertDialog alertDialog = new AlertDialog.Builder(NewActionActivity.this)
			.setTitle("Select Action Name")
			.setSingleChoiceItems(actionNames,0, new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					actionName.setText(radioListView.getAdapter().getItem(which).toString());
			        dialog.dismiss();
				}
			}).create();
		
			radioListView = alertDialog.getListView();
			alertDialog.show();
			return true;
		}
	}

	class PlaceEditLongClick implements OnLongClickListener{
		public boolean onLongClick(View v) {
			AlertDialog alertDialog = new AlertDialog.Builder(NewActionActivity.this)
			.setTitle("Select Action Place")
			.setSingleChoiceItems(actionPlaces,0, new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					actionPlace.setText(radioListView.getAdapter().getItem(which).toString());
			        dialog.dismiss();
				}
			}).create();
		
			radioListView = alertDialog.getListView();
			alertDialog.show();
			return true;
		}		
	}
	
	
	
	class AttendeesButtonClick implements OnClickListener{
		public void onClick(View v) {
			System.out.println("personsList.size:"+personsList.size()+" personState:"+personState);
			
			AlertDialog alertDialog = new AlertDialog.Builder(NewActionActivity.this)
			.setTitle("Please select attendees:")
			.setMultiChoiceItems(persons, personState,new DialogInterface.OnMultiChoiceClickListener() {
				
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					
				}
			}).setPositiveButton("Enter", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
						attendees.clear();
						for (int i = 0; i < personsList.size(); i++){
				        	if (checkListView.getCheckedItemPositions().get(i))
				        	{
				        		attendees.add((String)checkListView.getAdapter().getItem(i));
				        	}else {
								checkListView.getCheckedItemPositions().get(i, false);
								
							}
				        }
				        dialog.dismiss();
				}
			}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					for(int i=0; i<personState.length; i++)
					{
						personState[i] = false;
					}
					attendees.clear();
				}
			}).create();
			
			checkListView = alertDialog.getListView();
			alertDialog.show();
		}		
	}
	
	class PayerButtonClick implements OnClickListener{
		public void onClick(View v) {
		AlertDialog alertDialog = new AlertDialog.Builder(NewActionActivity.this)
			.setTitle("Please select payer:")
			.setSingleChoiceItems(persons,selectedPayerID, new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					selectedPayerID = which;
					selectedPayer = radioListView.getAdapter().getItem(which).toString();
			        dialog.dismiss();
				}
			}).create();
		
			radioListView = alertDialog.getListView();
			alertDialog.show();
		}		
	}
	
	
	class EnterButtonClick implements OnClickListener{
		public void onClick(View v) {
			int headcount=2,consume=0;
			
			if (actionName.getText().length() <= 0){
				Toast.makeText(NewActionActivity.this, "Please input action name!", Toast.LENGTH_SHORT).show();
				return;								
			}
			
			if (actionPlace.getText().length() <= 0){
				Toast.makeText(NewActionActivity.this, "Please input place name!", Toast.LENGTH_SHORT).show();
				return;								
			}
			
			try {
				headcount = Integer.parseInt(actionHeadcount.getText().toString());
			} catch (NumberFormatException e) {
				Toast.makeText(NewActionActivity.this, "Wrong headcount number!", Toast.LENGTH_SHORT).show();
				return;
			}
			try {
				consume = Integer.parseInt(actionConsume.getText().toString());				
			} catch (NumberFormatException e) {
				Toast.makeText(NewActionActivity.this, "Wrong consume number!", Toast.LENGTH_SHORT).show();
				return;				
			}
			
			if (headcount <=0){
				Toast.makeText(NewActionActivity.this, "Wrong headcount number!", Toast.LENGTH_SHORT).show();
				return;
			}
			if (consume <=0){
				Toast.makeText(NewActionActivity.this, "Wrong consume number!", Toast.LENGTH_SHORT).show();
				return;
			}			
			if (headcount != attendees.size()){
				Toast.makeText(NewActionActivity.this, "The attendees not match headcount!", Toast.LENGTH_SHORT).show();
				return;
			}
			
			if (selectedPayer.length() <= 0){
				Toast.makeText(NewActionActivity.this, "Please select payer!", Toast.LENGTH_SHORT).show();
				return;				
			}
			
			if (!attendees.contains(selectedPayer)){
				Toast.makeText(NewActionActivity.this, "The payer not into attendees list!", Toast.LENGTH_SHORT).show();			
				return;
			}
			
			action.setName(actionName.getText().toString());
			action.setPlace(actionPlace.getText().toString());
			action.setDate(""+actionDate.getYear()+"-"+actionDate.getMonth()+"-"+actionDate.getDayOfMonth());
			action.setHeadcount(headcount);
			action.setConsume(consume);
			action.setPayer(selectedPayer);
			
			for (int i=0; i<attendees.size();i++){
				action.addAttendee(attendees.get(i));
			}
			if (!dm.AddAction(action))
			{
				Toast.makeText(NewActionActivity.this, "Add new action failed!", Toast.LENGTH_SHORT).show();
				return;
			}
			//Intent intent=new Intent();
			//intent.setClass(NewActionActivity.this, DutchDateActivity.class);
			//startActivity(intent);    
			NewActionActivity.this.finish();
		}
	}

	class CancelButtonClick implements OnClickListener{
		public void onClick(View v) {
			//Intent intent=new Intent();
			//intent.setClass(NewActionActivity.this, DutchDateActivity.class);
			//startActivity(intent);    		
			NewActionActivity.this.finish();
		}
	}
	
}
