package com.example.tuicool;

import com.example.tuicool.R;
import com.example.util.ThemeToggleUtils;
import com.sevenheaven.iosswitch.ShSwitchView;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class SetShareActivity extends AppCompatActivity
{
	private ShSwitchView switch_view_setShare_weibo;
	private ShSwitchView switch_view_setShare_weixin;
	private ShSwitchView switch_view_setShare_qq_zone;
	private ShSwitchView switch_view_setShare_douban;
	private ShSwitchView switch_view_setShare_evernote;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// ����ҹ��ģʽ  �л�
				ThemeToggleUtils.setThemeToggle(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_share);
		initUI();
	}
	private void initUI()
	{
		initToolbar();
		initSwitchView();
		
	}
	private void initToolbar()
	{
		// ��getSupportActionBar()����֮ǰ����
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true); // ������ʾ���ؼ�
		// ���ñ���
		actionBar.setTitle("��������");
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
	private void initSwitchView()
	{
		switch_view_setShare_weibo = (ShSwitchView) findViewById(R.id.switch_view_setShare_weibo);
		switch_view_setShare_weixin = (ShSwitchView) findViewById(R.id.switch_view_setShare_weixin);
		switch_view_setShare_qq_zone = (ShSwitchView) findViewById(R.id.switch_view_setShare_qq_zone);
		switch_view_setShare_douban = (ShSwitchView) findViewById(R.id.switch_view_setShare_douban);
		switch_view_setShare_evernote = (ShSwitchView) findViewById(R.id.switch_view_setShare_evernote);
		
		switch_view_setShare_weibo.setOn(false, true); // animated���������Ƿ��Զ�����ʽ�л�switch״̬
//		switch_view_onlyWIFI.setOn(true); //ͨ��setOn(boolean on)��������switch״̬
		switch_view_setShare_weixin.setOn(false);
		switch_view_setShare_qq_zone.setOn(false);
		switch_view_setShare_douban.setOn(false);
		switch_view_setShare_evernote.setOn(false);
		
//		ͨ��isOn()��ȡswitch״̬ TODU -- using isOn() method to get switch state
//		ͨ��setOnSwitchStateChangeListener��������״̬�ı�ص�
//		����xml�ļ���ͨ��tintColor��ǩ�ı���ɫ
//		});
	}
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu)
//	{
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.set_share, menu);
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
			overridePendingTransition(0, 0);
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
