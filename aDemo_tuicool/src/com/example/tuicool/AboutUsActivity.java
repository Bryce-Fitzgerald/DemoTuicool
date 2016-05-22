package com.example.tuicool;

import com.example.tuicool.R;
import com.example.util.ThemeToggleUtils;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutUsActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// ����ҹ��ģʽ  �л�
		ThemeToggleUtils.setThemeToggle(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		initUI();
	}
	private void initUI()
	{
		initToolbar();
		TextView tv_AboutUs_text_info = (TextView) findViewById(R.id.tv_aboutUs_text_info);
		tv_AboutUs_text_info.setText("�����ƿ�רע��IT�������Ϣ�ھ�;ۺ��Ƽ�������ͨ�����������Ϣ��ȡ�����������⡣ ����Զ�أ��ƿ������ܳ�ΪIT֪ʶ��������������һվʽ�Ķ���ѧϰ��������ƽ̨��Ŀǰ���ƵĲ�Ʒ�У�\n1) ���Ի����¾ۺ��Ƽ����ṩ�����ݺ��ǿƼ�����������ơ�Ӫ���ȷ��档����û��С�࣬�ۺϺ��Ƽ���������ȫ�����ڷַ��Ӳ�ͣЪ���Ƽ�ϵͳ��\n2) IT����������ϻ�ۺ��Ƽ���Ŀǰ��¼�ĻӦ���ǹ�����ḻ�ģ����Ƶ�����кܳ���·Ҫ�ߡ�\n3) IT��Ĺ����Ρ���ݽ�����Ŀ��Ƶ�����ݵľۺ��Ƽ���\n�����ƿ�����2011�꣬����������2013��4����վ��ʽ���ߡ� ������վ�����ǻ��ṩ���ʵİ�׿��iOS�ͻ��˷�������Ķ���\n������ʵ�������ڿ������ƿ���������滮�ģ������źܳ��ܳ��ĵ�·Ҫ�ߡ����ǻ��Ǹ�С�Ŷӣ�������д�ҵ���鲢�Ͽ����ǵ�Ը������ӭ�������ǡ�");
		
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
	
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu)
//	{
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.about_us, menu);
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
