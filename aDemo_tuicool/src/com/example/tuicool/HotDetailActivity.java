package com.example.tuicool;

import java.text.SimpleDateFormat;

import com.android.volley.VolleyError;
import com.example.database_utils.DBUtils;
import com.example.model.Article;
import com.example.model.detail.HotDetail;
import com.example.util.APIClient;
import com.example.util.JsonHelper;
import com.example.util.PreferenceUtils;
import com.example.util.TKConstants;
import com.example.util.ThemeToggleUtils;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.demo.AccessTokenKeeper;
import com.sina.weibo.sdk.demo.Constants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.VolleyListener;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class HotDetailActivity extends SwipeBackActivity
{

	private WebView mWebView;
	private WebSettings settings;
	private HotDetail hotDetail;
	private Article mArticle;
	private MenuItem menuItemFavor;
	private String[] textSize = new String[]
	{ "��С", "ƫС", "�е�", "ƫ��", "���" };

	/** ��װ�� "access_token"��"expires_in"��"refresh_token"�����ṩ�����ǵĹ����� */
	private Oauth2AccessToken mAccessToken;
	/** ע�⣺SsoHandler ���� SDK ֧�� SSO ʱ��Ч */
	private SsoHandler mSsoHandler;
	private AuthInfo mAuthInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// ����ҹ��ģʽ �л�
		ThemeToggleUtils.setThemeToggle(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hot_detail);
		//�����ر�Activity ����ģʽ SwipeBackLayout.EDGE_LEFT ; .EDGE_RIGHT ; .EDGE_BOTTOM ; .EDGE_ALL
		SwipeBackLayout mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT); //�������һ����ر�
		initUI();
		initData();
		initWeiboSDK();
	}

	@Override
	protected void onStart()
	{
		// int fontSize = sp.getInt(TKConstants.fontSizeKey.DETAIL_FONT_SIZE,
		// 2);
		int fontSize = PreferenceUtils.readSize(this); // ��ʼ��   ��ȡ���������С������
		// if (fontSize < 0)
		// {
		// builder.setTitle("�����С(��ǰ��" + textSize[2] + "):");
		// }else
		// {
		// builder.setTitle("�����С(��ǰ��" + textSize[fontSize] + "):");
		// }
		switch (fontSize)
		{
		case 0:
			settings.setTextSize(TextSize.SMALLEST);
			break;
		case 1:
			settings.setTextSize(TextSize.SMALLER);
			break;
		case 2:
			settings.setTextSize(TextSize.NORMAL);
			break;
		case 3:
			settings.setTextSize(TextSize.LARGER);
			break;
		case 4:
			settings.setTextSize(TextSize.LARGEST);
			break;

		default:
			break;
		}
		super.onStart();
	}

	private void initData()
	{
		Intent intent = getIntent();
		mArticle = (Article) intent.getSerializableExtra(TKConstants.intentKey.ARTICLE_FAVOR);
		// �����������ҳ������
		APIClient.getHotDetail(this, new VolleyListener()
		{

			@Override
			public void onResponse(String arg0)
			{
				hotDetail = JsonHelper.fromExposeJson(arg0, HotDetail.class);
				mWebView.loadDataWithBaseURL(null, hotDetail.getArticle().getContent(), "text/html", "utf-8", null);
				// ����ͼƬ����Ӧ�������С 4.4�汾֮ǰ��Ч
				mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			}

			@Override
			public void onErrorResponse(VolleyError arg0)
			{
				Log.e("VolleyListener", "Error" + arg0.getMessage());
			}
		});
	}

	private void initUI()
	{
		initToolbar();
		initWebView();
	}

	private void initWebView()
	{
		mWebView = (WebView) findViewById(R.id.detail_WebView);
		settings = mWebView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setTextSize(TextSize.NORMAL); // ���������С --->�е�
		// ��ֹ���������������
		mWebView.setWebChromeClient(new WebChromeClient()
		{
			public void onProgressChanged(WebView view, int newProgress)
			{
				// TODO ������������htm����
			}
		});
		mWebView.setWebViewClient(new WebViewClient()
		{
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{
				// �����ַ�仯
				Log.e("WebViewClient", "UrlLoading " + url);
				return super.shouldOverrideUrlLoading(view, url);
			}
		});
	}

	private void initToolbar()
	{
		// ��getSupportActionBar()����֮ǰ����
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true); // ������ʾ���ؼ�
		// ���ñ���
		actionBar.setTitle("����");
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
		getMenuInflater().inflate(R.menu.hot_detail, menu);
		return true;
	}

	@Override // ��д����onPrepareOptionsMenu()���ҵ��ղز˵�����
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		// ����˵�xml�����ļ�ʹ��group
		// ͨ���������"��Ӳ˵�"�����
		// if (menuItemLike == null) {
		// menuItemLike = menu.getItem(3);
		// }

		// ����˵�xml�����ļ�ʹ��submenu
		MenuItem item = menu.getItem(2); // ���Actionbar�˵��±�
		SubMenu subMenu = item.getSubMenu();
		menuItemFavor = subMenu.getItem(1); // ͨ������ ��á���Ӳ˵�������
		// �����ѯ����ǿ� ���ղ�
		boolean hasFavor = DBUtils.hasFavor("article_id=?", mArticle);
		if (hasFavor)
		{
			menuItemFavor.setTitle("ȡ���ղ�"); // �˵����ָ�Ϊ"ȡ���ղ�"
		}
		return super.onPrepareOptionsMenu(menu);
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
		case R.id.action_share: // �򵥷���(�ο�SupportV4�ķ�������)
//			ShareCompat.IntentBuilder b = ShareCompat.IntentBuilder.from(this);
//			b.setType("text/plain").setText("I'm sharing!").startChooser();
			
			showShare(); // ����(�ο�mob����)
			return true;
		case R.id.action_comment: // ����
			return true;
		case R.id.action_add_read_wait: // ����
			return true;
		case R.id.action_add_favor: // �ղ�
			// �����ѯ����ǿ� ���ղ�
			boolean hasFavor = DBUtils.hasFavor("article_id=?", mArticle);
			if (hasFavor)
			{
				DBUtils.delete("article_id=?", mArticle); // ȡ���ղ�
				menuItemFavor.setTitle("����ղ�");
				Toast.makeText(this, "ȡ���ղ�", Toast.LENGTH_SHORT).show();
			} else
			{
				DBUtils.insert(mArticle); // �����뵽���ݿ�
				menuItemFavor.setTitle("ȡ���ղ�"); // ���˵����� ��Ϊ��ȡ���ղء�
				Toast.makeText(this, "���ղ�", Toast.LENGTH_SHORT).show(); // ֪ͨ��ʾ
			}
			break;
		case R.id.action_set_size: // �����ֺ�
			// Log.e("onCreateOptionsMenu", "�����ֺ�");
			showSetTextSizeDialog();
			return true;
		case R.id.action_bg_setting: // ��������
			return true;
		case R.id.action_open_next: // �򿪷�ҳ
			return true;
		case R.id.action_look_original: // �鿴ԭ��
			// ��תԭ�ĵ���ʽ��ͼ
			// Uri uri =
			// Uri.parse("http://tech.qq.com/a/20160317/019951.htm?utm_source=tuicool&utm_medium=referral");
			// Intent intent = new Intent(Intent.ACTION_VIEW, uri );

			Intent intent = new Intent(this, ArticleUrlActivity.class);
			intent.putExtra(TKConstants.intentKey.DETAIL_URL, hotDetail.getArticle().getUrl());
			startActivity(intent);
			return true;
		case R.id.action_error_article: // ���¾���
			return true;

		case android.R.id.home: // ���ؼ�����
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private AlertDialog.Builder builder;

	private void showSetTextSizeDialog()
	{
		builder = new AlertDialog.Builder(new ContextThemeWrapper(HotDetailActivity.this, R.style.AlertDialogCustom));

		// builder.setTitle(R.string.select_dialog);
		int readSize = PreferenceUtils.readSize(HotDetailActivity.this);
		if (readSize < 0)
		{
			builder.setTitle("�����С(��ǰ��" + textSize[2] + "):");
		} else
		{
			builder.setTitle("�����С(��ǰ��" + textSize[readSize] + "):");
		}
		builder.setItems(R.array.select_dialog_items, new DialogInterface.OnClickListener()
		{

			public void onClick(DialogInterface dialog, int position)
			{
				switch (position)
				{
				case 0:
					settings.setTextSize(TextSize.SMALLEST);
					break;
				case 1:
					settings.setTextSize(TextSize.SMALLER);
					break;
				case 2:
					settings.setTextSize(TextSize.NORMAL);
					break;
				case 3:
					settings.setTextSize(TextSize.LARGER);
					break;
				case 4:
					settings.setTextSize(TextSize.LARGEST);
					break;

				default:
					break;
				}
				// textShow = "�����С(��ǰ��" + textSize[position] + "):";
				PreferenceUtils.saveSize(HotDetailActivity.this, position);
			}
		});

		builder.show();
	}

	private void showShare() {
		 ShareSDK.initSDK(this);
		 OnekeyShare oks = new OnekeyShare();
		 //�ر�sso��Ȩ
		 oks.disableSSOWhenAuthorize(); 

		// ����ʱNotification��ͼ�������  2.5.9�Ժ�İ汾�����ô˷���
		 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		 // title���⣬ӡ��ʼǡ����䡢��Ϣ��΢�š���������QQ�ռ�ʹ��
		 oks.setTitle(getString(R.string.share));
		 // titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��
		 oks.setTitleUrl("http://sharesdk.cn");
		 // text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
		 oks.setText("���Ƿ����ı�");
		 // imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
		 //oks.setImagePath("/sdcard/test.jpg");//ȷ��SDcard������ڴ���ͼƬ
		 // url����΢�ţ��������Ѻ�����Ȧ����ʹ��
		 oks.setUrl("http://sharesdk.cn");
		 // comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ��
		 oks.setComment("���ǲ��������ı�");
		 // site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ��
		 oks.setSite(getString(R.string.app_name));
		 // siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ��
		 oks.setSiteUrl("http://sharesdk.cn");

		// ��������GUI
		 oks.show(this);
		 }
	
	private void initWeiboSDK() {
		// ����΢��ʵ��
		// mWeiboAuth = new WeiboAuth(this, Constants.APP_KEY,
		// Constants.REDIRECT_URL, Constants.SCOPE);
		// ������Ȩʱ���벻Ҫ���� SCOPE��������ܻ���Ȩ���ɹ�
		mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
		mSsoHandler = new SsoHandler(this, mAuthInfo);
		

		// �� SharedPreferences �ж�ȡ�ϴ��ѱ���� AccessToken ����Ϣ��
		// ��һ��������Ӧ�ã�AccessToken ������
		mAccessToken = AccessTokenKeeper.readAccessToken(this);
		if (mAccessToken.isSessionValid()) {
			updateTokenView(true);
		}
	}

	/**
	 * ΢����֤��Ȩ�ص��ࡣ 1. SSO ��Ȩʱ����Ҫ�� {@link #onActivityResult} �е���
	 * {@link SsoHandler#authorizeCallBack} �� �ûص��Żᱻִ�С� 2. �� SSO
	 * ��Ȩʱ������Ȩ�����󣬸ûص��ͻᱻִ�С� ����Ȩ�ɹ����뱣��� access_token��expires_in��uid ����Ϣ��
	 * SharedPreferences �С�
	 */
	class AuthListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {
			// �� Bundle �н��� Token
			mAccessToken = Oauth2AccessToken.parseAccessToken(values);
			// �������ȡ�û������ �绰������Ϣ
			String phoneNum = mAccessToken.getPhoneNum();
			if (mAccessToken.isSessionValid()) {
				// ��ʾ Token
				updateTokenView(false);

				// ���� Token �� SharedPreferences
				AccessTokenKeeper.writeAccessToken(HotDetailActivity.this, mAccessToken);
				Toast.makeText(HotDetailActivity.this, R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show();
			} else {
				// ���¼�������������յ� Code��
				// 1. ����δ��ƽ̨��ע���Ӧ�ó���İ�����ǩ��ʱ��
				// 2. ����ע���Ӧ�ó��������ǩ������ȷʱ��
				// 3. ������ƽ̨��ע��İ�����ǩ��������ǰ���Ե�Ӧ�õİ�����ǩ����ƥ��ʱ��
				String code = values.getString("code");
				String message = getString(R.string.weibosdk_demo_toast_auth_failed);
				if (!TextUtils.isEmpty(code)) {
					message = message + "\nObtained the code: " + code;
				}
				Toast.makeText(HotDetailActivity.this, message, Toast.LENGTH_LONG).show();
			}
		}

		@Override
		public void onCancel() {
			Toast.makeText(HotDetailActivity.this, R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_LONG).show();
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(HotDetailActivity.this, "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * ��ʾ��ǰ Token ��Ϣ��
	 * 
	 * @param hasExisted
	 *            �����ļ����Ƿ��Ѵ��� token ��Ϣ���ҺϷ�
	 */
	private void updateTokenView(boolean hasExisted) {
		String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
				.format(new java.util.Date(mAccessToken.getExpiresTime()));
		String format = getString(R.string.weibosdk_demo_token_to_string_format_1);
//		mTokenText.setText(String.format(format, mAccessToken.getToken(), date));

		String message = String.format(format, mAccessToken.getToken(), date);
		if (hasExisted) {
			message = getString(R.string.weibosdk_demo_token_has_existed) + "\n" + message;
		}
//		mTokenText.setText(message);
	}


	/**
	 * �� SSO ��Ȩ Activity �˳�ʱ���ú��������á�
	 * 
	 * @see {@link Activity#onActivityResult}
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// SSO ��Ȩ�ص�
		// ��Ҫ������ SSO ��½�� Activity ������д onActivityResults
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}

	}
	
}
