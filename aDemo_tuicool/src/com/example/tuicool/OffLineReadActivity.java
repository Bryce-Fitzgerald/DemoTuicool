package com.example.tuicool;

import com.example.tuicool.R;
import com.example.util.ThemeToggleUtils;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class OffLineReadActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// ����ҹ��ģʽ  �л�
				ThemeToggleUtils.setThemeToggle(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_off_line_read);
		initUI();
	}

	private void initUI()
	{
		initToolbar();
		TextView tv_offline_read_intro = (TextView) findViewById(R.id.tv_offline_read_intro);
		tv_offline_read_intro.setText("ʹ��˵�������"+"�������������б�ҳ��ѡ������Ҫ���ص�Ƶ����������ɺ����½��뱾ҳ���Կ��������ص�Ƶ���������������ʱ����Ƶ�������Ķ���\n���ڻ�������º�ͼƬ��Ӧ�û�������ʱɾ�����ڵ����ݣ�Ĭ����1����ǰ�Ѷ���Ƶ������");
		
	}

	private void initToolbar()
	{
		// ��getSupportActionBar()����֮ǰ����
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true); // ������ʾ���ؼ�
		// ���ñ���
		actionBar.setTitle("�����Ķ�");
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.off_line_read, menu);
		return true;
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
		case R.id.action_offline_download:
			Toast.makeText(this, "�������£����Ժ�����",Toast.LENGTH_SHORT ).show();
			return true;
		case android.R.id.home: // ���ؼ�����
			overridePendingTransition(0, 0);
			finish(); 
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
