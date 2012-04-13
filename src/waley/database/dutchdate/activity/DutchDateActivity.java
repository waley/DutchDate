package waley.database.dutchdate.activity;

import java.util.ArrayList;
import java.util.List;
import waley.database.dutchdate.R;
import waley.database.dutchdate.dao.DatabaseManagement;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class DutchDateActivity extends Activity {
	DatabaseManagement dm = null;
	private String currentUser;
	private TextView userTextView;
	private Spinner userSelectorSpinner;
	private Button newPersonButton;
	private Button newActionButton;
	private Button showSummaryButton;
	private Button showDetailButton;
	private Button paymentButton;
	private ArrayAdapter<String> adapter;
	private List<String> persons = new ArrayList<String>();
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setTitle("Main Menu");
        dm = new DatabaseManagement(this);
		System.out.println("onCreate()");

        //currentUser = new String("");
        //Intent intent = getIntent();
        //currentUser = intent.getStringExtra("user");

        userTextView = (TextView)findViewById(R.id.user);
        userSelectorSpinner = (Spinner) findViewById(R.id.userSelector);
        newPersonButton = (Button)findViewById(R.id.newPersonButton);
        newActionButton = (Button)findViewById(R.id.newActionButton);
        showSummaryButton = (Button)findViewById(R.id.showSummaryButton);
        showDetailButton = (Button)findViewById(R.id.showDetailButton);
        paymentButton = (Button)findViewById(R.id.paymentButton);
        
        persons = dm.getPersonNames();
        
        if (persons.isEmpty()){
        	persons.add("Person1");
        	persons.add("Person2");
        	persons.add("Person3");
        	persons.add("Person4");
        	persons.add("Person5");        	
        	persons.add("Person6");
        	persons.add("Person7");
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,persons);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSelectorSpinner.setAdapter(adapter);
        userSelectorSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());
        userSelectorSpinner.setVisibility(View.VISIBLE);
        
        /*
        for(int i=0; i<persons.size();i++)
        {
        	if (persons.get(i).equals("currentUser"))
        	{
        		userSelectorSpinner.setSelection(i);
        		System.out.println("select user:"+i+" name:"+currentUser);
        	}
        }
        */
        newPersonButton.setOnClickListener(new ButtonClickListener());
        newActionButton.setOnClickListener(new ButtonClickListener());
        showSummaryButton.setOnClickListener(new ButtonClickListener());
        showDetailButton.setOnClickListener(new ButtonClickListener());
        paymentButton.setOnClickListener(new ButtonClickListener());        
        
    }
    
    @Override
    protected void onResume() {
    	String lastUser = currentUser;
        persons = dm.getPersonNames();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,persons);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSelectorSpinner.setAdapter(adapter);
        userSelectorSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());
        userSelectorSpinner.setVisibility(View.VISIBLE);
        for(int i=0; i<persons.size();i++)
        {
        	if (persons.get(i).equals(lastUser))
        	{
        		userSelectorSpinner.setSelection(i);
        		System.out.println("select user:"+i+" name:"+currentUser);
        	}
        }
        super.onResume();
    }
    
    class SpinnerSelectedListener implements OnItemSelectedListener{
    	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
    			long arg3) {
    		currentUser = persons.get(arg2);
    		userTextView.setText("User: "+currentUser);
    	}
    	public void onNothingSelected(AdapterView<?> arg0) {
    		
    	}
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_MENU){
    		dm.deleteAll();
			DutchDateActivity.this.finish();
    		return true;
    	}else
    		return super.onKeyDown(keyCode, event);
    }
    
    class ButtonClickListener implements OnClickListener{
    	public void onClick(View v) {
    		if (v.getId() == showDetailButton.getId()){
    			Intent intent=new Intent();
    			intent.putExtra("user", currentUser);
    			intent.setClass(DutchDateActivity.this, ShowDetailActivity.class);
    			startActivity(intent);
    			//DutchDateActivity.this.finish();
    		}else if(v.getId()==showSummaryButton.getId()){
    			Intent intent=new Intent();
    			intent.putExtra("user", currentUser);
    			intent.setClass(DutchDateActivity.this, ShowSummaryActivity.class);
    			startActivity(intent);    			
    			//DutchDateActivity.this.finish();
    		}else if(v.getId()==newPersonButton.getId()){
    			Intent intent=new Intent();
    			intent.setClass(DutchDateActivity.this, NewPersonActivity.class);
    			startActivity(intent);    		
    			//DutchDateActivity.this.finish();   			
    		}else if(v.getId()==newActionButton.getId()){
    			Intent intent=new Intent();
    			intent.setClass(DutchDateActivity.this, NewActionActivity.class);
    			startActivity(intent);    		
    			//DutchDateActivity.this.finish(); 			
    		}else if(v.getId()==paymentButton.getId()){
    			Intent intent=new Intent();
    			intent.setClass(DutchDateActivity.this, PaymentActivity.class);
    			startActivity(intent);    		
    			//DutchDateActivity.this.finish();   			
    		}
    	}
    }
    
}