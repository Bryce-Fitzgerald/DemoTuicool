package com.example.tuicool;


import java.io.File;

import com.example.tuicool.R;
import com.example.util.PreferenceUtils;
import com.example.util.ThemeToggleUtils;
import com.xinbo.utils.FileUtils;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.TextSize;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class SettinsActivity extends SwipeBackActivity implements OnClickListener
{

	private TextView mTV_cache_data;
	private File cacheDir;
	private AlertDialog.Builder builder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// ����ҹ��ģʽ  �л�
		ThemeToggleUtils.setThemeToggle(this);
				
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settins);
		//�����ر�Activity ����ģʽ SwipeBackLayout.EDGE_LEFT ; .EDGE_RIGHT ; .EDGE_BOTTOM ; .EDGE_ALL
		SwipeBackLayout mSwipeBackLayout = getSwipeBackLayout();
		mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT); //�������һ����ر�
		initUI();
	}

	private void initUI()
	{
		initToolbar();
		RelativeLayout layout1_clear_img_cache = (RelativeLayout) findViewById(R.id.layout1_clear_img_cache);
		RelativeLayout layout2_share_setting = (RelativeLayout) findViewById(R.id.layout2_share_setting);
		RelativeLayout layout3_settings_more = (RelativeLayout) findViewById(R.id.layout3_settings_more);
		RelativeLayout layout4_feed_back = (RelativeLayout) findViewById(R.id.layout4_feed_back);
		RelativeLayout layout5_update_log = (RelativeLayout) findViewById(R.id.layout5_update_log);
		RelativeLayout layout6_check_for_updates = (RelativeLayout) findViewById(R.id.layout6_check_for_updates);
		RelativeLayout layout7_qiu_rentong = (RelativeLayout) findViewById(R.id.layout7_qiu_rentong);
		RelativeLayout layout8_share_friends = (RelativeLayout) findViewById(R.id.layout8_share_friends);
		RelativeLayout layout9_about_us = (RelativeLayout) findViewById(R.id.layout9_about_us);
		layout1_clear_img_cache.setOnClickListener(this);
		layout2_share_setting.setOnClickListener(this);
		layout3_settings_more.setOnClickListener(this);
		layout4_feed_back.setOnClickListener(this);
		layout5_update_log.setOnClickListener(this);
		layout6_check_for_updates.setOnClickListener(this);
		layout7_qiu_rentong.setOnClickListener(this);
		layout8_share_friends.setOnClickListener(this);
		layout9_about_us.setOnClickListener(this);
		
		mTV_cache_data = (TextView) findViewById(R.id.tv_cache_data);
		cacheDir = getCacheDir();
		mTV_cache_data.setText("(" + FileUtils.size(cacheDir)+")");
	}

	private void initToolbar()
	{
		// ��getSupportActionBar()����֮ǰ����
		Toolbar toolbar = (Toolbar) findViewById(R.id.tuicool_toolbar);
		setSupportActionBar(toolbar);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true); // ������ʾ���ؼ�
		// ���ñ���
		actionBar.setTitle("�������");
	}
	
	@Override
	public void onClick(View v)
	{
		int id = v.getId();
		switch (id)
		{ 
		case R.id.layout1_clear_img_cache: // ���ͼƬ����
			FileUtils.delFilesFromPath(cacheDir);
			Toast.makeText(this, "��������ɹ�", Toast.LENGTH_SHORT).show();
			mTV_cache_data.setText("(0KB)");
			break;
		case R.id.layout2_share_setting:  // ��������
			startActivity(new Intent(this, SetShareActivity.class));
			// ��һ�������ǵ�ǰactivity�˳�ʱ�Ķ������ڶ�����������Ҫ��ת��activity����ʱ����
			overridePendingTransition(R.anim.activity_open_enter,R.anim.activity_close_exit);
			break;
		case R.id.layout3_settings_more: // ��������
			startActivity(new Intent(this, MoreSettingActivity.class));
			overridePendingTransition(R.anim.activity_open_enter,R.anim.activity_close_exit);

			break;
		case R.id.layout4_feed_back: //�������
			startActivity(new Intent(this, YiJianFanKuiActivity.class));
			overridePendingTransition(R.anim.activity_open_enter,R.anim.activity_close_exit);

			break;
		case R.id.layout5_update_log: // ������־
			startActivity(new Intent(this, UpgradeLogActivity.class));
			overridePendingTransition(R.anim.activity_open_enter,R.anim.activity_close_exit);

			break;
		case R.id.layout6_check_for_updates: // ������
			Toast.makeText(this, "�������°汾��", Toast.LENGTH_SHORT).show();
			break;
		case R.id.layout7_qiu_rentong:  // ����ͬ��
			// δ����ʽ��ͼ��ֱ����Dialog��ʽToast
			showNoAppLinkDialog();
			break;
		case R.id.layout8_share_friends:  // �������
			ShareCompat.IntentBuilder b = ShareCompat.IntentBuilder.from(this);
			 b.setType("text/plain").setText("I'm sharing!").startChooser();
			 
			break;
		case R.id.layout9_about_us:  // ��������
			startActivity(new Intent(this, AboutUsActivity.class));
			overridePendingTransition(R.anim.activity_open_enter,R.anim.activity_close_exit);

			break;

		default:
			break;
		}
	}
	
	private void showNoAppLinkDialog() // ����ͬ�� û��app����ʱToast����
	{
		builder = new AlertDialog.Builder(
				new ContextThemeWrapper(SettinsActivity.this, R.style.AlertDialogCustom));
		builder.setItems(R.array.select_dialog_items_qiu_rentong, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int position)
					{
						switch (position)
						{
						case 0:
//							settings.setTextSize(TextSize.SMALLEST);
							break;
						default:
							break;
						}
					}
				});
		
		builder.show();
	}
	
//	@Override  //����Ҫʱȥ�� ���߿ͻ��˻�������ֿؼ�
//	public boolean onCreateOptionsMenu(Menu menu)
//	{
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.settins, menu);
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
			finish(); 
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
}
