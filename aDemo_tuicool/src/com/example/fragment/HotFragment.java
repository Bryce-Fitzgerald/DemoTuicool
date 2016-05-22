package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.example.database_utils.DBUtils;
import com.example.model.Article;
import com.example.model.TuikuHot;
import com.example.tuicool.HotDetailActivity;
import com.example.tuicool.R;
import com.example.util.APIClient;
import com.example.util.JsonHelper;
import com.example.util.TKConstants;
import com.example.util.ThemeToggleUtils;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.UILUtils;
import com.xinbo.utils.VolleyListener;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class HotFragment extends Fragment implements OnItemClickListener
{
	private List<Article> mData = new ArrayList<Article>();
	private View layout;
	private LayoutInflater mInflater;
	private ListView hotFrament_ListView;
	private HotAdapter mAdapter;
	private int position;
	private Bundle params;

	public HotFragment()
	{

	}

	public HotFragment(int position)
	{
		this.position = position;

	}

	@Override
	public void onStart()
	{
		super.onStart();
		Bundle params = getArguments();
		if (params != null)
		{
			String type = params.getString(TKConstants.bundleKey.FRAGMENT_TYPE);
			if (TKConstants.bundleKey.FRAGMENT_TYPE.equals(type))
			{
				getDataFromDB();
				return;
			}

		}

	}

	@Override
	public void onResume()
	{
		// onResume()�������� �����ղ�ҳ�������     paramsֵ �ղ�ҳ�洫����
		// ���ղ�ҳ�������ղص�������ҳ�棬�˵�ȡ���ղأ��ڷ��ص��ղ�ҳ����ø��º���ղ����� ��ʾ����
		if (params != null)
		{
			getDataFromDB();
		}
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// ����ҹ��ģʽ �л�
		ThemeToggleUtils.setThemeToggle(getContext());
		if (layout == null)
		{
			initUI(inflater, container);
			initData();
		}
		return layout;
	}

	private void initUI(LayoutInflater inflater, ViewGroup container)
	{
		mInflater = inflater;
		layout = inflater.inflate(R.layout.fragment_hot, container, false);
		hotFrament_ListView = (ListView) layout.findViewById(R.id.hotFrament_ListView);
		mAdapter = new HotAdapter();
		hotFrament_ListView.setAdapter(mAdapter);
		hotFrament_ListView.setOnItemClickListener(this);
	}

	@Override // ����ҳListView�е���¼����� --->��ת��������Ŀ����ҳ
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		Intent intent = new Intent(getContext(), HotDetailActivity.class);
		intent.putExtra(TKConstants.intentKey.ARTICLE_FAVOR, mData.get(position));
		startActivity(intent);
		// getActivity().overridePendingTransition(R.anim.activity_open_enter,R.anim.activity_close_exit);
		// // �ұ߽��� �ұ��˳�
		// getActivity().overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
		// ��߽��� ����˳�
		getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

	}

	private void initData()
	{
		params = getArguments();
		if (params != null)
		{
			String type = params.getString(TKConstants.bundleKey.FRAGMENT_TYPE);
			if (TKConstants.bundleKey.FRAGMENT_TYPE.equals(type))
			{
				// getDataFromDB();
				return;
			}

		}
		// HTTPUtils.get(getContext(), TKConstants.Url.HOT, new VolleyListener()
		// APIClient.getHOT(getContext(), new VolleyListener()
		// ��ǰFragment��Ϊ��ҳ���š��Ƽ���ҳ���Fragment
		APIClient.getHomeData(getContext(), position, new VolleyListener()
		{

			@Override
			public void onResponse(String arg0)
			{
				TuikuHot tuikuHot = JsonHelper.fromExposeJson(arg0, TuikuHot.class);
				List<Article> articles = tuikuHot.getArticles();
				mData.addAll(articles); // r�����������
				// data�仯���������֪ͨ����
				mAdapter.notifyDataSetChanged();
			}

			@Override
			public void onErrorResponse(VolleyError arg0)
			{
				Log.e("onErrorResponse", "error = " + arg0.getMessage());
			}
		});
	}

	private void getDataFromDB()
	{
		mData.clear(); // ��ѯ֮ǰ������� ��ֹ�ظ����
		// ��ǰFragment��Ϊ�ҵ��ղ�ҳ��
		List<Article> articles = DBUtils.queryFavor();
		mData.addAll(articles); // r�����������
		// data�仯���������֪ͨ����
		mAdapter.notifyDataSetChanged();
	}

	class HotAdapter extends BaseAdapter
	{

		@Override
		public int getViewTypeCount()
		{
			return 2;
		}

		// ��������в����Ҵܵ�����
		@Override
		public int getItemViewType(int position)
		{
			Article article = mData.get(position);
			if (article.getImg() != "")
			{
				return 0;
			} else
			{
				return 1;
			}
		}

		@Override
		public int getCount()
		{

			return mData.size();
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

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View layout = null;
			ViewHolder holder = null;
			Article article = mData.get(position);
			if (convertView == null)
			{
				holder = new ViewHolder();
				if (article.getImg() != "")
				{
					layout = mInflater.inflate(R.layout.fragment_hot_listitem_img, null);
					holder.img_hot_right = (ImageView) layout.findViewById(R.id.img_hot_right);
				} else
				{
					layout = mInflater.inflate(R.layout.fragment_hot_listitem_no_img, null);
				}
				holder.tv_Title = (TextView) layout.findViewById(R.id.tv_Title);
				holder.tv_From = (TextView) layout.findViewById(R.id.tv_From);
				holder.tv_Time = (TextView) layout.findViewById(R.id.tv_Time);
				layout.setTag(holder);
			} else
			{
				layout = convertView;
				holder = (ViewHolder) layout.getTag();
			}
			holder.tv_Time.setText(article.getTime());
			holder.tv_Title.setText(article.getTitle());
			holder.tv_From.setText(article.getFeedTitle());
			if (article.getImg() != "")
			{
				// ͨ��UIL���߽��� ��������ʾͼƬ
				UILUtils.displayImage(article.getImg(), holder.img_hot_right);
			}
			return layout;
		}

	}

	class ViewHolder
	{
		TextView tv_Title;
		TextView tv_From;
		TextView tv_Time;
		ImageView img_hot_right;
	}

}
