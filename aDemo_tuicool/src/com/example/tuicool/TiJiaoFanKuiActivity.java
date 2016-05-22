package com.example.tuicool;

import com.example.util.ThemeToggleUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class TiJiaoFanKuiActivity extends AppCompatActivity
{

	private EditText mEdSubmitFeedback;
	private String feedbackContent;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// ����ҹ��ģʽ �л�
		ThemeToggleUtils.setThemeToggle(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ti_jiao_fan_kui);
		mEdSubmitFeedback = (EditText) findViewById(R.id.ed_tijiao_fankui);
		overridePendingTransition(R.anim.activity_open_enter, R.anim.activity_close_exit);
		initUI();
	}

	private void initUI()
	{
		initToolbar();

	}

	private void initToolbar()
	{
		// ��getSupportActionBar()����֮ǰ����
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true); // ������ʾ���ؼ�
		// ���ñ���
		actionBar.setTitle("�ύ����");
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ti_jiao_fan_kui, menu);
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
		case R.id.action_tijiao_fankui:
			feedbackContent = mEdSubmitFeedback.getText().toString().trim();
			// Log.e("btnfankui",trim);
			if (feedbackContent != null)
			{

				Intent intent = new Intent(TiJiaoFanKuiActivity.this, YiJianFanKuiActivity.class);
				intent.putExtra("feedbackContent", feedbackContent);
				startActivityForResult(intent, 0);
				finish();
			} else
			{
				Toast.makeText(this, "����������", Toast.LENGTH_LONG).show();
			}
			break;
		case android.R.id.home: // ���ؼ�����
			overridePendingTransition(0, 0);
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
