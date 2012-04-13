package waley.database.dutchdate.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import waley.database.dutchdate.R;
import waley.database.dutchdate.dao.DatabaseManagement;
import waley.database.dutchdate.model.Consume;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ShowDetailActivity extends ListActivity{
	private String user = null;
	private DatabaseManagement dm = null;
	private List<Consume> details = new ArrayList<Consume>();
	private Button returnButton;
	private int totalConsume=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.show_detail);
        setTitle("Show Detail");

        returnButton = (Button)findViewById(R.id.detailReturnButton);
        returnButton.setOnClickListener(new ReturnButtonClickListener());
        
        Intent intent = getIntent();
        this.user = intent.getStringExtra("user");      
        System.out.println("user:"+user);
        dm = new DatabaseManagement(this);
        prepareDetailList();
	}

	private void prepareDetailList(){
		details = dm.getConsumeInfos(user);
		System.out.println("Detail info size:"+details.size());
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		for (Iterator iterator = details.iterator(); iterator.hasNext();) {
			Consume consume = (Consume) iterator.next();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("action_date", consume.getActionDate());
			map.put("action_name", consume.getActionName());
			map.put("action_consume",Integer.toString(consume.getMyConsume()));	
			System.out.println("date:"+consume.getActionDate()+" name:"+consume.getActionName()+" consume:"+consume.getMyConsume());
			totalConsume = totalConsume + consume.getMyConsume();
			list.add(map);
		}
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.show_detail_item
				, new String[]{"action_date","action_name","action_consume"}, new int[]{R.id.action_date,R.id.action_name,R.id.action_consume});
		setListAdapter(simpleAdapter);		
        setTitle("Show Detail  (Total Consume:"+totalConsume+")");
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		new AlertDialog.Builder(this)
		.setTitle("Detail")
		.setMessage("Action Date: "+details.get(position).getActionDate()
				+ "\nAction Name: "+details.get(position).getActionName()
				+ "\nAction Place: "+details.get(position).getActionPlace()
				+ "\nAction Headcount: "+Integer.toString(details.get(position).getActionHeadcount())
				+ "\nAction Consume: "+Integer.toString(details.get(position).getActionConsume())
				+ "\nAction Payer: "+details.get(position).getActionPayer()
				+ "\nMy Consume: "+Integer.toString(details.get(position).getMyConsume())
				)
		.setPositiveButton("OK",null).show();

		super.onListItemClick(l, v, position, id);
	}
	
	class ReturnButtonClickListener implements OnClickListener{
		public void onClick(View v) {
			//Intent intent=new Intent();
			//intent.putExtra("user", user);			
			//intent.setClass(ShowDetailActivity.this, DutchDateActivity.class);
			//startActivity(intent);  
			ShowDetailActivity.this.finish();						
		}
	}
}
