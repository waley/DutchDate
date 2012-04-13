package waley.database.dutchdate.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import waley.database.dutchdate.R;
import waley.database.dutchdate.dao.DatabaseManagement;
import waley.database.dutchdate.model.Person;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ShowSummaryActivity extends ListActivity{
	private String user = null;
	private DatabaseManagement dm = null;
	private List<Person> summarys = new ArrayList<Person>();
	private Button returnButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.show_summary);
        setTitle("Show Summary");

        returnButton = (Button)findViewById(R.id.summaryReturnButton);
        returnButton.setOnClickListener(new ReturnButtonClickListener());
        
        Intent intent = getIntent();
        this.user = intent.getStringExtra("user");     
        System.out.println("summary user:"+user);
        dm = new DatabaseManagement(this);
        
        prepareSummaryList();
	}

	private void prepareSummaryList(){
		summarys = dm.getSummaryInfos();
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		for (Iterator iterator = summarys.iterator(); iterator.hasNext();) {
			Person person = (Person)iterator.next();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("person_name", person.getName() );
			map.put("person_balance", Integer.toString(person.getBalance()));
			list.add(map);
		}
		
	//	SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.show_summary_item
	//			, new String[]{"person_name","person_balance",}, new int[]{R.id.person_name,R.id.person_balance});
		MyAdapter simpleAdapter = new MyAdapter(this);
		setListAdapter(simpleAdapter);		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		new AlertDialog.Builder(this)
		.setTitle("Summary")
		.setMessage("Name: "+summarys.get(position).getName()
				+ "\nPhone: "+summarys.get(position).getPhone()
				+ "\nTotal Payment: "+Integer.toString(summarys.get(position).getPayment())
				+ "\nTotal Consume: "+Integer.toString(summarys.get(position).getConsume())
				+ "\nBalance: "+Integer.toString(summarys.get(position).getBalance())
				)
		.setPositiveButton("OK",null).show();
		
		super.onListItemClick(l, v, position, id);
	}
	
	class ReturnButtonClickListener implements OnClickListener{
		public void onClick(View v) {
			//Intent intent=new Intent();
			//intent.putExtra("user", user);					
			//intent.setClass(ShowSummaryActivity.this, DutchDateActivity.class);
			//startActivity(intent); 
			ShowSummaryActivity.this.finish();							
		}
	}
	
	
	 public final class ViewHolder {
		  public TextView nameText;
		  public TextView balanceText;  
		 }

	class MyAdapter extends BaseAdapter{
		 private LayoutInflater mInflater;
		 
		  public MyAdapter(Context context) {
		   this.mInflater = LayoutInflater.from(context);
		  }
		  
		  
		public int getCount() {
			return summarys.size();
		}
		public Object getItem(int position) {
			return summarys.get(position);
		}
		public long getItemId(int position) {
			return position;
		}
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			   if (convertView == null) {
			    holder = new ViewHolder();
			    convertView = mInflater.inflate(R.layout.show_summary_item, null);
			    holder.nameText = (TextView) convertView.findViewById(R.id.person_name);
			    holder.balanceText = (TextView) convertView.findViewById(R.id.person_balance);
			    
			    convertView.setTag(holder);   
			   } else {
			    holder = (ViewHolder) convertView.getTag();
			   }
			   
			   holder.nameText.setText((String) summarys.get(position).getName());
			   holder.balanceText.setText((String)Integer.toString(summarys.get(position).getBalance()));   
			   			   
			   if (user.equalsIgnoreCase(summarys.get(position).getName())){
				   System.out.println("red");
				    convertView.setBackgroundColor(Color.RED);
			   }else{
				    convertView.setBackgroundColor(Color.TRANSPARENT);				   
			   }
			   //convertView.getBackground().setAlpha(80); 			   
			   return convertView;
		}
	}
}
