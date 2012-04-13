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
import android.view.View.OnLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class PaymentActivity extends Activity{
	private DatabaseManagement dm;
	private Button enterButton;
	private Button cancelButton;
	private Spinner payerSpinner;
	private Spinner payeeSpinner;
	private EditText moneyEditText;
	private ArrayAdapter<String> adapter;
	private List<String> persons = new ArrayList<String>();
	private String payerName = new String();
	private String payeeName = new String();
	private int money;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        setTitle("Payment");

		dm = new DatabaseManagement(this);
		payerSpinner = (Spinner)findViewById(R.id.payerSelector);
		payeeSpinner = (Spinner)findViewById(R.id.payeeSelector);
		moneyEditText = (EditText)findViewById(R.id.moneyInputEdit);
		enterButton = (Button)findViewById(R.id.paymentEnterButton);
		cancelButton = (Button)findViewById(R.id.paymentCancelButton);
		
		moneyEditText.setText("0");
		
        persons = dm.getPersonNames();
        
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,persons);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        payerSpinner.setAdapter(adapter);
        payerSpinner.setOnItemSelectedListener(new PayerSpinnerSelectedListener());
        payerSpinner.setVisibility(View.VISIBLE);		

        payeeSpinner.setAdapter(adapter);
        payeeSpinner.setOnItemSelectedListener(new PayeeSpinnerSelectedListener());
        payeeSpinner.setVisibility(View.VISIBLE);		        
        
        enterButton.setOnClickListener(new EnterButtonClickListener());
        cancelButton.setOnClickListener(new CancelButtonClickListener());
        
        moneyEditText.setOnLongClickListener(new MoneyEditLongClick());
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
			return true;
		return super.onKeyDown(keyCode, event);
	}
	
	class MoneyEditLongClick implements OnLongClickListener{
		public boolean onLongClick(View v) {
			moneyEditText.setText("");
			return true;
		}
	}
	
	class EnterButtonClickListener implements OnClickListener{
		public void onClick(View v) {
			
			if (payerName.length() <= 0){
				Toast.makeText(PaymentActivity.this, "Please select payer!", Toast.LENGTH_SHORT).show();
				return;								
			}
	
			if (payeeName.length() <= 0){
				Toast.makeText(PaymentActivity.this, "Please select payee!", Toast.LENGTH_SHORT).show();
				return;								
			}
			
			try {
				money = Integer.parseInt(moneyEditText.getText().toString());				
			} catch (NumberFormatException e) {
				Toast.makeText(PaymentActivity.this, "Wrong money input!", Toast.LENGTH_SHORT).show();
				return;
			}
			
			if (money <= 0)
			{
				Toast.makeText(PaymentActivity.this, "Wrong money input!", Toast.LENGTH_SHORT).show();
				return;
			}
						
			dm.AddPayment(payerName, payeeName, money);
			
			//Intent intent=new Intent();
			//intent.setClass(PaymentActivity.this, DutchDateActivity.class);
			//startActivity(intent);  	
			PaymentActivity.this.finish();
		}		
	}
	
	class CancelButtonClickListener implements OnClickListener{
		public void onClick(View v) {
			//Intent intent=new Intent();
			//intent.setClass(PaymentActivity.this, DutchDateActivity.class);
			//startActivity(intent);    
			PaymentActivity.this.finish();			
		}		
	}
	
	class PayerSpinnerSelectedListener implements OnItemSelectedListener{
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			payerName = persons.get(arg2);

		}
		public void onNothingSelected(AdapterView<?> arg0) {
			
		}
	}
	class PayeeSpinnerSelectedListener implements OnItemSelectedListener{
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			payeeName = persons.get(arg2);

		}
		public void onNothingSelected(AdapterView<?> arg0) {
			
		}
	}	
	
}
