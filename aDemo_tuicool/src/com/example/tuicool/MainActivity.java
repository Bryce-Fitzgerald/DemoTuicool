/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.tuicool;

import com.example.fragment.HomeFragment;
import com.example.fragment.SiteFragment;
import com.example.fragment.TopicFragment;
import com.example.tuicool.R;
import com.example.util.FragmentUtils;
import com.example.util.TKConstants;
import com.example.util.ThemeToggleUtils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnCloseListener;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//	4. �໬UI��ܴ
//�ο�SupportV7Demo��ActionBarWithDrawerLayout.java
//	1) �޸������Ͳ�������
//	2) �ع����룬��ȡ���������ڴ����Ķ�
public class MainActivity extends AppCompatActivity implements OnClickListener
{
	private DrawerLayout mDrawerLayout;
	private ListView mDrawer;
	private String[] LEFT_MENU;
	private ActionBarHelper mHelper;
	private ActionBarDrawerToggle mDrawerToggle;
	private TextView mTvSearchText;
	private SearchView mSearchView;
	private HomeFragment mFragment;
	static final int FRAGMENT_HOME = 0;
	static final int FRAGMENT_ZHANDIAN = 1;
	static final int FRAGMENT_ZHUTI = 2;
	static final int FRAGMENT_ZHOUKAN = 3;
	static final int FRAGMENT_MY = 4;

	private boolean isNight;
	private TextView drawer_tv_title;
	private ImageView drawer_img_icon;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// ����ҹ��ģʽ  �л�
		ThemeToggleUtils.setThemeToggle(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUI(); //��ʼ��UI
	}

	private void initUI()
	{
		mTvSearchText = (TextView) findViewById(R.id.tv_search_text);
		initResource();
		initDrawer(); // ��ʼ��
		initActionbar(); // ��ʼ��������
		initDawerToggle(); // �໬�л���ť

		addFragment();
		
	}
	private void initResource() {
		LEFT_MENU =getResources().getStringArray(R.array.left_menu);
//		getResDrawableArray(R.array.left_menuimgs);
	}
	private void addFragment()
	{
		mFragment = new HomeFragment();
		FragmentUtils.replaceFragment(MainActivity.this, mFragment);
	}


	private void initDawerToggle()
	{
		// ActionBarDrawerToggle provides convenient helpers for tying together
		// the
		// prescribed interactions between a top-level sliding drawer and the
		// action bar.
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
	}

	private void initActionbar()
	{
		mHelper = createActionBarHelper();
		mHelper.init();
	}

	private void initDrawer()
	{
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawer = (ListView) findViewById(R.id.start_drawer);
		mDrawerLayout.setDrawerListener(new DemoDrawerListener());
		// Ϊ�໬�˵����HeaderView��FooterView Ҫ�ڵ���listview��setAdapter()֮ǰ
		View drawer_HeaderView = getLayoutInflater().inflate(R.layout.drawer_headerview, null);
		mDrawer.addHeaderView(drawer_HeaderView);
		LinearLayout mDrawerHeader = (LinearLayout) drawer_HeaderView.findViewById(R.id.left_login_bar);
		mDrawerHeader.setOnClickListener(this);
		
		View drawer_FooterView = getLayoutInflater().inflate(R.layout.drawer_footerview, null);
		mDrawer.addFooterView(drawer_FooterView,null, false);
		
		footV_layout_nightModel = (RelativeLayout) drawer_FooterView.findViewById(R.id.footV_layout_nightModel);
		footV_layout_nightModel.setOnClickListener(this); //ҹ��ģʽ��������¼�
		tv_drawer_footV_nightModel = (TextView) drawer_FooterView.findViewById(R.id.tv_drawer_footV_nightModel);
		drawer_FooterView.findViewById(R.id.footV_layout_offline_download).setOnClickListener(this);
		drawer_FooterView.findViewById(R.id.footV_layout_settings).setOnClickListener(this);
		
		mDrawerLayout.setDrawerTitle(GravityCompat.START, getString(R.string.drawer_title));

		mDrawer.setAdapter(new DrawerAdapter());
		// ����listview��ѡ��״̬
		mDrawer.setTextFilterEnabled(true);
		mDrawer.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		// Start with first item activated.
		// Make the newly clicked item the currently selected one.
		mDrawer.setItemChecked(0, true);

		mDrawer.setOnItemClickListener(new DrawerItemClickListener());
	}

	class DrawerAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
//			return Shakespeare.TITLES_LIGHT.length;
			return LEFT_MENU.length;
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

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View layout = getLayoutInflater().inflate(R.layout.drawer_list_item, null);
			drawer_tv_title = (TextView) layout.findViewById(R.id.drawer_tv_title);
			drawer_img_icon = (ImageView) layout.findViewById(R.id.drawer_img_icon);
			if (position == 0)
			{
				// �໬�˵���һ��������ɫ ��ɫ
				// #ff0aa284
				drawer_tv_title.setTextColor(getResources().getColor(R.color.actionbar_bg));
			}
			drawer_tv_title.setText(LEFT_MENU[position]);
			drawer_img_icon.setImageResource(Shakespeare.IMAGE_TITLES_LIGHT[position]);
//			// ����ҹ�䡢�ռ�ģʽ�л��Ŀؼ�����
//			SharedPreferences sp = getSharedPreferences(TKConstants.SP_THEME_TOGGLE, 0);
//			boolean isNight = sp.getBoolean("isNight", false);
//			if (isNight)
//			{
//				tv_drawer_footV_nightModel.setText("�ռ�ģʽ");
//			}else
//			{
//				tv_drawer_footV_nightModel.setText("ҹ��ģʽ");
//			}
			return layout;
		}

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);

		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	/**
	 * This list item click listener implements very simple view switching by
	 * changing the primary content text. The drawer is closed when a selection
	 * is made.
	 */
	private class DrawerItemClickListener implements ListView.OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{
			// �����ΪListView���HeaderView Ҫ����һ��position
			position = position - mDrawer.getHeaderViewsCount();
			if (position < 0)
			{
				return;
			}
			// ���Խ������
			if (LEFT_MENU.length > 5 && LEFT_MENU.length < 0) {
				return;
			}
			mHelper.setToolbarTitle(LEFT_MENU[position]);
			mHelper.setTitle(Shakespeare.TITLES_LIGHT[position]);

			mDrawerLayout.closeDrawer(mDrawer);
			Fragment mFragment = null;
			switch (position)
			{
			case FRAGMENT_HOME:
				mFragment = new HomeFragment();
				break;
			case FRAGMENT_MY:
				Intent intent = new Intent(MainActivity.this, MyTuiCoolActivity.class);
				startActivity(intent);
				break;
				case FRAGMENT_ZHANDIAN:
				mFragment = new SiteFragment();
				break;
				case FRAGMENT_ZHUTI:
				mFragment = new TopicFragment();
				break;
				case FRAGMENT_ZHOUKAN:
					mFragment = new TopicFragment();
					break;
				
//			case FRAGMENT_MYFAVOR: // �ҵ��ղ�
//				//mFragment = new FindFragment();
//				Intent intent = new Intent(MainActivity.this, MyFavorActivity.class);
//				startActivity(intent);
//				break;

//			case FRAGMENT_LIXIANYUEDU:
//				mFragment = new YuanZhuoFragment();
//				break;
//			case FRAGMENT_YEJIAN_MODEL: // ҹ��ģʽ
//				//mFragment = new PersonalFragment();
//				setNightModel();
//				break;
//			case FRAGMENT_SETTING:
//				mFragment = new PersonalFragment();
//				break;
			default:
				break;
			}
			// ��̬�滻Fragment (Ҫ����õ����Ƭ�� ����װ�ڹ��������棬�������)
			FragmentUtils.replaceFragment(MainActivity.this, mFragment); 
		}

	}

	/**
	 * A drawer listener can be used to respond to drawer events such as
	 * becoming fully opened or closed. You should always prefer to perform
	 * expensive operations such as drastic relayout when no animation is
	 * currently in progress, either before or after the drawer animates.
	 *
	 * When using ActionBarDrawerToggle, all DrawerLayout listener methods
	 * should be forwarded if the ActionBarDrawerToggle is not used as the
	 * DrawerLayout listener directly.
	 */
	private class DemoDrawerListener implements DrawerLayout.DrawerListener
	{
		@Override
		public void onDrawerOpened(View drawerView)
		{
			mDrawerToggle.onDrawerOpened(drawerView);
			mHelper.onDrawerOpened();
		}

		@Override
		public void onDrawerClosed(View drawerView)
		{
			mDrawerToggle.onDrawerClosed(drawerView);
			mHelper.onDrawerClosed();
		}

		@Override
		public void onDrawerSlide(View drawerView, float slideOffset)
		{
			mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
		}

		@Override
		public void onDrawerStateChanged(int newState)
		{
			mDrawerToggle.onDrawerStateChanged(newState);
		}
	}

	/**
	 * Create a compatible helper that will manipulate the action bar if
	 * available.
	 */
	private ActionBarHelper createActionBarHelper()
	{
		return new ActionBarHelper();
	}

	/**
	 * Action bar helper for use on ICS and newer devices.
	 */
	private class ActionBarHelper
	{
		private final ActionBar mActionBar;
		private CharSequence mDrawerTitle;
		private CharSequence mTitle;

		public void setToolbarTitle(String title)
		{
			mActionBar.setTitle(title);
		}
		ActionBarHelper()
		{
			Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
			setSupportActionBar(toolbar);
			mActionBar = getSupportActionBar();
		}

		public void init()
		{
			mActionBar.setDisplayHomeAsUpEnabled(true);
			mActionBar.setDisplayShowHomeEnabled(false);
			mActionBar.setTitle("��ҳ");
			mTitle = mDrawerTitle = getTitle();
		}

		/**
		 * When the drawer is closed we restore the action bar state reflecting
		 * the specific contents in view.
		 */
		public void onDrawerClosed()
		{
			mActionBar.setTitle(mTitle);
		}

		/**
		 * When the drawer is open we set the action bar to a generic title. The
		 * action bar should only contain data relevant at the top level of the
		 * nav hierarchy represented by the drawer, as the rest of your content
		 * will be dimmed down and non-interactive.
		 */
		public void onDrawerOpened()
		{
			mActionBar.setTitle(mDrawerTitle);
		}

		public void setTitle(CharSequence title)
		{
			mTitle = title;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		mSearchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
		mSearchView.setOnQueryTextListener(mOnQueryTextListener);
		// mSearchView.setSubmitButtonEnabled(true);// �Ƿ���ʾȷ��������ť
		// mSearchView.setIconifiedByDefault(false);//
		// ����չ����ͼ�����ʽ,����ֻ������,һ��ͼ������������,һ������������
		// mSearchView.setIconified(false);// ����
		// mSearchView.clearFocus();// �������
		mSearchView.setOnCloseListener(new OnCloseListener()
		{
			public boolean onClose()
			{
				mDrawerToggle.setDrawerIndicatorEnabled(true);
				getSupportFragmentManager().beginTransaction().show(mFragment).commit();
				return false;
			}
		});
		// ����SearchView�ĵ���¼�
		mSearchView.setOnSearchClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// ����DrawerToggle��ͼ��Ϊback��ͷ
				mDrawerToggle.setDrawerIndicatorEnabled(false);
				// SearchView ��ʾʱ������Fragment
				getSupportFragmentManager().beginTransaction().hide(mFragment).commit();
				Toast.makeText(MainActivity.this, "onClick", Toast.LENGTH_SHORT).show();
				// isSearchViewShow = true;
			}
		});
		return true;
	}

	private final SearchView.OnQueryTextListener mOnQueryTextListener = new SearchView.OnQueryTextListener()
	{
		public boolean onQueryTextChange(String newText)
		{
			newText = TextUtils.isEmpty(newText) ? "" : "Query so far: " + newText;
			mTvSearchText.setText(newText);
			return true;
		}

		public boolean onQueryTextSubmit(String query)
		{
			Toast.makeText(MainActivity.this, "Searching for: " + query + "...", Toast.LENGTH_SHORT).show();
			return true;
		}
	};

	@Override
	public void onBackPressed()
	{
		// ��һ�ַ��� ��Ա��������״̬
		// if (isSearchViewShow)
		// {
		// mSearchView.setIconified(true);
		//// isSearchViewShow = false;
		// return;
		// }
		
		/*���Bug
		 *  ����໬�˵����ڴ�״̬����رղ˵� ,ͬʱ���ر�Activity
		 */ 
		if (mDrawerLayout.isDrawerOpen(mDrawer))
		{
			mDrawerLayout.closeDrawer(mDrawer);
			return;
		}
/*		boolean hasHidden = hideSearchView();
		if (hasHidden)  // ���������  
		{
			return;
		}*/
		if (hideSearchView())
		{
			return;
		}
		super.onBackPressed();
	}

	private boolean hideSearchView()
	{
		// ���Search��ʾ����ر�SearchView�� ��Ҫ�ر�Activity
		if (!mSearchView.isIconified())
		{
			mDrawerToggle.setDrawerIndicatorEnabled(true); // �޸�DrawerToggle������
															// ��ʾ����
			// SearchView����ʱ��Fragment��ʾ
			getSupportFragmentManager().beginTransaction().show(mFragment).commit();
			mSearchView.setIconified(true);
			return true;
		}else
		{
			return false; // SearchView����
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		/*
		 * The action bar home/up action should open or close the drawer.
		 * mDrawerToggle will take care of this.
		 */
		if (mSearchView.isIconified()) // ��SearchViewӰ��ʱ  ��ִ��mDrawerToggle����¼�
		{
			if (mDrawerToggle.onOptionsItemSelected(item))
			{
				return true;
			}
			
		}
		switch (item.getItemId())
		{
		case R.id.action_only_chinese:
			Toast.makeText(this, "�����Ķ�", Toast.LENGTH_LONG).show();
			return true;
		case android.R.id.home:
			// Toast.makeText(this, "back home", Toast.LENGTH_LONG).show();
			// ��Back���ָ�mDrawerToggle
			// onBackPressed();
			/* ���Bug
			 *  ��onBackPressed()��������Ϊ����hideSearchView()
			 */ 
			hideSearchView();
			return true;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	long preTime = 0;
	private RelativeLayout footV_layout_nightModel;
	private TextView tv_drawer_footV_nightModel;
	
	// ˫���˳�
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && 
				event.getAction() == KeyEvent.ACTION_DOWN)
		{
			if (System.currentTimeMillis() - preTime < 2000) // �������ڣ��˳�
			{
				finish();
			}else
			Toast.makeText(this, "�ٰ�һ���˳�Ӧ��", Toast.LENGTH_SHORT).show();
			// System.exit(0);
			preTime = System.currentTimeMillis(); // ����һ�ε����ʱ�䱣�� 
		}
		return true;// ����ϵͳ����
	}

	@Override
	public void onClick(View v)
	{  // TODO
		int id = v.getId();
		switch (id)
		{
		case R.id.footV_layout_settings: //�������
			startActivity(new Intent(this, SettinsActivity.class));
			// ��һ�������ǵ�ǰactivity�˳�ʱ�Ķ������ڶ�����������Ҫ��ת��activity����ʱ����
			overridePendingTransition(R.anim.activity_open_enter,R.anim.activity_close_exit);
			break;
		case R.id.footV_layout_offline_download: // ��������
			startActivity(new Intent(this, OffLineReadActivity.class));
			overridePendingTransition(R.anim.activity_open_enter,R.anim.activity_close_exit);
			break;
		case R.id.footV_layout_nightModel: // ҹ���ռ�ģʽ�л�
			setNightModel();
			break;
		case R.id.left_login_bar: // ��ת��½ҳ��
			startActivity(new Intent(this, LoginActivity.class));
			break;

		default:
			break;
		}
	}
	private void setNightModel()
	{
		SharedPreferences sp = getSharedPreferences(TKConstants.SP_THEME_TOGGLE, 0);
		isNight = sp.getBoolean("isNight", false);
		sp.edit().putBoolean("isNight", !isNight).commit();
		Intent intent = new Intent(MainActivity.this, MainActivity.class);
		startActivity(intent );
		finish();
		overridePendingTransition(0,0); // �رն���Ч��
	}
}
