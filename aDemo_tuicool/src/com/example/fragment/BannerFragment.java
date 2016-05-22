package com.example.fragment;

import com.example.tuicool.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class BannerFragment extends Fragment
{

	private static final int DILAY_MILLIS = 2500; // �ֲ�˯��
	private static final int MAX_LENGTH = 400000; // �ֲ�����
	private ViewPager mPager;
	private LayoutInflater mInflater;
	private View layout;
	protected boolean isDragging;
	private Runnable action;

	public BannerFragment()
	{
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		initUI(inflater, container);
		return layout;
	}

	private void initUI(LayoutInflater inflater, ViewGroup container)
	{
		// �ж�layoutΪ����Ϊ��һ�δ���������Ҫ����
		if (layout == null)
		{
			// ֻ��Ҫִ��һ�εĴ���
			mInflater = inflater;
			layout = inflater.inflate(R.layout.fragment_banner, container, false);
			initListView();
		}
		// ÿ�� fragment������ʱ����ִ��  // �Զ��ֲ�
		autoScroll(); 
	}

	private void initListView()
	{
		ListView mListView = (ListView) layout.findViewById(R.id.listView1);
		// ��ʼ��Banner
		View headerView = initBanner();
		mListView.addHeaderView(headerView, null, true);
		mListView.setAdapter(new MyAdapter());
	}

	private View initBanner()
	{
		View headerView = mInflater.inflate(R.layout.banner_headerview, null);
		mPager = (ViewPager) headerView.findViewById(R.id.pager);
		// ��ʼ��ʱ����Pager��ʾ�м�ҳ��
		mPager.setCurrentItem(MAX_LENGTH / 2);
		// Ƭ������Ƕ��Ƭ��Ҫ��getChildFragmentManager()����
		FragmentManager fm = getChildFragmentManager();
		// mPager.setOffscreenPageLimit(2); // �����������߻���ĸ���
		mPager.setAdapter(new BannerAdapter(fm));
		// �ֶ��Զ��ĳ�ͻ����
		mPager.addOnPageChangeListener(new BannerPAageListener());
		
		return headerView;
	}

	private void autoScroll()
	{
		Log.e("autoScroll()", "��ʼ����");
		action = new Runnable()
		{
			// ��ʱÿ2.5���Զ�������һҳ
			public void run()
			{
				// ��ʱ����һֱ����
				mPager.postDelayed(this, DILAY_MILLIS); // �����Զ��ֲ�
				if (!isDragging) // û����ק���飬�Զ���һҳ
				{
					int currentItem = mPager.getCurrentItem();
					int nextItem = currentItem + 1;
					mPager.setCurrentItem(nextItem);
				}
			}
		};
		mPager.postDelayed(action, DILAY_MILLIS);
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		// ��λ�����ҳPager ��Banner�Զ������ٶȱ��
		mPager.removeCallbacks(action);
		Log.e("onDestroyView()", "ֹͣ����");
	}
	private final class BannerPAageListener implements OnPageChangeListener
	{
		@Override
		public void onPageSelected(int arg0)
		{
			// ��ҳ��ѡ��
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2)
		{
			// ��ҳ�����
		}

		@Override
		public void onPageScrollStateChanged(int state)
		{
			switch (state)
			{
			case ViewPager.SCROLL_STATE_DRAGGING:
				Log.e("onPageScrollStateChanged", "�û���ק");
				isDragging = true;
				break;
			case ViewPager.SCROLL_STATE_SETTLING:
				Log.e("onPageScrollStateChanged", "��λ");
				isDragging = false;
				break;
			case ViewPager.SCROLL_STATE_IDLE:
				Log.e("onPageScrollStateChanged", "����");
				isDragging = false;
				break;

			default:
				break;
			}
		}
	}

	// Banner(ViewPager)�����Ż�
	// ��Banner��Item�϶�ʱ������ʹ��FragmentStatePagerAdapter
	private final class BannerAdapter extends FragmentStatePagerAdapter
	{
		private BannerAdapter(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public int getCount()
		{
			// ����ѭ�� �޸�Pager��������getCount()����400000
			return MAX_LENGTH;
		}

		@Override
		public Fragment getItem(int position)
		{
			return new HotFragment();
		}
	}

	class MyAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			return 10;
		}

		@Override
		public Object getItem(int position)
		{
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
			View layout = mInflater.inflate(R.layout.list_item, null);
			TextView textView = (TextView) layout.findViewById(R.id.textView1);
			textView.setText("position: " + position);
			return layout;
		}

	}

}
