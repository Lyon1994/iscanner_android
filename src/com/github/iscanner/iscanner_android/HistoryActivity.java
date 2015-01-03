package com.github.iscanner.iscanner_android;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

public class HistoryActivity extends Activity implements OnClickListener {
	private static final String TAG = "iscanner";
	private TextView title;
	private Button leftButton;
	private ListView listView;
	private List<?> parentList;
	private List<String> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_history);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
		getSharedPreferences("list");
		initView();
		Log.i(TAG, "start loading...");
	}

	public void getSharedPreferences(String locastorageKey) {
		SharedPreferences settings = this.getSharedPreferences(
				"localstoregeXML", 0);
		String list = settings.getString(locastorageKey, "");
		if (list != "") {
			Log.i(TAG, list);
			parentList = JSON.parseArray(list);
			initTabel();
		}
	}

	public void initView() {
		title = (TextView) findViewById(R.id.title);
		title.setText("History");
		leftButton = (Button) findViewById(R.id.button_first);
		leftButton.setOnClickListener(this);
		leftButton.setVisibility(View.VISIBLE);
	}

	public void initTabel() {
		listView = (ListView) findViewById(R.id.historyListView);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, getData());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String current = data.get(arg2);
				String regEx = "\\d+-\\d+-\\d+";
				Pattern pattern = Pattern.compile(regEx);
				Matcher matcher = pattern.matcher(current);
				if (!matcher.matches()) {
					Intent viewIntent = new Intent(
							"android.intent.action.VIEW", Uri.parse(current));
					startActivity(viewIntent);
				}
			}
		});
	}

	private List<String> getData() {
		data = new ArrayList<String>();

		for (int i = 0; i < parentList.size(); i++) {
			Map<String, Object> current = (Map<String, Object>) parentList
					.get(i);
			Set<String> keys = current.keySet();
			String tempKey = keys.toArray()[0].toString();
			Log.i(TAG, tempKey);
			data.add(tempKey);
			List<String> list = (List) current.get(tempKey);
			for (int j = 0; j < list.size(); j++) {
				data.add(list.get(j));
			}
		}
		return data;
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.button_first:
			finish();
			break;
		case R.id.button_second:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
