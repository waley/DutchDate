package waley.database.dutchdate.activity;

import java.util.ArrayList;
import java.util.List;

import waley.database.dutchdate.R;
import waley.database.dutchdate.dao.DatabaseManagement;
import waley.database.dutchdate.model.Person;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewPersonActivity extends Activity{
	private DatabaseManagement dm=null;
	private Button enterButton;
	private Button cancelButton;
	private EditText personName;
	private EditText personPhone;
	private Person person = new Person();
	private List<String> personNames = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.new_person);
        setTitle("New Person");
        
        dm = new DatabaseManagement(this);
        
        personName = (EditText)findViewById(R.id.personNameEdit);
        personPhone = (EditText)findViewById(R.id.personPhoneEdit);
        enterButton = (Button)findViewById(R.id.personEnterButton);
        cancelButton = (Button)findViewById(R.id.personCancelButton);
        
        enterButton.setOnClickListener(new EnterButtonClickListener());
        cancelButton.setOnClickListener(new CancelButtonClickListener());
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
			return true;
		return super.onKeyDown(keyCode, event);
	}
	
	class EnterButtonClickListener implements OnClickListener{
		public void onClick(View v) {
			
			if (personName.getText().length() <= 0){
				Toast.makeText(NewPersonActivity.this, "Please input person name!", Toast.LENGTH_SHORT).show();
				return;								
			}

			if (personPhone.getText().length() <= 0){
				Toast.makeText(NewPersonActivity.this, "Please input phone number!", Toast.LENGTH_SHORT).show();
				return;								
			}

			person.setName(personName.getText().toString());
			person.setPhone(personPhone.getText().toString());
			person.setBalance(0);
			person.setConsume(0);
			person.setPayment(0);
			personNames = dm.getPersonNames();
			
			for(int i=0; i<personNames.size(); i++){
				if (personNames.contains(person.getName())){
					Toast.makeText(NewPersonActivity.this, "The name already exist,please change!", Toast.LENGTH_SHORT).show();
					return;													
				}
			}
			
			dm.AddPerson(person);
			//Intent intent=new Intent();
			//intent.setClass(NewPersonActivity.this, DutchDateActivity.class);
			//startActivity(intent);   
			NewPersonActivity.this.finish();			
		}		
	}
	
	class CancelButtonClickListener implements OnClickListener{
		public void onClick(View v) {
			//Intent intent=new Intent();
			//intent.setClass(NewPersonActivity.this, DutchDateActivity.class);
			//startActivity(intent);   
			NewPersonActivity.this.finish();
		}
	}
}
