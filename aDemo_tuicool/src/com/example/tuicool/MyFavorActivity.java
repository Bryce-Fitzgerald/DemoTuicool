package com.example.tuicool;

import com.example.database_utils.DBUtils;
import com.example.fragment.HotFragment;
import com.example.tuicool.R;
import com.example.util.FragmentUtils;
import com.example.util.TKConstants;
import com.example.util.ThemeToggleUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MyFavorActivity extends AppCompatActivity
{

	private Fragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// ����ҹ��ģʽ �л�
		ThemeToggleUtils.setThemeToggle(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_favor);
		initUI();
		initData();
	}
	
	
	
	
	private void initUI()
	{
		initToolbar();
		
		mFragment = new HotFragment(); // ��������ҳ��������ղ�
		Bundle param = new Bundle();
		param.putString(TKConstants.bundleKey.FRAGMENT_TYPE, TKConstants.bundleKey.FRAGMENT_TYPE);
		mFragment.setArguments(param ); // androidϵͳ��API ���� ����
		
		FragmentUtils.replaceFragment(this, mFragment);// ��̬�滻Fragment
	}




	private void initData()
	{
		
	}
	private void initToolbar()
	{
		// ��getSupportActionBar()����֮ǰ����
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true); // ������ʾ���ؼ�
		// ���ñ���
		actionBar.setTitle("�ҵ��ղ�");
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



//	@Override
//	public boolean onCreateOptionsMenu(Menu menu)
//	{
//		getMenuInflater().inflate(R.menu.my_favor, menu);
//		return true;
//	}

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
//			Intent intent = new Intent(this, MainActivity.class);
//			startActivity(intent );
			overridePendingTransition(0, 0);
			finish(); 
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed()
	{
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent );
		overridePendingTransition(0, 0);
		finish(); 
		super.onBackPressed();
	}
}
