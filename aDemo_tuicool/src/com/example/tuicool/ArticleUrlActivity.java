package com.example.tuicool;

import com.activeandroid.util.Log;
import com.android.volley.VolleyError;
import com.example.tuicool.R;
import com.example.util.APIClient;
import com.example.util.TKConstants;
import com.example.util.ThemeToggleUtils;
import com.xinbo.utils.VolleyListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class ArticleUrlActivity extends AppCompatActivity
{

	private WebView article_url_webview;
	private ProgressBar article_url_bar;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// ����ҹ��ģʽ �л�
		ThemeToggleUtils.setThemeToggle(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_url);
		initUI();
		initData();
	}
	
	private void initUI()
	{
		article_url_bar = (ProgressBar) findViewById(R.id.article_url_bar);
		initToolbar();
		initWebView();		
	}

	public void initWebView()
	{
		article_url_webview = (WebView) findViewById(R.id.article_url_webview);
		WebSettings settings = article_url_webview.getSettings();
		settings.setJavaScriptCanOpenWindowsAutomatically(true); // ֧��JavaScript
		// settings.setTextSize(TextSize.LARGER); // ö�ٷ�ʽ ����webView�����С

		// 2) ��ֹ���������������
		article_url_webview.setWebChromeClient(new WebChromeClient()
		{

			@Override
			public void onProgressChanged(WebView view, int newProgress)
			{
				// ��� ���������htm����
				article_url_bar.setProgress(newProgress);
				super.onProgressChanged(view, newProgress);
			}
		});
		article_url_webview.setWebViewClient(new WebViewClient()
		{
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{
				// �����ַ�仯
				// Log.e("WebViewClient", "UrlLoading" + url);
				return super.shouldOverrideUrlLoading(view, url);
			}
		});
	}

	private void initData()
	{
		// �����������ҳ������
		APIClient.getHotDetail(this, new VolleyListener()
		{

			@Override
			public void onResponse(String arg0)
			{
				// JSON����
				//HotDetail hotDetail = GsonUtils.parseJSON(arg0, HotDetail.class);
				//article_url_webview.loadDataWithBaseURL(null, hotDetail.getArticle().getUrl(), "text/html", "utf-8",null);
				Intent intent = getIntent();
				String detailUrl = intent.getStringExtra(TKConstants.intentKey.DETAIL_URL);
				article_url_webview.loadUrl(detailUrl);
						
			}

			@Override
			public void onErrorResponse(VolleyError arg0)
			{
				Log.e("VolleyListener", "Error" + arg0.getMessage());
			}
		});
	}

//	// goBack()��ʾ����webView����һҳ��
//	public boolean onKeyDown(int keyCoder, KeyEvent event)
//	{
//		if (article_url_webview.canGoBack() && keyCoder == KeyEvent.KEYCODE_BACK)
//		{
//			article_url_webview.goBack();
//			return true;
//		}
//		return false;
//	}
	private void initToolbar()
	{
		// ��getSupportActionBar()����֮ǰ����
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true); // ������ʾ���ؼ�
		// ���ñ���
		actionBar.setTitle("");
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.article_url, menu);
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
		case R.id.action_settings:
			return true;
		case android.R.id.home: // ���ؼ�����
			finish(); 
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
