package com.example.tuicool;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.example.database_utils.DBUtils;
import com.example.model.Article;
import com.example.model.upgradelog.Item;
import com.example.model.upgradelog.Upgradelog;
import com.example.tuicool.R;
import com.example.util.APIClient;
import com.example.util.JsonHelper;
import com.example.util.ThemeToggleUtils;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.VolleyListener;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class UpgradeLogActivity extends AppCompatActivity
{

	private List<Item> mUpData = new ArrayList<Item>();
	private Item items;
	private UpgradeAdapter upgradeAdapter;
	public TextView tv_version_number;
	public TextView tv_date;
	public TextView tv_log;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// ����ҹ��ģʽ  �л�
				ThemeToggleUtils.setThemeToggle(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upgrade_log);
		
		initUI();
		initData();
	}

	private void initUI()
	{
		initToolbar();
		ListView listView_upgrade_log = (ListView) findViewById(R.id.listView_upgrade_log);
		upgradeAdapter = new UpgradeAdapter();
		listView_upgrade_log.setAdapter(upgradeAdapter); // ����������

	}

	private void initData()
	{
		
		APIClient.getUpgradeLog(this, new VolleyListener()
		{

			@Override
			public void onResponse(String arg0)
			{
				Upgradelog upgradelog = JsonHelper.fromExposeJson(arg0, Upgradelog.class);
				List<Item> items = upgradelog.getItems();
				mUpData.addAll(items); // r�����������
				// data�仯���������֪ͨ����
				upgradeAdapter.notifyDataSetChanged();
			}

			@Override
			public void onErrorResponse(VolleyError arg0)
			{
				Log.e("onErrorResponse", "error = " + arg0.getMessage());
			}
		});
		DBUtils.insertUpgradeLog(items);
	}

	private void initToolbar()
	{
		// ��getSupportActionBar()����֮ǰ����
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true); // ������ʾ���ؼ�
		// ���ñ���
		actionBar.setTitle("������־");
		// actionBar.setSubtitle("subTitle");
		// // ���ñ���ͼƬ
		// Drawable actionbarbg =
		// getResources().getDrawable(R.drawable.actionbar_bg);
		// actionBar.setBackgroundDrawable(actionbarbg );
		// // ����LOGO
		// actionBar.setDisplayUseLogoEnabled(true);
		// actionBar.setDisplayShowHomeEnabled(true);
		// actionBar.setLogo(R.drawable.ic_launcher);
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu)
	// {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.upgrade_log, menu);
	// return true;
	// }
	
//	@Override
//	protected void onStart()
//	{
//		if (mUpData.size() < 0)
//		{
//			return;
//		} else
//		{
//			getUpDataFromDB();
//		}
//		super.onStart();
//	}

	private void getUpDataFromDB()
	{
		mUpData.clear(); // ��ѯ֮ǰ������� ��ֹ�ظ����
		List<Item> queryUpgradeLog = DBUtils.queryUpgradeLog();
		mUpData.addAll(queryUpgradeLog); //
		// data�仯���������֪ͨ����
		upgradeAdapter.notifyDataSetChanged();
	}

	class UpgradeAdapter extends BaseAdapter
	{
		private View layout = null;

		@Override
		public int getCount()
		{
			return mUpData.size();
		}

		@Override
		public Object getItem(int position)
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position)
		{
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder = new ViewHolder();;
			if (convertView == null)
			{
				layout = getLayoutInflater().inflate(R.layout.list_item_upgrade_log, null);
				holder.tv_version_number = (TextView) layout.findViewById(R.id.tv_upgrade_version);
				holder.tv_date = (TextView) layout.findViewById(R.id.tv_upgrade_date);
				holder.tv_log = (TextView) layout.findViewById(R.id.tv_upgrade_log);
				layout.setTag(holder);
			} else
			{
				layout = convertView;
				holder = (ViewHolder) layout.getTag();
			}
			Item item = mUpData.get(position);
			holder.tv_version_number.setText(item.getVersionName());
			holder.tv_date.setText(item.getDate());
			holder.tv_log.setText(item.getLog());

			return layout;
		}
	}
	class ViewHolder
	{
		 TextView tv_version_number;
		 TextView tv_date;
		 TextView tv_log;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id)
		{
		case R.id.action_settings:
			return true;
		case android.R.id.home: // ���ؼ�����
			overridePendingTransition(0, 0);
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
