package com.example.tuicool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.example.util.Data;
import com.example.util.MyHelper;
import com.example.util.TKConstants;
import com.example.util.ThemeToggleUtils;
import com.gc.materialdesign.views.ButtonFloat;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class YiJianFanKuiActivity extends AppCompatActivity implements OnClickListener
{
	// private int backgroundColor = Color.parseColor("#ff4e00");
	private int color = Color.parseColor("#ff4e00");
	// private int color;
	int backgroundColor = Color.parseColor("#1E88E5");
	private SQLiteDatabase mdb;
	ArrayList<Data> mlist = new ArrayList<>();
	private String string;
	private String currentTime;
	private MyAdapter myAdapter;
	private ListView mListView;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// ����ҹ��ģʽ �л�
		ThemeToggleUtils.setThemeToggle(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yi_jian_fan_kui);
		MyHelper myHelper = new MyHelper(this);
		mdb = myHelper.getWritableDatabase();
		// color = getIntent().getIntExtra("BACKGROUND", Color.BLACK);
		initUI();
		initData();

	}

	private void initUI()
	{
		initToolbar();
		ButtonFloat fankui_ButtonFloat = (ButtonFloat) findViewById(R.id.buttonFloat);
		mListView = (ListView) findViewById(R.id.listview_feedback);
		fankui_ButtonFloat.setBackgroundColor(color);
		fankui_ButtonFloat.setOnClickListener(this);
	}

	private void initData()
	{
		// ��TiJiaoFanKuiActivity�л�ô��������
		Intent intent = getIntent();
		string = intent.getStringExtra("feedbackContent");
		if (string != null)
		{
			// ��ȡ��ǰʱ��
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			currentTime = simpleDateFormat.format(System.currentTimeMillis());
			addDB();
			query();
		}
		myAdapter = new MyAdapter();
		mListView.setAdapter(myAdapter);
		query();
		if (mlist != null)
		{
//			mtv_instruction.setVisibility(View.GONE);
			mListView.setVisibility(View.VISIBLE);
		}
		myAdapter.notifyDataSetChanged();
	}

	private void initToolbar()
	{
		// ��getSupportActionBar()����֮ǰ����
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true); // ������ʾ���ؼ�
		// ���ñ���
		actionBar.setTitle("�������");
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
	// getMenuInflater().inflate(R.menu.yi_jian_fan_kui, menu);
	// return true;
	// }

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

	// ����
	private void addDB()
	{
		ContentValues values = new ContentValues();
		values.put(TKConstants.Opinion.OPTIONINFO, string);
		values.put(TKConstants.Opinion.CURRENTTIME, currentTime);
		mdb.insert(TKConstants.Opinion.OPINION_TABLE, null, values);

	}

	// ��ѯ
	private void query()
	{
		mlist.clear();
		Cursor cursor = mdb.query(TKConstants.Opinion.OPINION_TABLE, null, null, null, null, null, null);
		boolean toFirst = cursor.moveToFirst();
		while (toFirst)
		{
			String info = cursor.getString(cursor.getColumnIndex(TKConstants.Opinion.OPTIONINFO));
			String currenttime = cursor.getString(cursor.getColumnIndex(TKConstants.Opinion.CURRENTTIME));
			mlist.add(new Data(info, currenttime));
			toFirst = cursor.moveToNext();
		}
		cursor.close();

	}

	// �Ż�����
	class ViewHolder
	{
		TextView mtvtime;
		TextView mtvMessage;
	}

	class MyAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
			return mlist.size();
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
			// TODO Auto-generated method stub
			return 0;
		}

		@SuppressWarnings("null")
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View layout = null;
			ViewHolder holder = null;
			if (convertView == null)
			{
				holder = new ViewHolder();
				layout = getLayoutInflater().inflate(R.layout.list_item_content_feedback, null);
				holder.mtvtime = (TextView) layout.findViewById(R.id.opinion_tv_time);
				holder.mtvMessage = (TextView) layout.findViewById(R.id.opinion_tv_message);
				layout.setTag(holder);
			} else
			{
				layout = convertView;
				holder = (ViewHolder) layout.getTag();
			}
			Data data = mlist.get(position);
			Log.e("data", data.getCurrenttime());
			Log.e("data", data.getInfo());
			holder.mtvtime.setText(data.getCurrenttime());
			holder.mtvMessage.setText(data.getInfo());
			return layout;
		}

	}

	@Override
	public void onClick(View v)
	{
		int id = v.getId();
		switch (id)
		{
		case R.id.buttonFloat:
			startActivity(new Intent(this, TiJiaoFanKuiActivity.class));
			// �������󻬶���Ч�� ��startActivity(Intent) ��finis()�������
			// ��һ�������ǵ�ǰactivity�˳�ʱ�Ķ������ڶ�����������Ҫ��ת��activity����ʱ����
			overridePendingTransition(R.anim.activity_open_enter, R.anim.activity_close_exit);
			finish(); 
			break;

		default:
			break;
		}
	}

}
